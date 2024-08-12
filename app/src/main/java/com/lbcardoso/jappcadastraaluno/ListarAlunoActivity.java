package com.lbcardoso.jappcadastraaluno;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lbcardoso.jappcadastraaluno.Aluno.Aluno;
import com.lbcardoso.jappcadastraaluno.Aluno.AlunoDAO;

import java.util.ArrayList;
import java.util.List;

public class ListarAlunoActivity extends AppCompatActivity {

    private ListView listView;
    private AlunoDAO dao;
    private List<Aluno> alunos;
    private List<Aluno> alunosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar_aluno);

        //vincular variaveis com os campos do layout
        listView = findViewById(R.id.lista_alunos);
        dao = new AlunoDAO(this);
        alunos = dao.obterTodos(); //todos alunos
        alunosFiltrados.addAll(alunos); //só os alunos que foram consultados

        //ArrayAdapter já vem pronto no android para colocar essa lista de alunos na listview
        ArrayAdapter<Aluno> adaptador = new ArrayAdapter<Aluno>(this,
                                                                android.R.layout.simple_list_item_1,
                                                                alunos);

        //colocar na listView o adaptador
        listView.setAdapter(adaptador);

        //Gerado automaticamente...
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void formCadastroAluno(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}