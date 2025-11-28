package br.edu.utfpr.oo2.ProjetoOO2.gui.conta.contaWorkers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.ContaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchContaTaskWorker extends SwingWorker<List<Conta>, Void> {

    private JFrame frameEditarConta;
    private JTable contasTable;
    private ContaService contaService;
    private Usuario userLogado;
    private GenericLoadingDialog genericLoadingDialog;


    public SearchContaTaskWorker(ContaService contaService, Usuario userLogado, JFrame frameEditarConta, GenericLoadingDialog genericLoadingDialog, JTable contasTable) {

        this.contaService = contaService;
        this.userLogado = userLogado;
        this.frameEditarConta = frameEditarConta;
        this.genericLoadingDialog = genericLoadingDialog;
        this.contasTable = contasTable;

    }


    @Override
    protected List<Conta> doInBackground() throws Exception {

        return contaService.buscarPorUsuario(userLogado);
    }


    @Override
    protected void done() {

        try {
            List<Conta> contas = get();

            // Verifica lista Vazia
            if (contas == null || contas.isEmpty()) {
                JOptionPane.showMessageDialog(
                        frameEditarConta,
                        "Nenhuma conta encontrada!",
                        "Não Encontrado",
                        JOptionPane.INFORMATION_MESSAGE
                );
                this.frameEditarConta.dispose();
                return;
            }
            preencherTabela(contas);


        } catch (ExecutionException e) {
            Throwable cause = e.getCause();

            if (cause instanceof SQLException || cause instanceof IOException) {
                JOptionPane.showMessageDialog(frameEditarConta, "Erro de infraestrutura: Falha ao comunicar com o DB.\nDetalhe: " + cause.getMessage(), "Erro de Banco", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frameEditarConta, "Ocorreu um erro inesperado.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (InterruptedException ignored) {
        } finally {
            genericLoadingDialog.dispose();
        }

    }


    private void preencherTabela(List<Conta> contas) {
        contasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableModel model = (DefaultTableModel) contasTable.getModel();
        model.setRowCount(0);

        for (Conta conta : contas) {
            model.addRow(new Object[]{
                    conta.getIdConta(),
                    conta.getNomeBanco(),
                    conta.getAgencia(),
                    conta.getNumeroConta(),
                    conta.getTipoConta()
            });
        }
    }
}
