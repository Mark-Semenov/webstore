package ru.gb.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.store.dto.UserDTO;
import ru.gb.store.entities.*;
import ru.gb.store.repositories.CartRepository;
import ru.gb.store.repositories.ProductInCartRepository;
import ru.gb.store.repositories.RoleRepository;
import ru.gb.store.repositories.UserRepository;
import ru.gb.store.session.UserSessionCart;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CartRepository cartRepository;
    private final UserSessionCart userSessionCart;
    private final ProductInCartRepository productInCartRepository;
    private final HttpServletRequest httpServletRequest;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = null;
        if (email != null && !email.isEmpty()) {
            user = userRepository.findByEmail(email);
            for (Role r : user.getRoles()) {
                if (!r.getName().equals("ROLE_ADMIN")) {
                    Cart cart = findUserByEmail(user.getEmail()).getCart();
                    if (cart.getProducts() != null) {
                        for (ProductInCart p : cart.getProducts()) {
                            if (userSessionCart.getProductCart().keySet().stream().noneMatch(product -> product.equals(p.getProduct()))) {
                                userSessionCart.getProductCart().put(p.getProduct(), p.getCount());
                            } else {
                                int count = userSessionCart.getProductCart().get(p.getProduct()) + p.getCount();
                                userSessionCart.getProductCart().put(p.getProduct(), count);
                            }
                        }
                        userSessionCart.calculateTotalDiscount();
                        userSessionCart.calculateTotalSum();
                    }
                }
            }
        }

        return user != null ? new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), user.getRoles()) : null;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Transactional
    public void registerNewUserAccount(UserDTO user) {
        User u = new User();
        Cart cart = new Cart();
        u.setFirstname(user.getFirstname());
        u.setLastname(user.getLastname());
        u.setDate(user.getAge());
        u.setPhone(user.getPhone());
        u.setPassword(user.getPassword());
        u.setEmail(user.getEmail());
        u.setRoles(roleRepository.findByName(user.getRole()));
        u.setCart(cart);
        cart.setUser(u);
        userRepository.save(u);
        saveUserCartWithProducts(cart);

        try {
            httpServletRequest.login(u.getEmail(), user.getMatchingPassword());
        } catch (ServletException e) {
            e.printStackTrace();
        }
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
