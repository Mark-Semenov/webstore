package ru.gb.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.store.service.CartService;
import ru.gb.store.service.ProductService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;


    @GetMapping
    public String productInfo(Model model, @RequestParam(name = "id", required = false) Long productId) {
        model.addAttribute("product", productService.findProductById(productId));
        return "product";
    }


    @GetMapping("/add")
    public String addProductToCart(@RequestParam(name = "id") Long prodId) {
        cartService.addToCart(prodId);
        return "redirect:/product?id=" + prodId;

    }


}
