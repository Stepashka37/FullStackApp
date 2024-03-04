package ru.dimax.auth;

import ru.dimax.dto.CustomerResponse;

public record AuthenticationResponse(
        String token,
        CustomerResponse customerResponse) {
}
