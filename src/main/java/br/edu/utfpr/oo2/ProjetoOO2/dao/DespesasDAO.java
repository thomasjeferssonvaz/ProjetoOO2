package br.edu.utfpr.oo2.ProjetoOO2.dao;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Despesas;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public class DespesasDAO implements DAO<Despesas, Integer>{

    private final Connection con;

    public DespesasDAO(Connection con) {
        this.con = con;
    }

    private void fecharTudo(Statement stem, ResultSet rs) throws SQLException {
        if(rs != null) rs.close();
        if(stem != null) stem.close();
        if(con != null) con.close();
    }

    @Override
    public int cadastrar(Despesas entidade) throws SQLException {
        String sql = "INSERT INTO despesas (nome, descricao, tipo_despesa ,id_usuario) VALUES (?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getDescricao());
        ps.setString(3,entidade.getTipoDespesa());
        ps.setInt(4, entidade.getId_usuario());
        return ps.executeUpdate();
    }

    @Override
    public List<Despesas> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM despesas";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
//        return rs.;
        return null;
    }

    @Override
    public Despesas buscarPorChave(Integer chaveDePesquisa) throws SQLException {
        return null;
    }

    @Override
    public int atualizar(Despesas entidade, Integer chaveDePesquisa) throws SQLException {
        return 0;
    }

    @Override
    public int excluir(Integer chavePrimaria) throws SQLException {
        return 0;
    }
}
