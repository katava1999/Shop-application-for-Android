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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import adapters.ArtikalAdapter;
import model.Artikal;
import model.Korisnik;
import model.Kupac;

public class MainActivityKupac extends AppCompatActivity {

    DBHelper DB;
    private SharedPreferenceConfig sharedPreferenceConfig;
    RecyclerView recyclerView;
    ArtikalAdapter artikliAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<Artikal> artikalList = new ArrayList<>();
    private Spinner spinner;
    EditText pretraga;
    Korisnik korisnik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kupac);

        Intent intent = getIntent();
        korisnik = (Korisnik) intent.getSerializableExtra("user");

        DB = new DBHelper(this);
        artikalList = DB.getArtikli();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        artikliAdapter = new ArtikalAdapter(this, artikalList, recyclerView);
        recyclerView.setAdapter(artikliAdapter);

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



        //Odavde ide SPINNER
        spinner = findViewById(R.id.aSpinnerToolBar);

        List<String> categories = new ArrayList<>();
        categories.add(0, "Options");
        categories.add("My items");
        categories.add("Bought items");
        categories.add("Logout");



        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("")) {
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(parent.getContext(), "Odabrali ste: " + item, Toast.LENGTH_SHORT).show();

                    if (parent.getItemAtPosition(position).equals("My items")) {
                        Intent intent = new Intent(MainActivityKupac.this, MainActivityProdavac.class);
                        Toast.makeText(parent.getContext(), "You choosed: " + item, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    if (parent.getItemAtPosition(position).equals("Logout")) {

                        SharedPreferences sharedPref = getSharedPreferences("My", Context.MODE_PRIVATE);
                        String usernameKupca = sharedPref.getString("userName", "No name defined");
                        Toast.makeText(parent.getContext(), "You choosed: " + item, Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivityKupac.this, "Logout successful for: " + usernameKupca, Toast.LENGTH_SHORT).show();
                        SharedPreferences pref = getSharedPreferences("My", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.clear();
                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();

                    }
                    if (parent.getItemAtPosition(position).equals("Bought items")) {

                        SharedPreferences sharedPref = getSharedPreferences("My", Context.MODE_PRIVATE);
                        String usernameKupca = sharedPref.getString("userName", "No name defined");
                        Toast.makeText(parent.getContext(), "You choosed: " + item, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), PrikazKupljenihArtikalaActivity.class);
                        startActivity(i);
                        finish();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
