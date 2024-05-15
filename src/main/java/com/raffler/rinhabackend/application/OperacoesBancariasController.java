package com.raffler.rinhabackend.application;

import com.raffler.rinhabackend.DTO.request.TransacaoRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
public class OperacoesBancariasController {
    private final OperacoesBancariasService operacoesBancariasService;

    @Autowired
    public OperacoesBancariasController(OperacoesBancariasService operacoesBancariasService) {
        this.operacoesBancariasService = operacoesBancariasService;
    }

    @PostMapping("/{id}/transacoes")
    public ResponseEntity realizarTransacao(@PathVariable Long id, @Valid @RequestBody TransacaoRequest transacao) {
        return operacoesBancariasService.realizarTransacao(id, transacao);
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity visualizarExtrato(@PathVariable Long id) {
        return operacoesBancariasService.visualizarExtrato(id);
    }



}