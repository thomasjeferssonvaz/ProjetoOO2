package br.edu.utfpr.oo2.ProjetoOO2.dao;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Transaciton;

import java.sql.*;

public class TransactionDAO {
    private Connection connection;

    public TransactionDAO(Connection connection) {
        this.connection = connection;
    }

    public void salvar(Transaciton transaciton) throws SQLException {
        String sql = "INSERT INTO transacao (id_conta_origem, id_conta_destino, valor, data_transacao, tipo, descricao) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            if(transaciton.getContaOrigem() != null){
                stmt.setLong(1, transaciton.getContaOrigem().getIdConta());
            }else{
                stmt.setNull(1, Types.BIGINT);
            }

            if(transaciton.getContaDestino() != null){
                stmt.setLong(2, transaciton.getContaDestino().getIdConta());
            }else {
                stmt.setNull(2, Types.BIGINT);
            }

            stmt.setBigDecimal(3, transaciton.getValor());
            stmt.setTimestamp(4, Timestamp.valueOf(transaciton.getDataTransacao()));
            stmt.setString(5, transaciton.getTipo());
            stmt.setString(6, transaciton.getDescricao());

            stmt.execute();
        }
    }
}
