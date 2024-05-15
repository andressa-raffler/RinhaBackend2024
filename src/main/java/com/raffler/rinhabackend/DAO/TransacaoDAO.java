package com.raffler.rinhabackend.DAO;

import com.raffler.rinhabackend.DTO.response.UltimasTransacoesResponse;
import com.raffler.rinhabackend.domain.Transacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransacaoDAO {
    private static final Logger logger = LoggerFactory.getLogger(TransacaoDAO.class);

    private final DataSource dataSource;

    @Autowired
    public TransacaoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<UltimasTransacoesResponse> buscarUltimasTransacoes(Long idUsuario) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM transacao WHERE cliente_id = ? ORDER BY realizada_em DESC LIMIT 10")) {
            ps.setLong(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                List<UltimasTransacoesResponse> transacoes = new ArrayList<>();
                while (rs.next()) {
                    UltimasTransacoesResponse transacao = new UltimasTransacoesResponse();
                    transacao.setValor(rs.getLong("valor"));
                    transacao.setTipo(rs.getString("tipo"));
                    transacao.setDescricao(rs.getString("descricao"));
                    transacao.setRealizada_em(rs.getObject("realizada_em", OffsetDateTime.class));
                    transacoes.add(transacao);
                }
                return transacoes;
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar últimas transações:", e);
        }
        return new ArrayList<>();
    }

    public void saveTransacao(Transacao transacao) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO transacao (valor, tipo, descricao, cliente_id, realizada_em) VALUES (?, ?, ?, ?, ?)")) {
            ps.setLong(1, transacao.getValor());
            ps.setString(2, transacao.getTipo().toLowerCase());
            ps.setString(3, transacao.getDescricao());
            ps.setLong(4, transacao.getCliente().getId());
            ps.setObject(5, transacao.getRealizadaEm());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.error("Erro ao salvar transação:", e);
        }
    }
}
