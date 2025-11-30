package br.edu.utfpr.oo2.ProjetoOO2.gui;


import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;
import br.edu.utfpr.oo2.ProjetoOO2.service.TransactionService;

import javax.swing.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;


public class MainWorker extends SwingWorker<Void, Void> {

    private JLabel label;
    private ContaService contaService;
    private Usuario userLogado;
    private JFrame frameMain;
    private JTable tbExtrato;
    private JTable tbMeta;
    private TransactionService transactionService;


    public MainWorker(JFrame frameMain, JLabel label, Usuario userLogado) {
        this.label = label;
        this.userLogado = userLogado;
        this.frameMain = frameMain;
        this.tbExtrato = tbExtrato;
        this.tbMeta = tbMeta;
        this.contaService = new ContaService();
        this.transactionService = new TransactionService();
    }

    @Override
    protected Void doInBackground() {
        try {
            // Atualizar o saldo
            double saldo = contaService.buscarSaldoTotal(userLogado.getId());
            DecimalFormat df = new DecimalFormat("0.00");
            String saldoFormat = df.format(saldo);
            label.setText("R$ " + saldoFormat);





        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(frameMain,
                    "Erro ao atualizar saldo\nDetalhes: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }










        return null;
    }
}
