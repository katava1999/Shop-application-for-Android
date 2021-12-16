package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import model.Korisnik;
import model.Prodavac;

public class LoginActivity extends AppCompatActivity {
    EditText user;
    EditText pass;
    Button login;
    DBHelper DB;
    private SharedPreferenceConfig sharedPreferenceConfig;
    TextView greska;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login = findViewById(R.id.btnLoginBS);
        Button reg = findViewById(R.id.btnReg);
        user = findViewById(R.id.inputUsername);
        pass = findViewById(R.id.inputPass);
        greska = findViewById(R.id.prikazGreske);

        //sharedPreferenceConfig = new SharedPreferenceConfig(getApplicationContext());


        user.addTextChangedListener(loginTextWather);
        pass.addTextChangedListener(loginTextWather);

        DB=new DBHelper(this);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterBuyerActivity.class);
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
                            finish();
                        } else if (korisnik.getUloga().equals("prodavac")) {
                            Prodavac prodavac = DB.findProdavac(username);
                            //String idProdavca = prodavac.getId().toString();
                            //String userProdavac = prodavac.getUsername();
                            String userProdavac = korisnik.getUsername();
                            String idProdavca = korisnik.getId().toString();
                            Toast.makeText(LoginActivity.this, "Uspesno ste se ulogovali kao prodavac" + " " + korisnik.getUsername(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivityProdavac.class);
                            SharedPreferences.Editor editor = getSharedPreferences("My", MODE_PRIVATE).edit();
                            editor.putString("userProdavac", userProdavac);
                            editor.putString("idProdavca", idProdavca);
                            editor.apply();

                            intent.putExtra("userProdavac", username);
                            intent.putExtra("idProdavca", idProdavca);
                            startActivity(intent);
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
                            finish();
                        }
                    }
                    else {
                        greska.setText("Pogresno koricničko ime ili lozinka");
                    }

            }}

        });


    }
    private TextWatcher loginTextWather = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String usenameInput = user.getText().toString().trim();
            String passwordInput = pass.getText().toString().trim();

            login.setEnabled(!usenameInput.isEmpty() && !passwordInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
