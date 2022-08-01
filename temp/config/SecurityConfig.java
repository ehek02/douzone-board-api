package project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.config.jwt.JwtAuthenticationFilter;
import project.config.jwt.JwtAuthorizationFilter;
import project.handler.CustomLogoutSuccessHandler;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // TODO 얘는 UserServiceImpl 를 참조한다.... ㄹㅇ 중요 대박사건임.

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 정적인 페이지에 대하여 접근권한 부여
        return (web -> web
                .ignoring()
                .antMatchers("/h2-console/**", "/favicon.ico"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Basic AuthenticationManager and UserDetailService Create
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);

        AuthenticationManager authenticationManager
                = authenticationManagerBuilder.build();

        // 해당 개체를 사용하여 URL 을 변경할 수 있으며, 사용자를 지정할 수 있는 몇가지 다른 항목도 있습니다.
        // 그래서 여기로 가서 필터를 설정하고 URL 을 처리할 수 있습니다.
        /**
         *  원래는 UsernamePasswordAuthenticationFilter 에서 /login 이 기본으로 구현되어 있지만,
         *  다른 주소로 해주고 싶으면 이런방식을 사용할 수 있습니다.
         */
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl("/api/login");

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS) // 세션 설정 끄기

                .and()
                .cors().configurationSource(corsConfigurationSource()) // cors 설정을 custom 으로 할때(밑에 Bean 있음.)

                .and()
                .formLogin().disable()
                .httpBasic().disable()

                // 권한 설정
                .authorizeHttpRequests()
                .antMatchers("/login/**", "/api/token/refresh/**", "/user", "/api/users/register", "/api/token/save-refresh", "/api/token/refresh").permitAll()
                .antMatchers(GET, "/api/user/**").hasAnyAuthority("ROLE_USER")
                .antMatchers(POST, "/api/user/save/**").hasAnyAuthority("ROLE_ADMIN") // 사용자를 저장하기 위해서는 관리자 권한이 필요.
                .anyRequest().authenticated() // 모든경로는 인증을 받아야 한다.

                // 필터랑 password encoder 등 이것저것 추가?
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                .authenticationManager(authenticationManager)
                .addFilter(authenticationFilter) // 인증필터
                .addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class); // 권한필터 // 모든 요청을 받으려면 다른 필터들 보다 먼저 처리되어야 한다.

        return http.build();
    }

    // CORS 허용 적용
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
//        configuration.addAllowedOriginPattern("*"); // url
        configuration.addAllowedHeader("*");        // header
        configuration.addAllowedMethod("*");        // method
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }
}