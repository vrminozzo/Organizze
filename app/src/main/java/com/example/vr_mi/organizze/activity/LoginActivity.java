package com.example.vr_mi.organizze.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vr_mi.organizze.R;
import com.example.vr_mi.organizze.config.ConfiguracaoFirebase;
import com.example.vr_mi.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {

    private Button buttonEntrar;
    private EditText editSenha;
    private EditText editEmail;
    private Usuario usuario;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acitivity);


        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        buttonEntrar = findViewById(R.id.buttonEntrar);

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();
                //Validar campos preenchidos

                    if ( !email.isEmpty()){
                        if( !senha.isEmpty()){
                            usuario = new Usuario();
                            usuario.setEmail(email);
                            usuario.setSenha(senha);
                            validarLogin();
                    }else{
                        Toast.makeText(LoginActivity.this,
                                "Preencha a senha",
                                Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(LoginActivity.this,
                            "Preencha o email",
                            Toast.LENGTH_SHORT).show();
                }

        }

        });
    }

        public void validarLogin(){

            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.signInWithEmailAndPassword(
                    usuario.getEmail(),usuario.getSenha()
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        abrirTelaPrincipal();
                    }else {
                        String excecao = "";

                        try {
                            throw task.getException();

                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                        } catch (FirebaseAuthInvalidUserException e) {
                            excecao = "Usuário não está cadastrado";
                        } catch (Exception e) {
                            excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                            e.printStackTrace();
                        }

                        Toast.makeText(LoginActivity.this,
                                excecao,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        public void abrirTelaPrincipal(){
         startActivity(new Intent(this, PrincipalActivity.class));
         finish();
        }

}



