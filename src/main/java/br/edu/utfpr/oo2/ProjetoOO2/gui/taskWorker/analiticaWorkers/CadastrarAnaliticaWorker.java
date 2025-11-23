package br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.analiticaWorkers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.AnaliticaFinanceira;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.AnaliticaFinanceiraService;

import javax.swing.*;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class CadastrarAnaliticaWorker extends SwingWorker<AnaliticaFinanceira, Void> {

    private JFrame frameCadastarDespesa;
    private GenericLoadingDialog genericLoadingDialog;
    private AnaliticaFinanceiraService despesasService;
    private AnaliticaFinanceira despesas;

    private int res;



    public CadastrarAnaliticaWorker(JFrame frameCadastarDespesa, GenericLoadingDialog genericLoadingDialog, AnaliticaFinanceiraService despesasService, AnaliticaFinanceira despesas) {

        this.frameCadastarDespesa = frameCadastarDespesa;
        this.genericLoadingDialog = genericLoadingDialog;
        this.despesasService = despesasService;
        this.despesas = despesas;

    }

    @Override
    protected AnaliticaFinanceira doInBackground() throws Exception {

         res = despesasService.cadastroAnalitica(despesas);

         if(res == 1){
             return despesas;
         }else {
             return null;
         }
    }

    @Override
    protected void done() {

        try {
            despesas = get();
            JOptionPane.showMessageDialog(frameCadastarDespesa, "Analitica cadastrada com sucesso!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
            genericLoadingDialog.dispose();

        } catch (InterruptedException ignore) {
        } catch (ExecutionException e) {
            if (e.getCause() instanceof SQLException) {
                JOptionPane.showMessageDialog(frameCadastarDespesa, "Analitica já existente", "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
                genericLoadingDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(frameCadastarDespesa, "Erro: " + e.getMessage(), "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
                genericLoadingDialog.dispose();
            }

        }
    }




}
