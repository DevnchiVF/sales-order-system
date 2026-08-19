package com.gestion.pedidos.service;

import com.gestion.pedidos.domain.Cliente;
import com.gestion.pedidos.dto.ClienteRequest;
import com.gestion.pedidos.dto.ClienteResponse;
import com.gestion.pedidos.exception.RecursoNoEncontradoException;
import com.gestion.pedidos.exception.RecursoYaExisteException;
import com.gestion.pedidos.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteResponse crear(ClienteRequest request){
        if(repo.existsByEmail(request.email())){
            throw new RecursoYaExisteException("email",request.email());
        }
        Cliente c = new Cliente();
        c.setNombre(request.nombre());
        c.setEmail(request.email());
        c.setTelefono(request.telefono());
        return toResponse(repo.save(c));
    }

    public ClienteResponse buscarPorId(Long id){
        Cliente c = repo.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", id));
        return toResponse(c);
    }

    private ClienteResponse toResponse(Cliente c){
        return new ClienteResponse(c.getId(), c.getNombre(), c.getEmail(), c.getTelefono());
    }
}
