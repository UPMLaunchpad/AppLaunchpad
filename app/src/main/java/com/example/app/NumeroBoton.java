package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NumeroBoton extends AppCompatActivity {

    private int cuadrante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeroboton);
        this.obtenerCuadrante();
  //      System.out.println(cuadrante);

    }

public void obtenerCuadrante(){
    Bundle datos = this.getIntent().getExtras();
    cuadrante = datos.getInt("Cuadrante");
}


}