package ru.gb.store.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.store.dto.UserDTO;
import ru.gb.store.entities.Category;
import ru.gb.store.entities.Product;
import ru.gb.store.entities.Role;
import ru.gb.store.service.*;

import java.io.IOException;
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
    private final FileService fileService;


    @ModelAttribute
    public void attributes(Model model) {
        model.addAttribute("blocks", productService.getAdminBlocks());
        model.addAttribute("roles", roleService.getNamesOfRoles());
        model.addAttribute("categories", categoryService.getNamesOfCategories());
    }

    @GetMapping
    public String welcome() {
        return "admin";
    }

    @GetMapping("/new_user")
    public String createUser(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("role", new Role());
        return "admin";
    }

    @PostMapping("/new_user")
    public String addNewUser(UserDTO user, Role role) {
        user.setRole(role.getName());
        user.setPassword(encoder.encode(user.getPassword()));
        userService.registerNewUserAccount(user);
        return "admin";
    }

    @GetMapping("/new_product")
    public String createProduct(Model model) {
        model.addAttribute("product", new Product());
        return "admin";
    }

    @PostMapping("/new_product")
    public String addNewProduct(String categoryName, MultipartFile file, Product product) throws IOException {
        Category category = categoryService.getCategoryByName(categoryName);
        fileService.setAndWriteImage(file);
        product.setImage(file.getOriginalFilename());
        product.setCategory(List.of(category));
        productService.saveProduct(product);
        category.setProducts(List.of(product));
        product = null;
        category = null;
        return "admin";
    }


    @GetMapping("/products")
    public String showAllProducts(Model model) {
        model.addAttribute("products", productService.getProductRepository().findAll());
        return "admin";
    }


}
