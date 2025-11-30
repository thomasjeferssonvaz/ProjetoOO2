package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Transaction;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.service.TransactionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ExtratoWorker extends SwingWorker<List<Transaction>, Void> {

    private JFrame frameMain;
    private JTable tabelaExtrato;
    private TransactionService transactionService;
    private Usuario userLogado;
    private List<Transaction> listaTransacoes;

    public ExtratoWorker(JFrame frameMain ,JTable tabelaExtrato, Usuario userLogado) {
        this.frameMain = frameMain;
        this.tabelaExtrato = tabelaExtrato;
        this.userLogado = userLogado;
        this.transactionService = new TransactionService();
    }

    @Override
    protected List doInBackground() throws Exception {

        listaTransacoes = transactionService.listarTransacoes(userLogado.getId());
        return listaTransacoes;
    }


    @Override
    protected void done() {

        try {

            listaTransacoes = get();

            DefaultTableModel model = (DefaultTableModel) tabelaExtrato.getModel();
            model.setRowCount(0);

            for (Transaction transacao : listaTransacoes) {
                model.addRow(new Object[]{
                        transacao.getDataTransacao(),
                        transacao.getNumero_conta(),
                        transacao.getAnaliticaFinanceira(),
                        transacao.getValor()

                });
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(frameMain, "Erro ao buscar Extrato", "Erro ", JOptionPane.ERROR_MESSAGE);
        }

    }
}
