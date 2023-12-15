package com.spring.ecommerce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
public class ManagementController {

    @GetMapping
    public ResponseEntity<String> get(){
        return ResponseEntity.ok("GET:: management controller");
    }
    @PostMapping
    public ResponseEntity<String> post(){
        return ResponseEntity.ok("POST:: management controller");
    }
    @PutMapping
    public ResponseEntity<String> update(){
        return ResponseEntity.ok("PUT:: management controller");
    }
    @DeleteMapping
    public ResponseEntity<String> delete(){
        return ResponseEntity.ok("DELETE:: management controller");
    }

}
