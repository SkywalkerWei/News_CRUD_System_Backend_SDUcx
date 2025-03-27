package redlib.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;
import redlib.backend.dao.NewsCategoryMapper;
import redlib.backend.dao.NewsMapper;
import redlib.backend.exception.BusinessException;
import redlib.backend.model.NewsCategory;
import redlib.backend.service.NewsCategoryService;

@Service
public class NewsCategoryServiceImpl implements NewsCategoryService {
    @Resource
    private NewsCategoryMapper newsCategoryMapper;
    
    @Resource
    private NewsMapper newsMapper;
    
    @Override
    @Transactional
    public NewsCategory createCategory(String name) {
        if (!StringUtils.hasText(name)) {
            throw new BusinessException("栏目名称不能为空");
        }
        
        // 检查栏目名称是否已存在
        NewsCategory existingCategory = newsCategoryMapper.selectByName(name);
        if (existingCategory != null) {
            throw new BusinessException("栏目名称已存在");
        }
        
        NewsCategory category = new NewsCategory();
        category.setName(name);
        
        newsCategoryMapper.insert(category);
        return category;
    }
    
    @Override
    @Transactional
    public void updateCategory(Long id, String name) {
        if (!StringUtils.hasText(name)) {
            throw new BusinessException("栏目名称不能为空");
        }
        
        // 检查栏目是否存在
        NewsCategory category = newsCategoryMapper.selectByPrimaryKey(id);
        if (category == null) {
            throw new BusinessException("栏目不存在");
        }
        
        // 检查新名称是否与其他栏目重复
        NewsCategory existingCategory = newsCategoryMapper.selectByName(name);
        if (existingCategory != null && !existingCategory.getId().equals(id)) {
            throw new BusinessException("栏目名称已存在");
        }
        
        category.setName(name);
        newsCategoryMapper.updateByPrimaryKey(category);
    }
    
    @Override
    @Transactional
    public void deleteCategory(Long id, Long transferCategoryId, String operator) {
        // 检查栏目是否存在
        NewsCategory category = newsCategoryMapper.selectByPrimaryKey(id);
        if (category == null) {
            throw new BusinessException("栏目不存在");
        }
        
        // 如果指定了转移目标栏目，则将当前栏目下的新闻转移到目标栏目
        if (transferCategoryId != null) {
            if (transferCategoryId.equals(id)) {
                throw new BusinessException("转移目标不能是当前栏目");
            }
            
            // 检查目标栏目是否存在
            NewsCategory targetCategory = newsCategoryMapper.selectByPrimaryKey(transferCategoryId);
            if (targetCategory == null) {
                throw new BusinessException("目标栏目不存在");
            }
            
            // 转移新闻
            newsMapper.updateCategoryIdBatch(id, transferCategoryId, operator);
        } else {
            // 检查栏目下是否有新闻
            int newsCount = newsCategoryMapper.countNewsByCategory(id);
            if (newsCount > 0) {
                throw new BusinessException("栏目下存在新闻，请先删除新闻或指定转移目标栏目");
            }
        }
        
        // 删除栏目
        newsCategoryMapper.deleteByPrimaryKey(id);
    }
    
    @Override
    public NewsCategory getCategory(Long id) {
        return newsCategoryMapper.selectByPrimaryKey(id);
    }
    
    @Override
    public List<NewsCategory> searchCategories(String name) {
        if (!StringUtils.hasText(name)) {
            // 当name为空时，返回所有栏目
            return newsCategoryMapper.selectByNameLike("");
        }
        return newsCategoryMapper.selectByNameLike(name);
    }
    
    @Override
    public boolean existsByName(String name) {
        if (!StringUtils.hasText(name)) {
            return false;
        }
        return newsCategoryMapper.selectByName(name) != null;
    }
}