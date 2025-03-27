package redlib.backend.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import redlib.backend.dao.NewsCategoryMapper;
import redlib.backend.dao.NewsMapper;
import redlib.backend.dto.NewsDTO;
import redlib.backend.dto.NewsQueryDTO;
import redlib.backend.exception.BusinessException;
import redlib.backend.model.News;
import redlib.backend.model.NewsCategory;
import redlib.backend.model.Page;
import redlib.backend.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {
    @Resource
    private NewsMapper newsMapper;
    
    @Resource
    private NewsCategoryMapper newsCategoryMapper;
    
    @Override
    @Transactional
    public News createNews(NewsDTO newsDTO, String operator) {
        // 验证栏目是否存在
        NewsCategory category = newsCategoryMapper.selectByPrimaryKey(newsDTO.getCategoryId());
        if (category == null) {
            throw new BusinessException("新闻栏目不存在");
        }
        
        News news = new News();
        BeanUtils.copyProperties(newsDTO, news);
        news.setCreatedBy(operator);
        news.setUpdatedBy(operator);
        
        newsMapper.insert(news);
        return news;
    }
    
    @Override
    @Transactional
    public void updateNews(NewsDTO newsDTO, String operator) {
        // 验证新闻是否存在
        News existingNews = newsMapper.selectByPrimaryKey(newsDTO.getId());
        if (existingNews == null) {
            throw new BusinessException("新闻不存在");
        }
        
        // 验证栏目是否存在
        NewsCategory category = newsCategoryMapper.selectByPrimaryKey(newsDTO.getCategoryId());
        if (category == null) {
            throw new BusinessException("新闻栏目不存在");
        }
        
        News news = new News();
        BeanUtils.copyProperties(newsDTO, news);
        news.setUpdatedBy(operator);
        
        newsMapper.updateByPrimaryKey(news);
    }
    
    @Override
    @Transactional
    public void deleteNews(Long id) {
        News news = newsMapper.selectByPrimaryKey(id);
        if (news == null) {
            throw new BusinessException("新闻不存在");
        }
        
        newsMapper.deleteByPrimaryKey(id);
    }
    
    @Override
    public News getNews(Long id) {
        return newsMapper.selectByPrimaryKey(id);
    }

    @Override
    public Page<News> queryNews(NewsQueryDTO queryDTO) {
        // 处理分页参数
        if (queryDTO.getCurrent() <= 0) {
            queryDTO.setCurrent(1);
        }
        if (queryDTO.getPageSize() <= 0) {
            queryDTO.setPageSize(10);
        }
        
        // 设置分页参数
        int offset = (queryDTO.getCurrent() - 1) * queryDTO.getPageSize();
        queryDTO.setPageNum(offset);
        queryDTO.setOffset(offset);
        queryDTO.setLimit(queryDTO.getPageSize());
        
        // 查询数据
        List<News> list = newsMapper.selectByCondition(queryDTO);
        int total = newsMapper.countByCondition(queryDTO);
        
        return new Page<>(queryDTO.getCurrent(), queryDTO.getPageSize(), total, list);
    }
}