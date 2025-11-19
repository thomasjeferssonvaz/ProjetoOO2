package br.edu.utfpr.oo2.ProjetoOO2.dao;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO implements DAO<Conta, Integer> {

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

        PreparedStatement st = null;
        ResultSet rs = null;
        List<Conta> contas = new ArrayList<>();

        try {

            st = conn.prepareStatement("select * from conta");
            rs = st.executeQuery();

            while (rs.next()) {
                Conta conta = new Conta();
                conta.setIdConta(rs.getInt("id_conta"));
                conta.setNomeBanco(rs.getString("nome_banco"));
                conta.setNumeroConta(rs.getInt("numero_conta"));
                conta.setAgencia(rs.getInt("agencia"));
                conta.setTipoConta(rs.getString("tipo_conta"));
                conta.setIdUsuario(rs.getInt("id_usuario"));
                conta.setSaldo(rs.getDouble("saldo"));
                contas.add(conta);
            }

            return contas;

        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }

    @Override
    public Conta buscarPorChave(Integer chavePrimaria) throws SQLException {

        Conta conta = new Conta();
        ResultSet rs = null;
        PreparedStatement st = null;


        try {


            st = conn.prepareStatement("select * from conta where id_conta=?");
            st.setInt(1, chavePrimaria);
            rs = st.executeQuery();

            if (rs.next()) {
                conta.setIdConta(rs.getInt("id_conta"));
                conta.setNomeBanco(rs.getString("nome_banco"));
                conta.setNumeroConta(rs.getInt("numero_conta"));
                conta.setAgencia(rs.getInt("agencia"));
                conta.setTipoConta(rs.getString("tipo_conta"));
                conta.setSaldo(rs.getDouble("saldo"));
                conta.setIdUsuario(rs.getInt("id_usuario"));

                return conta;
            } else {
                return null;
            }

        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }

    @Override
    public int atualizar(Conta contaNew, Integer id_conta) throws SQLException {

        PreparedStatement st = null;
        try {

            st = conn.prepareStatement("update conta set nome_banco=?, agencia=?,numero_conta=?, tipo_conta=? where id_conta=?");
            st.setString(1, contaNew.getNomeBanco());
            st.setInt(2, contaNew.getAgencia());
            st.setInt(3, contaNew.getNumeroConta());
            st.setString(4, contaNew.getTipoConta());
            st.setInt(5, contaNew.getIdConta());

            return st.executeUpdate();

        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }

    @Override
    public int excluir(Integer chavePrimaria) throws SQLException {

        PreparedStatement st = null;
        try {


        st = conn.prepareStatement("delete from conta where id_conta=?");
        st.setInt(1, chavePrimaria);

        return st.executeUpdate();
        }finally {

            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }
}
