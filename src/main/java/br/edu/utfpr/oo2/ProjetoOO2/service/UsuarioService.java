package br.edu.utfpr.oo2.ProjetoOO2.service;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.dao.UsuarioDAO;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class UsuarioService {

    public UsuarioService(){

    }

    public int cadastrarUsuario(Usuario usuario) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        int resultado = new UsuarioDAO(conn).cadastrar(usuario);
        if(resultado > 0) {
            System.out.println("Usuário cadastrado com sucesso");
        } else {
            System.out.println("Erro ao cadastrar usuário");
        }
        return resultado;
    }

    
    public List<Usuario> buscarTodosUsuarios() throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        List<Usuario> listaUsuario = new UsuarioDAO(conn).buscarTodos();

        int listSize = listaUsuario.size();
//        if(listSize <= 0) {
//            System.out.println("Sem usuários cadastrados");
//        }
//        System.out.println("==========================");
//        for (Usuario usuario : listaUsuario) {
//            System.out.println("Código: " + usuario.getId() + "\n" +
//                    "Nome: " + usuario.getNome() + "\n" +
//                    "Username: " + usuario.getUsername() + "\n" +
//                    "Senha: " + usuario.getSenha() + "\n" +
//                    "Sexo: " + usuario.getSexo() + "\n" +
//                    "Data de Nascimento: " + usuario.getDataNascimento() + "\n" +
//                    "Tipo de usuário: " + usuario.getUsuarioTipo() + "\n");
//            System.out.println("==========================");
//        }
        return listaUsuario;
    }


    public Usuario buscarUsuarioPorUsername(String username) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new UsuarioDAO(conn).buscarPorChave(username);
    }


    public void excluirUsuario(String username) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        int resultado = new UsuarioDAO(conn).excluir(username);
        if(resultado > 0) {
            System.out.println("Usuário excluido com sucesso");
        } else {
            System.out.println("Usuário não encontrado para efetuar a exclusão");
        }
    }


    public int atualizarUsuario(Usuario usuario, String username) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        int resultado = new UsuarioDAO(conn).atualizar(usuario, username);
        if(resultado > 0) {
            System.out.println("Usuário atualizado com sucesso");
        } else {
            System.out.println("Erro ao atualizar usuário");
        }
        return resultado;
    }

    public int atualizarSenhaUsuario(Usuario usuario, String username) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        int resultado = new UsuarioDAO(conn).atualizarSenha(usuario, username);
        if(resultado > 0) {
            System.out.println("Usuário atualizado com sucesso");
        } else {
            System.out.println("Erro ao atualizar usuário");
        }
        return resultado;
    }

}
