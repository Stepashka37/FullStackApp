package ru.dimax.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dimax.dto.CustomerRegistrationRequest;
import ru.dimax.dto.CustomerResponse;
import ru.dimax.dto.CustomerUpdateRequest;
import ru.dimax.jwt.JWTUtil;
import ru.dimax.service.CustomerService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final JWTUtil jwtUtil;

    @GetMapping()
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> response = customerService.getAllCustomers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("id") Integer id) {
        CustomerResponse response = customerService.getCustomerById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<?> registerNewCustomer(@RequestBody CustomerRegistrationRequest request) {
        CustomerResponse response = customerService.saveCustomer(request);
        String jwtToken = jwtUtil.issueToken(request.getEmail(), "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .body(response);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("id") Integer id) {
        customerService.deleteCustomerById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable("id") Integer id,
                                                           @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        customerService.updateCustomerById(id, customerUpdateRequest);
        return ResponseEntity.ok(null);
    }

}
