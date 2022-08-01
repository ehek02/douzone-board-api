package project.service.comments;


import project.web.dto.CommentDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CommentService {
	List<CommentDto> findAll(Long postNo);

    void createComment(CommentDto commentDto);


    void modifyComment(CommentDto commentDto);

    void removeComment(Long commentId, HttpServletRequest request, HttpServletResponse response);
}
