package com.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * je n'ai pas ajouté les annotations pour les champs de la base de données à l'exeption de @Id
 */
@Data
@Entity
public class Product {
    @Id
    private Long id;
    private String code;
    private String name;
    private String description;
    private Float price;
    private Short quantity;
    private String inventoryStatus;
    private String category;
    private String image;
    private Long rating;
}
