package com.example.vr_mi.organizze.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vr_mi.organizze.helper.Base64Custom;
import com.example.vr_mi.organizze.model.Usuario;
import com.example.vr_mi.organizze.R;
import com.example.vr_mi.organizze.config.ConfiguracaoFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText editNome, editEmail , editSenha;
    private Button buttonCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
  //  private TextView textTermo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

    //    getSupportActionBar().setTitle("Cadastro");

        editEmail = findViewById(R.id.editEmail);
        editNome = findViewById(R.id.editNome);
        editSenha = findViewById(R.id.editSenha);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);
  //      textTermo = findViewById(R.id.textTermos);
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = editNome.getText().toString();
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();
                //Validar campos preenchidos
                if( !nome.isEmpty()){
                    if ( !email.isEmpty()){
                        if( !senha.isEmpty()){

                            usuario = new Usuario();
                            usuario.setNome(nome);
                            usuario.setEmail(email);
                            usuario.setSenha(senha);

                            cadastrarUsuario();

                        }else{

                            Toast.makeText(CadastroActivity.this,
                                    "Preencha a senha",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CadastroActivity.this,
                                "Preencha o email",
                                Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha o nome",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void cadastrarUsuario() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ( task.isSuccessful()   ) {

                    String idusuario = Base64Custom.codificarBase64( usuario.getEmail() );
                    usuario.setIdusuario(idusuario);
                    usuario.salvar();
                    finish();

                }else{
                        String excecao = "";

                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Digite uma senha mais forte";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor, digite um e-mail válido";
                    }catch ( FirebaseAuthUserCollisionException e){
                        excecao = "Usuário já possui uma conta";
                    }catch ( Exception e ){
                        excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
