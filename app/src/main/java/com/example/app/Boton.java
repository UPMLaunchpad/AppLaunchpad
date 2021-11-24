package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.afollestad.materialdialogs.MaterialDialog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import yuku.ambilwarna.AmbilWarnaDialog;

public class Boton extends AppCompatActivity {

    private static final String TAG = null;
    private int boton;
    private int mDefaultColor;
    private int sonido;
    private int efecto;
    private File fichero, cache;
    private Button colorBoton;
    private Button sonidoBoton;
    private Button efectoBoton;
    private int numLed[] = {0, 1, 2, 3, 4, 5, 6, 7, 15, 14, 13, 12, 11, 10, 9, 8, 16, 17, 18, 19,
            20, 21, 22, 23, 31, 30, 29, 28, 27, 26, 25, 26, 32, 33, 34, 35, 36, 37, 38, 39, 47, 46,
            45, 44, 43, 42, 41, 40, 48, 49, 50, 51, 52, 53, 54, 55, 63, 62, 61, 60, 59, 58, 57, 56};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        cache = getCacheDir();
        mDefaultColor= -16777216;
        sonido =0;
        efecto =0;
        fichero = new File(cache, "/configuracion.txt");
        colorBoton = findViewById(R.id.colorBoton);
        sonidoBoton = findViewById(R.id.sonidoBoton);
        efectoBoton = findViewById(R.id.efectoBoton);
        this.obtenerBoton();
        this.setUpView();
    }
    @Override
    public void  onBackPressed(){
        try {
        guardarColor(Integer.toHexString(mDefaultColor));
        } catch (IOException e) {
        e.printStackTrace();
        }
        finish();
    }


    private void setUpView() {

        colorBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    openColorPicker();

            }
        });
        sonidoBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confgSonido();

            }
        });
        efectoBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confgEfecto();

            }

        });
    }


    private void obtenerBoton() {

        Bundle datos = this.getIntent().getExtras();
        boton = datos.getInt("Numero");

    }
    private void confgEfecto() {
        String items[]  = {"Sin efecto","Efecto cuadrado","Efecto cruz","Efecto cruz progresivo"};
        new MaterialDialog.Builder(this)
                .title("Elija un efecto")
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which){
                            case 1:
                                efecto =1;
                                break;
                            case 2:
                                efecto = 2;
                                break;
                            case 3:
                                efecto = 3;
                                break;
                            default:
                                break;
                        }
                    }
                })
                .show();
    }

public void confgSonido (){
        String items[]  = {"Sonido predeterminado","Sonido 1","Sonido 2","Sonido 3"};
    new MaterialDialog.Builder(this)
            .title("Elija un sonido")
            .items(items)
            .itemsCallback(new MaterialDialog.ListCallback() {
                @Override
                public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                    switch (which){
                        case 1:
                            sonido =1;
                            break;
                        case 2:
                            sonido = 2;
                            break;
                        case 3:
                            sonido = 3;
                            break;
                        default:
                            break;
                    }
                }
            })
            .show();
}
    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                System.out.println("color:" + mDefaultColor);
            }
        });
        colorPicker.show();
    }


    private void guardarColor(String RGB) throws IOException {
        this.saveFile(numeroLed(boton), HSLtoString(HSLtoHSLEsp(RGBtoHSL(RGB))),sonido,efecto);
    }

    private int numeroLed(int boton) {

        return numLed[boton - 1];
    }

    public float[] HSLtoHSLEsp(float[] hsl) {

        float[] hslEsp = new float[3];
        hslEsp[0] = (hsl[0] * 255) / 360;
        hslEsp[1] = (hsl[1] * 255) / 100;
        hslEsp[2] = (hsl[2] * 255) / 100;


        return hslEsp;
    }

    public String HSLtoString(float[] hsl) {
        String HSL = "";
        for (int x = 0; x < hsl.length; x++) {
            HSL = HSL + "," + String.valueOf(Math.round(hsl[x]));
        }
        return HSL;
    }

    public float[] RGBtoHSL(String Hex) {

        float r = 16 * conversor(Hex.charAt(2)) + conversor(Hex.charAt(3));
        float g = 16 * conversor(Hex.charAt(4)) + conversor(Hex.charAt(5));
        float b = 16 * conversor(Hex.charAt(6)) + conversor(Hex.charAt(7));

        float[] hsl = new float[3];

        for (int i = 0; i < 3; i++) {
            hsl[i] = 0;
        }

        r = r / 255;
        g = g / 255;
        b = b / 255;

        float cmax = Math.max(r, Math.max(g, b));
        float cmin = Math.min(r, Math.min(g, b));

        float dif = cmax - cmin;

        if (dif == 0) {
            hsl[0] = 0;

        } else if (cmax == r) {
            hsl[0] = ((g - b) / dif) % 6;

        } else if (cmax == g) {
            hsl[0] = (b - r) / dif + 2;

        } else {
            hsl[0] = (r - g) / dif + 4;

        }

        hsl[0] = Math.round(hsl[0] * 60);

        hsl[2] = dif / 2;

        if (hsl[0] < 0) {
            hsl[0] += 360;
        }

        if (dif == 0) {
            hsl[1] = 0;

        } else {
            hsl[1] = 100 * (dif / (1 - Math.abs(2 * hsl[2] - 1)));

        }

        hsl[2] *= 100;

        return hsl;

    }

    public float conversor(char c) {
        char[] con = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (int x = 0; x < con.length; x++) {
            if (c == con[x]) {
                return (float) x;
            }
        }
        return 0;

    }


    private void saveFile(int led, String color, int son,int efct) throws IOException {

        File file_temp = new File(cache, "temp.txt");

        if (!file_temp.exists()) {
            file_temp.createNewFile();
        }

        if (!fichero.exists()) {
            try {
                fichero.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "Error statusFile" + e.getMessage());
            }

        }
        Scanner scanner;
        String temp = "";
        FileOutputStream fileOutputStream = null;

        FileWriter flwriter = null;
        scanner = new Scanner(fichero);
        try {
            flwriter = new FileWriter(file_temp, true);
            BufferedWriter bfwriter = new BufferedWriter(flwriter);

            for (int x = 0; x < boton; x++) {

                if (scanner.hasNextLine()) {
                    temp = scanner.nextLine();
                    bfwriter.write(temp + "\n");
                } else {
                    bfwriter.write(" \n");
                }
            }

            bfwriter.write(led +","+ son + ","+ efct + color +";"+ "\n");
            if (scanner.hasNextLine()){
                temp=scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                temp = scanner.nextLine();
                bfwriter.write(temp + "\n");
            }

            bfwriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (flwriter != null) {
                try {
                    flwriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            fichero.delete();
            file_temp.renameTo(fichero);

        }

    }

}