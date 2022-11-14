package com.example.cadprodutos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.cadprodutos.model.DBHelper.ProdutosDB;
import com.example.cadprodutos.model.GuardarFicheiroLocal;
import com.example.cadprodutos.model.Produtos;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView lista;
    ProdutosDB bdHelper;
    ArrayList<Produtos> list_viewProdutos;
    Produtos produto;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.list_viewProdutos);
        produto = new Produtos();

        registerForContextMenu(lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int i, long l) {
                Produtos produtoEscolhido = (Produtos) adapter.getItemAtPosition(i);

                Intent intent = new Intent(MainActivity.this,FormularioProdutos.class);
                intent.putExtra("produto-escolhido", produtoEscolhido);
                startActivity(intent);

            }
        });



        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View view, int i, long l) {
                produto = (Produtos) adapter.getItemAtPosition(i);

                //OnCreateContextMenu(addOnContextAvailableListener(),view,list_viewProdutos);

                return false;
            }
        });




        Button bt_Cadastrar = findViewById(R.id.bt_AcMain_CadProd);
        bt_Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,FormularioProdutos.class);
                startActivity(intent);
            }
        });

        Button bt_GardarLocal = findViewById(R.id.bt_AcMain_Guardar_Ficheiro_Local);
        bt_GardarLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, GuardarFicheiroLocal.class);
                startActivity(intent2);
            }
        });


        Button bt_ACSP = findViewById(R.id.bt_ACSP);
        bt_ACSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this,SharedPreferencesScreen.class);
                startActivity(intent3);
            }
        });

    }

    protected void onResume(){
        super.onResume();
        carregarProduto();

    }



     @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu,v,menuInfo);

        MenuItem menuDelete = menu.add("Deletar Este Produto");
        menuDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                bdHelper = new ProdutosDB(MainActivity.this);
                bdHelper.deletarProduto(produto);
                bdHelper.close();

                carregarProduto();
                return true;
            }
        });
    }

    public void carregarProduto(){
            bdHelper = new ProdutosDB(MainActivity.this);
            list_viewProdutos = bdHelper.getLista();
            bdHelper.close();

            if(list_viewProdutos!=null){
                adapter = new ArrayAdapter<Produtos>(MainActivity.this, android.R.layout.simple_list_item_1,list_viewProdutos);
                lista.setAdapter(adapter);
            }
          //  finish();
    }

}