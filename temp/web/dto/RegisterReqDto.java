package project.web.dto;

import lombok.Data;
import project.entity.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegisterReqDto {
    // https://bamdule.tistory.com/35 (@Valid 어노테이션 종류)
    @Size(min = 2, max = 20)
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank(message = "이름은 비어있으면 안됩니다.")
    private String name;
    @NotNull
    private Integer userClass;


    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .name(name)
                .userClass(userClass)
                .name(this.name)
                .build();
    }
}
