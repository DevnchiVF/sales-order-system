package com.gestion.pedidos.service;

import com.gestion.pedidos.domain.Cliente;
import com.gestion.pedidos.domain.Pedido;
import com.gestion.pedidos.domain.PedidoItem;
import com.gestion.pedidos.domain.Producto;
import com.gestion.pedidos.dto.ItemRequest;
import com.gestion.pedidos.dto.ItemResponse;
import com.gestion.pedidos.dto.PedidoResponse;
import com.gestion.pedidos.exception.RecursoNoEncontradoException;
import com.gestion.pedidos.exception.StockInsuficienteException;
import com.gestion.pedidos.repository.ClienteRepository;
import com.gestion.pedidos.repository.PedidoRepository;
import com.gestion.pedidos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final ClienteRepository clienteRepository;

    @Transactional
    public PedidoResponse crear(Long clienteId, List<ItemRequest> items) {

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", clienteId));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);

        for (ItemRequest itemReq : items) {

            Producto producto = productoRepository.findByIdParaActualizar(itemReq.productoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto", itemReq.productoId()));

            if (producto.getStock() < itemReq.cantidad()) {
                throw new StockInsuficienteException(
                        producto.getNombre(), producto.getStock(), itemReq.cantidad());
            }

            producto.setStock(producto.getStock() - itemReq.cantidad());

            PedidoItem item = new PedidoItem();
            item.setProducto(producto);
            item.setCantidad(itemReq.cantidad());
            item.setPrecioUnitario(producto.getPrecio());
            pedido.agregarItem(item);
        }

        return toResponse(pedidoRepository.save(pedido));
    }

    @Transactional(readOnly = true)
    public PedidoResponse buscarPorId(Long id) {
        Pedido p = pedidoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Pedido", id));
        return toResponse(p);
    }

    private PedidoResponse toResponse(Pedido p) {
        List<ItemResponse> itemsResponse = p.getItems().stream()
                .map(item -> new ItemResponse(
                        item.getProducto().getId(),
                        item.getProducto().getNombre(),
                        item.getCantidad(),
                        item.getPrecioUnitario()
                ))
                .toList();

        return new PedidoResponse(
                p.getId(),
                p.getCliente().getId(),
                p.getEstado(),
                p.getCreadoEn(),
                itemsResponse
        );
    }
}
