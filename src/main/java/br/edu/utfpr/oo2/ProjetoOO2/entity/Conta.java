package br.edu.utfpr.oo2.ProjetoOO2.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Conta {

    private int idConta;

    private String nomeBanco;
    private int agencia;
    private int numeroConta;
    private double saldo;
    private String tipoConta;
    private int idUsuario;

    public Conta(String nomeBanco, int agencia, int numeroConta, String tipoConta, int idUsuario) {

        this.nomeBanco = nomeBanco;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
        this.saldo = 0.00;
        this.tipoConta = tipoConta;
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
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
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

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "idConta=" + idConta +
                ", nomeBanco='" + nomeBanco + '\'' +
                ", agencia=" + agencia +
                ", numeroConta=" + numeroConta +
                ", saldo=" + saldo +
                ", tipoConta='" + tipoConta + '\'' +
                ", idUsuario=" + idUsuario +
                '}';
    }

    public static Conta fromResultSet(ResultSet rs) throws SQLException {
        Conta c = new Conta();
        c.setIdConta(rs.getInt("id"));
        c.setNomeBanco(rs.getString("nome_banco"));
        c.setAgencia(rs.getInt("agencia"));
        c.setNumeroConta(rs.getInt("numero_conta"));
        c.setSaldo(rs.getDouble("saldo"));
        c.setTipoConta(rs.getString("tipo_conta"));
        c.setIdUsuario(rs.getInt("id_usuario"));
        return c;
    }
}
