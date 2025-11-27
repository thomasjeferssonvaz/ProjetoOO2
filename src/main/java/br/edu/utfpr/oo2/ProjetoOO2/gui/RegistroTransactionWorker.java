package br.edu.utfpr.oo2.ProjetoOO2.gui;


import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Transaction;
import br.edu.utfpr.oo2.ProjetoOO2.gui.Exception.SaldoInsufucuenteException;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;
import br.edu.utfpr.oo2.ProjetoOO2.service.TransactionService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

public class RegistroTransactionWorker extends SwingWorker<Integer, Void> {

    private Transaction transaction;
    private JFrame frame;
    private GenericLoadingDialog genericLoadingDialog;
    private TransactionService transactionService;
    private int result;
    private String tipoTransaction;
    private double saldo;


    public RegistroTransactionWorker(JFrame frame, Transaction transaction, GenericLoadingDialog genericLoadingDialog) {
        this.frame = frame;
        this.transaction = transaction;
        this.genericLoadingDialog = genericLoadingDialog;
        this.transactionService = new TransactionService();
        this.tipoTransaction = transaction.getTipo();


    }


    @Override
    protected Integer doInBackground() throws Exception {

        if (tipoTransaction.equals("Receita")) {

            ContaService contaService = new ContaService();

            Conta conta = new Conta();
            conta = contaService.buscarPorNumeroConta(transaction.getNumero_conta());
            saldo = conta.getSaldo() + transaction.getValor();
            conta.setSaldo(saldo);
            int resultConta = contaService.atualizarSaldo(conta);

            int resultTransaction = transactionService.cadastrarTransactionReceita(transaction);

            if (resultConta == resultTransaction) {
                return 1;
            } else {
                return 0;
            }

        } else if (tipoTransaction.equals("Despesa")) {

            ContaService contaService = new ContaService();
            Conta conta = contaService.buscarPorNumeroConta(transaction.getNumero_conta());

            saldo = conta.getSaldo() - transaction.getValor();
            double saldoAntigo = conta.getSaldo();
            conta.setSaldo(saldo);


            int resultTransaction = transactionService.cadastrarTransactionDespesa(transaction,saldoAntigo);
            int resultConta = contaService.atualizarSaldo(conta);

            if (resultConta == resultTransaction) {
                return 1;
            } else {
                return 0;
            }

        }
        return null;
    }

    @Override
    protected void done() {

        try {
            get();

            DecimalFormat df = new DecimalFormat("#0.00");
            String saldoFormatado = df.format(saldo);
            JOptionPane.showMessageDialog(frame, "Lançamento realizado com sucesso!\nSaldo da Conta: R$" + saldoFormatado);
            genericLoadingDialog.dispose();

        } catch (InterruptedException ignore) {

        } catch (ExecutionException e) {
            e.getCause();

            if (e.getCause() instanceof SaldoInsufucuenteException) {
                JOptionPane.showMessageDialog(frame, "Saldo insuficiente", "Erro", JOptionPane.WARNING_MESSAGE);
            } else if (e.getCause() instanceof SQLException || e.getCause() instanceof IOException) {
                JOptionPane.showMessageDialog(frame, "Erro de infraestrutura \nDetalhes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Erro Inesperado, tente novamente\nDetalhes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            genericLoadingDialog.dispose();
        }

    }


}
