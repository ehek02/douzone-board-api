package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.User;

public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository{
    User findByUsername(String username);
}
