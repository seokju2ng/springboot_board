package com.board.demo.service;

import com.board.demo.repository.CategoryRepository;
import com.board.demo.vo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getList() {
        return categoryRepository.findAll();
    }
}
