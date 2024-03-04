package ru.dimax.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = authenticationService.login(request);

        return ResponseEntity.ok()
                .header("Authorization", response.token())
                .body(response);
    }
}

