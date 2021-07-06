package ru.gb.store.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private Byte age;
    private String login;
    private String password;
    private String matchingPassword;
    private String email;
    private String role;
    private String phone;

}
