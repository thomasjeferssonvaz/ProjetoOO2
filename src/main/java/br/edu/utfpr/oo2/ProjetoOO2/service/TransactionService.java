package br.edu.utfpr.oo2.ProjetoOO2.service;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.dao.TransactionDAO;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Transaction;
import br.edu.utfpr.oo2.ProjetoOO2.gui.exception.SaldoInsufucuenteException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TransactionService {


    public TransactionService() {
    }

    public int cadastrarTransferencia(Transaction transSaida, Transaction transEntrada, double saldoSaida) throws Exception {

        if (transSaida == null || transEntrada == null) {
            throw new Exception("Conta null");
        }else if (transSaida.getValor() > saldoSaida) {
            throw new SaldoInsufucuenteException();
        } else if (transSaida.getNumero_conta() == transEntrada.getNumero_conta()) {
            throw new Exception("Mesma conta");
        } else if (transSaida.getValor()<0.0) {
            throw new Exception("Valor negativo");
        }

        transSaida.setValor(-Math.abs(transSaida.getValor()));

        Connection conn =BancoDados.conectar();
        int resultado =new TransactionDAO(conn).cadastrarTransferencia(transSaida, transEntrada);

        transSaida.setValor(Math.abs(transSaida.getValor())); //seta o valor para positivo novamente, para atualização do saldo da conta.
        return resultado;


    }

    public int cadastrarTransactionDespesa(Transaction transaction, double saldo) throws SaldoInsufucuenteException, SQLException, IOException {

        if (saldo < transaction.getValor()) {
            throw new SaldoInsufucuenteException();
        }


        Connection conn = BancoDados.conectar();

        return new TransactionDAO(conn).cadastrarReceitaDespesa(transaction);
    }

    public int cadastrarTransactionReceita(Transaction transaction)
            throws SQLException, IOException {

        Connection conn = BancoDados.conectar();
        return new TransactionDAO(conn).cadastrarReceitaDespesa(transaction);

    }

    public List<Transaction>listarTransacoes(int id) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new TransactionDAO(conn).listarTransacoes(id);

    }
}

