package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class NumeroBoton extends AppCompatActivity {

    private int cuadrante;

    private ImageView cuad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeroboton);
        cuad = (ImageView) findViewById(R.id.numeroCuadrante);
        this.obtenerCuadrante();

  //      System.out.println(cuadrante);

    }

public void obtenerCuadrante(){
    Bundle datos = this.getIntent().getExtras();
    cuadrante = datos.getInt("Cuadrante");
    switch (cuadrante) {
        case 1:
             cuad.setImageResource(R.drawable.cuadrante1);
             break;
        case 2:
            cuad.setImageResource(R.drawable.cuadrante2);
            break;
        case 3:
            cuad.setImageResource(R.drawable.cuadrante3);
            break;
        case 4:
            cuad.setImageResource(R.drawable.cuadrante4);
            break;
        default:
            cuad.setImageResource(R.drawable.completo);
            break;


    }
}


}