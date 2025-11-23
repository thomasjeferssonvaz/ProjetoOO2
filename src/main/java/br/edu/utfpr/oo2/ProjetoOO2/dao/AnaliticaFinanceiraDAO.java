package br.edu.utfpr.oo2.ProjetoOO2.dao;

import br.edu.utfpr.oo2.ProjetoOO2.entity.AnaliticaFinanceira;

import java.sql.*;
import java.util.List;

public class AnaliticaFinanceiraDAO implements DAO<AnaliticaFinanceira, Integer>{

    private final Connection con;

    public AnaliticaFinanceiraDAO(Connection con) {
        this.con = con;
    }

    private void fecharTudo(Statement stem, ResultSet rs) throws SQLException {
        if(rs != null) rs.close();
        if(stem != null) stem.close();
        if(con != null) con.close();
    }

    @Override
    public int cadastrar(AnaliticaFinanceira entidade) throws SQLException {
        String sql = "INSERT INTO analitica_financeira (nome,categoria_tipo,descricao, recorrencia ,id_usuario) VALUES (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, entidade.getNome());
        ps.setString(2,entidade.getCategoriaTipo());
        ps.setString(3, entidade.getDescricao());
        ps.setString(4,entidade.getRecorrencia());
        ps.setInt(5, entidade.getId_usuario());
        return ps.executeUpdate();
    }

    @Override
    public List<AnaliticaFinanceira> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM analiticaFinanceira;";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
//        return rs.;
        return null;
    }

    @Override
    public AnaliticaFinanceira buscarPorChave(Integer chaveDePesquisa) throws SQLException {
        return null;
    }

    @Override
    public int atualizar(AnaliticaFinanceira entidade, Integer chaveDePesquisa) throws SQLException {
        return 0;
    }

    @Override
    public int excluir(Integer chavePrimaria) throws SQLException {
        return 0;
    }
}
