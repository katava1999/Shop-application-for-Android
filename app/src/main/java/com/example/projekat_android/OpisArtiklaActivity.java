package com.example.projekat_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OpisArtiklaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opis_artikla);

        String opis = getIntent().getExtras().getString("opis");

        Button dugme = findViewById(R.id.btnPovrataknaProiz);
        TextView prikaz = findViewById(R.id.prikazOpis);
        prikaz.setText(opis);

        dugme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivityKupac.class);
                startActivity(intent);
            }
        });
    }
}
