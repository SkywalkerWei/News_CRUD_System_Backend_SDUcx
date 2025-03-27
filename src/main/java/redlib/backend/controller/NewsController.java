package redlib.backend.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import redlib.backend.dto.NewsDTO;
import redlib.backend.dto.NewsQueryDTO;
import redlib.backend.model.News;
import redlib.backend.model.Page;
import redlib.backend.model.Result;
import redlib.backend.service.NewsService;

@Tag(name = "新闻管理", description = "新闻的增删改查接口")
@RestController
@RequestMapping("/api/news-manager")
public class NewsController {
    @Resource
    private NewsService newsService;
    
    @Operation(summary = "创建新闻")
    @PostMapping
    public Result<News> createNews(@Validated @RequestBody NewsDTO newsDTO) {
        String operator = "admin";
        return Result.success(newsService.createNews(newsDTO, operator));
    }
    
    @Operation(summary = "更新新闻")
    @PutMapping("/{id}")
    public Result<Void> updateNews(@Parameter(description = "新闻ID") @PathVariable Long id, 
                                 @Validated @RequestBody NewsDTO newsDTO) {
        newsDTO.setId(id);
        String operator = "admin";
        newsService.updateNews(newsDTO, operator);
        return Result.success();
    }
    
    @Operation(summary = "删除新闻")
    @DeleteMapping("/{id}")
    public Result<Void> deleteNews(@Parameter(description = "新闻ID") @PathVariable Long id) {
        newsService.deleteNews(id);
        return Result.success();
    }
    
    @Operation(summary = "获取新闻详情")
    @GetMapping("/{id}")
    public Result<News> getNews(@Parameter(description = "新闻ID") @PathVariable Long id) {
        return Result.success(newsService.getNews(id));
    }
    
    @Operation(summary = "查询新闻列表", description = "支持按标题、栏目、创建时间等条件分页查询")
    @GetMapping
    public Result<Page<News>> queryNews(@Validated NewsQueryDTO queryDTO) {
        return Result.success(newsService.queryNews(queryDTO));
    }
}