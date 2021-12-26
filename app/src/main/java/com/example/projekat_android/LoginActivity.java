package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
                    Toast.makeText(LoginActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {

                    Boolean checkuserpass = DB.checkusernamepassword(username, password);
                    if (checkuserpass == true) {
                        Korisnik korisnik = DB.findKorisnik(username);
                        String id = korisnik.getId().toString();
                        Toast.makeText(LoginActivity.this, "Login successful for:" + " " + korisnik.getUsername(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivityKupac.class);
                        intent.putExtra("idProdavca", id);
                        SharedPreferences.Editor editor = getSharedPreferences("My", MODE_PRIVATE).edit();
                        editor.putString("userName", username);
                        editor.putString("idProdavca", id);
                        editor.apply();
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Animation anim = new AlphaAnimation(0.0f, 1.0f);
                        anim.setDuration(500);
                        anim.setStartOffset(20);
                        anim.setRepeatMode(Animation.REVERSE);
                        anim.setRepeatCount(3);
                        greska.startAnimation(anim);
                        greska.setText("Wrong username or password");
                    }

            }}

        });


    }

    /* <ImageView
        android:id="@+id/imageView"
        android:layout_width="120dp"
        android:layout_height="150dp"
        android:layout_marginTop="50dp"
        android:background="@drawable/logo"
        android:layout_alignParentTop="true"
        android:layout_centerHorizontal="true"
        />
*/
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
