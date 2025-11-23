package br.edu.utfpr.oo2.ProjetoOO2.gui.despesasWorkers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Despesas;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.DespesasService;

import javax.swing.*;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class CadastrarDespesaWorker extends SwingWorker<Despesas, Void> {

    private JFrame frameCadastarDespesa;
    private GenericLoadingDialog genericLoadingDialog;
    private DespesasService despesasService;
    private Despesas despesas;

    private int res;



    public CadastrarDespesaWorker(JFrame frameCadastarDespesa, GenericLoadingDialog genericLoadingDialog, DespesasService despesasService,  Despesas despesas) {

        this.frameCadastarDespesa = frameCadastarDespesa;
        this.genericLoadingDialog = genericLoadingDialog;
        this.despesasService = despesasService;
        this.despesas = despesas;

    }

    @Override
    protected Despesas doInBackground() throws Exception {

         res = despesasService.cadastroDespesas(despesas);

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
            JOptionPane.showMessageDialog(frameCadastarDespesa, "Despesa Cadastrada com sucesso!","Sucesso", JOptionPane.INFORMATION_MESSAGE);
            genericLoadingDialog.dispose();

        } catch (InterruptedException ignore) {
        } catch (ExecutionException e) {
            if (e.getCause() instanceof SQLException) {
                JOptionPane.showMessageDialog(frameCadastarDespesa, "Despesa já existente, tente novamente com outro numero de conta", "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
                genericLoadingDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(frameCadastarDespesa, "Erro: " + e.getMessage(), "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
                genericLoadingDialog.dispose();
            }

        }
    }




}
