package br.edu.utfpr.oo2.ProjetoOO2.entity;

import java.sql.Date;

public class Usuario {
    private int id;
    private String username;
    private String senha;
    private String nome;
    private Date dataNascimento;
    private String sexo;
    private String usuarioTipo;

    public Usuario() {
    }

    public Usuario(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.senha = usuario.getSenha();
        this.nome = usuario.getNome();
        this.dataNascimento = usuario.getDataNascimento();
        this.sexo = usuario.getSexo();
        this.usuarioTipo = usuario.getUsuarioTipo();
    }

    public Usuario(int id,
                   String username,
                   String senha,
                   String nome,
                   Date dataNascimento,
                   String sexo,
                   String usuarioTipo) {
        this.id = id;
        this.username = username;
        this.senha = senha;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.usuarioTipo = usuarioTipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getUsuarioTipo() {
        return this.usuarioTipo;
    }

    public void setUsuarioTipo(String usuarioTipo) {
        this.usuarioTipo = usuarioTipo;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", username=" + username + ", senha=" + senha + ", nome=" + nome
                + ", dataNascimento=" + dataNascimento + ", sexo=" + sexo + ", usuarioTipo=" + usuarioTipo + "]";
    }



}
