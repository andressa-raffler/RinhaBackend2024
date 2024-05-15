package com.raffler.rinhabackend.DTO.response;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class SaldoResponse {
    private Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public OffsetDateTime getData_extrato() {
        return data_extrato;
    }

    public void setData_extrato(OffsetDateTime data_extrato) {
        this.data_extrato = data_extrato;
    }

    public Long getLimite() {
        return limite;
    }

    public void setLimite(Long limite) {
        this.limite = limite;
    }

    private OffsetDateTime data_extrato;
    private Long limite;
}
