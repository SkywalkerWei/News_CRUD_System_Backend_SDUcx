package redlib.backend.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class News {
    private Long id;
    private String title;
    private String content;
    private Long categoryId;
    private String createBy;
    private String updateBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}