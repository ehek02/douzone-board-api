package project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.entity.Role;
import project.entity.User;
import project.entity.UserRole;
import project.repository.RoleRepository;
import project.repository.UserRepository;
import project.repository.UserRoleRepository;
import project.utils.SendResponseUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public User create(User user, HttpServletResponse response) throws IllegalAccessException, IOException {
        log.info("계정을 생성합니다. username => {}", user.getUsername());

        // TODO : 찍어보자
        // 이미 가입된 유저라면
        if (Objects.nonNull(userRepository.findByUsername(user.getUsername()))) {
            log.error("이미 가입된 유저입니다. username -> " + user.getUsername());

            // error 전송
            SendResponseUtils.sendBody(CONFLICT.value(), "이미 가입된 유저입니다.", response);

            // 회원가입 시 아이디 중복은 흔한 일인데 이걸 굳이 exception 을 던져서 프로그램을 뻗다이 시켜야하는가?
            return new User();
//            throw new KeyAlreadyExistsException("이미 가입된 유저입니다. username -> " + user.getUsername());
        }

        Role role = roleRepository.findById(1L).orElseThrow(IllegalAccessException::new);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role).build();

        userRoleRepository.save(userRole);

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("사용자가 로그인을 시도합니다. username => {}", username);

        // find user
        User user = userRepository.findByUsername(username);

        if (user == null) {
            log.error("user 정보가 db에 존재하지 않습니다.");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("user 정보를 찾았습니다 username => {}", username);
        }

        // Fixme : user 는 여러개의 권한을 가질 수 있어야 한다.
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // userRole table 에서 userId로 UserRole 들을 들고옴.
        List<UserRole> userRoles = userRoleRepository.findUserRolesByUser_id(user.getId());
        List<Role> roles = new ArrayList<>();

        // 가져온 userRole 들의 roleId 들로 Role 들을 만듬
        for (UserRole userRole : userRoles) {
            roles.add(roleRepository.findById(userRole.getRole().getId()).orElseThrow(IllegalArgumentException::new));
        }

        // 만든 Role 들을 유저 디테일스 오쏘라에 추가
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


    public User saveUser(User user) {
        log.info("Saving new user {} to the database", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Role saveRole(Role role) {
        log.info("Saving new role {} to the database", role.getName());
        return roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        log.info("userRole을 추가합니다 role => {} to user => {}", roleName, username);
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);

        userRoleRepository.save(new UserRole(user.getId(), user, role));
    }

}
