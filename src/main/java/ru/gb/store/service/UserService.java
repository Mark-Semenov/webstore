package ru.gb.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.store.entities.User;
import ru.gb.store.repositories.UserRepository;

@Component
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        if (username != null && !username.isEmpty()) {
            user = userRepository.findByLogin(username);
        }

        return user != null ? new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(), user.getRoles()) : null;
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    public void addUser(User user) {
        userRepository.save(user);
    }

}
