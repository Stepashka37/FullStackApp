package ru.dimax.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dimax.dto.CustomerRegistrationRequest;
import ru.dimax.dto.CustomerResponse;
import ru.dimax.dto.CustomerUpdateRequest;
import ru.dimax.exception.ResourceNotFoundException;
import ru.dimax.mapper.CustomerMapper;
import ru.dimax.model.Customer;
import ru.dimax.repository.CustomerRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final PasswordEncoder passwordEncoder;

    private EntityManager entityManager;

    public List<CustomerResponse> getAllCustomers() {
        log.info("Getting all customers");
        return repository.findAll()
                .stream()
                .map(x -> mapper.toDto(x))
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Integer id) {
        log.info("Getting customer with id=%s".formatted(id));

        Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Customer with id=%s not found"
                .formatted(id)));

        return mapper.toDto(customer);
    }

    public CustomerResponse saveCustomer(CustomerRegistrationRequest customer) {
        log.info("Saving new customer");

        Customer customerMapped = mapper.toModel(customer);
        customerMapped.setPassword(passwordEncoder.encode(customer.getPassword()));

        Customer savedCustomer = repository.save(customerMapped);

        log.info("Customer with id=%s saved".formatted(savedCustomer.getId()));

        return mapper.toDto(savedCustomer);
    }

    public void deleteCustomerById(Integer id) {
        log.info("Deleting customer with id=%s".formatted(id));

        Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Customer with id=%s not found"
                        .formatted(id)));

        repository.deleteById(id);

        log.info("Customer with id=%s deleted".formatted(id));
    }

    public CustomerResponse updateCustomerById(Integer id, CustomerUpdateRequest request) {
        log.info("Update customer with id=%s".formatted(id));

        Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Customer with id=%s not found"
                        .formatted(id)));

        Customer customerUpd = mapper.toModelForUpdate(request, customer);

        Customer customerSaved = repository.save(customerUpd);

        log.info("Customer with id=%s updated".formatted(id));

        return mapper.toDto(customerSaved);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        log.info("Getting customer by email=%s".formatted(email));

        Optional<Customer> customer = repository.findCustomerByEmail(email);

        return customer;
    }

}
