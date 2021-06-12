package ru.gb.store.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.store.dto.UserDTO;
import ru.gb.store.entities.Category;
import ru.gb.store.entities.Img;
import ru.gb.store.entities.Product;
import ru.gb.store.entities.Role;
import ru.gb.store.service.CategoryService;
import ru.gb.store.service.ProductService;
import ru.gb.store.service.RoleService;
import ru.gb.store.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@Log4j2
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final RoleService roleService;
    private final BCryptPasswordEncoder encoder;


    @GetMapping
    public String welcome() {
        return "admin";
    }

    @GetMapping("/new_user")
    public String createUser(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("role", new Role());
        model.addAttribute("roles", roleService.getNamesOfRoles());
        return "create_user";
    }

    @PostMapping("/new_user")
    public String addNewUser(UserDTO user, Role role) {
        user.setRole(role.getName());
        user.setPassword(encoder.encode(user.getPassword()));
        userService.addUser(user);
        return "create_user";
    }

    @GetMapping("/new_product")
    public String createProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("category", new Category());
        model.addAttribute("image", new Img());
        model.addAttribute("categories", categoryService.getNamesOfCategories());
        return "new_product";
    }

    @PostMapping("/new_product")
    public String addNewProduct(MultipartFile file, Product product, Category category, Img image) throws IOException {
        List<Category> c = categoryService.getCategoryByName(category.getName());
//        categoryService.getCategoryByName(category.getName()).iterator().next().setProducts(List.of(product));
        image.setName(file.getOriginalFilename());
        image.setContent(file.getBytes());
        product.setImage(image.getName());
        Files.write(Path.of("src\\main\\resources\\static\\images\\".concat(image.getName())).toAbsolutePath(), image.getBytes());
        product.setCategory(c);
        productService.saveProduct(product);
        product = null;
        return "new_product";
    }


}
