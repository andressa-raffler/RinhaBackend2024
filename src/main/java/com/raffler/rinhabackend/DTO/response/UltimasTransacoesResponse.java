package com.raffler.rinhabackend.DTO.response;

import java.time.OffsetDateTime;

public class UltimasTransacoesResponse {
    private double valor;
    private String tipo;
    private String descricao;
    private OffsetDateTime realizada_em;


    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public OffsetDateTime getRealizada_em() {
        return realizada_em;
    }

    public void setRealizada_em(OffsetDateTime realizada_em) {
        this.realizada_em = realizada_em;
    }
}