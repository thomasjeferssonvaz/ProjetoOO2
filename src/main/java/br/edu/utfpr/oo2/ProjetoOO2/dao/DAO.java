package br.edu.utfpr.oo2.ProjetoOO2.dao;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T, K> {
    int cadastrar(T entidade) throws SQLException;
    List<T> buscarTodos() throws SQLException;
    T buscarPorChave(K chaveDePesquisa) throws SQLException;
    int atualizar(T entidade,K chaveDePesquisa) throws SQLException;
    int excluir(K chavePrimaria) throws SQLException;
}
