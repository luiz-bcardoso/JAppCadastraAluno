package com.lbcardoso.jappcadastraaluno.Aluno;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Aluno implements Serializable {

    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private byte[] foto;
    private String grauEscolar;
    private Integer isActive;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getGrauEscolar() {
        return grauEscolar;
    }

    public void setGrauEscolar(String grauEscolar) {
        this.grauEscolar = grauEscolar;
    }

    public Integer getActive() {
        return isActive;
    }

    public void setActive(Integer active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return nome+", "+cpf+", "+telefone+", "+grauEscolar+", "+isActive;
    }
}
