package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.config.security.TokenService;
import com.example.demo.controller.dto.TokenDto;
import com.example.demo.controller.form.LoginForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Profile(value = {"prod", "test"})
public class AuthenticacaoController {

    @Autowired
    private AuthenticationManager authManager; 
    
    @Autowired
    private TokenService tokenSerive;

    @PostMapping
    public ResponseEntity<?> authenticar(@RequestBody @Valid LoginForm form) {
        
        try {
            UsernamePasswordAuthenticationToken dadosLogin = form.converter();
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenSerive.gerarToken(authentication);
            
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }
}
