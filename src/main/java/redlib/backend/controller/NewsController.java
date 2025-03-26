package redlib.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import redlib.backend.dto.NewsDTO;
import redlib.backend.dto.NewsQueryDTO;
import redlib.backend.model.News;
import redlib.backend.model.Page;
import redlib.backend.service.NewsService;
import redlib.backend.model.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@Tag(name = "新闻管理", description = "新闻的增删改查接口")
@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Resource
    private NewsService newsService;
    
    @Operation(summary = "创建新闻")
    @PostMapping
    public Result<News> createNews(@Validated @RequestBody NewsDTO newsDTO) {
        if (newsDTO == null) {
            return Result.error("新闻数据不能为空");
        }
        // TODO: 从SecurityContext中获取当前用户
        String operator = "admin";
        return Result.success(newsService.createNews(newsDTO, operator));
    }
    
    @Operation(summary = "更新新闻")
    @PutMapping("/{id}")
    public Result<Void> updateNews(@Parameter(description = "新闻ID") @PathVariable Long id, 
                                 @Validated @RequestBody NewsDTO newsDTO) {
        if (id == null) {
            return Result.error("新闻ID不能为空");
        }
        if (newsDTO == null) {
            return Result.error("新闻数据不能为空");
        }
        newsDTO.setId(id);
        // TODO: 从SecurityContext中获取当前用户
        String operator = "admin";
        newsService.updateNews(newsDTO, operator);
        return Result.success();
    }
    
    @Operation(summary = "删除新闻")
    @DeleteMapping("/{id}")
    public Result<Void> deleteNews(@Parameter(description = "新闻ID") @PathVariable Long id) {
        if (id == null) {
            return Result.error("新闻ID不能为空");
        }
        newsService.deleteNews(id);
        return Result.success();
    }
    
    @Operation(summary = "获取新闻详情")
    @GetMapping("/{id}")
    public Result<News> getNews(@Parameter(description = "新闻ID") @PathVariable Long id) {
        if (id == null) {
            return Result.error("新闻ID不能为空");
        }
        return Result.success(newsService.getNews(id));
    }
    
    @Operation(summary = "查询新闻列表", description = "支持按标题、栏目、创建时间等条件分页查询")
    @GetMapping
    public Result<Page<News>> queryNews(@Validated NewsQueryDTO queryDTO) {
        if (queryDTO == null) {
            return Result.error("查询参数不能为空");
        }
        return Result.success(newsService.queryNews(queryDTO));
    }
}