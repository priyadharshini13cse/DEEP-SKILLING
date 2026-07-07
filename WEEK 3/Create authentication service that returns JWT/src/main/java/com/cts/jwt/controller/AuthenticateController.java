package com.cts.jwt.controller;

import com.cts.jwt.util.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@RestController
public class AuthenticateController {

    private final JwtUtil jwtUtil;

    public AuthenticateController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Authenticates the user and returns a JWT.
     *
     * Spring Security's HTTP Basic filter reads the Authorization header,
     * decodes the Base64-encoded username:password, and validates the
     * credentials against the in-memory UserDetailsService.
     *
     * If authentication succeeds, the Principal is injected here and
     * a signed JWT is generated and returned.
     *
     * Usage:  curl -s -u user:pwd http://localhost:8090/authenticate
     * Response: {"token":"eyJhbGciOi..."}
     *
     * @param principal the authenticated user (injected by Spring Security)
     * @return a map containing the JWT token
     */
    @GetMapping("/authenticate")
    public Map<String, String> authenticate(Principal principal) {
        String token = jwtUtil.generateToken(principal.getName());
        return Collections.singletonMap("token", token);
    }
}
