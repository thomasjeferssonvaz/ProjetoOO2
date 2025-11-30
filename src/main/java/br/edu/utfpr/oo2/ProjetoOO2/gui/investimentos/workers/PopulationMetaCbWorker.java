package br.edu.utfpr.oo2.ProjetoOO2.gui.investimentos.workers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Investimento;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.InvestimentoService;


import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PopulationMetaCbWorker extends SwingWorker<List<Investimento>, Void> {

    private JFrame frameInvestimento;
    private GenericLoadingDialog loadingDialog;
    private JComboBox cbInvestimento;
    private List<Investimento> investimentos;
    private Usuario userLogado;
    private InvestimentoService investimentoService;

    public PopulationMetaCbWorker(JFrame frameInvestimento, GenericLoadingDialog loadingDialog, JComboBox cbInvestimento, Usuario userLogado) {
        this.frameInvestimento = frameInvestimento;
        this.loadingDialog = loadingDialog;
        this.cbInvestimento = cbInvestimento;
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

            if(investimentos.size()>0) {

                for(Investimento investimento : investimentos) {
                    cbInvestimento.addItem(investimento.getNome());
                }

            }else {
                JOptionPane.showMessageDialog(frameInvestimento,"Não há investimentos cadastrados.","Erro",JOptionPane.ERROR_MESSAGE);
            }


        } catch (InterruptedException ignore) {
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                JOptionPane.showMessageDialog(frameInvestimento, "Erro Ao buscar metas de Investimentos\nDetalhes: "+cause.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE );
            }else{
                JOptionPane.showMessageDialog(frameInvestimento,"Erro Inesperado\nDetalhes: "+cause.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
            }

        }finally {
            loadingDialog.dispose();
        }


    }
}
