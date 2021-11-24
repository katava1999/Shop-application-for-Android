package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import adapters.ArtikalAdapter;
import model.Artikal;

public class MainActivityKupac extends AppCompatActivity {

    DBHelper DB;
    private SharedPreferenceConfig sharedPreferenceConfig;
    RecyclerView recyclerView;
    ArtikalAdapter artikliAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Artikal> artikalList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kupac);

        DB = new DBHelper(this);
        artikalList = DB.getArtikli();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        artikliAdapter = new ArtikalAdapter(this, artikalList, recyclerView);
        recyclerView.setAdapter(artikliAdapter);
    }
}
