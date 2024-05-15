package com.raffler.rinhabackend.application;

import com.raffler.rinhabackend.DAO.ClienteDAO;
import com.raffler.rinhabackend.DTO.request.TransacaoRequest;
import com.raffler.rinhabackend.DTO.response.ExtratoResponse;
import com.raffler.rinhabackend.DTO.response.SaldoResponse;
import com.raffler.rinhabackend.DTO.response.TransacaoResponse;
import com.raffler.rinhabackend.DTO.response.UltimasTransacoesResponse;
import com.raffler.rinhabackend.domain.Cliente;
import com.raffler.rinhabackend.DAO.TransacaoDAO;
import com.raffler.rinhabackend.mapper.TransacaoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OperacoesBancariasService {

    private final ClienteDAO clienteDAO;
    private final TransacaoDAO transacaoDAO;
    private final TransacaoConverter transacaoConverter;
    private TransacaoResponse transacaoResponse;
    private SaldoResponse saldoResponse;

    @Autowired
    public OperacoesBancariasService(ClienteDAO clienteDAO, TransacaoDAO transacaoDAO, TransacaoConverter transacaoConverter,
                                     TransacaoResponse transacaoResponse, SaldoResponse saldoResponse) {
        this.clienteDAO = clienteDAO;
        this.transacaoDAO = transacaoDAO;
        this.transacaoConverter = transacaoConverter;
        this.transacaoResponse = transacaoResponse;
        this.saldoResponse = saldoResponse;
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
         return clienteDAO.buscarPorId(id);
    }


    private Long calcularNovoSaldo(Cliente cliente, TransacaoRequest transacao) {
        Long saldo = cliente.getSaldo();
        Long valorTransacao = transacao.getValor().longValue();
        String tipoTransacao = transacao.getTipo().toLowerCase();
        switch (tipoTransacao) {
            case "r":
                saldo += valorTransacao;
                break;
            case "d":
                saldo -= valorTransacao;
                break;
            default:
                throw new IllegalArgumentException("Tipo de transação inválido: " + tipoTransacao);
        }
        return saldo;
    }


    private boolean limiteEhValido(Cliente cliente, Long novoSaldo){
        if((novoSaldo + cliente.getLimite()) >= 0){
            return true;
        }
        return false;
    }

    public ResponseEntity realizarTransacao(Long id, TransacaoRequest transacao) {
        ResponseEntity<String> validacaoResponse = validarTransacao(id, transacao);
        if (!validacaoResponse.getStatusCode().is2xxSuccessful()) {
            return validacaoResponse;
        }

        Optional<Cliente> clienteOptional = buscarClientePorId(id);
        if(clienteOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }

        Cliente cliente = clienteOptional.get();
        Long novoSaldo = calcularNovoSaldo(cliente, transacao);
        if(!limiteEhValido(cliente, novoSaldo)) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("O cliente não possui limite para essa transação.");
        }
        cliente.setSaldo(novoSaldo);
        atualizarSaldo(cliente);
        atualizarTransacao(transacao, cliente);
        transacaoResponse = transacaoConverter.convertEntityToTransacaoResponse(cliente);
        return ResponseEntity.ok().body(transacaoResponse);
    }

    private void atualizarSaldo(Cliente clienteAtualizado) {
        try {
            clienteDAO.updateSaldoCliente(clienteAtualizado.getId(),clienteAtualizado.getSaldo());
        }
        catch (Exception e ){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Erro ao atualizar saldo", e);
        }
    }

    private void atualizarTransacao(TransacaoRequest transacao, Cliente cliente) {
        try {
            OffsetDateTime realizadaEm = OffsetDateTime.now();
            transacaoDAO.saveTransacao(transacaoConverter.convertTransacaoRequestToEntity(transacao, cliente, realizadaEm));
        }
        catch (Exception e ){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Erro ao atualizar transação", e);
        }
    }


    private ResponseEntity<String> validarTransacao(Long id, TransacaoRequest transacao) {

        if (transacao == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Transação inválida.");
        }
        if(id == null || id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Id do cliente inválido.");
        }
        BigDecimal valorTransacao = transacao.getValor();
        if (valorTransacao == null || valorTransacao.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Valor da transação inválido.");
        }

        if (transacao.getTipo() == null || (!"r".equalsIgnoreCase(transacao.getTipo()) && !"d".equalsIgnoreCase(transacao.getTipo()))) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("O tipo da transação deve ser 'r' para recebível ou 'd' para débito.");
        }
        if (transacao.getDescricao().length() < 1 || transacao.getDescricao().length() > 10) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("A descrição da transação deve ter entre 1 e 10 caracteres.");
        }
        if (!(transacao.getValor().scale() == 0)) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("O valor da transação deve ser um número inteiro.");
        }
        if (transacao.getValor().compareTo(BigDecimal.ZERO) < 0) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body("O valor da transação deve ser maior ou igual a zero.");
        }

        return ResponseEntity.ok().body("A transação é válida.");
    }


    public ResponseEntity visualizarExtrato(Long id) {
       Cliente cliente;
        ExtratoResponse extratoResponse = new ExtratoResponse();
       if(buscarClientePorId(id).isPresent()){
           cliente = buscarClientePorId(id).get();
           List<UltimasTransacoesResponse> transacaos = listarDezUltimasTransacoes(id);
           extratoResponse.setUltimas_transacoes(transacaos);
           saldoResponse.setData_extrato(OffsetDateTime.now());
           saldoResponse.setTotal(cliente.getSaldo());
           saldoResponse.setLimite(cliente.getLimite());
           extratoResponse.setSaldo(saldoResponse);
           return ResponseEntity.ok().body(extratoResponse);
       }
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
    }

    private List<UltimasTransacoesResponse> listarDezUltimasTransacoes(Long id) {
        return transacaoDAO.buscarUltimasTransacoes(id);
    }

}
