package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
