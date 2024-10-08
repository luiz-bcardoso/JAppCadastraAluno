package com.lbcardoso.jappcadastraaluno.Aluno;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lbcardoso.jappcadastraaluno.Conexao;

import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public AlunoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    //AULA 1 - método para cadastrar
    public long inserir(Aluno aluno){
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        values.put("foto", aluno.getFoto());
        values.put("grauEscolar", aluno.getGrauEscolar());
        values.put("isActive", aluno.getActive());

        return banco.insert("aluno", null, values);
    }

    //AULA 2 - método para consultar
    public List<Aluno> obterTodos() {
        List<Aluno> alunos = new ArrayList<>();
        //cursor aponta para as linhas retornadas
        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone", "foto", "grauEscolar", "isActive"},
                null, null, null, null, null); //nome da tabela, nome das colunas, completa com null o método

        //que por padrão pede esse número de colunas obrigatórias
        while (cursor.moveToNext()) { //verifica se consegue mover para o próximo ponteiro ou linha
            Aluno a = new Aluno();
            a.setId(cursor.getInt(0)); // new String[]{"id", "nome", "cpf", "telefone"}, id é coluna '0'
            a.setNome(cursor.getString(1)); // new String[]{"id", "nome", "cpf", "telefone"}, nome é coluna '1'
            a.setCpf(cursor.getString(2)); // new String[]{"id", "nome", "cpf", "telefone"}, cpf é coluna '2'
            a.setTelefone(cursor.getString(3)); // new String[]{"id", "nome", "cpf", "telefone"}, telefone é coluna '3'
            a.setFoto(cursor.getBlob(4)); // new Byte[]
            a.setGrauEscolar(cursor.getString(5));
            a.setActive(cursor.getInt(6));
            alunos.add(a);
        }
        return alunos;
    }

    //AULA 3 - Método para excluir
    public void excluir(Aluno a){
        banco.delete("aluno", "id = ?",new String[]{a.getId().toString()}); // no lugar do ? vai colocar o id do aluno
    }

    //AULA 3 - Método para atualizar aluno
    public void atualizar(Aluno aluno) {
        ContentValues values = new ContentValues(); //valores que irei inserir
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        values.put("foto", aluno.getFoto());
        values.put("grauEscolar", aluno.getGrauEscolar());
        values.put("isActive", aluno.getActive());
        banco.update("aluno", values, "id = ?", new String[]{aluno.getId().toString()});
    }

}
