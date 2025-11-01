package br.edu.utfpr.oo2.ProjetoOO2.dao;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ContaDAO implements DAO<Conta, Integer>{

    Connection conn;

    public ContaDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int cadastrar(Conta conta) throws SQLException {

        PreparedStatement st = null;
try{

        st = conn.prepareStatement("insert into conta (nome_banco, agencia,tipo_conta,id_usuario,saldo) values (?,?,?,?,?)");

        st.setString(1, conta.getNomeBanco());
        st.setInt(2, conta.getAgencia());
        st.setString(3, conta.getTipoConta());
        st.setInt(4, conta.getIdUsuario()); //Id do usuario que criou a conta
        st.setDouble(5, 0.0);

        return st.executeUpdate();

}finally {
    BancoDados.finalizarStatement(st);
    BancoDados.desconectar();
}
    }

    @Override
    public List<Conta> buscarTodos() throws SQLException {
        return null;
    }

    @Override
    public Conta buscarPorChave(Integer chavePrimaria) throws SQLException {
        return null;
    }

    @Override
    public int atualizar(Conta entidade) throws SQLException {
        return 0;
    }

    @Override
    public int excluir(Integer chavePrimaria) throws SQLException {
        return 0;
    }
}
