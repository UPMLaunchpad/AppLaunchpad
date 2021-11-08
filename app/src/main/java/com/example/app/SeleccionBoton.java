package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class SeleccionBoton extends AppCompatActivity {


    private ImageButton cuadrante1,cuadrante2,cuadrante3,cuadrante4;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_boton);
        cuadrante1 = findViewById(R.id.Cuadrante1);
        cuadrante2 = findViewById(R.id.Cuadrante2);
        cuadrante3 = findViewById(R.id.Cuadrante3);
        cuadrante4 = findViewById(R.id.Cuadrante4);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.setUpView();
    }

    private void setUpView() {

        cuadrante1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Cuadrante 1", Toast.LENGTH_LONG).show();
              //  Toast.makeText(getApplicationContext(), "The faith is my shield", Toast.LENGTH_LONG).show();
                nuevaVentana(view,1);

            }

        });
        cuadrante2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Cuadrante 2", Toast.LENGTH_LONG).show();
              //  Toast.makeText(getApplicationContext(), "For the emperor", Toast.LENGTH_LONG).show();
                nuevaVentana(view,2);
            }

        });
        cuadrante3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Cuadrante 3", Toast.LENGTH_LONG).show();
            //    Toast.makeText(getApplicationContext(), "Burn the Heretic! Kill the Mutant! Purge the Unclean!", Toast.LENGTH_LONG).show();
                nuevaVentana(view,3);
            }

        });
        cuadrante4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Cuadrante 4", Toast.LENGTH_LONG).show();
            //    Toast.makeText(getApplicationContext(), "The planet broke before the guard did", Toast.LENGTH_LONG).show();
                nuevaVentana(view,4);
            }

        });
    }

    private void nuevaVentana(View view, int x){
        Intent in = new Intent(this, NumeroBoton.class);
        in.putExtra("Cuadrante", x);
        startActivity(in);
    }


}