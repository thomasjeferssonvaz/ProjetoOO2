package br.edu.utfpr.oo2.ProjetoOO2;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.dao.DespesasDAO;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Despesas;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DespesasDAOTeste {
    public static void main(String[] args) {

        try {

            cadastrarDespesa();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void cadastrarDespesa() throws SQLException, IOException {

        Connection conn = BancoDados.conectar();

        Despesas despesa = new Despesas("Aluguel","Aluguel da casa","RECORRENTE", 10);

        int res = new DespesasDAO(conn).cadastrar(despesa);

        if (res == 1){
            System.out.println("Despesa cadastrada com sucesso");
        }else {
            System.out.println("Erro ao  cadastrar Despesa");
        }

    }

}
