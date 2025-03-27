package redlib.backend.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import redlib.backend.dto.NewsQueryDTO;
import redlib.backend.model.News;

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
                            @Param("updatedBy") String updatedBy);
}