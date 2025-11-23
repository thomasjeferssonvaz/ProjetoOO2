package br.edu.utfpr.oo2.ProjetoOO2.service;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.dao.AnaliticaFinanceiraDAO;
import br.edu.utfpr.oo2.ProjetoOO2.entity.AnaliticaFinanceira;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AnaliticaFinanceiraService {

    public AnaliticaFinanceiraService() {
    }

    public int cadastroAnalitica(AnaliticaFinanceira despesas) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new AnaliticaFinanceiraDAO(conn).cadastrar(despesas);
    }
}
