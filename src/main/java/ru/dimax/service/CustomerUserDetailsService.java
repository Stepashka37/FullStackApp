package ru.dimax.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.dimax.dto.CustomerResponse;
import ru.dimax.model.Customer;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerService.getCustomerByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email=" + username + " not found"));
    }
}
