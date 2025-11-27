package br.edu.utfpr.oo2.ProjetoOO2.dao;

import br.edu.utfpr.oo2.ProjetoOO2.entity.AnaliticaFinanceira;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnaliticaFinanceiraDAO implements DAO<AnaliticaFinanceira, Integer> {

    private final Connection conn;

    public AnaliticaFinanceiraDAO(Connection con) {
        this.conn = con;
    }

    private void fecharTudo(Statement stem, ResultSet rs) throws SQLException {
        if (rs != null) rs.close();
        if (stem != null) stem.close();
        if (conn != null) conn.close();
    }

    @Override
    public int cadastrar(AnaliticaFinanceira entidade) throws SQLException {
        String sql = "INSERT INTO analitica_financeira (nome,categoria_tipo,descricao, recorrencia ,id_usuario) VALUES (?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, entidade.getNome());
        ps.setString(2, entidade.getCategoriaTipo());
        ps.setString(3, entidade.getDescricao());
        ps.setString(4, entidade.getRecorrencia());
        ps.setInt(5, entidade.getId_usuario());
        return ps.executeUpdate();
    }

    @Override
    public List<AnaliticaFinanceira> buscarTodos() throws SQLException {
        String sql = "SELECT * FROM analiticaFinanceira;";
        PreparedStatement ps = conn.prepareStatement(sql);
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

    public List<AnaliticaFinanceira> listarReceitas(int id_usuario) throws SQLException {

        List<AnaliticaFinanceira> analiticasBD = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM analitica_financeira WHERE categoria_tipo = 'RECEITA' AND id_usuario = ?");
        ResultSet rs = null;
        try {


            ps.setInt(1, id_usuario);
            rs = ps.executeQuery();

            while (rs.next()) {
                AnaliticaFinanceira analiticaBD = new AnaliticaFinanceira();
                analiticaBD.setId(rs.getInt("id"));
                analiticaBD.setNome(rs.getString("nome"));
                analiticaBD.setCategoriaTipo(rs.getString("categoria_tipo"));
                analiticaBD.setDescricao(rs.getString("descricao"));
                analiticaBD.setId_usuario(rs.getInt("id_usuario"));

                analiticasBD.add(analiticaBD);
            }
            return analiticasBD;
        }finally{
            BancoDados.finalizarResultSet(rs);
            BancoDados.finalizarStatement(ps);
            BancoDados.desconectar();
        }

    }

    public List<AnaliticaFinanceira> listarDespesas(int id_usuario) throws SQLException {

        List<AnaliticaFinanceira> analiticasBD = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM analitica_financeira WHERE categoria_tipo = 'DESPESA' AND id_usuario = ?");
        ResultSet rs = null;
        try {


            ps.setInt(1, id_usuario);
            rs = ps.executeQuery();

            while (rs.next()) {
                AnaliticaFinanceira analiticaBD = new AnaliticaFinanceira();
                analiticaBD.setId(rs.getInt("id"));
                analiticaBD.setNome(rs.getString("nome"));
                analiticaBD.setCategoriaTipo(rs.getString("categoria_tipo"));
                analiticaBD.setDescricao(rs.getString("descricao"));
                analiticaBD.setId_usuario(rs.getInt("id_usuario"));

                analiticasBD.add(analiticaBD);
            }
            return analiticasBD;
        }finally{
            BancoDados.finalizarResultSet(rs);
            BancoDados.finalizarStatement(ps);
            BancoDados.desconectar();
        }

    }

}
