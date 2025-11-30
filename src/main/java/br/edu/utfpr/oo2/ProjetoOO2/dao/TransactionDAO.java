package br.edu.utfpr.oo2.ProjetoOO2.dao;


import br.edu.utfpr.oo2.ProjetoOO2.entity.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private final Connection conn;

    public TransactionDAO(Connection conn) {
        this.conn = conn;
    }

    public int cadastrarTransferencia(
            Transaction transSaida,
            Transaction transEntrada) throws SQLException {

        PreparedStatement psSaida = null;
        PreparedStatement psEntrada = null;

        try {
            conn.setAutoCommit(false);

            // SAÍDA (origem) -> valor negativo
            psSaida = conn.prepareStatement(
                    "INSERT INTO transacao (numero_conta, valor, data_transacao, tipo, analitica, descricao, id_usuario) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            psSaida.setInt(1, transSaida.getNumero_conta());
            psSaida.setDouble(2, transSaida.getValor()); // já deve vir NEGATIVO
            psSaida.setDate(3, transSaida.getDataTransacao());
            psSaida.setString(4, transSaida.getTipo());
            psSaida.setString(5, transSaida.getAnaliticaFinanceira());
            psSaida.setString(6, transSaida.getDescricao());
            psSaida.setInt(7, transSaida.getId_usuario());
            int res = psSaida.executeUpdate();

            // ENTRADA (destino) -> valor positivo
            psEntrada = conn.prepareStatement(
                    "INSERT INTO transacao (numero_conta, valor, data_transacao, tipo, analitica, descricao, id_usuario) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );

            psEntrada.setInt(1, transEntrada.getNumero_conta());
            psEntrada.setDouble(2, transEntrada.getValor()); // já deve vir POSITIVO
            psEntrada.setDate(3, transEntrada.getDataTransacao());
            psEntrada.setString(4, transEntrada.getTipo());
            psEntrada.setString(5, transEntrada.getAnaliticaFinanceira());
            psEntrada.setString(6, transEntrada.getDescricao());
            psEntrada.setInt(7, transEntrada.getId_usuario());
            res += psEntrada.executeUpdate();

            conn.commit();
            return res;

        } catch (SQLException e) {
            conn.rollback();
            throw e;

        } finally {
            BancoDados.finalizarStatement(psSaida);
            BancoDados.finalizarStatement(psEntrada);
            conn.setAutoCommit(true);
            // NÃO FECHA A CONEXÃO AQUI
        }
    }


    public int cadastrarReceitaDespesa(Transaction transaction) throws SQLException {

        PreparedStatement ps = conn.prepareStatement("INSERT INTO transacao (numero_conta,valor,data_transacao,tipo,analitica,descricao,id_usuario) VALUES (?,?,?,?,?,?,?)");

        try {

            ps.setInt(1, transaction.getNumero_conta());
            ps.setDouble(2, transaction.getValor());
            ps.setDate(3, transaction.getDataTransacao());
            ps.setString(4, transaction.getTipo());
            ps.setString(5, transaction.getAnaliticaFinanceira());
            ps.setString(6, transaction.getDescricao());
            ps.setInt(7, transaction.getId_usuario());

            int res = ps.executeUpdate();

            return res;
        } finally {
            BancoDados.finalizarStatement(ps);
            BancoDados.desconectar();
        }

    }


    public List<Transaction> listarTransacoes(int id) throws SQLException {
        List<Transaction> listaTransacoes = new ArrayList<>();

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM transacao WHERE id_usuario = ? ORDER BY data_transacao DESC");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        try {



                while (rs.next()) {
                    Transaction transSaida = new Transaction();
                    transSaida.setNumero_conta(rs.getInt("numero_conta"));
                    transSaida.setValor(rs.getDouble("valor"));
                    transSaida.setDataTransacao(rs.getDate("data_transacao"));
                    transSaida.setTipo(rs.getString("tipo"));
                    transSaida.setAnaliticaFinanceira(rs.getString("analitica"));
                    transSaida.setDescricao(rs.getString("descricao"));
                    transSaida.setId_usuario(rs.getInt("id_usuario"));
                    listaTransacoes.add(transSaida);

                }
                return listaTransacoes;
            }finally {
                BancoDados.finalizarStatement(ps);
                BancoDados.finalizarResultSet(rs);

            }
    }



}

