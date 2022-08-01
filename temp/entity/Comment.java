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
public class Comment {
    @Id
    @Column(name = "comment_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentNo;

    @Column(name = "comment_content")
    @NotNull
    private String commentContent;

    @Column(name = "comment_create_dt")
    @NotNull
    private LocalDateTime commentCreateDt;

    @Column(name = "comment_modify_dt")
    private LocalDateTime commentModifyDt;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "assignment_dt")
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name="postNo")
    private Board board;

    @NotNull
    @Column(name = "top_comment_no")
    private Long topCommentNo;


}
