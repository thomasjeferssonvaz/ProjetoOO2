package br.edu.utfpr.oo2.ProjetoOO2.dao;

import br.edu.utfpr.oo2.ProjetoOO2.entity.Transaction;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionDAOTeste {

    public static void main(String[] args) {

        try {

            cadastrarReceitaDespesa();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void cadastrarReceitaDespesa() throws SQLException, IOException {
        Connection conn = BancoDados.conectar();

        LocalDate data = LocalDate.parse("12/08/2006",
                DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Date sqlDate = Date.valueOf(data);

        Transaction transac = new Transaction(6,10.00, sqlDate,"Despesa","Aluguel",null,10);

        int res = new TransactionDAO(conn).cadastrarReceitaDespesa(transac);
        if(res == 1){
            System.out.println("Cadastrado com sucesso");
        }else {
            System.out.println("erro ao cadastrar");
        }
    }
}
