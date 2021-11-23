package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import model.Korisnik;

public class LoginActivity extends AppCompatActivity {
    EditText user;
    EditText pass;
    Button login;
    DBHelper DB;
    private SharedPreferenceConfig sharedPreferenceConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        login = findViewById(R.id.btnLoginBS);
        Button reg = findViewById(R.id.btnReg);
        Button salesman = findViewById(R.id.btnSalesman);
        user = findViewById(R.id.inputUsername);
        pass = findViewById(R.id.inputPass);

        /*if (sharedPreferenceConfig.readLoginStatus()) {
            startActivity(new Intent(this, RegisterBuyerActivity.class));
            finish();

        }

         */

        DB=new DBHelper(this);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterBuyerActivity.class);
                startActivity(intent);
            }
        });

        salesman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterSalesmanActivity.class);
                startActivity(intent);
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user.getText().toString();
                String password = pass.getText().toString();

                if (username.equals("")||password.equals("")){
                    Toast.makeText(LoginActivity.this, "Popunite sva polja", Toast.LENGTH_SHORT).show();
                }
                else {

                    Boolean checkuserpass = DB.checkusernamepassword(username, password);
                    if (checkuserpass == true) {
                        Korisnik korisnik = DB.findKorisnik(username);
                        int id = korisnik.getId();
                        if (korisnik.getUloga().equals("kupac")) {
                            Toast.makeText(LoginActivity.this, "Uspesno ste se ulogovali kao kupac" + " " + korisnik.getUsername(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivityKupac.class);
                            intent.putExtra("id", id);
                            SharedPreferences.Editor editor = getSharedPreferences("My pref", MODE_PRIVATE).edit();
                            editor.putString("userName", username);
                            editor.apply();
                            startActivity(intent);
                            sharedPreferenceConfig.writeLoginStatus(true);
                            finish();
                        } else if (korisnik.getUloga().equals("prodavac")) {
                            Toast.makeText(LoginActivity.this, "Uspesno ste se ulogovali kao prodavac" + " " + korisnik.getUsername(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivityProdavac.class);

                            SharedPreferences.Editor editor = getSharedPreferences("My pref", MODE_PRIVATE).edit();
                            editor.putString("userName", username);
                            editor.apply();

                            intent.putExtra("user", username);
                            intent.putExtra("id", id);
                            startActivity(intent);
                            sharedPreferenceConfig.writeLoginStatus(true);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Uspesno ste se ulogovali kao admin", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, RegisterSalesmanActivity.class);
                            //intent.putExtra("user", user);
                            //intent.putExtra("id", id);
                            SharedPreferences.Editor editor = getSharedPreferences("My pref", MODE_PRIVATE).edit();
                            editor.putString("userName", username);
                            editor.putInt("id", id);
                            editor.apply();
                            startActivity(intent);
                            sharedPreferenceConfig.writeLoginStatus(true);
                            finish();
                        }
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Unesite prave kredencijale!", Toast.LENGTH_SHORT).show();
                    }

            }}

        });
    }
}


