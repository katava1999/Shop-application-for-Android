package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

import model.Korisnik;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        DBHelper DB = new DBHelper(this);

        //We supply the user if he is already logged in
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("My", Context.MODE_PRIVATE);
        String userIsLoggedUsername = sharedPreferences.getString("userName", null);
        String userIsLoggedPassword = sharedPreferences.getString("password", null);
        boolean userIsLoggedIn = sharedPreferences.getBoolean("is_logged", false);

        if(userIsLoggedIn == true){
            //Set logged user info
            Korisnik korisnik = DB.findKorisnik(userIsLoggedUsername);

            //Start home activity with user info
            Intent intent = new Intent(MainActivity.this, MainActivityKupac.class);
            intent.putExtra("user", korisnik);
            startActivity(intent);
            finish();
            return;
        } else {
            //If user isn't logged
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
