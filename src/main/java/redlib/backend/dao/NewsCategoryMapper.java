package redlib.backend.dao;

import redlib.backend.model.NewsCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface NewsCategoryMapper {
    NewsCategory selectByPrimaryKey(Long id);
    
    NewsCategory selectByName(String name);
    
    List<NewsCategory> selectByNameLike(String name);
    
    int insert(NewsCategory category);
    
    int updateByPrimaryKey(NewsCategory category);
    
    int deleteByPrimaryKey(Long id);
    
    int countNewsByCategory(Long categoryId);
}