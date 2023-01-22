package ru.gb.mark.webstore.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class UserDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate date;
    private String login;
    private String password;
    private String matchingPassword;
    private String email;
    private String role;
    private String phone;

}
