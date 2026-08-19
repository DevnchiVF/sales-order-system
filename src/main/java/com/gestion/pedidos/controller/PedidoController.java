package com.gestion.pedidos.controller;

import com.gestion.pedidos.dto.PedidoRequest;
import com.gestion.pedidos.dto.PedidoResponse;
import com.gestion.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;

    @PostMapping
    public ResponseEntity<PedidoResponse> crear(@RequestBody @Valid PedidoRequest request) {
        PedidoResponse response = service.crear(request.clienteId(), request.items());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(@PathVariable Long id){
        return  ResponseEntity.ok(service.buscarPorId(id));
    }
}