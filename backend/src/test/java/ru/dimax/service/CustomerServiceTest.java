package ru.dimax.service;

import com.github.javafaker.Faker;
import ru.dimax.UtilTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.dimax.dto.CustomerRegistrationRequest;
import ru.dimax.dto.CustomerResponse;
import ru.dimax.dto.CustomerUpdateRequest;
import ru.dimax.enums.Gender;
import ru.dimax.mapper.CustomerMapper;
import ru.dimax.model.Customer;
import ru.dimax.repository.CustomerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService underTest;

    @Mock
    private CustomerRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Spy
    private CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    private Faker FAKER = new Faker();

    private UtilTest testData = new UtilTest();

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(repository, mapper, encoder);
    }

    @Test
    void itShouldGetAllCustomers() {
        // Given
        // When
        underTest.getAllCustomers();
        // Then
        verify(repository).findAll();
    }

    @Test
    void itShouldGetCustomerById() {
        // Given
        Customer customer1 = new Customer(
                1,
                testData.randomName(),
                testData.randomEmail(),
                testData.randomAge(),
                testData.maleGender(),
                testData.getPassword()
        );

        when(repository.findById(1)).thenReturn(Optional.of(customer1));

        // When
        underTest.getCustomerById(1);
        // Then
        verify(repository).findById(1);
    }

    @Test
    void itShouldSaveCustomer() {
        // Given

        String name = testData.randomName();

        String email = testData.randomEmail();

        Integer age = testData.randomAge();

        Gender gender = testData.maleGender();

        String password = testData.getPassword();

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name,
                email,
                password,
                age,
                gender
        );

        Customer customer1 = new Customer(
                1,
                name,
                email,
                age,
                gender,
                testData.getPassword()
        );

        when(repository.save(any())).thenReturn(customer1);

        String passwordHash = "23o2ot4tf2,m";

        when(encoder.encode(any())).thenReturn(passwordHash);

        // When

        CustomerResponse response = underTest.saveCustomer(request);
        // Then
        assertNotNull(response);
        verify(repository).save(any());

    }

    @Test
    void itShouldDeleteCustomerById() {
        // Given
        Customer customer1 = new Customer(
                1,
                testData.randomName(),
                testData.randomEmail(),
                testData.randomAge(),
                testData.femaleGender(),
                testData.getPassword()
        );

        when(repository.findById(1)).thenReturn(Optional.of(customer1));
        // When
        underTest.deleteCustomerById(1);
        // Then
        verify(repository).deleteById(1);
    }

    @Test
    void itShouldUpdateCustomerById() {
        // Given

        String name = testData.randomName();

        String email = testData.randomEmail();

        Integer age = testData.randomAge();

        Gender gender = testData.maleGender();

        CustomerUpdateRequest request = new CustomerUpdateRequest(
                name,
                email,
                age
        );

        Customer customer1 = new Customer(
                1,
                name,
                email,
                age,
                gender,
                testData.getPassword()
        );

        when(repository.findById(1)).thenReturn(Optional.of(customer1));
        // When
        underTest.updateCustomerById(1,request);
        // Then
        verify(repository).save(any());
    }
}