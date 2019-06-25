package com.example.vr_mi.organizze.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vr_mi.organizze.R;
import com.example.vr_mi.organizze.config.ConfiguracaoFirebase;
import com.example.vr_mi.organizze.helper.Base64Custom;
import com.example.vr_mi.organizze.helper.DataCustom;
import com.example.vr_mi.organizze.model.Movimentacao;
import com.example.vr_mi.organizze.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class DespesaActivity extends AppCompatActivity {

    private TextInputEditText data;
    private TextInputEditText categoria;
    private TextInputEditText descricao;
    private EditText valor;
    private Movimentacao movimentacao;
    private DatabaseReference firebaseReference = ConfiguracaoFirebase.getFirebaseDataBase();
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    private Double despesaTotal ;

//    private Double despesaAtualizada;
 //   private FloatingActionButton fabSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        data = findViewById(R.id.editData);
        categoria = findViewById(R.id.editCategoria);
        descricao = findViewById(R.id.editDescricao);
        valor = findViewById(R.id.editValor);
     //   fabSalvar = findViewById(R.id.fabSalvar);

        data.setText(DataCustom.dataAtual());
        recuperarDespesaTotal();
    }

    public void salvarDespesa(View view){


        if(validarDespesaCampo()){

        movimentacao = new Movimentacao();
        String dataEscolhida = data.getText().toString();
        Double valorRecuperado = Double.parseDouble(valor.getText().toString());

        movimentacao.setValor(valorRecuperado);
        movimentacao.setCategoria(categoria.getText().toString());
        movimentacao.setDescricao(descricao.getText().toString());
        movimentacao.setData(dataEscolhida);
        movimentacao.setTipo("d");

        Double despesaAtualizada = (despesaTotal + valorRecuperado);
        atualizarDespesa(despesaAtualizada);

        movimentacao.salvar(dataEscolhida);
        finish();
    }
    }

    public Boolean validarDespesaCampo(){

        String textoValor = valor.getText().toString();
        String textoCategoria =categoria.getText().toString();
        String textoDescricao = descricao.getText().toString();
        String textoData = data.getText().toString();

        if( !textoValor.isEmpty()){
            if( !textoCategoria.isEmpty()){
                if( !textoDescricao.isEmpty()){
                    if( !textoData.isEmpty()){
                        return true;
                    }else{

                        Toast.makeText(DespesaActivity.this,
                                "Data não preenchida",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }else{

                    Toast.makeText(DespesaActivity.this,
                            "Descriçao não preenchido",
                            Toast.LENGTH_SHORT).show();
                    return false;
                }
            }else{

                Toast.makeText(DespesaActivity.this,
                        "Categoria não preenchido",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }else{

            Toast.makeText(DespesaActivity.this,
                    "Valor não preenchido",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void recuperarDespesaTotal(){
        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseReference.child("usuarios").child(idUsuario);

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                if(despesaTotal == null){
                    despesaTotal = 0.00;
                }else {
                    despesaTotal = usuario.getDespesaTotal();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void atualizarDespesa(Double despesa){

        String emailUsuario = autenticacao.getCurrentUser().getEmail();
        String idUsuario = Base64Custom.codificarBase64(emailUsuario);
        DatabaseReference usuarioRef = firebaseReference.child("usuarios").child(idUsuario);

        usuarioRef.child("despesaTotal").setValue(despesa);

    }
}
