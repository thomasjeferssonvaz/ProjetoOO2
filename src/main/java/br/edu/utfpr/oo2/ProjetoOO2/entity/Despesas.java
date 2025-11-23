package br.edu.utfpr.oo2.ProjetoOO2.entity;

public class Despesas {
    private int id;
    private String nome;
    private String descricao;

    public Despesas(String nome, String descricao, int id_usuario) {
        this.nome = nome;
        this.descricao = descricao;
        this.id_usuario = id_usuario;
    }

    private int id_usuario;

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
}
