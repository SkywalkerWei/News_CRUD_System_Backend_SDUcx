package redlib.backend.service;

import redlib.backend.model.NewsCategory;
import java.util.List;

public interface NewsCategoryService {
    NewsCategory createCategory(String name);
    
    void updateCategory(Long id, String name);
    
    void deleteCategory(Long id, Long transferCategoryId, String operator);
    
    NewsCategory getCategory(Long id);
    
    List<NewsCategory> searchCategories(String name);
    
    boolean existsByName(String name);
}