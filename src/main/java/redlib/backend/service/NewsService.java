package redlib.backend.service;

import redlib.backend.dto.NewsDTO;
import redlib.backend.dto.NewsQueryDTO;
import redlib.backend.model.News;
import redlib.backend.model.Page;

public interface NewsService {
    News createNews(NewsDTO newsDTO, String operator);
    
    void updateNews(NewsDTO newsDTO, String operator);
    
    void deleteNews(Long id);
    
    News getNews(Long id);
    
    Page<News> queryNews(NewsQueryDTO queryDTO);
}