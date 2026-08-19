package com.gestion.pedidos.controller;

import com.gestion.pedidos.dto.ProductoRequest;
import com.gestion.pedidos.dto.ProductoResponse;
import com.gestion.pedidos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@RequestBody @Valid ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}