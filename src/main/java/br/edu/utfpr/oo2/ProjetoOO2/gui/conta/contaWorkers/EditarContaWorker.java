package br.edu.utfpr.oo2.ProjetoOO2.gui.conta.contaWorkers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class EditarContaWorker extends SwingWorker<Conta, Void> {

    private JFrame frameEditarConta;
    private GenericLoadingDialog genericLoadingDialog;
    private Conta contaOld;
    private Conta contaNew;
    private ContaService contaService;
    private Conta conta;

    public EditarContaWorker(JFrame frameEditarConta,GenericLoadingDialog genericLoadingDialog, Conta contaOld, Conta contaNew, ContaService contaService) {
        this.frameEditarConta = frameEditarConta;
        this.genericLoadingDialog = genericLoadingDialog;
        this.contaOld = contaOld;
        this.contaNew = contaNew;
        this.contaService = contaService;

    }


    @Override
    protected Conta doInBackground() throws Exception {

        int resultadoUpdate = contaService.atualizarConta(contaNew,contaOld);

        if (resultadoUpdate == 1) {
            conta = contaOld;
        }else {
            return null;
        }

        return conta;

    }

    @Override
    protected void done() {

        try {
            conta = get();
            JOptionPane.showMessageDialog(frameEditarConta, "Conta atualizada!","Sucesso",JOptionPane.INFORMATION_MESSAGE);

            genericLoadingDialog.dispose();

        } catch (InterruptedException ignore) {
        } catch (ExecutionException e) {
            e.getCause();

            if(e.getCause() instanceof SQLException || e.getCause() instanceof IOException){
                JOptionPane.showMessageDialog(frameEditarConta, "Esta conta já existe, tente com outro numero de conta","Error" ,JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(frameEditarConta,"Ocorreu um erro Inesperado \nDetalhes: "+ e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            genericLoadingDialog.dispose();
        }

    }
}
