package com.gestion.pedidos.service;

import com.gestion.pedidos.domain.Cliente;
import com.gestion.pedidos.domain.Pedido;
import com.gestion.pedidos.domain.Producto;
import com.gestion.pedidos.dto.ItemRequest;
import com.gestion.pedidos.exception.StockInsuficienteException;
import com.gestion.pedidos.repository.ClienteRepository;
import com.gestion.pedidos.repository.PedidoRepository;
import com.gestion.pedidos.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock ClienteRepository clienteRepository;
    @Mock ProductoRepository productoRepository;
    @Mock PedidoRepository pedidoRepository;

    @InjectMocks PedidoService pedidoService;

    @Test
    void crear_debe_descontar_stock_y_crear_item() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Producto producto = new Producto();
        producto.setId(10L);
        producto.setNombre("Teclado");
        producto.setPrecio(new BigDecimal("120.00"));
        producto.setStock(5);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findByIdParaActualizar(10L)).thenReturn(Optional.of(producto));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = pedidoService.crear(1L, List.of(new ItemRequest(10L, 2)));

        assertThat(producto.getStock()).isEqualTo(3);
        assertThat(response.items()).hasSize(1);
        assertThat(response.items().getFirst().precioUnitario()).isEqualByComparingTo("120.00");
    }

    @Test
    void crear_debe_rechazar_pedido_si_no_hay_stock() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Producto producto = new Producto();
        producto.setId(10L);
        producto.setNombre("Teclado");
        producto.setPrecio(new BigDecimal("120.00"));
        producto.setStock(1);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(productoRepository.findByIdParaActualizar(10L)).thenReturn(Optional.of(producto));

        assertThatThrownBy(() ->
                pedidoService.crear(1L, List.of(new ItemRequest(10L, 2)))
        ).isInstanceOf(StockInsuficienteException.class);

        assertThat(producto.getStock()).isEqualTo(1);
    }
}
