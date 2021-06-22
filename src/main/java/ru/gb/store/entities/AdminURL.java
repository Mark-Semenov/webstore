package ru.gb.store.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "admin_url")
public class AdminURL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;
    private String action;

    @ManyToOne
    private AdminPanelBlock adminPanelBlock;

}
