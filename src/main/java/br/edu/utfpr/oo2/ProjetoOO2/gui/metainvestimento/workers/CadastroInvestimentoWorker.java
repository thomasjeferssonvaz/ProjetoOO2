package br.edu.utfpr.oo2.ProjetoOO2.gui.metainvestimento.workers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Investimento;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.InvestimentoService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class CadastroInvestimentoWorker extends SwingWorker<Integer, Void> {

    private JFrame frameInvestimento;
    private GenericLoadingDialog genericLoadingDialog;
    private Investimento investimento;
    private InvestimentoService investimentoService;
    private int res;

    public CadastroInvestimentoWorker(JFrame frameInvestimento, GenericLoadingDialog genericLoadingDialog, Investimento investimento) {
        this.frameInvestimento = frameInvestimento;
        this.genericLoadingDialog = genericLoadingDialog;
        this.investimento = investimento;
        this.investimentoService = new InvestimentoService();
    }

    @Override
    protected Integer doInBackground() throws Exception {

        res = investimentoService.cadastrarInvestimento(investimento);
        return res;
    }

    @Override
    protected void done() {
        try {
            res = get();
            JOptionPane.showMessageDialog(frameInvestimento, "Meta de investimento Cadastrado com Sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            genericLoadingDialog.dispose();


        } catch (InterruptedException ignore) {
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                if (cause.getMessage().startsWith("Duplicate ")) {
                    JOptionPane.showMessageDialog(frameInvestimento, "Já possui uma meta com este nome\nTente novamente com outro nome", "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frameInvestimento, "Erro de infraestrutura\nDetalhes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(frameInvestimento, "Erro Inesperado\nDetalhes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } finally {
            genericLoadingDialog.dispose();
        }


    }
}
