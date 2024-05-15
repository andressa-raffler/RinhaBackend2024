package com.raffler.rinhabackend.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;


@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @Column(nullable = false)
    private Long valor;

    @Column(nullable = false, length = 1)
    @Pattern(regexp = "[rd]", message = "O tipo deve ser 'r' para recebível ou 'd' para débito.")
    private String tipo;

    @Column(nullable = false)
    @Size(min = 1, max = 10)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "realizada_em", nullable = false)
    private OffsetDateTime realizadaEm;


    public OffsetDateTime getRealizadaEm() {
        return realizadaEm;
    }

    public void setRealizadaEm(OffsetDateTime realizadaEm) {
        this.realizadaEm = realizadaEm;
    }


    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }



}
