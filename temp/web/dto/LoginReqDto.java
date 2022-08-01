package project.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginReqDto {
    // https://bamdule.tistory.com/35 (@Valid 어노테이션 종류)
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
