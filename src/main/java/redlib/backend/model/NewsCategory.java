package redlib.backend.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NewsCategory {
    private Long id;
    private String name;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}