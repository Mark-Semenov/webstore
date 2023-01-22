package ru.gb.mark.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.mark.webstore.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByName(String name);

    Category findCategoryById(Long id);

}
