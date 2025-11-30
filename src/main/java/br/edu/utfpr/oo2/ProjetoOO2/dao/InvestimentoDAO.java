package br.edu.utfpr.oo2.ProjetoOO2.dao;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Investimento;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            ps = conn.prepareStatement("INSERT INTO investimento (nome,tipo,local,aporte_mensal,id_usuario) VALUES (?,?,?,?,?)");
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getTipo());
            ps.setString(3, entidade.getLocal());
            ps.setDouble(4, entidade.getAporte());
            ps.setInt(5, entidade.getIdUsuario());

            int res = ps.executeUpdate();
            return res;
        } finally {
            BancoDados.finalizarStatement(ps);
            BancoDados.desconectar();
        }
    }

    public List<Investimento> listarInvestimentosPorId(int id) throws SQLException {
        List<Investimento> listaInvestimentos = new ArrayList<>();

        PreparedStatement ps = conn.prepareStatement("SELECT * FROM investimento WHERE id_usuario = ? ORDER BY aporte_mensal");
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        try {

            while (rs.next()) {
                Investimento investimento = new Investimento();
                investimento.setId(rs.getInt("id"));
                investimento.setNome(rs.getString("nome"));
                investimento.setTipo(rs.getString("tipo"));
                investimento.setLocal(rs.getString("local"));
                investimento.setAporte(rs.getDouble("aporte_mensal"));
                investimento.setIdUsuario(rs.getInt("id_usuario"));
                listaInvestimentos.add(investimento);
            }
            return listaInvestimentos;
        }finally {
            BancoDados.finalizarStatement(ps);
            BancoDados.finalizarResultSet(rs);
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

    public int excluirPorNome(String nomeInvestimento, int id_usuario) throws SQLException {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM investimento WHERE nome = ? AND id_usuario = ?");
            ps.setString(1, nomeInvestimento);
            ps.setInt(2, id_usuario);
            int res = ps.executeUpdate();
            return res;
        }finally {
            BancoDados.finalizarStatement(ps);
            BancoDados.desconectar();
        }
    }

}
