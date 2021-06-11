package ru.gb.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.store.entities.Category;
import ru.gb.store.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    public List<String> getNamesOfCategories() {
        return getCategories().stream().map(Category::getName).collect(Collectors.toList());
    }

    @Transactional
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }


}
