package br.edu.utfpr.oo2.ProjetoOO2.dao;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Investimento;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InvestimentoDAO implements DAO<Investimento, Integer> {

    Connection conn;

    public InvestimentoDAO(Connection conn) {
        this.conn = conn;
    }


    @Override
    public int cadastrar(Investimento entidade) throws SQLException {

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("INSERT INTO investimento (nome,tipo,aporte_mensal,id_usuario) VALUES (?,?,?,?)");
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getTipo());
            ps.setDouble(3, entidade.getAporte());
            ps.setInt(4, entidade.getIdUsuario());

            int res = ps.executeUpdate();
            return res;
        } finally {
            BancoDados.finalizarStatement(ps);
            BancoDados.desconectar();
        }
    }

    @Override
    public List<Investimento> buscarTodos() throws SQLException {
        return null;
    }

    @Override
    public Investimento buscarPorChave(Integer chaveDePesquisa) throws SQLException {
        return null;
    }

    @Override
    public int atualizar(Investimento entidade, Integer chaveDePesquisa) throws SQLException {
        return 0;
    }

    @Override
    public int excluir(Integer chavePrimaria) throws SQLException {
        return 0;
    }
}
