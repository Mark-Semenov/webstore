package ru.gb.store.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.store.entities.Product;
import ru.gb.store.service.CartService;
import ru.gb.store.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CartService cartService;


    @GetMapping
    public String productInfo(Model model,
                              @RequestParam(name = "id", required = false) Long productId,
                              @RequestParam(name = "search", required = false) String search) {
        List<Product> productsList = null;

        if (productId != null) {
            productsList = List.of(productService.findProductById(productId));
        }

        if (search != null && !search.equals("")) {
            productsList = productService.findByName(search);

            if (productsList.isEmpty()) {
                model.addAttribute("notFound", "Sorry, product " + search + " not found. Please, try again.");
            } else if (productsList.size() == 1) {
                productId = productsList.get(0).getId();
                productsList = List.of(productService.findProductById(productId));
            }
        }


        model.addAttribute("products", productsList);
        return "product";
    }


    @GetMapping("/add")
    public String addProductToCart(@RequestParam(name = "id") Long prodId,
                                   @RequestParam(name = "search", required = false) String search) {
        cartService.addToCart(prodId);
        return "redirect:/product?id=" + prodId + "&search=" + search;

    }


}
