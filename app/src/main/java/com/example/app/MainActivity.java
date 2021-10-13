package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AutomaticZenRule;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final UUID MY_UUID = null;
    private static final String TAG = null;
    public static BluetoothAdapter bluetoothAdapter;
    private EditText etFile;
    private Button sendF;
    private Button btRead;
    private Button color;
    private int mDefaultColor;
    private static final String FILE_NAME = "texto.txt";
    String path;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.setUpView();
    }
    private void setUpView(){
        etFile = findViewById(R.id.etFile);
        sendF = findViewById(R.id.SendFile);
        color = findViewById(R.id.color);
      /*  btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
     /*   btRead = findViewById(R.id.btRead);
          btRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readFile();
            }
        });*/


        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }

        });


    }

    private void saveFile(String datos){ //MODIFICAR PARA GUARDAR EN EL MISMO FICHERO
      //  String datos = etFile.getText().toString();
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutputStream.write(datos.getBytes());
            Log.d("TAG1", "Fichero Salvado en: " + getFilesDir() + "/" + FILE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null){
                try{
                    fileOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        path= String.valueOf(getFilesDir());
    }

    private void readFile(){
        FileInputStream fileInputStream = null;
        try{
            fileInputStream = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineaTexto;
            StringBuilder stringBuilder = new StringBuilder();
            while((lineaTexto = bufferedReader.readLine())!=null){
                stringBuilder.append(lineaTexto).append("\n");

            }
            StringBuilder r = stringBuilder;
            etFile.setText(stringBuilder);
        }catch (Exception e){

        }finally {
            if(fileInputStream !=null){
                try {
                    fileInputStream.close();
                }catch (Exception e){

                }
            }
        }


    }
    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
             //   System.out.println(mDefaultColor);
             //   saveFile(Integer.toHexString(mDefaultColor));
                readFile();

               guadarColor(RGBtoHSL(Integer.toHexString(mDefaultColor)));

               // mLayout.setBackgroundColor(mDefaultColor);
            }
        });
        colorPicker.show();
    }

    private void guadarColor(float[] rgBtoHSL) {

    }

    public float[] RGBtoHSL (String Hex) {

        float r=16*conversor(Hex.charAt(2))+conversor(Hex.charAt(3));
        float g=16*conversor(Hex.charAt(4))+conversor(Hex.charAt(5));
        float b=16*conversor(Hex.charAt(6))+conversor(Hex.charAt(7));

        float [] hsl = new float[3];

        for (int i = 0 ; i < 3 ; i++) {
            hsl[i] = 0;
        }

        r = r / 255;
        g = g / 255;
        b = b / 255;

        float cmax = Math.max(r,Math.max(g,b));
        float cmin = Math.min(r,Math.min(g,b));

        float dif = cmax - cmin;

        if (dif == 0) {
            hsl[0] = 0;

        } else if (cmax == r) {
            hsl[0] = ((g - b) / dif ) % 6;

        } else if (cmax == g) {
            hsl[0] = (b - r) / dif + 2;

        } else {
            hsl[0] = (r - g) / dif + 4;

        }

        hsl[0] = Math.round(hsl[0] * 60);

        hsl[2] = dif / 2;

        if(hsl[0] < 0){
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
    public float conversor(char c){
        char[] con= {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        for(int x=0;x<con.length;x++){
            if(c==con[x]){
                return (float) x;
            }
        }
        return 0;

    }

    public void configurarBluetooth(){
      bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            System.out.println("El dispositivo no tiene bluetooth");
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

    }



}