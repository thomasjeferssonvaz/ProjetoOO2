package br.edu.utfpr.oo2.ProjetoOO2.entity;

public class Investimento {

    private int id;
    private String nome;
    private String tipo;
    private double aporte;
    private String local;
    private int idUsuario;

    public Investimento(String nome, String tipo, double aporte, String local) {
        this.nome = nome;
        this.tipo = tipo;
        this.aporte = aporte;
        this.local = local;
    }

    public Investimento() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getAporte() {
        return aporte;
    }

    public void setAporte(double aporte) {
        this.aporte = aporte;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
