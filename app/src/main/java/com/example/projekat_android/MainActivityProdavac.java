package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapters.ArtikalAdapterProdavac;
import model.Artikal;

public class MainActivityProdavac extends AppCompatActivity {
    RecyclerView recyclerView;
    private SharedPreferenceConfig sharedPreferenceConfig;
    DBHelper DB;
    ArtikalAdapterProdavac artikliProdavac;
    List<Artikal> artikal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_prodavac);

        recyclerView=findViewById(R.id.recyclerViewProdavac);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        DB = new DBHelper(this);

        SharedPreferences prefs = getSharedPreferences("My", MODE_PRIVATE);
        int idP = prefs.getInt("idProdavca", 0);
        String idProdavca = String.valueOf(idP);
        String usernameProdavca = prefs.getString("userProdavac", "No name defined");

        artikal = DB.getArtikliProdavca(idProdavca);

        if (artikal.size() > 0){
            artikliProdavac = new ArtikalAdapterProdavac(this, artikal, recyclerView);
            recyclerView.setAdapter(artikliProdavac);
        }else{
            Toast.makeText(MainActivityProdavac.this, "Nema artikala u bazi podataka", Toast.LENGTH_SHORT).show();
        }
    }
}
