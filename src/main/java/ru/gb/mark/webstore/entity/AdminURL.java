package ru.gb.mark.webstore.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "admin_url")
public class AdminURL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String action;

}
