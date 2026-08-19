package com.gestion.pedidos.controller;

import com.gestion.pedidos.dto.ClienteRequest;
import com.gestion.pedidos.dto.ClienteResponse;
import com.gestion.pedidos.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(@RequestBody @Valid ClienteRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
