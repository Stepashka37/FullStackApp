package ru.dimax.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
