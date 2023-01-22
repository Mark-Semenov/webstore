package ru.gb.mark.webstore.service;

import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import ru.gb.mark.webstore.dto.UserDTO;
import ru.gb.mark.webstore.entity.*;
import ru.gb.mark.webstore.repository.CartRepository;
import ru.gb.mark.webstore.repository.ProductInCartRepository;
import ru.gb.mark.webstore.repository.RoleRepository;
import ru.gb.mark.webstore.repository.UserRepository;
import ru.gb.mark.webstore.session.UserSessionCart;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;
    private final UserSessionCart userSessionCart;
    private final ProductInCartRepository productInCartRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (user.get().getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_USER"))) {
                Cart cart = user.get().getCart();
                if (cart.getProducts() != null) {
                    for (ProductInCart p : cart.getProducts()) {
                        if (userSessionCart.getProductCart().keySet().stream().anyMatch(product -> product.equals(p.getProduct()))) {
                            userSessionCart.getProductCart().replace(p.getProduct(), p.getCount() + userSessionCart.getProductCart().get(p.getProduct()));
                        } else {
                            userSessionCart.getProductCart().put(p.getProduct(), p.getCount());
                        }
                    }
                    userSessionCart.calculateTotalDiscount();
                    userSessionCart.calculateTotalSum();
                }
            }

        }

        return user.map(value -> new org.springframework.security.core.userdetails.User(value.getEmail(),
                value.getPassword(), value.getRoles())).orElse(null);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    //Used builder
    public String registerNewUserAccount(UserDTO user) {

        User u = UserWrapper.builder()
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .setDate(user.getDate())
                .setPhone(user.getPhone())
                .setEmail(user.getEmail())
                .setPassword(encoder.encode(user.getPassword()))
                .setRoles(roleRepository.findByName("ROLE_USER"))
                .setCart(new Cart())
                .build();


        userRepository.save(u);
        cartRepository.save(u.getCart());
        saveUserCartWithProducts(u.getCart());
        userSessionCart.getProductCart().clear();

        return "redirect:/login";

    }

    public void saveUserCartWithProducts(Cart cart) {
        ProductInCart product;
        List<ProductInCart> listOfProductInCart = new ArrayList<>();
        if (!userSessionCart.getProductCart().isEmpty()) {
            for (Product p : userSessionCart.getProductCart().keySet()) {
                product = new ProductInCart();
                product.setProduct(p);
                product.setCount(userSessionCart.getProductCart().get(p));
                productInCartRepository.save(product);
                listOfProductInCart.add(product);
            }
            cart.setProducts(listOfProductInCart);
        } else cart.setProducts(null);

        cartRepository.save(cart);
    }


}
