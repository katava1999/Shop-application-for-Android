package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterBuyerActivity extends AppCompatActivity {
    EditText name, lastname, username, password;
    RadioButton radioButton;
    RadioButton radioButton2;
    RadioGroup radioGroup;
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
        radioButton = findViewById(R.id.radioKupac);
        radioButton2 = findViewById(R.id.radioProdavac);
        radioGroup = findViewById(R.id.radioGroup);

        DB = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ime = name.getText().toString();
                String prezime = lastname.getText().toString();
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String kupac = "kupac";
                String prodavac = "prodavac";

                if (ime.equals("")||prezime.equals("")||user.equals("")||pass.equals("")){
                    Toast.makeText(RegisterBuyerActivity.this, "Morate popuniti sva polja", Toast.LENGTH_SHORT).show();
                }else if (radioButton.isChecked()){
                    Boolean check = DB.checkusername(user);
                    if (check == false){
                        Boolean insert = DB.insertData(ime, prezime, user, pass, kupac);
                        if (insert == true){
                            Toast.makeText(RegisterBuyerActivity.this, "Uspesno ste se registrovali", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                }else{
                        Toast.makeText(RegisterBuyerActivity.this, "Korisnik vec postoji", Toast.LENGTH_SHORT).show();
                    }
                }else if (radioButton2.isChecked()){
                    Boolean check = DB.checkusername(user);
                    if (check == false){
                        Boolean insert = DB.insertData(ime, prezime, user, pass, prodavac);
                        if (insert == true){
                            Toast.makeText(RegisterBuyerActivity.this, "Uspesno ste se registrovali", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }else{
                            Toast.makeText(RegisterBuyerActivity.this, "Registracion failed", Toast.LENGTH_SHORT).show();
                        }
                }else{
                        Toast.makeText(RegisterBuyerActivity.this, "Korisnik vec postoji", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        TextView odjava = findViewById(R.id.odjavaRegKupac);
        odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}

