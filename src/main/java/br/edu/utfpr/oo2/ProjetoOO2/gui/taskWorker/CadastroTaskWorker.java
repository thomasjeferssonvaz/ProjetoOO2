package br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class CadastroTaskWorker extends SwingWorker<Usuario, Void> {
    private Usuario usuario;
    private UsuarioService usuarioService;
    private JFrame cadastroFrame;
    private GenericLoadingDialog genericLoadingDialog;
    private int cadastroRow;

    public CadastroTaskWorker(Usuario usuario, UsuarioService usuarioService, JFrame cadastroFrame, GenericLoadingDialog genericLoadingDialog) {
        this.usuario = usuario;
        this.usuarioService = usuarioService;
        this.cadastroFrame = cadastroFrame;
        this.genericLoadingDialog = genericLoadingDialog;
    }

    @Override
    protected Usuario doInBackground() throws Exception {


        if (usuario.getUsername() != null &&
                usuario.getSenha() != null &&
                usuario.getDataNascimento() != null &&
                usuario.getNome() != null) {
            try {

                int cadastro = usuarioService.cadastrarUsuario(usuario);
                if (cadastro > 0) {
                    JOptionPane.showMessageDialog(cadastroFrame, "Usuário cadastrado com sucesso!!", "Sucesso ao cadastrar", JOptionPane.INFORMATION_MESSAGE);
                    cadastroRow = cadastro;
                    return usuario;
                }
                return null;
            } catch (SQLException | IOException e) {
                if (e.getMessage().startsWith("Duplicate")) {
                    JOptionPane.showMessageDialog(cadastroFrame, "Usuário já existente, tente novamente com outro nome de usuário!", "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
                    genericLoadingDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(cadastroFrame, "Erro: " + e.getMessage(), "Erro ao cadastrar", JOptionPane.ERROR_MESSAGE);
                    genericLoadingDialog.dispose();
                }
                return null;
            }
        } else {
            JOptionPane.showMessageDialog(cadastroFrame, "Erro: Preencha todos os campos.", "Erro de cadastro", JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }

    @Override
    protected void done(){
        genericLoadingDialog.dispose();

        try {
            Usuario usuario = get();
        } catch (InterruptedException ignore) {
        } catch (java.util.concurrent.ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                JOptionPane.showMessageDialog(cadastroFrame, "Erro de infraestrutura: Falha ao comunicar com o DB.\nDetalhe: " + cause.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(cadastroFrame, "Ocorreu um erro inesperado: " + cause.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
