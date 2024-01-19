package ru.dimax;


import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import ru.dimax.model.Customer;
import ru.dimax.repository.CustomerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest extends AbstractUnitTest {

    @Autowired
    private CustomerRepository underTest;

    private UtilTest testData = new UtilTest();

    @AfterEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void itShouldSaveCustomer() {
        // Given
        Customer customer = new Customer(
                1,
                testData.randomName(),
                testData.randomEmail(),
                testData.randomAge()
        );

        // When

        Customer saved = underTest.save(customer);
        // Then

        assertThat(saved).isEqualTo(customer);
    }

    @Test
    void itShouldGetAllCustomers() {
        // Given
        Customer customer1 = new Customer(
                1,
                testData.randomName(),
                testData.randomEmail(),
                testData.randomAge()
        );

        Customer customer2 = new Customer(
                2,
                testData.randomName(),
                testData.randomEmail(),
                testData.randomAge()
        );

        Customer saved1 = underTest.save(customer1);

        Customer saved2 = underTest.save(customer2);
        // When

        List<Customer> customers = underTest.findAll();

        // Then

        assertThat(customers.size()).isEqualTo(2);
    }
}
