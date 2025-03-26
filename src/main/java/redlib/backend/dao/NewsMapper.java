package redlib.backend.dao;

import redlib.backend.model.News;
import redlib.backend.dto.NewsQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface NewsMapper {
    News selectByPrimaryKey(Long id);
    
    int insert(News news);
    
    int updateByPrimaryKey(News news);
    
    int deleteByPrimaryKey(Long id);
    
    List<News> selectByCondition(NewsQueryDTO queryDTO);
    
    int countByCondition(NewsQueryDTO queryDTO);
    
    int updateCategoryIdBatch(@Param("oldCategoryId") Long oldCategoryId,
                            @Param("newCategoryId") Long newCategoryId,
                            @Param("updateBy") String updateBy);
}