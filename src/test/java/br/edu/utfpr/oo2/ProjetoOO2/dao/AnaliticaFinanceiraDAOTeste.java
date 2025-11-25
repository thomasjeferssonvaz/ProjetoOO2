package br.edu.utfpr.oo2.ProjetoOO2.dao;

import br.edu.utfpr.oo2.ProjetoOO2.entity.AnaliticaFinanceira;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AnaliticaFinanceiraDAOTeste {
    public static void main(String[] args) {

        try {


            // cadastrarAnalitica();
                listarReceitas();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void listarReceitas() throws SQLException, IOException {
        Connection conn = BancoDados.conectar();

        List<AnaliticaFinanceira> listaAnalitica = new AnaliticaFinanceiraDAO(conn).listarReceitas(40);

        for (AnaliticaFinanceira a : listaAnalitica) {
            System.out.println(a.toString());
        }


    }




    public static void cadastrarAnalitica() throws SQLException, IOException {

        Connection conn = BancoDados.conectar();

        AnaliticaFinanceira despesa = new AnaliticaFinanceira("Aluguel","DESPESA","Aluguel da casa","RECORRENTE", 10);

        int res = new AnaliticaFinanceiraDAO(conn).cadastrar(despesa);

        if (res == 1){
            System.out.println("Despesa cadastrada com sucesso");
        }else {
            System.out.println("Erro ao  cadastrar Despesa");
        }

    }

}
