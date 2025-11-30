package br.edu.utfpr.oo2.ProjetoOO2.gui.usuario.usuarioWorkers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class AlterarSenhaUsuarioWorker extends SwingWorker<Usuario, Void> {
    private String senhaAntiga;
    private String novaSenha;
    private String cmBoxUsername;
    private UsuarioService usuarioService;
    private JFrame alterarSenhaFrame;
    private GenericLoadingDialog genericLoadingDialog;
    private Usuario usuarioNovo;
    private int resultado;
    private Usuario userLogado;

    public AlterarSenhaUsuarioWorker(Usuario userLogado, String cmBoxUsername, String senhaAntiga, String novaSenha, UsuarioService usuarioService, JFrame alterarSenhaFrame, GenericLoadingDialog genericLoadingDialog){
        this.userLogado = userLogado;
        this.cmBoxUsername = cmBoxUsername;
        this.senhaAntiga = senhaAntiga;
        this.novaSenha = novaSenha;
        this.usuarioService = usuarioService;
        this.alterarSenhaFrame = alterarSenhaFrame;
        this.genericLoadingDialog = genericLoadingDialog;
    }


    @Override
    protected Usuario doInBackground() throws Exception{
        try {
            Usuario usuarioASerAlterado = usuarioService.buscarUsuarioPorUsername(cmBoxUsername);
            if(usuarioASerAlterado.getSenha().equals(senhaAntiga)){
                usuarioNovo = new Usuario(usuarioASerAlterado);
                usuarioNovo.setSenha(novaSenha);
                resultado = usuarioService.atualizarSenhaUsuario(usuarioNovo, usuarioASerAlterado.getUsername());
            } else {
                genericLoadingDialog.dispose();
                JOptionPane.showMessageDialog(this.alterarSenhaFrame, "Senha antiga invalida, tente novamente!", "Erro ao alterar senha", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException | SQLException e) {
            genericLoadingDialog.dispose();
            JOptionPane.showMessageDialog(this.alterarSenhaFrame, "Erro: " + e.getMessage(), "Erro ao atualizar senha", JOptionPane.ERROR_MESSAGE);
        }

        return usuarioNovo;
    }

    @Override
    protected void done(){
        genericLoadingDialog.dispose();

        try {
            Usuario usuario = get();
            if(resultado>0) {
                if (userLogado.getUsuarioTipo().equals("Admin")){
                    JOptionPane.showMessageDialog(alterarSenhaFrame, "Sucesso ao atualizar senha", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(alterarSenhaFrame, "Sucesso ao atualizar senha", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                    alterarSenhaFrame.dispose();
                }

            }
        } catch (InterruptedException ignore) {
        } catch (java.util.concurrent.ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                JOptionPane.showMessageDialog(alterarSenhaFrame, "Erro de infraestrutura: Falha ao comunicar com o DB.\nDetalhe: " + cause.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(alterarSenhaFrame, "Ocorreu um erro inesperado: " + cause.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
