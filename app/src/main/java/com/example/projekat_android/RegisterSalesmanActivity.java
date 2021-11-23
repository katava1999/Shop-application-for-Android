package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterSalesmanActivity extends AppCompatActivity {
    EditText name, lastname, user, pass;
    Button dugme;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_salesman);

        name = findViewById(R.id.inputimeSalesman);
        lastname = findViewById(R.id.inputprezimeSalesman);
        user = findViewById(R.id.inputusersalesman);
        pass = findViewById(R.id.inputpasssalesman);

        dugme = findViewById(R.id.btnSalesmanreg);

        DB = new DBHelper(this);

        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ime = name.getText().toString();
                String prezime = lastname.getText().toString();
                String username = user.getText().toString();
                String password = pass.getText().toString();
                String uloga = "prodavac";

                if (ime.equals("")||prezime.equals("")||user.equals("")||pass.equals("")){
                    Toast.makeText(RegisterSalesmanActivity.this, "Unesite sva polja!", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean chechuser = DB.checkusername(username);
                    if (chechuser == false){
                        Boolean insert = DB.insertData(ime, prezime, username, password, uloga);
                        Boolean insert2= DB.insertProdavci(ime, prezime, username, password);

                        if (insert == true){
                            Toast.makeText(RegisterSalesmanActivity.this, "Uspesno ste se registrovali", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }else{
                            Toast.makeText(RegisterSalesmanActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterSalesmanActivity.this, "Korisnik vec postoji!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
