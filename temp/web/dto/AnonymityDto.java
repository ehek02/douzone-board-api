package project.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class AnonymityDto {
    private Long id;
    private LocalDateTime mailCreateDt;
    private String sendYn;
    private String mailContent;
}
