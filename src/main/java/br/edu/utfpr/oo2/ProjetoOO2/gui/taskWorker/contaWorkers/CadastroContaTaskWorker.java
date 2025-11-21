package br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.contaWorkers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class CadastroContaTaskWorker extends SwingWorker<Conta, Void> {
    private ContaService contaService;
    private JFrame jFrameCadastroConta;
    private Conta conta;
    private GenericLoadingDialog genericLoadingDialog;

    public CadastroContaTaskWorker(Conta conta, ContaService contaService, JFrame jFrameCadastroConta, GenericLoadingDialog genericLoadingDialog) {
        this.conta = conta;
        this.contaService = contaService;
        this.jFrameCadastroConta = jFrameCadastroConta;
        this.genericLoadingDialog = genericLoadingDialog;
    }

    @Override
    protected Conta doInBackground() throws Exception {

        try {

            int resultado = contaService.cadastrarConta(conta);

            if (resultado > 0) {
                JOptionPane.showMessageDialog(jFrameCadastroConta, "Conta Cadastrada com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                return this.conta;
            }
            return null;

        } catch (SQLException | IOException e) {
            if (e.getMessage().startsWith("Duplicate")) {
                JOptionPane.showMessageDialog(jFrameCadastroConta, "Conta já existente, tente novamente com outro numero de conta", "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
                genericLoadingDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(jFrameCadastroConta, "Erro: " + e.getMessage(), "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
                genericLoadingDialog.dispose();
            }
            return null;
        } catch (NumberFormatException e) { //Não irá cair aqui, pois ja foi impedido a digitação de letras em CadastroUsuarioWindow
            JOptionPane.showMessageDialog(jFrameCadastroConta, "Os campos devem conter apenas Números", "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


    @Override
    protected void done(){
        genericLoadingDialog.dispose();

        try {
            Conta conta = get();
        } catch (InterruptedException ignore) {
        } catch (java.util.concurrent.ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                JOptionPane.showMessageDialog(jFrameCadastroConta, "Erro de infraestrutura: Falha ao comunicar com o DB.\nDetalhe: " + cause.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(jFrameCadastroConta, "Ocorreu um erro inesperado: " + cause.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
