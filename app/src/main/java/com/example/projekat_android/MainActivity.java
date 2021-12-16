package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText user;
    EditText pass;
    Button login;
    private SharedPreferenceConfig sharedPreferenceConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        login = findViewById(R.id.btnLoginBS);
        Button reg = findViewById(R.id.btnReg);
        user = findViewById(R.id.inputUsername);
        pass = findViewById(R.id.inputPass);
    }
    public void loginUser(View view){
        String username = user.getText().toString();
        String password = pass.getText().toString();
    }

}
