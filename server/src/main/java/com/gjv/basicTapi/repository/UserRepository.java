package com.gjv.basicTapi.repository;

import com.gjv.basicTapi.model.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
    User getUser(
            @Param("email") String email,
            @Param("password") String password
    );

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User getUser(
            @Param("id") String id
    );

    @Modifying
    @Transactional
    @Query("INSERT INTO User(id, name, cellphone, email, cpf, rg, stateRg, birthDate, password) " +
            "VALUES (:id, :name, :cellphone, :email, :cpf, :rg, :stateRg, :birthDate, :password)")
    void setUser(
            @Param("id") String id,
            @Param("name") String name,
            @Param("cellphone") String cellphone,
            @Param("email") String email,
            @Param("cpf") String cpf,
            @Param("rg") String rg,
            @Param("stateRg") String stateRg,
            @Param("birthDate") Date birthDate,
            @Param("password") String password
    );

    @Modifying
    @Transactional
    @Query("UPDATE User SET name=:name, cellphone=:cellphone, email=:email, cpf=:cpf, rg=:rg, password=:password WHERE id = :id")
    void editUser(
        @Param("name") String name,
        @Param("cellphone") String cellphone,
        @Param("email") String email,
        @Param("cpf") String cpf,
        @Param("rg") String rg,
        @Param("password") String password,
        @Param("id") String id
    );

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id = :id")
    void deleteUser(
            @Param("id") String id
    );
}
