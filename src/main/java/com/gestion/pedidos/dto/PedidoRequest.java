package com.gestion.pedidos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PedidoRequest(

        @NotNull(message = "El cliente es obligatorio")
        Long clienteId,

        @NotNull(message = "Debe incluir al menos un item")
        @Size(min = 1, message = "Debe incluir al menos un item")
        List<@Valid ItemRequest> items
) {}
