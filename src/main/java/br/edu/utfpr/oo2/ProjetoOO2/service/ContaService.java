package br.edu.utfpr.oo2.ProjetoOO2.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.dao.ContaDAO;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;

public class ContaService {


    public ContaService() {

    }

    public int cadastrarConta(Conta conta) throws SQLException, IOException {

    	Connection conn = BancoDados.conectar();
    	
    	int resultado = new ContaDAO(conn).cadastrar(conta);
    	
    	if (resultado>0) {
    		System.out.println("Conta cadastrada com Sucesso");
			
		}else {
			System.out.println("Erro ao cadastrar conta");
		}
		return resultado;
    	
    }
}
