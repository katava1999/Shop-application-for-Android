package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import model.Artikal;

public class DodajArtikalActivity extends AppCompatActivity {
    EditText txtNaziv, txtOpis, txtCena;
    TextView dodaj;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_artikal);

        txtNaziv = findViewById(R.id.txtNaziv);
        txtOpis = findViewById(R.id.txtOpis);
        txtCena = findViewById(R.id.txtCena);
        dodaj = findViewById(R.id.dodajartikalubazu);

        /*
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");

         */
        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getExtras().getString("id");
                Integer ID = Integer.valueOf(id);

                String naziv = txtNaziv.getText().toString();
                String opis = txtOpis.getText().toString();
                Double cena = Double.valueOf(txtCena.getText().toString());

                if (naziv.equals("") || opis.equals("") || cena.equals("")){
                    Toast.makeText(DodajArtikalActivity.this, "Unesite sva polja", Toast.LENGTH_SHORT).show();
                }else{
                    DB = new DBHelper(DodajArtikalActivity.this);
                    Artikal artikal = new Artikal(naziv, opis, cena, ID);
                    DB.insertArtikal(artikal);
                    Toast.makeText(DodajArtikalActivity.this, "Uspesno ste dodali artikal", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivityProdavac.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
