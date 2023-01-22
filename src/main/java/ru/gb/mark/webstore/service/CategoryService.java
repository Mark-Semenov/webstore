package ru.gb.mark.webstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.mark.webstore.entity.Category;
import ru.gb.mark.webstore.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }

    public Category getById(Long id) {
        return categoryRepository.findCategoryById(id);
    }

    public List<String> getNamesOfCategories() {
        return getCategories().stream().map(Category::getName).collect(Collectors.toList());
    }

    @Transactional
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }


}
