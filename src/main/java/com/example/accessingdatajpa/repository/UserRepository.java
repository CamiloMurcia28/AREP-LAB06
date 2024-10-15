/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.accessingdatajpa.repository;


import com.example.accessingdatajpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author camilo.murcia-e
 */
/**
 * The UserRepository interface provides database access methods for the User entity.
 * It extends the JpaRepository interface, which provides standard CRUD operations 
 */
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Finds a User entity by its username.
     * 
     * @param username the username to search for
     * @return the User entity if found, or null if no user exists with the given username
     */
    User findByUsername(String username);
}
