package com.gjv.basicTapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchases")
public class Purchase {

    @Id
    @Column(name = "id_purchase")
    private String idPurchase;

    @Column(name = "id_user")
    private String idUser;

    @Column(name = "id_prod")
    private String idProduct;

    @Column(name = "prod_quantity")
    private int productQuantity;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "purchase_date")
    private String purchaseDate;

    @Column(name = "purchase_code")
    private String purchaseCode;
}
