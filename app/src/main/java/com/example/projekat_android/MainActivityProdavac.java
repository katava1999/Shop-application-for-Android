package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
        String idP = prefs.getString("idProdavca", "No name defined");
        //String usernameProdavca = prefs.getString("userProdavac", "No name defined");

        artikal = DB.getArtikliProdavca(idP);

        if (artikal.size() > 0){
            artikliProdavac = new ArtikalAdapterProdavac(this, artikal, recyclerView);
            recyclerView.setAdapter(artikliProdavac);
        }else{
            Toast.makeText(MainActivityProdavac.this, "Nema artikala u bazi podataka", Toast.LENGTH_SHORT).show();
        }

        TextView odjava = findViewById(R.id.odjavaProdavac);
        odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("My", MODE_PRIVATE);
                String user = prefs.getString("userProdavac", "No name defined");
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                Toast.makeText(MainActivityProdavac.this, "Odjavljen prodavac "+user, Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });

    }
}
