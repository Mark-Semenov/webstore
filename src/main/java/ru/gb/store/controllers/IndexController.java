package ru.gb.store.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.gb.store.entities.Category;
import ru.gb.store.entities.Product;
import ru.gb.store.repositories.BrandRepository;
import ru.gb.store.service.CartService;
import ru.gb.store.service.CategoryService;
import ru.gb.store.service.ProductService;
import ru.gb.store.session.UserSessionCart;

import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class IndexController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private Page<Product> products;
    private final UserSessionCart userSessionCart;
    private final BrandRepository brandRepository;
    private final CartService cartService;

    @ModelAttribute
    public void attributes(Model model) {
        model.addAttribute("pages", productService.getPages());
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("cart", userSessionCart);
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
                               @RequestParam(required = false, value = "filter") String filter,
                               String minPrice, String maxPrice) {

        products = productService.getPageWithProducts(page, null, minPrice, maxPrice, prodName);
//        searchRequest(prodName);

        model.addAttribute("productName", prodName);
        model.addAttribute("filter", filter);
        model.addAttribute("products", products);
        model.addAttribute("pageable", productService.getPageable());
        return "index";
    }

    @GetMapping("/categories/{category}")
    public String showProductsByCategory(Model model,
                                         @PathVariable(required = false, name = "category") @NonNull String categoryName,
                                         @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
                                         @RequestParam(required = false, defaultValue = "", value = "search") @NonNull String productName) {

        if (!categoryName.isEmpty()) {
            Category category = categoryService.getCategories().stream().filter(c -> c.getName().equals(categoryName)).iterator().next();
            products = productService.getPageWithProducts(page, category, (String[]) null);
        }

//        searchRequest(productName);
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
