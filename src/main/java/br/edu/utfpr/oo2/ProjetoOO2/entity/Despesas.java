package br.edu.utfpr.oo2.ProjetoOO2.entity;

import java.time.LocalDateTime;

public class Despesas {
    private int id;
    private String nome;
    private String descricao;
    private String tipoDespesa;
    private int id_usuario;


    public Despesas(String nome, String descricao,String tipoDespesa, int id_usuario) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipoDespesa = tipoDespesa;
        this.id_usuario = id_usuario;

    }
    public Despesas() {}


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getTipoDespesa() {
        return tipoDespesa;
    }
    public void setTipoDespesa(String tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }
}
