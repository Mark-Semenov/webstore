package ru.gb.store.controllers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.store.dto.CartDTO;
import ru.gb.store.entities.Category;
import ru.gb.store.entities.Product;
import ru.gb.store.service.CategoryService;
import ru.gb.store.service.ProductService;

import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IndexController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private Page<Product> products;
    private final CartDTO cartDTO;


    @GetMapping("/login")
    public String login(Model model, @RequestParam(value = "error", required = false) Boolean error) {
        String errorMessage = null;
        if (error != null && error) {
            errorMessage = "invalid login or password";
        }
        model.addAttribute("error", errorMessage);
        return "login";
    }


    @GetMapping("/")
    public String showProducts(Model model,
                               @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
                               @RequestParam(required = false, defaultValue = "", value = "search") String productName,
                               @RequestParam(required = false, defaultValue = "", value = "filter") String filter) {

        products = productService.getPageWithProducts(page, null, filter);
        searchRequest(productName);

        model.addAttribute("productName", productName);
        model.addAttribute("products", products);
        model.addAttribute("pages", productService.getPages());
        model.addAttribute("pageable", productService.getPageable());
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("filter", filter);
        model.addAttribute("cart", cartDTO);
        log.info("размер корзины " + cartDTO.getProductCart().size());
        return "index";
    }

    @GetMapping("/categories/{category}")
    public String showProductsByCategory(Model model,
                                         @PathVariable(required = false, name = "category") @NonNull String categoryName,
                                         @RequestParam(required = false, defaultValue = "0", value = "page") Integer page,
                                         @RequestParam(required = false, defaultValue = "", value = "search") @NonNull String productName) {

        if (!categoryName.isEmpty()) {
            Category category = categoryService.getCategories().stream().filter(c -> c.getName().equals(categoryName)).iterator().next();
            products = productService.getPageWithProducts(page, category, null);
        }

        searchRequest(productName);

        model.addAttribute("pageable", productService.getPageable());
        model.addAttribute("products", products);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("pages", productService.getPages());
        model.addAttribute("cart", cartDTO);
        model.addAttribute("search", productName);


        return "index";
    }

    private void searchRequest(@NonNull String productName) {
        if (!productName.isEmpty()) {
            products = new PageImpl<>(products.stream()
                    .filter(product -> product.getName().toLowerCase().matches(".*" + productName.toLowerCase() + ".*"))
                    .collect(Collectors.toList()));
        }
    }


}
