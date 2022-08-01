package project.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true)
    @NotNull
    private String username;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @Column(name = "class")
    @NotNull
    private Integer userClass;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||
            Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}