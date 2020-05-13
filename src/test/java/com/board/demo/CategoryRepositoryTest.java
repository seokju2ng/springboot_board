package com.board.demo;

import com.board.demo.repository.CategoryRepository;
import com.board.demo.vo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Optional;

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

    @Test
    public void read(){
        Optional<Category> category = categoryRepository.findById(1L);
        category.ifPresent(selectCategory ->{
            System.out.println("category: "+selectCategory);
        });
    }

    @Test
    @Transactional
    public void update(){
        Optional<Category> category = categoryRepository.findById(1L);
        category.ifPresent(selectCategory -> {
            selectCategory.setCategoryName("바바");
            Category newCategory = categoryRepository.save(selectCategory);
            System.out.println("category: "+newCategory);
        });
    }
}
