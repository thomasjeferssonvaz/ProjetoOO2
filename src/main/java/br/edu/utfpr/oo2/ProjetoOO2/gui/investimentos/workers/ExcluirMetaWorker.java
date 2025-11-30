package br.edu.utfpr.oo2.ProjetoOO2.gui.investimentos.workers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.InvestimentoService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class ExcluirMetaWorker extends SwingWorker<Integer, Void> {

    private JFrame frameMetas;
    private GenericLoadingDialog genericLoadingDialog;
    private Usuario userLogado;
    private String nomeInvestimento;

    private InvestimentoService investimentoService;
    private int res;

    public ExcluirMetaWorker(JFrame frameMetas, GenericLoadingDialog genericLoadingDialog, Usuario userLogado, String nomeInvestimento) {
        this.frameMetas = frameMetas;
        this.genericLoadingDialog = genericLoadingDialog;
        this.userLogado = userLogado;
        this.nomeInvestimento = nomeInvestimento;
        this.investimentoService = new InvestimentoService();
    }

    @Override
    protected Integer doInBackground() throws Exception {

        res = investimentoService.excluirPorNome(nomeInvestimento,userLogado.getId());
        return res;

    }


    @Override
    protected void done() {

        try {
            res = get();
            if(res == 1){
                JOptionPane.showMessageDialog(frameMetas,"Meta excluida com sucesso!","Sucesso",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(frameMetas,"Não foi Possivel excluir Meta", "Erro",JOptionPane.ERROR_MESSAGE);
            }

        } catch (InterruptedException ignore) {
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                JOptionPane.showMessageDialog(frameMetas, "Erro Ao ecluir meta\nDetalhes: "+cause.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE );
            }else{
                JOptionPane.showMessageDialog(frameMetas,"Erro Inesperado\nDetalhes: "+cause.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
            }

        }finally {
            genericLoadingDialog.dispose();
        }


    }
}
