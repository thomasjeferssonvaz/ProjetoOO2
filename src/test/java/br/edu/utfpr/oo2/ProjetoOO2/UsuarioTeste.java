package br.edu.utfpr.oo2.ProjetoOO2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.dao.UsuarioDAO;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.entity.UsuarioTipo;

public class UsuarioTeste {
	public static void main(String[] args) {
		try {
			Date data = new Date(07-10-2005);
			cadastrarUsuarioTeste("tjv", "thomas34", "Thomas Vaz", data, "Masculino", UsuarioTipo.ADMIN);
		} catch (SQLException | IOException e) {
			System.out.println("Erro: "+ e.getMessage());
		}
	}
	
public static void cadastrarUsuarioTeste(String username, 
		   								 String senha, 
		   								 String nome, 
		   								 Date dataNascimento, 
		   								 String sexo,
		   								 Enum usuarioTipo) throws SQLException, IOException {
	Usuario usuario = new Usuario();
	usuario.setUsername(username);
	usuario.setSenha(senha);
	usuario.setNome(nome);
	usuario.setDataNascimento(dataNascimento);
	usuario.setSexo(sexo);
	usuario.setUsuarioTipo(usuarioTipo);
	
	Connection conn = BancoDados.conectar();
	int resultado = new UsuarioDAO(conn).cadastrar(usuario);
	if(resultado > 0) {
		System.out.println("Usuário cadastrado com sucesso");
	} else {
		System.out.println("Erro ao cadastrar usuário");
	}
	
}

}
