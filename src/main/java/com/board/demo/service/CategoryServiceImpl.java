package com.board.demo.service;

import com.board.demo.repository.CategoryRepository;
import com.board.demo.vo.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getList() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean addCategory(String categoryName) {
        Category category = Category.builder()
                .categoryName(categoryName)
                .build();
        log.info(category.getCategoryId()+"");
        category = categoryRepository.save(category);
        log.info(category.getCategoryId()+"");
        return true;
    }
}
