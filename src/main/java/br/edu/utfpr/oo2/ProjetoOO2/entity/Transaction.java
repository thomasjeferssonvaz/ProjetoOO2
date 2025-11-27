package br.edu.utfpr.oo2.ProjetoOO2.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class Transaction {
    private long id;
    private int numero_conta;
    private double valor;
    private Date dataTransacao;
    private String tipo;
    private String analiticaFinanceira;
    private String descricao;
    private int id_usuario;

    public Transaction(int numero_conta, double valor, Date dataTransacao, String tipo, String analiticaFinanceira, String descricao, int id_usuario) {
        this.numero_conta = numero_conta;
        this.valor = valor;
        this.dataTransacao = dataTransacao;
        this.tipo = tipo;
        this.analiticaFinanceira = analiticaFinanceira;
        this.descricao = descricao;
        this.id_usuario = id_usuario;
    }

    public Transaction(){}

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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getNumero_conta() {
        return numero_conta;
    }

    public void setNumero_conta(int numero_conta) {
        this.numero_conta = numero_conta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
