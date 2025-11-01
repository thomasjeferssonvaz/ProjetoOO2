package br.edu.utfpr.oo2.ProjetoOO2.entity;

public class Conta {

    private int idConta; //Numero da conta
    private String nomeBanco;
    private int agencia;
    private double saldo;
    private ContaTipo contaTipo;
    private int idUsuario;

    public Conta(String nomeBanco, int agencia, ContaTipo contaTipo, int idUsuario) {
        this.nomeBanco = nomeBanco;
        this.agencia = agencia;
        this.saldo = 0.00;
        this.contaTipo = contaTipo;
        this.idUsuario = idUsuario;
    }

    public Conta() {
    }

    public int getIdConta() {
        return idConta;
    }

    public void setIdConta(int idConta) {
        this.idConta = idConta;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoConta() {
        return contaTipo.name();
    }

    public void setTipoConta(ContaTipo contaTipo) {
        this.contaTipo = contaTipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }
}
