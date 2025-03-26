package redlib.backend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import redlib.backend.dto.query.PageQueryDTO;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
public class NewsQueryDTO extends PageQueryDTO {
    private String title;
    private Long categoryId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}