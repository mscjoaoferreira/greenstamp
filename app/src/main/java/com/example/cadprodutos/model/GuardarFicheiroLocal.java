package com.example.cadprodutos.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cadprodutos.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class GuardarFicheiroLocal extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE=1000;
    private static final int READ_REQUEST_CODE = 42;

    EditText et_ACGFicheiro_Nome;
    EditText et_ACGFicheiro_Conteudo,et_AcG_read_filer;
    Button btn_ACGFicheiro_Guardar,btn_AcGFicheiro_Deletar,btn_AcG_Read_file;
    EditText et_AcGFicheiro_Nome_Deletar;
    TextView tv_readfile_output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar_ficheiro_local);

            //Request write Permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }

        et_ACGFicheiro_Conteudo = findViewById(R.id.et_AcGFicheiro_Conteudo);
        et_ACGFicheiro_Nome = findViewById(R.id.et_AcGFicheiro_Nome);
        btn_ACGFicheiro_Guardar = findViewById(R.id.btn_AcGFicheiro_Guardar);
        et_AcG_read_filer = findViewById(R.id.et_AcG_read_filer);




        btn_ACGFicheiro_Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String nomeFicheiro = et_ACGFicheiro_Nome.getText().toString();
                    String conteudoFicheiro = et_ACGFicheiro_Conteudo.getText().toString();

                    if(!nomeFicheiro.equals("") && !conteudoFicheiro.equals("")){
                        guardarTextoemFicheiro(nomeFicheiro, conteudoFicheiro);
                }

            }
        });

        //Section: Read File ======================================================

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_STORAGE);
        }

        tv_readfile_output = findViewById(R.id.tv_readfile_output);
        btn_AcG_Read_file = findViewById(R.id.btn_AcG_Read_file);

        btn_AcG_Read_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //performFileSearch();
                if (!et_AcG_read_filer.getText().toString().isEmpty()) {
                    tv_readfile_output.setText(readText(et_AcG_read_filer.getText().toString()+".txt"));
                }else{
                    Toast.makeText(GuardarFicheiroLocal.this,"Please enter a name for the file.",Toast.LENGTH_SHORT).show();
                }
            }
        });



        //Section: Delete File ======================================================

        btn_AcGFicheiro_Deletar = findViewById(R.id.btn_AcGFicheiro_Deletar);
        et_AcGFicheiro_Nome_Deletar = findViewById(R.id.et_AcGFicheiro_Nome_Deletar);

       btn_AcGFicheiro_Deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeFicheiro_Deletar = et_AcGFicheiro_Nome_Deletar.getText().toString();
                //File diret;
                //diret = getFilesDir();
               // System.out.println("Diretorio --> "+diret);
                //System.out.println("Nome Ficheiro -->"+nomeFicheiro_Deletar);
                deletarFicheiro(nomeFicheiro_Deletar+".txt");
                System.out.println("Nome Ficheiro -->"+nomeFicheiro_Deletar+".txt");
            }
        });





    }

    private void guardarTextoemFicheiro(String nomeficheiro, String conteudoFicheiro) {
        String nomeFicheiro = nomeficheiro + ".txt";

        //Criar arquivo
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),nomeFicheiro);

        //Escrever no arquivo
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(conteudoFicheiro.getBytes());
            fos.close();
            Toast.makeText(this,"Ficheiro Gravado com Sucesso!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,"Ficheiro não encontrado!",Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this,"Erro ao Gravar!", Toast.LENGTH_SHORT).show();
        }

    }

   // Diretório onde os ficheiros estão salvos --> /storage/self/primary
   private void deletarFicheiro(String nomeFicheiro){
        //File diretorio = getFilesDir();

       File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),nomeFicheiro);
       System.out.println("======= DIRETORIO --> "+Environment.getExternalStorageDirectory().getAbsolutePath());
       System.out.println("======= ARQUIVO --> "+nomeFicheiro);
       System.out.println("====== FILE-ABSOLUTPATH"+file.getAbsolutePath());

        file.delete();

        boolean fileDeleted = file.delete();

        if(fileDeleted){
            Toast.makeText(this,"Ficheiro Deletado! UHUUUUU:"+nomeFicheiro,Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"Ficheiro NÃO deletado",Toast.LENGTH_SHORT).show();
        }
   }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //   super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission not Granted!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }

    }


    //Section: Read Text ======================================================
    private String readText(String input){
       // File file = new File(Environment.getExternalStorageDirectory(), input);
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),input);
        StringBuilder text = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine())!=null){
                text.append(line);
                text.append("\n");                 }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this,"Exception: File not Found!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();

        }

        return text.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            if(data !=null){{
                Uri uri = data.getData();
                String path =  uri.getPath();
                path.substring(path.indexOf(":")+1);
                if(path.contains("emulated")){
                    path =  path.substring(path.indexOf("0")+1);
                }
                Toast.makeText(this, ""+path,Toast.LENGTH_SHORT).show();
                tv_readfile_output.setText(readText(path));
            }
            }
        }
    }

    //Section: Select File  from Storage ======================================================

    private void performFileSearch(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent,READ_REQUEST_CODE);
    }

}