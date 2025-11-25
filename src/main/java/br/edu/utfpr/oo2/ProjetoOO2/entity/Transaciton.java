package br.edu.utfpr.oo2.ProjetoOO2.entity;

import java.sql.Date;
import java.time.LocalDateTime;

public class Transaciton {
    private long id;
    private int id_conta;
    private Double valor;
    private Date dataTransacao;
    private String tipo;
    private String analiticaFinanceira;
    private String descricao;
    private int id_usuario;

    public Transaciton(int id_conta, Double valor, Date dataTransacao, String tipo, String analiticaFinanceira, String descricao, int id_usuario) {
        this.id_conta = id_conta;
        this.valor = valor;
        this.dataTransacao = dataTransacao;
        this.tipo = tipo;
        this.analiticaFinanceira = analiticaFinanceira;
        this.descricao = descricao;
        this.id_usuario = id_usuario;
    }

    public Transaciton(){}

    public int getId_usuario() {
        return id_usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAnaliticaFinanceira() {
        return analiticaFinanceira;
    }

    public void setAnaliticaFinanceira(String analiticaFinanceira) {
        this.analiticaFinanceira = analiticaFinanceira;
    }

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public int getId_conta() {
        return id_conta;
    }

    public void setId_conta(int id_conta) {
        this.id_conta = id_conta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
