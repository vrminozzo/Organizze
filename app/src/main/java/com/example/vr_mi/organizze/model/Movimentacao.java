package com.example.vr_mi.organizze.model;

import com.example.vr_mi.organizze.config.ConfiguracaoFirebase;
import com.example.vr_mi.organizze.helper.Base64Custom;
import com.example.vr_mi.organizze.helper.DataCustom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Movimentacao {

    private String data;
    private String descricao;
    private String categoria;
    private String tipo;
    private Double valor;
    private String key;


    public Movimentacao() {
    }

    public void salvar(String dataEscolhida){

        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String idusuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        String mesAno = DataCustom.mesAnoDataEscolhida(dataEscolhida);
        DatabaseReference firebaseDataBase = ConfiguracaoFirebase.getFirebaseDataBase();
        firebaseDataBase.child("movimentacao")
                .child(idusuario )
                .child(mesAno)
                .push()
                .setValue(this);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
