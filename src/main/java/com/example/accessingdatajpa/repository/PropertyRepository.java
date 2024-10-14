package com.example.accessingdatajpa.repository;

import com.example.accessingdatajpa.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author camilo.murcia-e
 */

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
