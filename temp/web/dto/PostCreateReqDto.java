package project.web.dto;

import lombok.Data;

@Data
public class PostCreateReqDto {
    private String postTitle;
    private String postContent;
}
