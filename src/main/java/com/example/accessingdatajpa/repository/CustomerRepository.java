package com.example.accessingdatajpa.repository;

import com.example.accessingdatajpa.model.Customer;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);
}
