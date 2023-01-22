package ru.gb.mark.webstore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.mark.webstore.dto.UserDTO;
import ru.gb.mark.webstore.entity.*;
import ru.gb.mark.webstore.repository.BrandRepository;
import ru.gb.mark.webstore.repository.OrderRepository;
import ru.gb.mark.webstore.service.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    private final BrandRepository brandRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;


    @ModelAttribute
    public void attributes(Model model) {
        model.addAttribute("blocks", productService.getAdminBlocks());
        model.addAttribute("roles", roleService.getNamesOfRoles());
        model.addAttribute("categories", categoryService.getNamesOfCategories());
        model.addAttribute("brands", getBrandsNames());
        model.addAttribute("statuses", OrderStatus.values());

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
    public String addNewProduct(String categoryName, String brandName, MultipartFile file, Product product) throws IOException {
        Category category = categoryService.getCategoryByName(categoryName);
        Brand brand = brandRepository.findByTitle(brandName);
        fileService.setAndWriteImage(file);
        product.setImage(file.getOriginalFilename());
        product.setCategory(List.of(category));
        product.setBrand(brand);
        product.setSale(product.getOldPrice() != null);
        productService.saveProduct(product);
        brand.setProducts(List.of(product));
        category.setProducts(List.of(product));
        return "admin";
    }

    private List<String> getBrandsNames() {
        return brandRepository.findAll().stream().map(Brand::getTitle).collect(Collectors.toList());
    }


    @GetMapping("/products")
    public String showAllProducts(Model model) {
        model.addAttribute("products", productService.getProductRepository().findAll());
        return "admin";
    }

    @GetMapping("/products/remove/{id}")
    public String removeProduct(@PathVariable(name = "id") Long id) {
        productService.removeProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/update/{id}")
    public String updateProduct(Model model, @PathVariable(name = "id") Long id) {
        Product product = productService.findProductById(id);
        model.addAttribute("prod", product);
        productService.saveProduct(product);
        return "update";
    }

    @GetMapping("/orders")
    public String showAllOrders(Model model) {
        model.addAttribute("orders", orderRepository.findAll());
        return "admin";
    }


    @PostMapping("/orders/update/status")
    public String changeOrderStatus(String statusName, Long orderId) {
        orderService.updateOrderStatus(statusName, orderId);
        return "redirect:/admin/orders";
    }

    @GetMapping("new_category")
    public String addNewCategory(Model model) {
        model.addAttribute("newCategory", new Category());
        return "admin";
    }

    @PostMapping("/new_category")
    public String saveNewCategory(Category newCategory) {
        categoryService.saveCategory(newCategory);
        return "redirect:/admin";
    }


}
