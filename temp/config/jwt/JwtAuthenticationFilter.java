package project.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.web.dto.LoginReqDto;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

// 로그인이 성공할 때마다 access token 과 refresh token 을 발급해줄 filter
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    /**
     * 인증 시도 호출되는 메서드
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // request에 있는 username과 password를 파싱해서 자바 Object로 받기
        ObjectMapper om = new ObjectMapper();
        LoginReqDto dto = null;

        try {
            dto = om.readValue(request.getInputStream(), LoginReqDto.class);
        } catch (Exception e) {
//            log.error("로그인 데이터를 읽어오던 중 오류가 발생했습니다. message => {}", e.getMessage());
//            e.printStackTrace();
        }

        if (Objects.isNull(dto)) {
//            log.error("전달받은 login 객체가 없습니다.");
        }

        String username = Objects.requireNonNull(dto).getUsername();
        String password = dto.getPassword();

//        log.info("Username is : {}", username);
//        log.info("password is : {}", password);

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        // authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
        // loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
        // UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
        // UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
        // Authentication 객체를 만들어서 필터체인으로 리턴해준다.

        // Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
        // Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
        // 결론은 인증 프로바이더에게 알려줄 필요가 없음.
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * 인증 성공시(로그인이 되었을 때 호출 될 메서드),
     * 사용자에게 access token 과,
     * 성공적으로 로그인 한 후 refresh token 을 제공하는 메서드
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal(); // 현재 인증된(로그인한) 사용자의 정보를 가져온다
        Algorithm algorithm = Algorithm.HMAC256(JwtProperties.SECRET.getBytes());

        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        log.info("{} 님이 로그인 하였습니다.", user.getUsername());

        /* token header 로 던지기 */
//        response.setHeader("access_token", access_token);
//        response.setHeader("refresh_token", refresh_token);

        /* token body 로 던지기 */
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);

        request.setAttribute("tokens", tokens);

        request.getRequestDispatcher("/api/token/save-refresh").forward(request, response);

    }
}
