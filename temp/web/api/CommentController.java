package project.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.service.comments.CommentService;
import project.web.dto.CommentDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

	private final CommentService commentService;

	@GetMapping("/getAllByPost/{postNo}")
	public List<CommentDto> getAllCommentsByPostNo(@PathVariable Long postNo){
		return commentService.findAll(postNo);
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public void newComment(@RequestBody CommentDto commentDto){
		commentService.createComment(commentDto);
	}

	@PutMapping("/modify")
	@ResponseStatus(HttpStatus.OK)
	public void editComment(@RequestBody CommentDto commentDto){ commentService.modifyComment(commentDto); }

	@DeleteMapping("/remove/{commentId}")
	@ResponseStatus(HttpStatus.OK)
	public void eraseComment(@PathVariable Long commentId, HttpServletRequest request, HttpServletResponse response){
		commentService.removeComment(commentId, request, response); };
	
}
