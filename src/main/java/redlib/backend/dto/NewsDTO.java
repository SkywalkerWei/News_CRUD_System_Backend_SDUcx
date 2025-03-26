package redlib.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewsDTO {
    private Long id;
    
    @NotBlank(message = "新闻标题不能为空")
    private String title;
    
    @NotBlank(message = "新闻内容不能为空")
    private String content;
    
    @NotNull(message = "栏目ID不能为空")
    private Long categoryId;
}