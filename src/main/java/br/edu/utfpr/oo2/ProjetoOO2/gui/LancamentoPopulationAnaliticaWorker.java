package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.AnaliticaFinanceira;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.AnaliticaFinanceiraService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LancamentoPopulationAnaliticaWorker<T> extends SwingWorker<List<AnaliticaFinanceira>, Void> {

    private JFrame frame;
    private JComboBox cbAnalitica;
    private GenericLoadingDialog genericLoadingDialog;
    private AnaliticaFinanceiraService analiticaFinanceiraService;
    private Usuario usuario;
    private List<AnaliticaFinanceira> analiticasBD;
    private String tipo;

    public LancamentoPopulationAnaliticaWorker(JFrame frame, AnaliticaFinanceiraService analiticaFinanceiraService, Usuario usuario, GenericLoadingDialog genericLoadingDialog, JComboBox cbAnalitica, String tipo) {
        this.frame = frame;
        this.analiticaFinanceiraService = analiticaFinanceiraService;
        this.usuario = usuario;
        this.genericLoadingDialog = genericLoadingDialog;
        this.analiticasBD = new ArrayList<>();
        this.cbAnalitica = cbAnalitica;
        this.tipo = tipo;


    }



    @Override
    protected List<AnaliticaFinanceira> doInBackground() throws Exception {

        if (this.tipo.equals("Receita")) {
            analiticasBD = analiticaFinanceiraService.listarReceitas(usuario.getId());


            return analiticasBD;
        }else if (this.tipo.equals("Despesa")) {

            analiticasBD = analiticaFinanceiraService.listarDespesas(usuario.getId());
            return analiticasBD;
        }

        return null;
    }


    @Override
    protected void done() {

        try {
            analiticasBD = get();

            if (analiticasBD == null) {
                JOptionPane.showMessageDialog(frame,
                        "Nenhuma analitica encontrado.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                genericLoadingDialog.dispose();
                return;
            }


            for(AnaliticaFinanceira analitica : analiticasBD) {
                cbAnalitica.addItem(analitica.getNome());
            }
            genericLoadingDialog.dispose();

        } catch (InterruptedException ignore) {
        } catch (ExecutionException e) {

            if(e.getCause() instanceof SQLException || e.getCause() instanceof IOException) {
                JOptionPane.showMessageDialog(frame, "Erro ao buscar analiticas \nDetalhes: "+ e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }else {
                JOptionPane.showMessageDialog(frame, "Erro inesperado \nDetalhes: "+ e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
            genericLoadingDialog.dispose();
        }


    }
}
