package redlib.backend.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NewsQueryDTO extends PageQueryDTO {
    private String title;
    private Long categoryId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}