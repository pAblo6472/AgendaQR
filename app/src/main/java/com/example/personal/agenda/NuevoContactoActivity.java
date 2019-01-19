package com.example.personal.agenda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class NuevoContactoActivity extends AppCompatActivity {

    //declara los abojetos de la vista
    //ImageView imageViewNCimage, imageViewNC1,imageViewNC2,imageViewNC3,imageViewNC4;
    ImageView imageViewChange;
    EditText editTextNCName,editTextNCPhone, editTextNCLat,editText2NCLen;
    String image;
    TextView txtNombre;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_contacto);
        imageViewChange = findViewById(R.id.imageViewNCimage);
        editTextNCName= findViewById(R.id.editTextNCName);
        editTextNCPhone = findViewById(R.id.editTextNCPhone);
        editTextNCLat = findViewById(R.id.editTextNCLat);
        editText2NCLen = findViewById(R.id.editText2NCLen);
        txtNombre = findViewById(R.id.txtNombre);

        SharedPreferences sharpref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
        String valor = sharpref.getString("Usuario","");
        txtNombre.setText(valor);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!= null){
            Contact contact = (Contact) bundle.getSerializable("contact");
            String name = contact.name;
            String phone = contact.phone;
            editTextNCName.setText(name);
            editTextNCPhone.setText(phone);
        }


    }


    public void guardar(){

        //SharedPreferences sharpref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharpref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharpref.edit();
        editor.putInt("count", count );
        editor.commit();
    }

    //ingresamos el codigo para la galeria y camara

    public void onClickTakePic(View v){
        switch (v.getId()){
            case R.id.buttonCamaraNuevoContacto:
                SharedPreferences sharpref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
                count = sharpref.getInt("imagen",0);
                count++;
                guardar();
                image = Environment.getExternalStorageDirectory() + "/" + "fotouser"+count+".jpg";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Uri output = Uri.fromFile(new File(photo));
                Uri output = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID, new File(image));

                intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                startActivityForResult(intent, 0);
                break;
            case R.id.buttonGaleriaNuevoContacto:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
                break;

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (resultCode == this.RESULT_OK) {
                    File fileTemp = new File(image);
                    if (!fileTemp.exists()) {
                        Toast.makeText(this,
                                "No se ha realizado la foto", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        imageViewChange.setImageBitmap(BitmapFactory.decodeFile(image));
                    }
                }

                break;
            case 1:
                if (resultCode == this.RESULT_OK) {
                    Uri uri = data.getData();
                    String[] projection = { MediaStore.Images.Media.DATA };
                    Cursor cursor = this.getContentResolver().query(uri, projection,
                            null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    image = cursor.getString(columnIndex); // returns null
                    cursor.close();
                    imageViewChange.setImageBitmap(BitmapFactory.decodeFile(image));
                }
                break;
        }

    }





/*

    //identifica el objeto q inicia el evento View V

    public void onClickImage(View v){
        //extraer el id del objeto
        switch (v.getId()){
            //nos e necesita declara xq se va a trabajar con el id
            case R.id.imageViewNC1:

                imageViewChange.setImageResource(R.drawable.usuario1);
                image=R.drawable.usuario1;

                break;

            case R.id.imageViewNC2:

                imageViewChange.setImageResource(R.drawable.usuario2);
                image=R.drawable.usuario2;

                break;

            case R.id.imageViewNC3:

                imageViewChange.setImageResource(R.drawable.usuario3);
                image=R.drawable.usuario3;

                break;

            case R.id.imageViewNC4:

                imageViewChange.setImageResource(R.drawable.usuario4);
                image=R.drawable.usuario4;

                break;

        }

    }
*/
    public void onClickGuardar(View v){

        String name = editTextNCName.getText().toString();
        String phone = editTextNCPhone.getText().toString();
        String lat = editTextNCLat.getText().toString();
        String lng = editText2NCLen.getText().toString();
        //guardar los datos n un objeto por mejdio del construxtor
        //guardar objetos en el programa es mas escalable

        Contact contact = new Contact(image,name,phone,lat,lng);
        //libreria oml hace que la clase contacto herede de la libreria y utilice los metodos

        contact.save();
        Intent intent = new Intent(this,ContactosActivity.class);
        startActivity(intent);
        finish();

    }

}
