package com.gestion.pedidos.dto;

import java.math.BigDecimal;

public record ProductoResponse(
        Long id,
        String nombre,
        BigDecimal precio,
        Integer stock
) {}
