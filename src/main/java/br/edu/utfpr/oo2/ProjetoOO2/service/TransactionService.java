package br.edu.utfpr.oo2.ProjetoOO2.service;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.dao.ContaDAO;
import br.edu.utfpr.oo2.ProjetoOO2.dao.TransactionDAO;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Transaciton;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransactionService {
    public void transfer(Conta origem, Conta destino, BigDecimal valor, String descricao) throws SQLException, Exception {
        if(valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("O valor deves ser maior que zero");
        }
        if(origem.getIdConta() == destino.getIdConta()) {
            throw new Exception("Conta de origem e destino são iguais");
        }
        if(origem.getSaldo() < valor.doubleValue()) {
            throw new Exception("O saldo insuficiente");
        }

        Connection conn = BancoDados.conectar();

        try{
            conn.setAutoCommit(false);
            ContaDAO contaDAO = new ContaDAO(conn);
            TransactionDAO transactionDAO = new TransactionDAO(conn);

            origem.setSaldo(origem.getSaldo() - valor.doubleValue());
            destino.setSaldo(destino.getSaldo() + valor.doubleValue());

            contaDAO.atualizar(origem, origem.getIdConta());
            contaDAO.atualizar(destino, destino.getIdConta());

            Transaciton t = new Transaciton();
            t.setContaOrigem(origem);
            t.setContaDestino(destino);
            t.setValor(valor);
            t.setDataTransacao(LocalDateTime.now());
            t.setTipo("TRANSFERENCIA");
            t.setDescricao(descricao);

            transactionDAO.salvar(t);

            conn.commit();
        }catch(Exception e){
            if(conn != null) conn.rollback();
            throw new Exception("Erro ao salvar");
        }finally {
            if(conn != null) conn.close();
        }
    }
}
