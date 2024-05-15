package com.raffler.rinhabackend.DTO.response;

import java.util.ArrayList;
import java.util.List;

public class ExtratoResponse {
    private SaldoResponse saldo;
    private List<UltimasTransacoesResponse> ultimas_transacoes = new ArrayList<>();

    public SaldoResponse getSaldo() {
        return saldo;
    }

    public void setSaldo(SaldoResponse saldo) {
        this.saldo = saldo;
    }

    public List<UltimasTransacoesResponse> getUltimas_transacoes() {
        return ultimas_transacoes;
    }

    public void setUltimas_transacoes(List<UltimasTransacoesResponse> ultimas_transacoes) {
        this.ultimas_transacoes = ultimas_transacoes;
    }

}
