package br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.usuarioWorkers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.EditarUsuarioWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoadSelectedUserDataWorker extends SwingWorker<Usuario, Void>{
    private String cmBoxUsername;
    private UsuarioService usuarioService;
    private EditarUsuarioWindow editarUsuarioWindow;
    private GenericLoadingDialog loadingDialog;
    private Usuario usuario;


    public LoadSelectedUserDataWorker(String cmBoxUsername, UsuarioService usuarioService, EditarUsuarioWindow editarUsuarioWindow, GenericLoadingDialog loadingDialog) {
    this.cmBoxUsername = cmBoxUsername;
    this.usuarioService = usuarioService;
    this.editarUsuarioWindow = editarUsuarioWindow;
    this.loadingDialog = loadingDialog;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    protected Usuario doInBackground() throws Exception{
        try {
            setUsuario(usuarioService.buscarUsuarioPorUsername(cmBoxUsername));
            return usuario;
        } catch (IOException | SQLException e) {
            loadingDialog.dispose();
            JOptionPane.showMessageDialog(editarUsuarioWindow, "Erro: " + e.getMessage(), "Erro ao atualizar senha", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    @Override
    protected void done(){
        loadingDialog.dispose();

        try {
            Usuario usuario = get();
        } catch (InterruptedException ignore) {
        } catch (java.util.concurrent.ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                JOptionPane.showMessageDialog(editarUsuarioWindow, "Erro de infraestrutura: Falha ao comunicar com o DB.\nDetalhe: " + cause.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(editarUsuarioWindow, "Ocorreu um erro inesperado: " + cause.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
