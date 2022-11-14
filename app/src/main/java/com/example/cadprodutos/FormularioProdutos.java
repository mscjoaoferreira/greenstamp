package com.example.cadprodutos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cadprodutos.model.DBHelper.ProdutosDB;
import com.example.cadprodutos.model.Produtos;

public class FormularioProdutos extends AppCompatActivity {
    private EditText et_NomeProduto;
    private EditText et_DescricaoProduto;
    private EditText et_qtdProduto;
    private Button bt_CadastrarProduto;
    private Produtos editarProdutos,novoProduto;
    ProdutosDB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_produtos);

        Intent intent = getIntent();
        editarProdutos = (Produtos) intent.getSerializableExtra("produto-escolhido");

        et_NomeProduto = findViewById(R.id.et_NomeProduto);
        et_DescricaoProduto = findViewById(R.id.et_DescricaoProduto);
        et_qtdProduto = findViewById(R.id.et_qtdProduto);
        bt_CadastrarProduto = findViewById(R.id.bt_CadastrarProduto);
        dbHelper = new ProdutosDB(FormularioProdutos.this);
        novoProduto = new Produtos();

        // O texto do botão varia de acordo com a pesquisa
        if (editarProdutos != null){
            bt_CadastrarProduto.setText("Modificar");

            et_NomeProduto.setText(editarProdutos.getNomeProduto());
            et_DescricaoProduto.setText(editarProdutos.getDescricaoProduto());
            et_qtdProduto.setText(editarProdutos.getQtdProduto()+"")    ;
            novoProduto.setId(editarProdutos.getId());


        }else{
            bt_CadastrarProduto.setText("Cadastrar");
        }

        bt_CadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(et_NomeProduto.getText().toString());
                novoProduto.setNomeProduto(et_NomeProduto.getText().toString());
                novoProduto.setDescricaoProduto(et_DescricaoProduto.getText().toString());
                novoProduto.setQtdProduto(Integer.parseInt(et_qtdProduto.getText().toString()));

                if(bt_CadastrarProduto.getText().toString().equals("Cadastrar")){
                        dbHelper.salvarProdutos(novoProduto);
                        dbHelper.close();

                }else{
                    dbHelper.alterarProduto(novoProduto);
                    dbHelper.close();
                }

                finish();

            }



        });

    }




}