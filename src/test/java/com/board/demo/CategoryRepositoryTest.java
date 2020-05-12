package com.board.demo;

import com.board.demo.repository.CategoryRepository;
import com.board.demo.vo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create(){
        Category category = new Category();
        category.setCategoryName("잡담");
        Category newCategory = categoryRepository.save(category);
        System.out.println(newCategory);
    }
}
