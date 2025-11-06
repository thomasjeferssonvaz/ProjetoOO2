package br.edu.utfpr.oo2.ProjetoOO2.gui;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

// Retorna o objeto Usuario ou null se o login falhar
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

    /**
     * O trabalho pesado do DB (buscar e validar) acontece aqui.
     * NÃO é a EDT.
     */
    @Override
    protected Usuario doInBackground() throws Exception {
        // Simulação de um delay no DB, além do tempo real da query
        // Thread.sleep(1000);

        Usuario usuario = usuarioService.buscarUsuarioPorUsername(username);

        // Validação da Senha (com a melhoria de segurança que discutimos, se aplicada)
        if (usuario != null && usuario.getSenha().equals(password)) {
            return usuario;
        }

        return null; // Login falhou
    }

    /**
     * Executado na Event Dispatch Thread (EDT) após doInBackground().
     * Define o resultado do Login.
     */
    @Override
    protected void done() {
        // Oculta o loading dialog primeiro, independentemente do resultado
        loadingDialog.dispose();

        try {
            Usuario usuario = get(); // Obtém o resultado do doInBackground()

            if (usuario != null) {
                // Login bem-sucedido
                loginFrame.dispose();
                MainWindow mainFrame = new MainWindow();
                mainFrame.setVisible(true);
            } else {
                // Login falhou (Usuário não encontrado ou Senha incorreta)
                JOptionPane.showMessageDialog(loginFrame, "Usuário ou Senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        } catch (InterruptedException ignore) {
            // Ignorar
        } catch (java.util.concurrent.ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                // Erro Crítico (Infraestrutura)
                JOptionPane.showMessageDialog(loginFrame, "Erro de infraestrutura: Falha ao comunicar com o DB.\nDetalhe: " + cause.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            } else {
                // Outras exceções
                JOptionPane.showMessageDialog(loginFrame, "Ocorreu um erro inesperado: " + cause.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}