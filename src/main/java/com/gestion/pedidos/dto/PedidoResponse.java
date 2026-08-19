package com.gestion.pedidos.dto;

import com.gestion.pedidos.domain.EstadoPedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
        Long id,
        Long clienteId,
        EstadoPedido estado,
        LocalDateTime creadoEn,
        List<ItemResponse> items
) {}
