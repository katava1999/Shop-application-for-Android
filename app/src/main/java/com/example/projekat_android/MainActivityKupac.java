package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
    TextView odjava;
    EditText pretraga;

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


        odjava = findViewById(R.id.btnOutKupac);
        odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getSharedPreferences("My", Context.MODE_PRIVATE);
                String usernameKupca = sharedPref.getString("userName", "No name defined");
                Toast.makeText(MainActivityKupac.this, "Odjavljen kupac: " + usernameKupca, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
        pretraga = findViewById(R.id.idPretragaKupac);
        pretraga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        final TextView artikliProdavac = findViewById(R.id.prodavacArtikli);
        artikliProdavac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityKupac.this, MainActivityProdavac.class);
                startActivity(intent);
            }
        });

    }



    private void filter(String text) {
        ArrayList<Artikal> filterList = new ArrayList<>();
        for (Artikal item : artikalList) {
            if (item.getNaziv().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            } else if (item.getOpis().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(item);
            }
            artikliAdapter.filteredList(filterList);
        }
    }
}
