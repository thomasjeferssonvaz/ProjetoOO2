package br.edu.utfpr.oo2.ProjetoOO2.gui.usuario.usuarioWorkers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class EditarStatusUsuarioWorker extends SwingWorker<Usuario, Void>{
    private UsuarioService usuarioService;
    private JFrame editarUsuarioFrame;
    private GenericLoadingDialog genericLoadingDialog;
    private String cmBoxUsername;
    private Usuario userLogado;
    private String operacao;
    private int resultado;

    public EditarStatusUsuarioWorker(GenericLoadingDialog genericLoadingDialog, UsuarioService usuarioService, JFrame editarUsuarioFrame, String cmBoxUsername, Usuario userLogado, String operacao) {
        this.genericLoadingDialog = genericLoadingDialog;
        this.usuarioService = usuarioService;
        this.editarUsuarioFrame = editarUsuarioFrame;
        this.cmBoxUsername = cmBoxUsername;
        this.userLogado = userLogado;
        this.operacao = operacao;
    }

    @Override
    protected Usuario doInBackground() throws Exception{
        Usuario usuario = null;
        try {
            usuario = usuarioService.buscarUsuarioPorUsername(cmBoxUsername);
            if(operacao.equals("Ativando")){
                usuario.setStatus("ativo");
            } else {
                usuario.setStatus("inativo");
            }
            System.out.println(usuario);
            resultado = usuarioService.atualizarUsuario(usuario, cmBoxUsername);
        } catch (IOException | SQLException e) {
            genericLoadingDialog.dispose();
            JOptionPane.showMessageDialog(this.editarUsuarioFrame, "Erro: " + e.getMessage(), "Erro ao atualizar usuário", JOptionPane.ERROR_MESSAGE);
        }

        return usuario;
    }

    @Override
    protected void done(){
        genericLoadingDialog.dispose();
        try {
            Usuario usuario = get();
            if(resultado>0) {
                if (userLogado.getUsuarioTipo().equals("Admin")){
                    JOptionPane.showMessageDialog(this.editarUsuarioFrame, "Sucesso ao atualizar o usuário", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this.editarUsuarioFrame, "Sucesso ao atualizar o usuário", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                    this.editarUsuarioFrame.dispose();
                }
            }
        } catch (InterruptedException ignore) {
        } catch (java.util.concurrent.ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException || cause instanceof IOException) {
                JOptionPane.showMessageDialog(this.editarUsuarioFrame, "Erro de infraestrutura: Falha ao comunicar com o DB.\nDetalhe: " + cause.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this.editarUsuarioFrame, "Ocorreu um erro inesperado: " + cause.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
