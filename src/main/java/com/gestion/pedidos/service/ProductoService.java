package com.gestion.pedidos.service;

import com.gestion.pedidos.domain.Producto;
import com.gestion.pedidos.dto.ProductoRequest;
import com.gestion.pedidos.dto.ProductoResponse;
import com.gestion.pedidos.exception.RecursoNoEncontradoException;
import com.gestion.pedidos.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoResponse crear(ProductoRequest request) {
        Producto p = new Producto();
        p.setNombre(request.nombre());
        p.setPrecio(request.precio());
        p.setStock(request.stock());
        return toResponse(repo.save(p));
    }

    public ProductoResponse buscarPorId(Long id) {
        Producto p = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto", id));
        return toResponse(p);
    }

    private ProductoResponse toResponse(Producto p) {
        return new ProductoResponse(p.getId(), p.getNombre(), p.getPrecio(), p.getStock());
    }
}
