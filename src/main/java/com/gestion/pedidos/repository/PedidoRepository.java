package com.gestion.pedidos.repository;

import com.gestion.pedidos.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
