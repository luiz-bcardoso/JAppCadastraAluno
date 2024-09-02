package com.lbcardoso.jappcadastraaluno;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "bancoAlunoV6.db";
    private static final int version = 1;

    public Conexao(Context context){
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Cria a tabela Aluno
        db.execSQL("create table aluno(id integer primary key autoincrement," +
                "nome varchar(120), cpf varchar(14), telefone varchar(12), foto BLOB)");

        //Cria a tabela Pagamento
        db.execSQL("CREATE TABLE pagamento(id integer primary key autoincrement NOT NULL, " +
                "alunoId integer NOT NULL, valor double, data date," +
                "CONSTRAINT FK_AlunoPagamento FOREIGN KEY (alunoId) REFERENCES Aluno(id))");
                // Restrição de chave estangeira para Aluno(id)
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS pagamento");
        db.execSQL("DROP TABLE IF EXISTS aluno");
        onCreate(db);
    }

}
