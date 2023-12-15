package com.spring.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping
    public ResponseEntity<String> get(){
        return ResponseEntity.ok("GET:: user controller");
    }
    @PostMapping
    public ResponseEntity<String> post(){
        return ResponseEntity.ok("POST:: user controller");
    }
    @PutMapping
    public ResponseEntity<String> update(){
        return ResponseEntity.ok("PUT:: user controller");
    }
    @DeleteMapping
    public ResponseEntity<String> delete(){
        return ResponseEntity.ok("DELETE:: user controller");
    }
}
