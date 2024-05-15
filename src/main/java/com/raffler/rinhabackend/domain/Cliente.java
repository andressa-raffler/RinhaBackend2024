package com.raffler.rinhabackend.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;
    @Column(nullable = false)
    private Long limite;
    @Column(nullable = false)
    private Long saldo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLimite(Long limite) {
        this.limite = limite;
    }

    public void setSaldo(Long saldo) {
        this.saldo = saldo;
    }

    public Long getLimite() {
        return limite;
    }

    public Long getSaldo() {
        return saldo;
    }

    public Cliente(Long id, Long limite, Long saldo) {
        this.id = id;
        this.limite = limite;
        this.saldo = saldo;
    }

    public Cliente() {
    }



}

