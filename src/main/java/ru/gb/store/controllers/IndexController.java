package ru.gb.store.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.store.entities.Category;
import ru.gb.store.entities.Product;
import ru.gb.store.repositories.BrandRepository;
import ru.gb.store.service.CartService;
import ru.gb.store.service.CategoryService;
import ru.gb.store.service.ProductService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private Page<Product> products;
    private final BrandRepository brandRepository;
    private final CartService cartService;
    private final Map<String, String> filters = new HashMap<>();

    @ModelAttribute
    public void attributes(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("brands", brandRepository.findAll());
    }

    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) Boolean error) {
        String errorMessage = null;
        if (error != null && error) {
            errorMessage = "invalid login or password";
        }
        model.addAttribute("error", errorMessage);
        return "login";
    }


    @GetMapping
    public String showProducts(Model model,
                               @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
                               @RequestParam(required = false, defaultValue = "", name = "search") String prodName,
                               @RequestParam(required = false, defaultValue = "", name = "minPrice") String minPrice,
                               @RequestParam(required = false, defaultValue = "", name = "maxPrice") String maxPrice,
                               @RequestParam(required = false, name = "brandName") String brandName) {

        filters.put("search", prodName);
        filters.put("minPrice", minPrice);
        filters.put("maxPrice", maxPrice);
        filters.put("brandName", brandName);

        products = productService.getPageWithProducts(page, null, filters);

        model.addAttribute("productName", prodName);
        model.addAttribute("products", products);
        model.addAttribute("pageable", productService.getPageable());
        return "index";
    }

    @GetMapping("/categories/{category}")
    public String showProductsByCategory(Model model,
                                         @PathVariable(required = false, name = "category") @NonNull String categoryName,
                                         @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
                                         @RequestParam(required = false, defaultValue = "", value = "search") @NonNull String productName,
                                         @RequestParam(required = false, defaultValue = "", name = "minPrice") String minPrice,
                                         @RequestParam(required = false, defaultValue = "", name = "maxPrice") String maxPrice,
                                         @RequestParam(required = false, name = "brandName") String brandName) {

        filters.put("search", productName);
        filters.put("minPrice", minPrice);
        filters.put("maxPrice", maxPrice);
        filters.put("brandName", brandName);

        if (!categoryName.isEmpty()) {
            Category category = categoryService.getCategories().stream().filter(c -> c.getName().equals(categoryName)).iterator().next();
            products = productService.getPageWithProducts(page, category, filters);
        }
        model.addAttribute("products", products);
        model.addAttribute("pageable", productService.getPageable());
        return "index";
    }


    @GetMapping("/add")
    public String addProductToCart(@RequestParam(name = "id") Long prodId,
                                   @RequestParam(required = false, name = "page") Integer page,
                                   @RequestParam(required = false, defaultValue = "", name = "search") String search) {

        cartService.addToCart(prodId);
        return "redirect:/?page=" + page + "&search=" + search;
    }


}
