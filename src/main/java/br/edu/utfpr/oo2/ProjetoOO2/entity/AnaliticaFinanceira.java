package br.edu.utfpr.oo2.ProjetoOO2.entity;

public class AnaliticaFinanceira {
    private int id;
    private String nome;
    private String categoriaTipo;
    private String descricao;
    private String recorrencia;
    private int id_usuario;


    public AnaliticaFinanceira(String nome, String categoriaTipo, String descricao, String tipoDespesa, int id_usuario) {
        this.nome = nome;
        this.categoriaTipo = categoriaTipo;
        this.descricao = descricao;
        this.recorrencia = tipoDespesa;
        this.id_usuario = id_usuario;

    }
    public AnaliticaFinanceira() {}


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

    public void setCategoriaTipo(String categoriaTipo) {
        this.categoriaTipo = categoriaTipo;
    }

    public void setRecorrencia(String recorrencia) {
        this.recorrencia = recorrencia;
    }

    public String getCategoriaTipo() {
        return categoriaTipo;
    }

    public String getRecorrencia() {
        return recorrencia;
    }
}
