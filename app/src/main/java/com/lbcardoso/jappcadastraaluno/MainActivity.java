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

import com.lbcardoso.jappcadastraaluno.Aluno.Aluno;
import com.lbcardoso.jappcadastraaluno.Aluno.AlunoDAO;

public class MainActivity extends AppCompatActivity {

    //Define o nome dos campos e a DAO
    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private AlunoDAO dao;

    private Aluno aluno = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Auto generated...
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Atribui valores dos campos no MainActivity
        nome = findViewById(R.id.editText_Nome);
        cpf = findViewById(R.id.editText_Cpf);
        telefone = findViewById(R.id.editText_Telefone);
        dao = new AlunoDAO(this);

        //(Editar) PEGAR OS DADOS QUE VEM NO INTENT DO ATUALIZAR
        Intent it = getIntent(); //pega intenção
        if(it.hasExtra("aluno")) {
            aluno = (Aluno) it.getSerializableExtra("aluno");
            nome.setText(aluno.getNome().toString());
            cpf.setText(aluno.getCpf());
            telefone.setText(aluno.getTelefone());
        }

        // Auto generated...
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Ação performada ao clicar no botao "Cadastrar" no MainActivity
    public void salvar(View view) {
        // Novo aluno sendo criado...
        if(aluno == null){
            //Cria um novo objeto de aluno
            Aluno a = new Aluno();

            //Atribui os dados do formulário preenchido
            a.setNome(nome.getText().toString());
            a.setCpf(cpf.getText().toString());
            a.setTelefone(telefone.getText().toString());

            //Cadastra o aluno e informa ao usuário
            long id = dao.inserir(a);
            Toast.makeText(this, "Aluno inserido com id: " + id, Toast.LENGTH_SHORT).show();

        // Aluno sendo atualizado
        }else{
            //RECEBENDO DADOS DO ATUALIZAR NA VARIAVEL 'aluno'
            aluno.setNome(nome.getText().toString());
            aluno.setCpf(cpf.getText().toString());
            aluno.setTelefone(telefone.getText().toString());

            dao.atualizar(aluno); //inserir o aluno
            Toast.makeText(this,"Aluno Atualizado!! com id: ", Toast.LENGTH_SHORT).show();
        }

    }

    public void listarAlunos(View view) {
        Intent intent = new Intent(this, ListarAlunoActivity.class);
        startActivity(intent);
    }
}