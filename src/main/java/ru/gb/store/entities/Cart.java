package ru.gb.store.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "carts")
public class Cart {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @OneToMany
   @JoinTable(

           name = "carts_products",
           joinColumns = @JoinColumn(name = " cart_id"),
           inverseJoinColumns = @JoinColumn(name = "products_id")
   )
   private List<Product> products;

   @OneToOne
   private User user;
}
