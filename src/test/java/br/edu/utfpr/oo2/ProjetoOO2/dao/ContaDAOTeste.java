package br.edu.utfpr.oo2.ProjetoOO2.dao;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Conta;
import br.edu.utfpr.oo2.ProjetoOO2.entity.ContaTipo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ContaDAOTeste {

    public static void main(String[] args) {

        try {

            cadastrarContaTeste("Banco do Itau", 5527,ContaTipo.CONTA_POUPANCA, 21);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void cadastrarContaTeste(String nomeBanco,
                                           int agencia,
                                           ContaTipo contaTipo,
                                           int idUsuario) throws SQLException, IOException {

       Conta conta = new Conta();

       conta.setAgencia(agencia);
       conta.setNomeBanco(nomeBanco);
       conta.setTipoConta(contaTipo);
       conta.setIdUsuario(idUsuario);
       //conta.setSaldo(0.0);


        Connection conn = BancoDados.conectar();

        int resultado = new ContaDAO(conn).cadastrar(conta);

        if(resultado > 0) {
            System.out.println("conta cadastrada com sucesso");
        } else {
            System.out.println("Erro ao cadastrar conta");
        }



    }

}
