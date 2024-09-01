package com.lbcardoso.jappcadastraaluno.Pagamento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lbcardoso.jappcadastraaluno.Conexao;

import java.util.ArrayList;
import java.util.List;

public class PagamentoDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    public PagamentoDAO(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    //método para cadastrar
    public long inserir(Pagamento pagamento){
        ContentValues values = new ContentValues();
        values.put("id", pagamento.getId());
        values.put("alunoId", pagamento.getAlunoId());
        values.put("valor", pagamento.getValor());
        values.put("data", pagamento.getData());

        return banco.insert("pagamento", null, values);
    }

    //método para consultar
    public List<Pagamento> obterTodos() {
        List<Pagamento> pagamentos = new ArrayList<>();
        //cursor aponta para as linhas retornadas
        Cursor cursor = banco.query("pagamento", new String[]{"id", "alunoId", "valor", "data"},
                null, null, null, null, null); //nome da tabela, nome das colunas, completa com null o método

        //que por padrão pede esse número de colunas obrigatórias
        while (cursor.moveToNext()) { //verifica se consegue mover para o próximo ponteiro ou linha
            Pagamento a = new Pagamento();
            a.setId(cursor.getInt(0));
            a.setAlunoId(cursor.getInt(1));
            a.setValor(cursor.getDouble(2));
            a.setData(cursor.getString(3));
            pagamentos.add(a);
        }
        return pagamentos;
    }

    //Método para excluir
    public void excluir(Pagamento a){
        banco.delete("pagamento", "id = ?",new String[]{a.getId().toString()}); // no lugar do ? vai colocar o id
    }

    //Método para atualizar
    public void atualizar(Pagamento pagamento) {
        ContentValues values = new ContentValues(); //valores que irei inserir
        values.put("id", pagamento.getId());
        values.put("alunoId", pagamento.getAlunoId());
        values.put("valor", pagamento.getValor());
        values.put("data", pagamento.getData());
        banco.update("pagamento", values, "id = ?", new String[]{pagamento.getId().toString()});
    }
}
