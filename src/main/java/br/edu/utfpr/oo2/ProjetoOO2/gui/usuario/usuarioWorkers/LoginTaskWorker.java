package br.edu.utfpr.oo2.ProjetoOO2.gui.usuario.usuarioWorkers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.MainWindow;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;


public class LoginTaskWorker extends SwingWorker<Usuario, Void> {

    private Usuario user;
    private String username;
    private String password;
    private UsuarioService usuarioService;
    private JFrame loginFrame;
    private GenericLoadingDialog loadingDialog;

    public LoginTaskWorker(String username, String password, UsuarioService service, JFrame frame, GenericLoadingDialog dialog) {
        this.username = username;
        this.password = password;
        this.usuarioService = service;
        this.loginFrame = frame;
        this.loadingDialog = dialog;
    }


    @Override
    protected Usuario doInBackground() throws Exception {
        Usuario usuario = usuarioService.buscarUsuarioPorUsername(username);
        if (usuario != null) {
            if (usuario.getSenha().equals(password)) {
                if (usuario.getStatus().equals("ativo")) {
                    return usuario;
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Usuário desativado, tente novamente com outro usuário ou entre em contato com um administrador do sistema.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Senha incorreta.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(loginFrame, "Usuário inexistente.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    private void setUser(Usuario user){
        this.user = user;
    }

    public Usuario getUser() {
        return this.user;
    }

    @Override
    protected void done() {
        loadingDialog.dispose();
        try {
            Usuario usuario = get();
            loginFrame.dispose();
            setUser(usuario);
            MainWindow mainFrame = new MainWindow(getUser());
            mainFrame.setVisible(true);
        } catch (InterruptedException ignore) {
        } catch (java.util.concurrent.ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                JOptionPane.showMessageDialog(loginFrame, "Erro de infraestrutura: Falha ao comunicar com o DB.\nDetalhe: " + cause.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Ocorreu um erro inesperado: " + cause.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}