package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Anonymity;

public interface AnonymityRepository extends JpaRepository<Anonymity, Long> {
}
