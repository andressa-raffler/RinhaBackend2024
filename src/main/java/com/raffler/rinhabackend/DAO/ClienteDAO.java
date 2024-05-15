package com.raffler.rinhabackend.DAO;
import com.raffler.rinhabackend.domain.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
public class ClienteDAO {

    private final DataSource dataSource;

    @Autowired
    public ClienteDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    private static final Logger logger = LoggerFactory.getLogger(ClienteDAO.class);

    public Optional<Cliente> buscarPorId(Long id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM cliente WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Cliente cliente = new Cliente();
                        cliente.setId(rs.getLong("id"));
                        cliente.setLimite(rs.getLong("limite"));
                        cliente.setSaldo(rs.getLong("saldo"));
                        return Optional.of(cliente);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Erro ao buscar cliente por ID:", e);
        }
        return Optional.empty();
    }

    public void updateSaldoCliente(Long id, Long novoSaldo) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE cliente SET saldo = ? WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, novoSaldo);
                ps.setLong(2, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            logger.error("Erro ao atualizar saldo do cliente:", e);
        }
    }
}
