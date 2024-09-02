package com.lbcardoso.jappcadastraaluno;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lbcardoso.jappcadastraaluno.Pagamento.Pagamento;
import com.lbcardoso.jappcadastraaluno.Pagamento.PagamentoDAO;

public class PagamentoActivity extends AppCompatActivity {

    //Define o nome dos campos e a DAO
    private EditText valor;
    private EditText data;
    private EditText alunoId;
    private PagamentoDAO dao;

    private Pagamento pagamento = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Auto generated...
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagamento);

        valor = findViewById(R.id.editText_valor);
        data = findViewById(R.id.editText_data);
        alunoId = findViewById(R.id.editText_alunoId);

        dao = new PagamentoDAO(this);

        //(Editar) PEGAR OS DADOS QUE VEM NO INTENT DO ATUALIZAR
        Intent it = getIntent(); //pega intenção
        if(it.hasExtra("pagamento")) {
            pagamento = (Pagamento) it.getSerializableExtra("pagamento");
            valor.setText(pagamento.getValor().toString());
            data.setText(pagamento.getData());
            alunoId.setText(pagamento.getAlunoId().toString());
        }

        // Auto generated...
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void salvar(View view) {
        // Novo pagamento sendo criado...
        if(pagamento == null){
            //Cria um novo objeto de pagamento
            Pagamento a = new Pagamento();

            //Atribui os dados do formulário preenchido
            a.setValor(Double.valueOf(String.valueOf(valor.getText())));
            a.setData(data.getText().toString());
            a.setAlunoId(Integer.valueOf(String.valueOf(alunoId.getText())));


            //Cadastra o pagamento e informa ao usuário
            long id = dao.inserir(a);
            Toast.makeText(this, "Pagamento inserido com id: " + id, Toast.LENGTH_SHORT).show();

        // pagamento sendo atualizado
        }else{
            //RECEBENDO DADOS DO ATUALIZAR NA VARIAVEL 'pagamento'
            pagamento.setValor(Double.valueOf(valor.getText().toString()));
            pagamento.setData(data.getText().toString());
            pagamento.setAlunoId(Integer.valueOf(String.valueOf(alunoId.getText())));

            dao.atualizar(pagamento); //inserir o pagamento
            Toast.makeText(this,"Pagamento Atualizado com id: ", Toast.LENGTH_SHORT).show();
        }
    }

    public void listarPagamentos(View view) {
        Intent intent = new Intent(this, ListarPagamentoActivity.class);
        startActivity(intent);
    }

    public void listarAlunos(View view){
        Intent intent = new Intent(this, ListarAlunoActivity.class);
        startActivity(intent);
    }
}