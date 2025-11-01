package br.edu.utfpr.oo2.ProjetoOO2.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.entity.UsuarioTipo;

public class UsuarioDAOTeste {
	public static void main(String[] args) {
		try {
			LocalDate dataNascimento = LocalDate.of(2005,10,07);//Recebe a data do Jframe
			Date dataNascimentoSql = Date.valueOf(dataNascimento);//Transforma a data de LocalDate para java.sql.Date
			cadastrarUsuarioTeste("thiago", "thiago", "Thiago Algustin  ", dataNascimentoSql, "Masculino", UsuarioTipo.ADMIN);//Cria o usuário utilizando a data correta
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
