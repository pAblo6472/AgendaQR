package com.example.personal.agenda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    EditText editTextLoginUsuario, editTextLoginPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = this;
        final SharedPreferences sharedPreferences = getSharedPreferences("Archivo", context.MODE_PRIVATE);

        editTextLoginUsuario=findViewById(R.id.editTextUsuario);
        editTextLoginPassword=findViewById(R.id.editTextPassword);

        mostrar();
    }

   /* public void onClickMostrar(View v){
        SharedPreferences sharpref = getPreferences(Context.MODE_PRIVATE);
        String valor = sharpref.getString("Usuario","No hay dato");
        Toast.makeText(getApplicationContext(), "Usuario : "+valor,Toast.LENGTH_LONG).show();
    }*/


    public void onClickAceptar(View v){
        guardar();
        String user = editTextLoginUsuario.getText().toString();
        String password = editTextLoginPassword.getText().toString();
        /*Bundle bundle = new Bundle();

        bundle.putString("user",user);
        bundle.putString("password",password);

        Intent intent = new Intent(this,ContactosActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);*/
        mostrar();
        finish(); /*destruye la pantalla */


    }


    public void guardar(){

        //SharedPreferences sharpref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharpref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharpref.edit();
        editor.putString("Usuario", editTextLoginUsuario.getText().toString());
        editor.putString("Contrasenia", editTextLoginPassword.getText().toString());
        editor.commit();

    }

    public String mostrar(){
        SharedPreferences sharpref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
        String valor = sharpref.getString("Usuario","");
        String valor1 = sharpref.getString("Contrasenia","");
        Toast.makeText(getApplicationContext(), "Usuario : "+valor,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "Contrasenia : "+valor1,Toast.LENGTH_LONG).show();
        if(valor!=""){
            Intent intent = new Intent(this, ContactosActivity.class);
            startActivity(intent);
            finish();
        }


        return valor;
    }






}
