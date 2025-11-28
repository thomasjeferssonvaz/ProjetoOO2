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

public class TransferenciaWorker extends SwingWorker<Integer, Void> {


    private JFrame frameTransferencia;
    private GenericLoadingDialog genericLoadingDialog;
    private Transaction transactionSaida;
    private Transaction transactionEntrada;

    private TransactionService transactionService;
    private ContaService contaService;
    private Conta contaSaida;
    private Conta contaEntrada;
    private double saldoSaida;
    private int resultado;


    public TransferenciaWorker(JFrame frameTransferencia, GenericLoadingDialog genericLoadingDialog, Transaction transactionSaida, Transaction transactionEntrada) {
        this.frameTransferencia = frameTransferencia;
        this.genericLoadingDialog = genericLoadingDialog;
        this.transactionSaida = transactionSaida;
        this.transactionEntrada = transactionEntrada;

        this.transactionService = new TransactionService();
        this.contaService = new ContaService();
        this.contaSaida = new Conta();
        this.contaEntrada = new Conta();

    }

    @Override
    protected Integer doInBackground() throws Exception {
        contaEntrada = contaService.buscarPorNumeroConta(transactionEntrada.getNumero_conta());
        contaSaida = contaService.buscarPorNumeroConta(transactionSaida.getNumero_conta());//busca a conta de saida
        saldoSaida = contaSaida.getSaldo();

        int resCadastrarTransf = transactionService.cadastrarTransferencia(transactionSaida, transactionEntrada, saldoSaida);

        if (resCadastrarTransf == 2) {

            contaSaida.setSaldo(contaSaida.getSaldo() - transactionSaida.getValor());
            int resContaSaida = contaService.atualizarSaldo(contaSaida);

            contaEntrada.setSaldo(contaEntrada.getSaldo() + transactionEntrada.getValor());
            int resContaEntrada = contaService.atualizarSaldo(contaEntrada);

            if (resContaSaida == resContaEntrada) {
                this.resultado = 3;
                return resultado;
            }
        }


        return 0;
    }

    @Override
    protected void done() {

        try {
            resultado = get();

            DecimalFormat df = new DecimalFormat("#0.00");
            String saldoFormatadoSaida = df.format(contaSaida.getSaldo());
            String saldoFormatadoEntrada = df.format(contaEntrada.getSaldo());

            if (resultado == 3) {
                JOptionPane.showMessageDialog(frameTransferencia,
                        "Transferencia realizada com sucesso!\n Conta " + contaSaida.getNumeroConta() + " R$ " + saldoFormatadoSaida +
                        "\n Conta " + contaEntrada.getNumeroConta() + " R$ " + saldoFormatadoEntrada
                        , "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                frameTransferencia.dispose();
            }


        } catch (InterruptedException ignore) {
        } catch (ExecutionException e) {
            e.getMessage();
            if (e.getCause() instanceof SQLException || e.getCause() instanceof IOException) {
                JOptionPane.showMessageDialog(frameTransferencia, "Erro de Infraestrutura \nDetalhes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

            } else if (e.getCause() instanceof SaldoInsufucuenteException) {

                DecimalFormat df = new DecimalFormat("#0.00");
                String saldoSaida = df.format(contaSaida.getSaldo());
                JOptionPane.showMessageDialog(frameTransferencia, "Saldo insuficiente\nSaldo disponivel : " + saldoSaida, "Erro", JOptionPane.ERROR_MESSAGE);

            } else if (e.getCause().getMessage().equals("Conta null")) {
                JOptionPane.showMessageDialog(frameTransferencia, "Erro na transferencia, conta nao selecionada", "Erro", JOptionPane.ERROR_MESSAGE);

            } else if (e.getCause().getMessage().equals("Mesma conta")) {
                JOptionPane.showMessageDialog(frameTransferencia, "Transferencia entre a mesma conta\nTente novamente com contas diferentes", "Erro", JOptionPane.ERROR_MESSAGE);

            } else if (e.getMessage().startsWith("Valor")) {
                JOptionPane.showMessageDialog(frameTransferencia, "Insira um valor positivo", "Erro", JOptionPane.ERROR_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(frameTransferencia, "Erro Inesperado \nDetalhes:" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } finally {
            genericLoadingDialog.dispose();
        }


    }
}
