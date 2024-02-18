package ru.dimax.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import ru.dimax.UtilTest;
import ru.dimax.dto.CustomerRegistrationRequest;
import ru.dimax.enums.Gender;
import ru.dimax.model.Customer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerIT {

    @Autowired
    private WebTestClient webTestClient;

    private UtilTest testData = new UtilTest();

    private final String CUSTOMER_URI = "/api/v1/customers";

    @Test
    void itShouldRegisterACustomer() {
        // Given
        String name = testData.randomName();

        String email = testData.randomName() + "@yandex.ru";

        Integer age = testData.randomAge();

        Gender gender = testData.maleGender();

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                name,
                email,
                age,
                gender
        );
        // When
        webTestClient.post()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CustomerRegistrationRequest.class)
                .exchange()
                .expectStatus()
                .isCreated();
        // Then

        List<Customer> allCustomers = webTestClient.get()
                .uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                })
                .returnResult()
                .getResponseBody();

        Customer expectedCustomer = new Customer(
                1,
                name,
                email,
                age,
                gender
        );

        assertThat(allCustomers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(expectedCustomer);


        //get customer by id
        int id = allCustomers.stream()
                        .filter(c -> c.getEmail().equals(email))
                        .map(c -> c.getId())
                        .findFirst()
                        .orElseThrow();

        expectedCustomer.setId(id);

        webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                })
                .isEqualTo(expectedCustomer);

        //delete customer by id
        webTestClient.delete()
                .uri(CUSTOMER_URI + "/{id}", id)
                .exchange()
                .expectStatus()
                .isNoContent();

        webTestClient.get()
                .uri(CUSTOMER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
