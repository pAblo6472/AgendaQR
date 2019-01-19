package com.example.personal.agenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactosActivity extends AppCompatActivity {
    TextView textViewContactosUser;
    ListView listViewContactos;
    List <Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos); /*aqui se dice que vista se va a controlar */
        textViewContactosUser=findViewById(R.id.textViewContactoUser);
        listViewContactos=findViewById(R.id.listViewContactos);
        mostrarDatos();


        //Intent intent = this.getIntent();
       // Bundle bundle = intent.getExtras();

        /* if(intent.getExtras()!=null) {
            String user = bundle.getString("user");
            String password = bundle.getString("password");
            textViewContactosUser.setText(user);
        }*/

       /* Contact contacto1 = new Contact(R.drawable.usuario1,"Hamilton","0992570844");
        Contact contacto2 = new Contact(R.drawable.usuario2,"Juan","0992570447");
        Contact contacto3 = new Contact(R.drawable.usuario3,"Solano","0994578945");


  */
       // List<Contact> contacts = new ArrayList<Contact>();
/*
        contacts.add(contacto1);
        contacts.add(contacto2);
        contacts.add(contacto3);
*/
    //se almacena en el listado
        //listall busca en toda la tabla de contactos los mapea y asigna como un objeto de tipo list

        contacts = Contact.listAll(Contact.class); /* se obtiene todo el contenido de la tabla contacto*/
        MyCustomArrayAdapter adapter = new MyCustomArrayAdapter(this,contacts);
        listViewContactos.setAdapter(adapter);

        listViewContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Contact contact = contacts.get(position);
                /*
                String name = contact.name;
                String phone = contact.phone;

                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("phone",phone);
                */

                Bundle bundle = new Bundle();
                bundle.putSerializable("contact", contact);
                Intent intent = new Intent(ContactosActivity.this,DetalleActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



    }

    public void mostrarDatos(){
        SharedPreferences sharpref = getSharedPreferences("Archivo", Context.MODE_PRIVATE);
        String valor = sharpref.getString("Usuario","");
        textViewContactosUser.setText(valor);

    }

    public void onClickNuevoContacto(View v){
        Intent intent = new Intent(this,NuevoContactoActivity.class);
                startActivity(intent);
                mostrarDatos();
                finish();
    }

    public class MyCustomArrayAdapter extends ArrayAdapter<Contact> {// adaptador utiliza la clase contact. cambiar el nombre y crear otra clase
        private final Activity context;
        private List<Contact> contacts;

        class ViewHolder {// de claracion de objetos que se pasa en la plantilla de las filas de la lista
            ImageView imageContact;
            TextView nameContact;
            TextView phoneContact;
            TextView latContact;
            TextView lngContact;
        }

        public MyCustomArrayAdapter(Activity context, List<Contact> contacts) {//contac si se hace refencia a otra clase se cambia
            super(context, R.layout.layout_row, contacts);//se hace refencia al layout que contiene la  plantilla de las filas
            this.context = context;
            this.contacts = contacts;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                rowView = inflater.inflate(R.layout.layout_row, null);//se hace refencia al layout que contiene la  plantilla de las filas
                final ViewHolder viewHolder = new ViewHolder();


                // se refencia a los objetos gaficos de la plantilla lista
                viewHolder.imageContact = (ImageView) rowView.findViewById(R.id.imageButtonRow);
                viewHolder.nameContact = (TextView) rowView.findViewById(R.id.textViewRowName);
                viewHolder.phoneContact = (TextView) rowView.findViewById(R.id.textViewRowPhone);
                viewHolder.latContact = (TextView) rowView.findViewById(R.id.textViewRowLat);
                viewHolder.lngContact = (TextView) rowView.findViewById(R.id.textViewRowLng);
                rowView.setTag(viewHolder);
            }
            ViewHolder holder = (ViewHolder) rowView.getTag();
            Contact contact = contacts.get(position);
            holder.imageContact.setImageBitmap(BitmapFactory.decodeFile(contact.image));//se hace refencia  a los atributos de la clase contacto
            holder.nameContact.setText(contact.name);//se hace refencia  a los atributos de la clase contacto
            holder.phoneContact.setText(contact.phone);//se hace refencia  a los atributos de la clase contacto
            holder.latContact.setText(contact.lat);
            holder.lngContact.setText(contact.name);
            return rowView;
        }
    }


}
