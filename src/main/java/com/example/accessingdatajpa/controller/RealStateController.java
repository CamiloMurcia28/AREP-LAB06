/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.accessingdatajpa.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.example.accessingdatajpa.model.Property;
import com.example.accessingdatajpa.repository.PropertyRepository;
import java.util.List;

/**
 *
 * @author camilo.murcia-e
 */

@RestController
@RequestMapping("/api/properties")
@CrossOrigin("*" )
public class RealStateController {

    @Autowired
    public PropertyRepository propertyService;

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        return propertyService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Property createProperty(@RequestBody Property property) {
        return propertyService.save(property);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property updatedProperty) {
        return propertyService.findById(id)
                .map(property -> {
                    property.setAddress(updatedProperty.getAddress());
                    property.setPrice(updatedProperty.getPrice());
                    property.setSize(updatedProperty.getSize());
                    property.setDescription(updatedProperty.getDescription());
                    return ResponseEntity.ok(propertyService.save(property));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProperty(@PathVariable Long id) {
        return propertyService.findById(id)
                .map(property -> {
                    propertyService.delete(property);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

