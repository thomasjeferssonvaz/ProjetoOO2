package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;


public class LoginTaskWorker extends SwingWorker<Usuario, Void> {

    private String username;
    private String password;
    private UsuarioService usuarioService;
    private JFrame loginFrame;
    private LoginLoadingDialog loadingDialog;

    public LoginTaskWorker(String username, String password, UsuarioService service, JFrame frame, LoginLoadingDialog dialog) {
        this.username = username;
        this.password = password;
        this.usuarioService = service;
        this.loginFrame = frame;
        this.loadingDialog = dialog;
    }


    @Override
    protected Usuario doInBackground() throws Exception {
        Usuario usuario = usuarioService.buscarUsuarioPorUsername(username);
        if (usuario != null && usuario.getSenha().equals(password)) {
            return usuario;
        }
        return null;
    }

    @Override
    protected void done() {
        loadingDialog.dispose();
        try {
            Usuario usuario = get();
            if (usuario != null) {
                loginFrame.dispose();
                MainWindow mainFrame = new MainWindow();
                mainFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Usuário ou Senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
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