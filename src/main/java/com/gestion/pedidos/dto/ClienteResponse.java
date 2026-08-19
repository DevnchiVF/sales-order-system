package com.gestion.pedidos.dto;

public record ClienteResponse(
        Long id,
        String nombre,
        String email,
        String telefono
) {
}
