package project.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Assignment {

    @Id
    @Column(name = "assignment_dt")
    @NotNull
    private LocalDate assignmentDt;

    @Column(name = "assignment_title")
    @NotNull
    private String assignmentTitle;

    @Column(name = "assignment_content")
    @NotNull
    private String assignmentContent;

    @Column(name = "class_category")
    @NotNull
    private Integer classCategory;
}
