package com.raffler.rinhabackend.mapper;

import com.raffler.rinhabackend.DTO.request.ClienteRequest;
import com.raffler.rinhabackend.domain.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteConverter {

    public Cliente convertToEntity(ClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setId(request.getId());
        cliente.setLimite(request.getLimite());
        cliente.setSaldo(request.getSaldo());
        return cliente;
    }
}