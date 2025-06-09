package sayyeed.com.news.controllers;

import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sayyeed.com.news.dtos.auth.LoginDTO;
import sayyeed.com.news.dtos.auth.RegistrationDTO;
import sayyeed.com.news.dtos.auth.VerificationBySmsDTO;
import sayyeed.com.news.dtos.profile.ProfileInfoDTO;
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

    @GetMapping("/registration/email/verification/{token}")
    public ResponseEntity<String> verificationByLink(@PathVariable String token) {
        return ResponseEntity.ok(authService.verificationByLink(token));
    }

    @PutMapping("/registration/sms/verification")
    public ResponseEntity<String> verificationBySms(@RequestBody VerificationBySmsDTO dto) {
        return ResponseEntity.ok(authService.verificationBySms(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileInfoDTO> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

}
