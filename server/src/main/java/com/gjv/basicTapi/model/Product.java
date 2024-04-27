package com.gjv.basicTapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
public class Product {
    @Id
    @Column(name = "id_product")
    private String idProduct;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "created_at")
    private String createdAt;

}
