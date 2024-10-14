/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.accessingdatajpa.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.accessingdatajpa.model.Property;
import com.example.accessingdatajpa.repository.PropertyRepository;

/**
 *
 * @author camilo.murcia-e
 */
@Service
public class PropertiesService {

    public final PropertyRepository propertyRepository;

    @Autowired
    public PropertiesService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> findAll(){
        return this.propertyRepository.findAll();
    }
    

}
