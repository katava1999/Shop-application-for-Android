package com.example.projekat_android;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import adapters.ArtikalAdapterProdavac;
import model.Artikal;

public class MainActivityProdavac extends AppCompatActivity {
    RecyclerView recyclerView;
    DBHelper DB;
    ArtikalAdapterProdavac artikliProdavac;
    List<Artikal> artikal = new ArrayList<>();
    private Spinner Spinner;

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
            Toast.makeText(MainActivityProdavac.this, "There are no items in the database", Toast.LENGTH_SHORT).show();
        }

        //Odavde ide SPINNER
        Spinner = findViewById(R.id.ToolBar);

        List<String> categories = new ArrayList<>();
        categories.add(0, "Options");
        categories.add("Return to items");
        categories.add("Add a new item");



        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner.setAdapter(dataAdapter);

        Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("")) {
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(parent.getContext(), "Odabrali ste: " + item, Toast.LENGTH_SHORT).show();

                    if (parent.getItemAtPosition(position).equals("Return to items")) {
                        Toast.makeText(parent.getContext(), "You have chosen: " + item, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivityKupac.class);
                        startActivity(i);
                        finish();


                    }if (parent.getItemAtPosition(position).equals("Add a new item")) {
                        Toast.makeText(parent.getContext(), "You have chosen: " + item, Toast.LENGTH_SHORT).show();
                        SharedPreferences prefs = getSharedPreferences("My", MODE_PRIVATE);
                        String i = prefs.getString("idProdavca", "No name defined");
                        Intent intent = new Intent(MainActivityProdavac.this, DodajArtikalActivity.class);
                        intent.putExtra("id", i);
                        startActivity(intent);
                }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
    }
}
