package com.example.vr_mi.organizze.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.vr_mi.organizze.R;
import com.example.vr_mi.organizze.activity.CadastroActivity;
import com.example.vr_mi.organizze.activity.LoginActivity;
import com.example.vr_mi.organizze.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class MainActivity extends IntroActivity {

    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_1)
                .build());


        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_2)
                .build());


        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_3)
                .build());

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_4)
                .build());


        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_cadastro)
                .canGoForward(false)
                .build());

    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void btEntrar(View view){
        startActivity(  new Intent(this, LoginActivity.class));
        System.out.println("Entrou");

    }
    public void btCadastrar(View view){
        startActivity(new Intent( this, CadastroActivity.class));
        System.out.println("Foi");
    }
    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
     //  autenticacao.signOut();

        if(autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, PrincipalActivity.class));

    }

}
      //  setContentView(R.layout.activity_main);
/*
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new SimpleSlide.Builder()
        .title("Título")
        .description("Descrição")
        .image(R.drawable.um)
                .background(android.R.color.holo_orange_light)
        .build()
        );
        addSlide(new SimpleSlide.Builder()
                .title("Título2")
                .description("Descrição2")
                .image(R.drawable.dois)
                .background(android.R.color.holo_orange_light)
                .build()
        );

        addSlide(new SimpleSlide.Builder()
                .title("Título3")
                .description("Descrição3")
                .image(R.drawable.tres)
                .background(android.R.color.holo_orange_light)
                .build()
        );

        addSlide(new SimpleSlide.Builder()
                .title("Título4")
                .description("Descrição4")
                .image(R.drawable.quatro)
                .background(android.R.color.holo_orange_light)
                .build()
        );
        */
