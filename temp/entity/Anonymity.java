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
public class Anonymity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mail_id")
    private Long id;

    @Column(name = "mail_create_dt")
    @NotNull
    private LocalDateTime mailCreateDt;

    @Column(name = "send_yn")
    @NotNull
    private String sendYn;

    @Column(name = "mail_content")
    @NotNull
    private String mailContent;
}
