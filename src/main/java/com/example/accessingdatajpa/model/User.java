package com.example.accessingdatajpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The User class represents a user entity in the system.
 * It is mapped to the "users" table in the database using JPA annotations.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    private String username;
    private String password;

    /**
     * Sets the password for the user.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Gets the username of the user.
     * 
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for the user.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    
}