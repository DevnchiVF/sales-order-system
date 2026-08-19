package com.gestion.pedidos.dto;

import java.math.BigDecimal;

public record ItemResponse(
        Long productoId,
        String nombreProducto,
        Integer cantidad,
        BigDecimal precioUnitario
) {}