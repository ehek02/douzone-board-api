package project.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class PostDto {
    private Long postNo;
    private String postTitle;
    private String postContent;
    private LocalDateTime postCreateDt;
    private LocalDateTime postModifyDt;
    private UserDto userDto;
}
