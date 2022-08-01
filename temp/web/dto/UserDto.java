package project.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserDto {
    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    @Size(min = 1, max=2)
    private Integer userClass;
}
