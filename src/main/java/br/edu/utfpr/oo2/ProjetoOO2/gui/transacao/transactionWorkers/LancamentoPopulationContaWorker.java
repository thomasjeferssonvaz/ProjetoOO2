package br.edu.utfpr.oo2.ProjetoOO2.gui.transacao.transactionWorkers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;


import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LancamentoPopulationContaWorker<T> extends SwingWorker<List<Conta>, Void> {

    private JFrame frame;
    private JComboBox cbContas;
    private GenericLoadingDialog genericLoadingDialog;
    private ContaService contaService;
    private Usuario usuario;
    private List<Conta> contasBD;

    public LancamentoPopulationContaWorker(JFrame frame, ContaService contaService, Usuario usuario, GenericLoadingDialog genericLoadingDialog, JComboBox cbContas) {
        this.frame = frame;
        this.contaService = contaService;
        this.usuario = usuario;
        this.genericLoadingDialog = genericLoadingDialog;
        this.contasBD = new ArrayList<>();
        this.cbContas = cbContas;


    }



    @Override
    protected List<Conta> doInBackground() throws Exception {

        contasBD = contaService.buscarPorUsuario(usuario);


        return contasBD;
    }


    @Override
    protected void done() {

        try {
            contasBD = get();

            if (contasBD == null) {
                JOptionPane.showMessageDialog(frame,
                        "Nenhuma conta encontrada.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                genericLoadingDialog.dispose();
                return;
            }

            for(Conta conta : contasBD) {
                cbContas.addItem(conta.getNumeroConta());
            }
            genericLoadingDialog.dispose();

        } catch (InterruptedException ignore) {
        } catch (ExecutionException e) {

            if(e.getCause() instanceof SQLException || e.getCause() instanceof IOException) {
                JOptionPane.showMessageDialog(frame, "Erro ao buscar contas \nDetalhes: "+ e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(frame, "Erro inesperado \nDetalhes: "+ e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            genericLoadingDialog.dispose();
        }


    }
}
