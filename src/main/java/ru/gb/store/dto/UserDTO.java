package ru.gb.store.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
public class UserDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate age;
    private String login;
    private String password;
    private String matchingPassword;
    private String email;
    private String role;
    private String phone;
}
