package ru.gb.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.store.entities.Category;
import ru.gb.store.repositories.CategoryRepository;

import javax.transaction.Transactional;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public List<Category> getCategoryByName(String name) {
        return categoryRepository.findCategoryByName(name);
    }


}
