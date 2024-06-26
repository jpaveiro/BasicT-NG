package com.gjv.basicTapi.repository;

import com.gjv.basicTapi.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT u FROM Product u WHERE u.idProduct = :idProduct")
    Product getProduct(
            @Param("idProduct") String idProduct
    );

    @Modifying
    @Transactional
    @Query("INSERT INTO Product(idProduct, name, price) " +
            "VALUES (:idProduct, :name, :price)")
    void setProduct(
            @Param("idProduct") String idProduct,
            @Param("name") String name,
            @Param("price") double price
    );

    @Modifying
    @Transactional
    @Query("DELETE FROM Product u WHERE u.id = :id")
    void deleteProduct(
            @Param("id") String id
    );
}
