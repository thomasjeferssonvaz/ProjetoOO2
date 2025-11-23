package br.edu.utfpr.oo2.ProjetoOO2;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.dao.AnaliticaFinanceiraDAO;
import br.edu.utfpr.oo2.ProjetoOO2.entity.AnaliticaFinanceira;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AnaliticaFinanceiraDAOTeste {
    public static void main(String[] args) {

        try {

            cadastrarAnalitica();

        }catch (Exception e){
            System.out.println(e.getMessage());
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
