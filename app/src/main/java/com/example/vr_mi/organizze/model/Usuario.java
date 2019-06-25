package com.example.vr_mi.organizze.model;

import android.support.annotation.NonNull;

import com.example.vr_mi.organizze.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private String idusuario;
    private Double receitaTotal = 0.0;
    private Double despesaTotal = 0.0;

    public Usuario() {
    }
    public void salvar(){
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDataBase();
        firebase.child("usuarios")
                .child(this.idusuario)
                .setValue(this);
    }

    public Double getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(Double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public Double getDespesaTotal() {
        return despesaTotal;

    }

    public void setDespesaTotal(Double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }

    @Exclude
    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
