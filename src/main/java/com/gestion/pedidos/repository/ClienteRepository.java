package com.gestion.pedidos.repository;

import com.gestion.pedidos.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmail(String email);
}
