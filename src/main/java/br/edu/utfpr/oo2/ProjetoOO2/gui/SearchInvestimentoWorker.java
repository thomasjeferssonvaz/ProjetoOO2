package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Investimento;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.InvestimentoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchInvestimentoWorker extends SwingWorker<List<Investimento>, Void> {

    private JFrame frameInvestimento;
    private GenericLoadingDialog genericLoadingDialog;
    private JTable investimentoTable;
    private Usuario userLogado;
    private List<Investimento> investimentos;
    private InvestimentoService investimentoService;

    public SearchInvestimentoWorker(JFrame frameInvestimento, GenericLoadingDialog genericLoadingDialog, JTable investimentoTable, Usuario userLogado) {
        this.frameInvestimento = frameInvestimento;
        this.genericLoadingDialog = genericLoadingDialog;
        this.investimentoTable = investimentoTable;
        this.userLogado = userLogado;
        this.investimentoService = new InvestimentoService();
    }

    @Override
    protected List<Investimento> doInBackground() throws Exception {
        investimentos = investimentoService.listarInvestimentosPorId(userLogado.getId());

        return investimentos;
    }


    @Override
    protected void done() {

        try {
            investimentos = get();

            if (investimentos.size() > 0) {

                DefaultTableModel model = (DefaultTableModel) investimentoTable.getModel();
                model.setRowCount(0);

                for (Investimento investimento : investimentos) {

                    model.addRow(new Object[]{
                            investimento.getId(),
                            investimento.getNome(),
                            investimento.getTipo(),
                            investimento.getLocal(),
                            investimento.getAporte()
                    });

                }
            } else {
                JOptionPane.showMessageDialog(frameInvestimento, "Não há metas de Investimento Cadastrada", "Erro", JOptionPane.ERROR_MESSAGE);
            }


        } catch (InterruptedException ignore) {
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                JOptionPane.showMessageDialog(frameInvestimento, "Erro ao tentar buscar metas de Investimento\nDetalhes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frameInvestimento, "Erro Inesperado \nDetalhes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }finally {
            genericLoadingDialog.dispose();
        }

    }
}
