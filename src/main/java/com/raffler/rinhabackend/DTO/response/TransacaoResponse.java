package com.raffler.rinhabackend.DTO.response;

import org.springframework.stereotype.Component;

@Component
public class TransacaoResponse {
    private long limite;
    private long saldo;

    public long getSaldo() {
        return saldo;
    }

    public void setSaldo(long saldo) {
        this.saldo = saldo;
    }

    public long getLimite() {
        return limite;
    }

    public void setLimite(long limite) {
        this.limite = limite;
    }
}
