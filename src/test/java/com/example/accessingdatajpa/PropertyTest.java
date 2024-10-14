/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.accessingdatajpa;

import com.example.accessingdatajpa.controller.RealStateController;
import com.example.accessingdatajpa.model.Property;
import com.example.accessingdatajpa.repository.PropertyRepository;

import org.assertj.core.internal.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DataJpaTest
public class PropertyTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PropertyRepository pr;

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private RealStateController realStateController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    void getPropertyById_Found() {
        Long id = 1L;
        Property property = new Property();
        property.setId(id);

        when(propertyRepository.findById(id)).thenReturn(Optional.of(property));

        ResponseEntity<Property> response = realStateController.getPropertyById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(property, response.getBody());
    }

    @Test
    void getPropertyById_NotFound() {
        Long id = 1L;

        when(propertyRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Property> response = realStateController.getPropertyById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createProperty() {
        Property property = new Property();
        property.setAddress("123 Test St");

        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        Property result = realStateController.createProperty(property);

        assertEquals("123 Test St", result.getAddress());
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void updateProperty_Found() {
        Long id = 1L;
        Property existingProperty = new Property();
        existingProperty.setId(id);
        existingProperty.setAddress("Old Address");

        Property updatedProperty = new Property();
        updatedProperty.setAddress("New Address");

        when(propertyRepository.findById(id)).thenReturn(Optional.of(existingProperty));
        when(propertyRepository.save(any(Property.class))).thenReturn(existingProperty);

        ResponseEntity<Property> response = realStateController.updateProperty(id, updatedProperty);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("New Address", response.getBody().getAddress());
    }

    @Test
    void deleteProperty_Found() {
        Long id = 1L;
        Property property = new Property();
        property.setId(id);

        when(propertyRepository.findById(id)).thenReturn(Optional.of(property));

        ResponseEntity<Object> response = realStateController.deleteProperty(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(propertyRepository, times(1)).delete(property);
    }


	
    @Test
    public void whenSaveProperty_thenReturnProperty() {
        Property property = new Property();
        property.setAddress("123 Test St");
        
        Property savedProperty = pr.save(property);
        
        assertThat(savedProperty).hasFieldOrPropertyWithValue("address", "123 Test St");
    }

    @Test
    public void whenFindById_thenReturnProperty() {
        Property property = new Property();
        property.setAddress("123 Test St");
        
        entityManager.persist(property);
        entityManager.flush();
        
        Optional<Property> found = pr.findById(property.getId());
        
        assertThat(found).isPresent();
        assertThat(found.get().getAddress()).isEqualTo(property.getAddress());
    }

    @Test
    public void whenFindAll_thenReturnPropertyList() {
        Property property1 = new Property();
        property1.setAddress("123 Test St");
        
        Property property2 = new Property();
        property2.setAddress("456 Sample Rd");
        
        entityManager.persist(property1);
        entityManager.persist(property2);
        entityManager.flush();
        
        List<Property> properties = pr.findAll();
        
        assertThat(properties).hasSize(2);
    }

    @Test
    public void whenDeleteById_thenPropertyShouldNotExist() {
        Property property = new Property();
        property.setAddress("789 Delete Ln");
        
        entityManager.persist(property);
        entityManager.flush();
        
        propertyRepository.deleteById(property.getId());
        
        Optional<Property> deleted = propertyRepository.findById(property.getId());
        assertThat(deleted).isNotPresent();
    }
}
