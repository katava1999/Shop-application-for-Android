package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.ArtikalAdapterProdavac;
import adapters.ArtikalPrikazAdapter;
import model.Artikal;
import model.Kupljen;

public class PrikazKupljenihArtikalaActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DBHelper DB;
    ArtikalPrikazAdapter artikalPrikazAdapter;
    List<Kupljen> kupljen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prikaz_kupljenih_artikala);

        recyclerView = findViewById(R.id.prikazArtikala);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        DB = new DBHelper(this);

        SharedPreferences prefs = getSharedPreferences("My", MODE_PRIVATE);
        //String idP = prefs.getString("idProdavca", "No name defined");
        String usernameProdavca = prefs.getString("userName", "No name defined");

        kupljen = DB.getArtikliKupca(usernameProdavca);

        if (kupljen.size() > 0){
            artikalPrikazAdapter = new ArtikalPrikazAdapter(this, kupljen, recyclerView);
            recyclerView.setAdapter(artikalPrikazAdapter);
            Toast.makeText(PrikazKupljenihArtikalaActivity.this, "Total number of bought items: " + kupljen.size(), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(PrikazKupljenihArtikalaActivity.this, "There are no items in the database", Toast.LENGTH_SHORT).show();
        }
    }
}
