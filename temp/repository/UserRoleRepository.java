package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.UserRole;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findUserRolesByUser_id(Long id);
}