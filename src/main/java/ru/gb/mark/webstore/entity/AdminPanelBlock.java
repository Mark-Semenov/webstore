package ru.gb.mark.webstore.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "admin_block")
public class AdminPanelBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @OneToMany
    @JoinTable(
            name = "admin_block_url",
            joinColumns = @JoinColumn(name = "admin_block_id"),
            inverseJoinColumns = @JoinColumn(name = "admin_url_id")
    )
    private List<AdminURL> adminURL;
}
