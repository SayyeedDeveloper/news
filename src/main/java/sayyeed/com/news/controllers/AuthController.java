package sayyeed.com.news.controllers;

import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sayyeed.com.news.dtos.auth.RegistrationDTO;
import sayyeed.com.news.services.auth.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }

    @GetMapping("/registration/email/verification/{username}/{code}")
    public ResponseEntity<String> verificationByLink(@PathVariable("username") String username, @PathVariable("code") String code) {
        return ResponseEntity.ok(authService.verificationByLink(username, code));
    }
}
