/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.accessingdatajpa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author camilo.murcia-e
 */
@Controller
@RequestMapping("/")
@CrossOrigin("*")
public class AllController {

    @GetMapping
    public String index() {
        return "index"; // This will serve index.html from src/main/resources/static
    }
}
