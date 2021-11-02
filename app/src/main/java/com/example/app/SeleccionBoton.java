package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SeleccionBoton extends AppCompatActivity {


    private ImageButton prueba;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_boton);
      //  prueba = findViewById(R.id.coco);
        this.setUpView();
    }

    private void setUpView() {

     /*   prueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Esta unidad tiene alma?");
            }

        });*/
    }


}