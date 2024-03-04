package ru.dimax.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.dimax.dto.CustomerResponse;
import ru.dimax.jwt.JWTUtil;
import ru.dimax.mapper.CustomerMapper;
import ru.dimax.model.Customer;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final CustomerMapper customerMapper;
    private final JWTUtil jwtUtil;

    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        Customer principal = (Customer) authentication.getPrincipal();
        CustomerResponse customerDTO = customerMapper.toDto(principal);
        customerDTO.setRoles(List.of("ROLE_USER"));
        String token = jwtUtil.issueToken(customerDTO.getUsername(), customerDTO.getRoles());
        return new AuthenticationResponse(token, customerDTO);
    }
}

