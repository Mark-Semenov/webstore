package ru.gb.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.store.dto.UserDTO;
import ru.gb.store.entities.Cart;
import ru.gb.store.entities.Product;
import ru.gb.store.entities.ProductInCart;
import ru.gb.store.entities.User;
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
            if (user.getRoles().iterator().next().getName().equals("ROLE_USER")) {
                Cart cart = findUserByEmail(user.getEmail()).getCart();
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
        cartRepository.save(cart);
        u.setFirstname(user.getFirstname());
        u.setLastname(user.getLastname());
        u.setDate(user.getDate());
        u.setPhone(user.getPhone());
        u.setPassword(user.getPassword());
        u.setEmail(user.getEmail());
        u.setRoles(roleRepository.findByName(user.getRole()));
        u.setCart(cart);
        userRepository.save(u);
        saveUserCartWithProducts(cart);
        userSessionCart.getProductCart().clear();

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
