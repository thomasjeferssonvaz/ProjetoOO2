package br.edu.utfpr.oo2.ProjetoOO2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.dao.UsuarioDAO;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Usuario;

public class UsuarioDAOTeste {
    public static void main(String[] args) {

//		Teste Cadastro
        try {
            LocalDate dataNascimento = LocalDate.of(2005,10,07);//Recebe a data do Jframe
            Date dataNascimentoSql = Date.valueOf(dataNascimento);//Transforma a data de LocalDate para java.sql.Date
            cadastrarUsuarioTeste("tjv3", "thomas34", "Thomas Vaz", dataNascimentoSql, "Masculino", "Admin");//Cria o usuário utilizando a data correta
        } catch (SQLException | IOException e) {
            if(e.getMessage().startsWith("Duplicate entry")) {
                System.out.println("Erro: Usuário já cadastrado, por favor tente novamente com outro usuário");
            } else {
                System.out.println("Erro: "+ e.getMessage());
            }
        }

//		Teste Buscar Todos
//		try {
//			UsuarioTeste.buscarTodosUsuariosTeste();
//		} catch (SQLException | IOException e) {
//			System.out.println("Erro: "+e.getMessage());
//		}

//		Teste Buscar por chave
//		try {
//			UsuarioTeste.buscarPorChaveTeste("tjv2");
//		} catch (SQLException | IOException e) {
//			System.out.println("Erro: "+e.getMessage());
//		}
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
        //System.out.println(usuario);

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
            System.out.println("Curso não encontrado");
        }

    }





















}
