package project.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class AssignmentCommentDto {

	private Long commentId;

	private String boardCategory;

	private String commentContent;

	private LocalDateTime commentCreateDt;

	private LocalDateTime commentModifyDt;

	private String username;

	private LocalDate assignmentDt;

	private Long parentCommentId;
}
