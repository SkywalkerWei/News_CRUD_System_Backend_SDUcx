package redlib.backend.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class News {
    private Long id;
    private String title;
    private String content;
    private Long categoryId;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}