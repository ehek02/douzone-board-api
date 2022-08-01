package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
