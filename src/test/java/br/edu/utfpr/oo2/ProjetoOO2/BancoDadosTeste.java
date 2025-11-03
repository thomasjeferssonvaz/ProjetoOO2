package br.edu.utfpr.oo2.ProjetoOO2;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class BancoDadosTeste {
	public static void main(String[] args) {
		try {
			conectarTeste();
			desconectarTeste();
		} catch (SQLException | IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	
	public static void conectarTeste() throws SQLException, IOException {
		Connection conn = BancoDados.conectar();
		
		if (conn != null) {
			System.out.println("Conexão realizada com sucesso");
		} else {
			System.out.println("Erro ao realizar conexão");
		}
		
		
	}
	
	public static void desconectarTeste() throws SQLException, IOException {
		Connection conn =  BancoDados.desconectar();
		
		if (conn == null) {
			System.out.println("Desconexão realizada com sucesso");
		} else {
			System.out.println("Falha ao desconectar");
		}
	}
}
