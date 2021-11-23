package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterBuyerActivity extends AppCompatActivity {
    EditText name, lastname, username, password;
    Button register;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.inputImeBuyer);
        lastname = findViewById(R.id.inputPrezimeBuyer);
        username = findViewById(R.id.inputUsernameBuyer);
        password = findViewById(R.id.inputPasswordBuyer);

        register = findViewById(R.id.btnRegisterBuyer);

        DB = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ime = name.getText().toString();
                String prezime = lastname.getText().toString();
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String uloga = "kupac";

                if(ime.equals("")||prezime.equals("")||user.equals("")||pass.equals(""))
                    Toast.makeText(RegisterBuyerActivity.this, "Unesite sva polja!", Toast.LENGTH_SHORT).show();
                else{
                    Boolean check = DB.checkusername(user);
                    if (check == false){
                        Boolean insert = DB.insertData(ime, prezime, user, pass, uloga);
                        Boolean insert2 = DB.insertKupci(ime, prezime, user, pass);

                        if (insert == true){
                            Toast.makeText(RegisterBuyerActivity.this, "Uspesno ste se registrovali", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }else{
                            Toast.makeText(RegisterBuyerActivity.this, "Registracion failed", Toast.LENGTH_SHORT).show();
                        }
                    }else
                    {
                        Toast.makeText(RegisterBuyerActivity.this, "Korisnik vec postoji", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
}}

