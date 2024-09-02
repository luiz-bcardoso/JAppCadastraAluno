package com.lbcardoso.jappcadastraaluno;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lbcardoso.jappcadastraaluno.Aluno.Aluno;
import com.lbcardoso.jappcadastraaluno.Aluno.AlunoDAO;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    //Define o nome dos campos e a DAO
    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private AlunoDAO dao;

    private CheckBox isActive;
    private RadioGroup grauEscolar;
    private RadioButton grauEscolhido;

    private Aluno aluno = null;

    //Elementos utilizados na foto do aluno
    private ImageView iV_fotoAluno;
    private Bitmap foto;

    //Códigos usados na permissao da camera e armazenamento
    private static final int CAMERA_REQUEST_CODE = 100;

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
        iV_fotoAluno = findViewById(R.id.imageView_fotoAluno);
        isActive = findViewById(R.id.chkBox_estaAtivo);
        grauEscolar = findViewById(R.id.radioGroup);

        dao = new AlunoDAO(this);

        //(Editar) PEGAR OS DADOS QUE VEM NO INTENT DO ATUALIZAR
        Intent it = getIntent(); //pega intenção
        if(it.hasExtra("aluno")) {
            aluno = (Aluno) it.getSerializableExtra("aluno");
            nome.setText(aluno.getNome().toString());
            cpf.setText(aluno.getCpf());
            telefone.setText(aluno.getTelefone());
            iV_fotoAluno.setImageBitmap(byteArrayToBitmap(aluno.getFoto()));
            //TODO: Restore selected radioButton id somehow
            for(int i = 0; i<grauEscolar.getChildCount(); i++){
                View view = grauEscolar.getChildAt(i);
                if(view instanceof RadioButton){
                    RadioButton selecaoValida = (RadioButton) view;
                    if(selecaoValida.getText().equals(aluno.getGrauEscolar())){
                        selecaoValida.setChecked(true);
                        break;
                    }
                }
            }
            isActive.setChecked(aluno.getActive() == 1);

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
            grauEscolhido = findViewById(grauEscolar.getCheckedRadioButtonId());

            //Atribui os dados do formulário preenchido
            a.setNome(nome.getText().toString());
            a.setCpf(cpf.getText().toString());
            a.setTelefone(telefone.getText().toString());
            a.setFoto(bitmapToByteArray(foto));
            a.setGrauEscolar(grauEscolhido.getText().toString());
            if(isActive.isChecked()){
                a.setActive(1);
            }else{
                a.setActive(0);
            }

            //Cadastra o aluno e informa ao usuário
            long id = dao.inserir(a);
            Toast.makeText(this, "Aluno inserido com id: " + id, Toast.LENGTH_SHORT).show();

        // Aluno sendo atualizado
        }else{
            //RECEBENDO DADOS DO ATUALIZAR NA VARIAVEL 'aluno'
            grauEscolhido = findViewById(grauEscolar.getCheckedRadioButtonId());
            aluno.setNome(nome.getText().toString());
            aluno.setCpf(cpf.getText().toString());
            aluno.setTelefone(telefone.getText().toString());
            aluno.setFoto(bitmapToByteArray(foto));
            aluno.setGrauEscolar(grauEscolhido.getText().toString());
            if(isActive.isChecked()){
                aluno.setActive(1);
            }else{
                aluno.setActive(0);
            }

            dao.atualizar(aluno); //inserir o aluno
            Toast.makeText(this,"Aluno Atualizado!! com id: ", Toast.LENGTH_SHORT).show();
        }

    }

    public void listarAlunos(View view) {
        Intent intent = new Intent(this, ListarAlunoActivity.class);
        startActivity(intent);
    }
    

    // Método para abrir camera e capturar foto do aluno
    public void tirarFoto(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    //Método que captura a imagem retornada pela camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK){
            //Gera o BITMAP da foto do aluno e carrega no ImageView do cadastro.
            foto = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            iV_fotoAluno.setImageBitmap(foto);
            Toast.makeText(this,"Foto cadastrada com sucesso!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Falha ao abrir a camera, revise as permissões...", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Método que converte BITMAP para BYTE ARRAY
    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    // Método que converte BYTE ARRAY para BITMAP
    private Bitmap byteArrayToBitmap(byte[] byteArray) {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }


}