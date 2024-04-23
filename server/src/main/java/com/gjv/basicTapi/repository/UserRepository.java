package com.gjv.basicTapi.repository;

import com.gjv.basicTapi.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    long getUser(
            @Param("email") String email,
            @Param("password") String password
    );

    @Modifying
    @Transactional
    @Query("INSERT INTO User(id, name, cellphone, email, cpf, rg, password) VALUES (:id, :name, :cellphone, :email, :cpf, :rg, :password)")
    void setUser(
            @Param("id") String id,
            @Param("name") String name,
            @Param("cellphone") String cellphone,
            @Param("email") String email,
            @Param("cpf") String cpf,
            @Param("rg") String rg,
            @Param("password") String password
    );
}
