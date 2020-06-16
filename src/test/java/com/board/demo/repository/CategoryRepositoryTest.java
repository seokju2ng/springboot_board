package com.board.demo.repository;

import com.board.demo.vo.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    public void create() {
        String categoryName = "여행";
        Category category = Category.builder()
                .categoryName(categoryName)
                .build();
        category = categoryRepository.save(category);
        log.info(category.toString());
    }

    @Test
    public void readAll() {
        List<Category> category = categoryRepository.findAll();
        category.forEach(x -> log.info(x.toString()));
    }

    @Test
    @Transactional
    public void update() {
        Optional<Category> category = categoryRepository.findById(1L);
        category.ifPresent(selectCategory -> {
            selectCategory.setCategoryName("바바");
            Category newCategory = categoryRepository.save(selectCategory);
            System.out.println("category: " + newCategory);
        });
    }
}
