package project.repository;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomUserRepository {

    String findRefreshTokenByUsername(String username);
}
