package br.edu.utfpr.oo2.ProjetoOO2.service;

import br.edu.utfpr.oo2.ProjetoOO2.dao.BancoDados;
import br.edu.utfpr.oo2.ProjetoOO2.dao.InvestimentoDAO;
import br.edu.utfpr.oo2.ProjetoOO2.entity.Investimento;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class InvestimentoService {

    public  InvestimentoService() {
    }

    public int cadastrarInvestimento(Investimento invest) throws SQLException, IOException {
        Connection conexao = BancoDados.conectar();

        return new InvestimentoDAO(conexao).cadastrar(invest);

    }

    public List<Investimento> listarInvestimentosPorId(int id) throws SQLException, IOException {
        Connection conn = BancoDados.conectar();
        return new InvestimentoDAO(conn).listarInvestimentosPorId(id);
    }
}
