package com.board.demo.service;

import com.board.demo.vo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getList();

    boolean addCategory(String category);
}
