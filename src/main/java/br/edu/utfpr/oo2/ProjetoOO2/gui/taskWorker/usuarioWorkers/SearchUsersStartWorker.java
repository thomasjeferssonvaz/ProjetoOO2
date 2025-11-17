package br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.usuarioWorkers;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;
import br.edu.utfpr.oo2.ProjetoOO2.gui.taskWorker.GenericLoadingDialog;
import br.edu.utfpr.oo2.ProjetoOO2.service.UsuarioService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchUsersStartWorker extends SwingWorker<Usuario, Void> {
    private Usuario usuario;
    private UsuarioService usuarioService;
    private JFrame alterarSenhaFrame;
    private GenericLoadingDialog genericLoadingDialog;
    private List<Usuario> listaUsuario;
    private List<Usuario> listaUsuarioDB;

    public SearchUsersStartWorker(Usuario usuario, UsuarioService usuarioService, JFrame alterarSenhaFrame, GenericLoadingDialog genericLoadingDialog){
        this.usuario = usuario;
        this.usuarioService = usuarioService;
        this.alterarSenhaFrame = alterarSenhaFrame;
        this.genericLoadingDialog = genericLoadingDialog;
    }




    @Override
    protected Usuario doInBackground() throws Exception{
        listaUsuario = new ArrayList<>();
        listaUsuarioDB = usuarioService.buscarTodosUsuarios();
        return usuario;
    }

    @Override
    protected void done(){
        if(usuario.getUsuarioTipo().equals("Admin")) {
            for(Usuario usuarioDB : listaUsuarioDB) {
                listaUsuario.add(usuarioDB);
            }
        } else {
            listaUsuario.add(usuario);
        }
        genericLoadingDialog.dispose();

        try {
            Usuario usuario = get();
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


    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }
}
