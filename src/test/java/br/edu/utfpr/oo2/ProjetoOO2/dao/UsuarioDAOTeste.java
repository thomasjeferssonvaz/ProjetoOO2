package br.edu.utfpr.oo2.ProjetoOO2.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;

public class UsuarioDAOTeste {
    public static void main(String[] args) {

//		Teste Cadastro
        try {
            LocalDate dataNascimento = LocalDate.of(2005,10, 7);//Recebe a data do Jframe
            Date dataNascimentoSql = Date.valueOf(dataNascimento);//Transforma a data de LocalDate para java.sql.Date
            cadastrarUsuarioTeste("tjv3", "thomas34", "Thomaz Vaz", dataNascimentoSql, "Masculino", "Admin");//Cria o usuário utilizando a data correta
        } catch (SQLException | IOException e) {
            if(e.getMessage().startsWith("Duplicate entry")) {
                System.out.println("Erro: Usuário já cadastrado, por favor tente novamente com outro usuário");
            } else {
                System.out.println("Erro: "+ e.getMessage());
            }
        }



//		Teste Buscar Todos
//		try {
//            buscarTodosUsuariosTeste();
//		} catch (SQLException | IOException e) {
//			System.out.println("Erro: "+e.getMessage());
//		}


//		Teste Buscar por chave
//		try {
//			buscarPorChaveTeste("tjv3");
//		} catch (SQLException | IOException e) {
//			System.out.println("Erro: "+e.getMessage());
//		}

//		Teste Exclusão
//        try {
//            excluirUsuarioTeste("tjv3");
//        } catch (SQLException | IOException e) {
//            System.out.println("Erro: "+ e.getMessage());
//        }


//      Teste atualização
        try {
            Usuario usuario = new Usuario();
            usuario.setNome("Thomas Jefersson Vaz");

            LocalDate dataNascimento = LocalDate.of(2007,11, 8);
            Date dataNascimentoSql = Date.valueOf(dataNascimento);

            usuario.setDataNascimento(dataNascimentoSql);
            usuario.setSexo("Feminino");
            atualizarUsuarioTeste("tjv3", usuario);

        } catch (SQLException | IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }


    public static void cadastrarUsuarioTeste(String username,
                                             String senha,
                                             String nome,
                                             Date dataNascimento,
                                             String sexo,
                                             String usuarioTipo) throws SQLException, IOException {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setSenha(senha);
        usuario.setNome(nome);
        usuario.setDataNascimento(dataNascimento);
        usuario.setSexo(sexo);
        usuario.setUsuarioTipo(usuarioTipo);

        Connection conn = BancoDados.conectar();
        int resultado = new UsuarioDAO(conn).cadastrar(usuario);
        if(resultado > 0) {
            System.out.println("Usuário cadastrado com sucesso");
        } else {
            System.out.println("Erro ao cadastrar usuário");
        }

    }

    public static void buscarTodosUsuariosTeste() throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        List<Usuario> listaUsuario = new UsuarioDAO(conn).buscarTodos();

        int listSize = listaUsuario.size();
        if(listSize <= 0) {
            System.out.println("Sem usuários cadastrados");
            return;
        }
        System.out.println("==========================");
        for (Usuario usuario : listaUsuario) {
            System.out.println("Código: " + usuario.getId() + "\n" +
                    "Nome: " + usuario.getNome() + "\n" +
                    "Username: " + usuario.getUsername() + "\n" +
                    "Senha: " + usuario.getSenha() + "\n" +
                    "Sexo: " + usuario.getSexo() + "\n" +
                    "Data de Nascimento: " + usuario.getDataNascimento() + "\n" +
                    "Tipo de usuário: " + usuario.getUsuarioTipo() + "\n");
            System.out.println("==========================");
        }
    }


    public static void buscarPorChaveTeste(String chaveDePesquisa) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        Usuario usuario = new UsuarioDAO(conn).buscarPorChave(chaveDePesquisa);

        if (usuario != null) {
            System.out.println("==========================");
            System.out.println("Código: " + usuario.getId() + "\n" +
                    "Nome: " + usuario.getNome() + "\n" +
                    "Username: " + usuario.getUsername() + "\n" +
                    "Senha: " + usuario.getSenha() + "\n" +
                    "Sexo: " + usuario.getSexo() + "\n" +
                    "Data de Nascimento: " + usuario.getDataNascimento() + "\n" +
                    "Tipo de usuário: " + usuario.getUsuarioTipo());
            System.out.println("==========================");
        } else {
            System.out.println("Usuário não encontrado");
        }

    }

    public static void excluirUsuarioTeste(String username) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        int resultado = new UsuarioDAO(conn).excluir(username);
        if(resultado > 0) {
            System.out.println("Usuário excluido com sucesso");
        } else {
            System.out.println("Usuário não encontrado para efetuar a exclusão");
        }
    }

    public static void atualizarUsuarioTeste(String username, Usuario usuario) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        int resultado = new UsuarioDAO(conn).atualizar(usuario, username);
        if(resultado > 0) {
            System.out.println("Usuário atualizado com sucesso");
        } else {
            System.out.println("Erro ao atualizar usuário");
        }
    }



















}
