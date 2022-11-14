package com.example.cadprodutos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SharedPreferencesScreen extends AppCompatActivity {

    Button bt_ACSP_store,bt_ACSP_retrieve,bt_ACSP_delete;
    EditText et_ACSP_username, et_ACSP_password;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences_screen);

        et_ACSP_username = findViewById(R.id.et_ACSP_username);
        et_ACSP_password = findViewById(R.id.et_ACSP_password);
        bt_ACSP_store = findViewById(R.id.bt_ACSP_store);
        bt_ACSP_retrieve = findViewById(R.id.bt_ACSP_retrieve);
        bt_ACSP_delete = findViewById(R.id.bt_ACSP_delete);
        sharedPreferences = getSharedPreferences("Data",MODE_PRIVATE);

        bt_ACSP_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("username",et_ACSP_username.getText().toString());
                editor.putString("password",et_ACSP_password.getText().toString());
                editor.apply();

                Toast.makeText(SharedPreferencesScreen.this, "Stored!", Toast.LENGTH_SHORT).show();
            }
        });

        bt_ACSP_retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.contains("username")){
                    et_ACSP_username.setText(sharedPreferences.getString("username",""));
                }

                if (sharedPreferences.contains(("password"))){
                    et_ACSP_password.setText(sharedPreferences.getString("password",""));
                }


            }
        });
        bt_ACSP_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.clear();
                editor.commit();
                et_ACSP_username.setText("");
                et_ACSP_password.setText("");
                Toast.makeText(getApplicationContext(), "Deleted!",Toast.LENGTH_SHORT).show();
            }
        });


    }
}