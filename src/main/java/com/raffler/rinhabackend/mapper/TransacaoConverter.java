package com.raffler.rinhabackend.mapper;

import com.raffler.rinhabackend.DTO.request.TransacaoRequest;
import com.raffler.rinhabackend.DTO.response.TransacaoResponse;
import com.raffler.rinhabackend.domain.Cliente;
import com.raffler.rinhabackend.domain.Transacao;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class TransacaoConverter {


    public Transacao convertTransacaoRequestToEntity(TransacaoRequest request, Cliente cliente, OffsetDateTime realizadaEm) {
        Transacao transacao = new Transacao();
        transacao.setDescricao(request.getDescricao());
        transacao.setTipo(request.getTipo().toLowerCase());
        transacao.setValor(request.getValor().longValue());
        transacao.setCliente(cliente);
        transacao.setRealizadaEm(realizadaEm);
        return transacao;
    }

    public TransacaoResponse convertEntityToTransacaoResponse(Cliente cliente){
        TransacaoResponse transacaoResponse = new TransacaoResponse();
        transacaoResponse.setLimite(cliente.getLimite());
        transacaoResponse.setSaldo(cliente.getSaldo());
        return transacaoResponse;
    }

}
