package redlib.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import redlib.backend.model.NewsCategory;
import redlib.backend.service.NewsCategoryService;
import redlib.backend.model.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Tag(name = "新闻栏目管理", description = "新闻栏目的增删改查接口")
@RestController
@RequestMapping("/api/news/categories")
public class NewsCategoryController {
    @Resource
    private NewsCategoryService newsCategoryService;
    
    @Operation(summary = "创建新闻栏目")
    @PostMapping
    public Result<NewsCategory> createCategory(@Parameter(description = "栏目名称") @RequestParam String name) {
        return Result.success(newsCategoryService.createCategory(name));
    }
    
    @Operation(summary = "更新新闻栏目")
    @PutMapping("/{id}")
    public Result<Void> updateCategory(@Parameter(description = "栏目ID") @PathVariable Long id, 
                                     @Parameter(description = "新栏目名称") @RequestParam String name) {
        newsCategoryService.updateCategory(id, name);
        return Result.success();
    }
    
    @Operation(summary = "删除新闻栏目", description = "删除栏目时可以指定将该栏目下的新闻转移到其他栏目")
    @DeleteMapping("/{id}")
    public Result<Void> deleteCategory(
            @Parameter(description = "要删除的栏目ID") @PathVariable Long id, 
            @Parameter(description = "新闻转移的目标栏目ID，不指定则无法删除有新闻的栏目") 
            @RequestParam(required = false) Long transferCategoryId) {
        // TODO: 从SecurityContext中获取当前用户
        String operator = "admin";
        newsCategoryService.deleteCategory(id, transferCategoryId, operator);
        return Result.success();
    }
    
    @Operation(summary = "获取栏目详情")
    @GetMapping("/{id}")
    public Result<NewsCategory> getCategory(@Parameter(description = "栏目ID") @PathVariable Long id) {
        return Result.success(newsCategoryService.getCategory(id));
    }
    
    @Operation(summary = "搜索栏目", description = "支持按名称模糊查询")
    @GetMapping("/search")
    public Result<List<NewsCategory>> searchCategories(
            @Parameter(description = "栏目名称，支持模糊查询") 
            @RequestParam(required = false) String name) {
        return Result.success(newsCategoryService.searchCategories(name));
    }
    
    @Operation(summary = "检查栏目名称是否已存在")
    @GetMapping("/exists")
    public Result<Boolean> categoryExists(@Parameter(description = "栏目名称") @RequestParam String name) {
        return Result.success(newsCategoryService.existsByName(name));
    }
}