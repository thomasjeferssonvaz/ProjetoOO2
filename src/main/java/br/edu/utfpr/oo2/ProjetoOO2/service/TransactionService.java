package br.edu.utfpr.oo2.ProjetoOO2.service;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.dao.TransactionDAO;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Transaction;
import br.edu.utfpr.oo2.ProjetoOO2.gui.Exception.SaldoInsufucuenteException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionService {


    public TransactionService() {
    }

    ;

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
}

