package com.example.personal.agenda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetalleActivity extends FragmentActivity implements OnMapReadyCallback {

    TextView textViewContactName;
    TextView textViewContactPhone;
    ImageView imageViewD;
    private GoogleMap mMap;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        contact = (Contact) bundle.getSerializable("contact");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        textViewContactName = findViewById(R.id.textViewContactName);
        textViewContactPhone = findViewById(R.id.textViewContactPhone);
        imageViewD = findViewById(R.id.imageViewD);

        textViewContactName.setText(contact.name);
        textViewContactPhone.setText(contact.phone);
        imageViewD.setImageBitmap(BitmapFactory.decodeFile(contact.image));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Double lat = Double.parseDouble(contact.lat);
        Double lng = Double.parseDouble(contact.lng);

        LatLng sydney = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


}
