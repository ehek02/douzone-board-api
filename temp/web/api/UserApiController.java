package project.web.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import project.entity.User;
import project.service.UserService;
import project.service.UserSessionService;
import project.web.dto.LogoutDto;
import project.web.dto.RegisterReqDto;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserApiController {
    private final UserService userService;
    private final UserSessionService userSessionService;

    // 회원가입
    @PostMapping("api/users/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterReqDto dto, BindingResult bindingResult,
                                         HttpServletResponse response)
            throws IllegalAccessException, IOException {
        log.info("회원가입을 시도합니다. username => " + dto.getUsername());

        if (bindingResult.hasErrors()) {
            throw new KeyAlreadyExistsException("회원가입에 실패 했습니다. username => " + dto.getUsername());
        }

        User user = dto.toEntity(); // dto -> entity
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/register").toUriString());
        return ResponseEntity.created(uri).body(userService.create(user, response));
    }

    @GetMapping("/api/token/refresh")
    public void refreshTokenCheck(HttpServletRequest request, HttpServletResponse response){
        userSessionService.checkRefresh(request, response);
    }

    @PostMapping("/api/token/save-refresh")
    @ResponseStatus(HttpStatus.OK)
    public void inputRefresh(HttpServletRequest request,HttpServletResponse response) throws IOException {
        userSessionService.insertRefreshToken(request,response);
    }

    @PostMapping("api/token/remove-refresh")
    public void eraseRefreshToken(@RequestBody LogoutDto logoutDto){ userSessionService.logout(logoutDto.getUsername()); }

}