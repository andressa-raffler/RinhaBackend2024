SET client_encoding = 'UTF8';

CREATE TABLE cliente (id SERIAL PRIMARY KEY,
                      limite BIGINT NOT NULL,
                      saldo BIGINT NOT NULL);

INSERT INTO cliente (id, limite, saldo) VALUES (1, 100000, 0),
                                               (2, 80000, 0),
                                               (3, 1000000, 0),
                                               (4, 10000000, 0),
                                               (5, 500000, 0);


CREATE TABLE transacao (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    valor BIGINT NOT NULL,
    tipo CHAR(1) NOT NULL CHECK (tipo IN ('r', 'd')),
    descricao VARCHAR(10) NOT NULL,
    cliente_id BIGINT NOT NULL,
    realizada_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- Adicionando o campo realizada_em
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);