package br.edu.utfpr.oo2.ProjetoOO2.gui.conta.contaWorkers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;
import br.edu.utfpr.oo2.ProjetoOO2.gui.conta.EditarContaWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class LoadSelectetContaWorker extends SwingWorker<Conta, Void> {

    private JFrame frameContaSelecionada;
    private ContaService contaService;
    private GenericLoadingDialog genericLoadingDialog;
    private int idContaSelecionada;
    private Conta contaBD;


   public LoadSelectetContaWorker(JFrame frameContaSelecionada, ContaService contaService, GenericLoadingDialog genericLoadingDialog, int idContaSelecionada) {
       this.frameContaSelecionada = frameContaSelecionada;
       this.contaService = contaService;
       this.genericLoadingDialog = genericLoadingDialog;
       this.contaBD = new Conta();
       this.idContaSelecionada = idContaSelecionada;
   }


    @Override
    protected Conta doInBackground() throws Exception {

        contaBD = contaService.buscarPorId(idContaSelecionada);

        return contaBD;
    }


    @Override
    protected void done() {

    try{

        contaBD = get();

        if(contaBD == null) {
            JOptionPane.showMessageDialog(frameContaSelecionada, "Conta não encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        EditarContaWindow editarContaWindow = new EditarContaWindow(contaBD);
        genericLoadingDialog.dispose();
        editarContaWindow.setVisible(true);

    }catch (InterruptedException ignored){
    } catch(ExecutionException e) {
            e.getCause();

            if(e.getCause() instanceof SQLException || e.getCause() instanceof IOException){
                JOptionPane.showMessageDialog(frameContaSelecionada, "Erro de infraestrutura: Falha ao comunicar com o DB.\nDetalhe:" + e.getMessage(),"Error" ,JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(frameContaSelecionada,"Ocorreu um erro Inesperado \nDetalhes: "+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
