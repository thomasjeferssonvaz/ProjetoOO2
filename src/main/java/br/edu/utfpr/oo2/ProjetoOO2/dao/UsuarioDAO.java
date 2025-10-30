package br.edu.utfpr.oo2.ProjetoOO2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;

public class UsuarioDAO implements DAO<Usuario, Integer>{

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
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Usuario buscarPorChave(Integer chavePrimaria) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int atualizar(Usuario entidade) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int excluir(Integer chavePrimaria) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
