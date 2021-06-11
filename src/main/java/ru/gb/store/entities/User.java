package ru.gb.store.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate age;
    private String login;
    private String password;
    private String email;
    private Integer phone;

    public User(String firstname, String lastname, LocalDate age, String login, String password, String email, Integer phone, List<Role> roles) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.login = login;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.roles = roles;
    }

    public User(String login, String password, String email, List<Role> roles) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @OneToOne
    private Cart cart;

}
