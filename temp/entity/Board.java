package project.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNo;

    @Column(name = "post_title")
    @NotNull
    private String postTitle;

    @Column(name = "post_content")
    @NotNull
    private String postContent;

    @Column(name = "post_create_dt")
    @NotNull
    private LocalDateTime postCreateDt;

    @Column(name = "post_modify_dt")
    private LocalDateTime postModifyDt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

}
