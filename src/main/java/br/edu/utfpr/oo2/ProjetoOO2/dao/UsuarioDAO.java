package br.edu.utfpr.oo2.ProjetoOO2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;

public class UsuarioDAO implements DAO<Usuario, String>{

    private Connection conn;


    public UsuarioDAO(Connection conn) {
        this.conn = conn;
    }


    @Override
    public int cadastrar(Usuario usuario) throws SQLException {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into usuario (username, senha, nome, dataNascimento, sexo, tipo_usuario) values (?, ?, ?, ?, ?, ?)");
            st.setString(1, usuario.getUsername());
            st.setString(2, usuario.getSenha());
            st.setString(3, usuario.getNome());
            st.setDate(4, usuario.getDataNascimento());
            st.setString(5, usuario.getSexo());
            st.setString(6, usuario.getUsuarioTipo());

            return st.executeUpdate();
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }


    @Override
    public List<Usuario> buscarTodos() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select * from usuario order by nome");

            rs = st.executeQuery();

            List<Usuario> listaUsuario = new ArrayList<>();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setNome(rs.getString("nome"));
                usuario.setDataNascimento(rs.getDate("dataNascimento"));
                usuario.setSexo(rs.getString("sexo"));
                usuario.setUsuarioTipo(rs.getString("tipo_usuario"));

                listaUsuario.add(usuario);
            }
            return listaUsuario;
        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }


    @Override
    public Usuario buscarPorChave(String chaveDePesquisa) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("Select * from usuario where username = ? OR nome = ?");
            st.setString(1, chaveDePesquisa);
            st.setString(2, chaveDePesquisa);

            rs = st.executeQuery();


            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setNome(rs.getString("nome"));
                usuario.setDataNascimento(rs.getDate("dataNascimento"));
                usuario.setSexo(rs.getString("sexo"));
                usuario.setUsuarioTipo(rs.getString("tipo_usuario"));
                return usuario;
            }

            return null;

        } finally {
            BancoDados.finalizarStatement(st);
            BancoDados.finalizarResultSet(rs);
            BancoDados.desconectar();
        }
    }

    @Override
    public int excluir(String username) throws SQLException {
        PreparedStatement st = null;

        try {

            st = conn.prepareStatement("delete from usuario where username = ?");
            st.setString(1, username);


            return st.executeUpdate();

        } finally {

            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }



    @Override
    public int atualizar(Usuario usuarioNew, String usernameUsuario) throws SQLException {
        PreparedStatement st = null;

        try {

            st = conn.prepareStatement("update usuario set nome = ?, dataNascimento = ?, sexo = ? where username = ?");
            st.setString(1, usuarioNew.getNome());
            st.setDate(2, usuarioNew.getDataNascimento());
            st.setString(3, usuarioNew.getSexo());
            st.setString(4, usernameUsuario);


            return st.executeUpdate();

        } finally {

            BancoDados.finalizarStatement(st);
            BancoDados.desconectar();
        }
    }



}
