package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;
import android.widget.Toast;
import com.harrysoft.androidbluetoothserial.BluetoothManager;



public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 200;
    private Button sendF;
    private Button cambiar;
    private Button borrar;
    private  BluetoothManager bluetoothManager;
    private Context cont;
    private File fich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        File cache = getCacheDir();
        fich =   new File(cache, "/configuracion.txt");
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        sendF = findViewById(R.id.SendFile);
        cambiar = findViewById(R.id.cambiar);
        borrar = findViewById(R.id.borrar);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.solicitarPermisos();
        this.configurarBluetoothSerial();
        this.setUpView();
        cont =this;

    }

    private void configurarBluetoothSerial() {

         bluetoothManager = BluetoothManager.getInstance();
        if (bluetoothManager == null) {
            Toast.makeText(this, "El Bluetooth no se encuentra disponible", Toast.LENGTH_LONG).show();
        }

    }
        private void conseguirMacs(BluetoothManager bluetoothManager){
            Collection<BluetoothDevice> pairedDevices = bluetoothManager.getPairedDevicesList();
            for (BluetoothDevice device : pairedDevices) {
                Log.d("Mi App Bluetooth", "Nombre del dispositivo: " + device.getName());
                Log.d("Mi App Bluetooth", "Direccion MAC: " + device.getAddress());
            }
        }
    private void solicitarPermisos() {
        int PermisoLectura = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int PermisoEscritura = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int PermisoLocalizacion = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int PermisoAdmin = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN);
        int PermisoBluetooth = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);

        if (PermisoLectura == PackageManager.PERMISSION_GRANTED && PermisoEscritura == PackageManager.PERMISSION_GRANTED && PermisoAdmin == PackageManager.PERMISSION_GRANTED
                && PermisoBluetooth == PackageManager.PERMISSION_GRANTED && PermisoLocalizacion == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH}, REQUEST_CODE);
        }


    }


    private void setUpView() {

        sendF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              MyBluetoothService bluetooh = new MyBluetoothService();
                try {
                    if (fich.exists()){

                        bluetooh.iniciar(bluetoothManager,readFile(),cont);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "DEBE CONFIGURAR PRIMERO EL TECLADO", Toast.LENGTH_LONG).show();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        });
        cambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevaVentana(view);
            }

        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (fich.exists()){
                       fich.delete();
                        Toast.makeText(getApplicationContext(), "Fichero borrado", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "No existe ningún archivo previo", Toast.LENGTH_LONG).show();
                    }
            }

        });

    }



    private void nuevaVentana(View view){
        Intent in = new Intent(this, SeleccionBoton.class);
        startActivity(in);
    }

    private String readFile() throws FileNotFoundException {
        String linea = "";
        Scanner scanner;
        String conf = "";
        scanner = new Scanner(fich);
        while (scanner.hasNextLine()) {
            linea = scanner.nextLine();
            if(linea =="") {
                linea = "64,4,0,0,0,0;";
            }
            System.out.println(linea);
            conf+=linea+"\n";

        }

        scanner.close();
        conf+="-";
        return conf;

    }



    }
