package br.edu.utfpr.oo2.ProjetoOO2.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.dao.ContaDAO;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;



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

    public List<Conta> listarContas() throws SQLException, IOException {
    	
    	

        Connection conn = BancoDados.conectar();
        List<Conta>contasDB = new ContaDAO(conn).buscarTodos();
        
        return contasDB;
    }

    public Conta buscarPorId(int id) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new ContaDAO(conn).buscarPorChave(id);
    }

    public Conta buscarPorNumeroConta(int numero_conta) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new ContaDAO(conn).buscarPorNumeroConta(numero_conta);
    }

    public int atualizarConta(Conta contaNew, Conta contaOld) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new ContaDAO(conn).atualizar(contaNew,contaOld.getIdConta());

    }
    
    
    public List<Conta> buscarPorUsuario(Usuario userLogado) throws SQLException, IOException{
    	
    	
    	Connection conn = BancoDados.conectar();

    	return new ContaDAO(conn).buscarPorUsuario(userLogado.getId());
    	
    }

    public int atualizarSaldo(Conta conta) throws SQLException, IOException {
        double saldoNovo = conta.getSaldo();
        int id = conta.getIdConta();
        Connection conn = BancoDados.conectar();
        return new ContaDAO(conn).atualizarSaldo(saldoNovo, id);
    }
}
