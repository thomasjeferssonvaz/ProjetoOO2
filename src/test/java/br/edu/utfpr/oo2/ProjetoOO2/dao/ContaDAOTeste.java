package br.edu.utfpr.oo2.ProjetoOO2.dao;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ContaDAOTeste {

    public static void main(String[] args) {

        try {
            //Teste de Cadastro de uma nova conta
            //cadastrarContaTeste("Banco do Brasil", 1234, 456, "Conta salario", 21);

            //Teste buscarPorChave
            //buscarPorChaveTeste(4);

            //teste BuscarTodos
            //buscarTodosTeste();

            //Teste Atualizar
            //atualizarTeste();

            //Teste Excluir
            //excluirTeste();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void cadastrarContaTeste(String nomeBanco,
                                           int numeroConta,
                                           int agencia,
                                           String tipoConta,
                                           int idUsuario) throws SQLException, IOException {


        Conta conta = new Conta();

        conta.setNomeBanco(nomeBanco);
        conta.setNumeroConta(numeroConta);
        conta.setAgencia(agencia);
        conta.setTipoConta(tipoConta);
        conta.setIdUsuario(idUsuario);
        //conta.setSaldo(0.0);


        Connection conn = BancoDados.conectar();

        int resultado = new ContaDAO(conn).cadastrar(conta);

        if (resultado > 0) {
            System.out.println("conta cadastrada com sucesso");
        } else {
            System.out.println("Erro ao cadastrar conta");
        }

    }

    public static void buscarTodosTeste() throws SQLException, IOException {

        Connection conn = BancoDados.conectar();

        List contas = new ContaDAO(conn).buscarTodos();
        if (contas != null) {
            for (Object conta : contas) {
                System.out.println(conta);
            }
        }
    }

    public static void buscarPorChaveTeste(int chave) throws SQLException, IOException {

        Connection conn = BancoDados.conectar();

        Conta conta = new Conta();
        conta = new ContaDAO(conn).buscarPorChave(chave);

        if (conta != null) {
            System.out.println("conta buscada com sucesso");
            System.out.println(conta);
        } else {
            System.out.println("Erro ao buscar por chave");
        }
    }

    public static void atualizarTeste() throws SQLException, IOException {

        Conta conta = new Conta();
        conta.setNomeBanco("Banco do Itaú");
        conta.setAgencia(1590);
        conta.setNumeroConta(1523);
        conta.setTipoConta("Conta Salario");
        conta.setIdConta(4);

        Connection conn = BancoDados.conectar();

        int resultado = new ContaDAO(conn).atualizar(conta, 4);

        if (resultado > 0) {
            System.out.println("Atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar!");
        }
    }

    public static void excluirTeste() throws SQLException, IOException {

        Connection conn = BancoDados.conectar();

        int resultado = new ContaDAO(conn).excluir(4);
        if (resultado > 0) {
            System.out.println("Excluido com sucesso!");
        } else {
            System.out.println("Erro ao excluir!");
        }
    }


}
