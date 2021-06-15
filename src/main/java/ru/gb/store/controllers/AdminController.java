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
import ru.gb.store.entities.*;
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

    @ModelAttribute
    public void attributes(Model model){
        model.addAttribute("blocks", productService.getAdminBlocks());
        model.addAttribute("roles", roleService.getNamesOfRoles());
        model.addAttribute("categories", categoryService.getNamesOfCategories());
    }

    @GetMapping
    public String welcome(Model model) {
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
        userService.addUser(user);
        return "admin";
    }

    @GetMapping("/new_product")
    public String createProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("image", new Img());
        return "admin";
    }

    @PostMapping("/new_product")
    public String addNewProduct(String categoryName, MultipartFile file, Product product, Img image) throws IOException {
        Category category = categoryService.getCategoryByName(categoryName);
        setAndWriteImage(file, product, image);
        product.setCategory(List.of(category));
        productService.saveProduct(product);
        category.setProducts(List.of(product));
        product = null;
        category = null;
        return "admin";
    }

    private void setAndWriteImage(MultipartFile file, Product product, Img image) throws IOException {
        if (!file.isEmpty()) {
            image.setName(file.getOriginalFilename());
            image.setContent(file.getBytes());
            product.setImage(image.getName());
            Files.write(Path.of("src\\main\\resources\\static\\images\\".concat(image.getName())).toAbsolutePath(), image.getBytes());
        }
    }


}
