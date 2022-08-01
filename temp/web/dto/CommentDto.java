package project.web.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CommentDto {

    private Long commentNo;

    private String commentContent;

    private String username;

    private Long userId;

    private LocalDate assignment;

    private Long postNo;

    private Long topCommentNo;


}
