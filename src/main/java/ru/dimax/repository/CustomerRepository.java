package ru.dimax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dimax.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query
    Customer existsCustomerByEmail(String email);
}
