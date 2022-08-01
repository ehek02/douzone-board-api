package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Long>{
    List<Comment> findAllByBoardPostNo(Long postNo);
}
