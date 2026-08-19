package com.gestion.pedidos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
        String nombre,

        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El email no tiene un formato válido")
        String email,

        @NotBlank(message = "El teléfono es obligatorio")
        @Size(min = 7, max = 15, message = "El teléfono debe tener entre 7 y 15 caracteres")
        String telefono

) {}
