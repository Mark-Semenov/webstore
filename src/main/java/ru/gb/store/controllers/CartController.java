package ru.gb.store.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.store.dto.CartDTO;
import ru.gb.store.entities.Order;
import ru.gb.store.entities.Product;
import ru.gb.store.entities.User;
import ru.gb.store.service.CartService;
import ru.gb.store.service.ProductService;
import ru.gb.store.service.UserService;

import java.security.Principal;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;
    private final CartDTO cartDTO;
    private Product product;

    @GetMapping("/add")
    public String addProductToCart(Model model,
                                   @RequestParam(name = "id") Long id,
                                   @RequestParam(name = "page") Integer page,
                                   @RequestParam(required = false, defaultValue = "", name = "search") String search) {

        product = productService.findProductById(id);
        if (cartDTO.getProductCart().stream().noneMatch(product1 -> product1.equals(product))) {
            cartService.addProduct(product);
        }
        model.addAttribute("productId", id);
        return "redirect:/?page=" + page + "&search=" + search;

    }


    @GetMapping
    public String cart(Model model, Principal principal) {
        User user;
        if (principal != null) {
            user = userService.findUserByLogin(principal.getName());
            model.addAttribute("userId", user.getId());
        }

        model.addAttribute("cart", cartDTO);
        model.addAttribute("prodInCart", cartDTO.getProductCart());
        return "cart";
    }

    @GetMapping("/delete")
    public String deleteProdFromCart(Model model, @RequestParam(name = "id", required = false) Long prodId) {
        cartService.deleteProduct(cartService.getProductById(prodId));
        model.addAttribute("cart", cartDTO);
        return "redirect:/cart";
    }


    @GetMapping("/order")
    public String buyProducts(Model model) {
        Order order = new Order();
        model.addAttribute("order", order);
        model.addAttribute("totalSum", cartDTO.getTotalSum());
        return "order";
    }

    @PostMapping("/order/checkout")
    public String checkout(Principal principal, Order order) {
        order.setUser(userService.findUserByLogin(principal.getName()));
        cartService.buyProducts(order);
        return "order";

    }

}
