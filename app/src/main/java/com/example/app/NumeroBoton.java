package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class NumeroBoton extends AppCompatActivity {

    private int cuadrante;
    private int c1[]={1,2,3,4,9,10,11,12,17,18,19,20,25,26,27,28};
    private int c2[]={5,6,7,8,13,14,15,16,21,22,23,24,29,30,31,32};
    private int c3[]={33,34,35,36,41,42,43,44,49,50,51,52,57,58,59,60};
    private int c4[]={37,38,39,40,45,46,47,48,53,54,55,56,61,62,63,64};
    private ImageView cuad;
    private ImageButton boton1,boton2,boton3,boton4,boton5,boton6,boton7,boton8,boton9,boton10,boton11,boton12,boton13,boton14,boton15,boton16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeroboton);
        cuad = (ImageView) findViewById(R.id.numeroCuadrante);
        boton1 = (ImageButton) findViewById(R.id.Button1);
        boton2 = (ImageButton) findViewById(R.id.Button2);
        boton3 = (ImageButton) findViewById(R.id.Button3);
        boton4 = (ImageButton) findViewById(R.id.Button4);
        boton5 = (ImageButton) findViewById(R.id.Button5);
        boton6 = (ImageButton) findViewById(R.id.Button6);
        boton7 = (ImageButton) findViewById(R.id.Button7);
        boton8 = (ImageButton) findViewById(R.id.Button8);
        boton9 = (ImageButton) findViewById(R.id.Button9);
        boton10 = (ImageButton) findViewById(R.id.Button10);
        boton11 = (ImageButton) findViewById(R.id.Button11);
        boton12 = (ImageButton) findViewById(R.id.Button12);
        boton13 = (ImageButton) findViewById(R.id.Button13);
        boton14 = (ImageButton) findViewById(R.id.Button14);
        boton15 = (ImageButton) findViewById(R.id.Button15);
        boton16 = (ImageButton) findViewById(R.id.Button16);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.obtenerCuadrante();
        this.setUpView();
    }

    private void setUpView() {

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(1));
            }

        });
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(2));
            }

        });
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(3));
            }

        });
        boton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(4));
            }

        });
        boton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(5));
            }

        });
        boton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(6));
            }

        });
        boton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(7));
            }

        });
        boton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(8));
            }

        });
        boton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(9));
            }

        });
        boton10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(10));
            }

        });
        boton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(11));
            }

        });
        boton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(12));
            }

        });
        boton13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(13));
            }

        });
        boton14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(14));
            }

        });
        boton15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(15));
            }

        });
        boton16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view,numeroBoton(16));
            }

        });

        }

        private int numeroBoton(int boton){
        int temp=0;
            switch (cuadrante) {
                case 1:
                    temp=c1[boton-1];
                    break;
                case 2:
                    temp=c2[boton-1];
                    break;
                case 3:
                    temp=c3[boton-1];
                    break;
                case 4:
                    temp=c4[boton-1];
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                    break;
            }
            Toast.makeText(getApplicationContext(), "Boton "+temp, Toast.LENGTH_LONG).show();
        return temp;
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
        private void nuevaVentana(View view, int x){
            Intent in = new Intent(this, Boton.class);
            in.putExtra("Numero", x);
            startActivity(in);
    }


}