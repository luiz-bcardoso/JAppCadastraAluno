package com.lbcardoso.jappcadastraaluno;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

        //registrar o menu de contexto (excluir e atualizar) na listview
        registerForContextMenu(listView);

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

    //METODO MENU_CONTEXTO PARA INFLAR O MENU QUANDO ITEM PRESSIONADO
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        // Chama o método da superclasse (neste caso, o método onCreateContextMenu da classe pai).
        // Isso é importante para garantir que qualquer comportamento padrão do método na superclasse
        // (por exemplo, qualquer configuração padrão de menu que a superclasse realiza) seja executado antes
        // de você adicionar suas próprias ações ao menu.
        super.onCreateContextMenu(menu, v, menuInfo);

        // Cria um objeto MenuInflater, que é responsável por inflar (converter um arquivo XML de menu em um objeto Menu)
        // o menu de contexto a partir de um arquivo XML de menu que você criou anteriormente.
        MenuInflater i = getMenuInflater();

        // O método inflate do MenuInflater é usado para inflar o menu de contexto.
        // Aqui, você está especificando o recurso XML (R.menu.menu_contexto) que define as opções de menu
        // que aparecerão quando um item da lista for pressionado.
        i.inflate(R.menu.menu_contexto, menu); //Aqui coloca o nome do menu que havia sido configurado
    }

    //METODO EXCLUIR
    public void excluir(MenuItem item){
        //pegar qual a posicao do item da lista que eu selecionei para excluir
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)
                item.getMenuInfo();
        final Aluno alunoExcluir = alunosFiltrados.get(menuInfo.position);
        //mensagem perguntando se quer realmente excluir
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir o aluno?")
                .setNegativeButton("NÃO",null)
                .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alunosFiltrados.remove(alunoExcluir);
                        alunos.remove(alunoExcluir);
                        dao.excluir(alunoExcluir);
                        listView.invalidateViews();
                    }
                } ).create(); //criar a janela
        dialog.show(); //manda mostrar a janela
    }

    //MÉTODO ATUALIZAR
    public void atualizar(MenuItem item){
        //mesma lógica do excluir porque o botão de menu é o mesmo
        //pegar qual a posicao do item da lista que eu selecionei para atualizar
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Aluno alunoAtualizar = alunosFiltrados.get(menuInfo.position);

        //Ao selecionar atualizar, abrir a janela de cadastro e enviar esse aluno para lá
        Intent it = new Intent(this, MainActivity.class); //Nosso cadastrar se chama 'MainActivity’

        //será preenchido com os dados do aluno que quer atualizar, podemos alterar e salvar
        it.putExtra("aluno",alunoAtualizar);
        startActivity(it);
    }


}