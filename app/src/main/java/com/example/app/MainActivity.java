package com.example.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final UUID MY_UUID = null;
    private static final String TAG = null;
    private static final int REQUEST_CODE = 200;



    private Button sendF;
    private Button color;
    private Button cambiar;

    public static BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket btSocket = null;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    private int mDefaultColor;
   


   private File fich;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("hola");
        super.onCreate(savedInstanceState);
        File cache = getCacheDir();

        fich =   new File(cache, "/configuracion.txt");
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sendF = findViewById(R.id.SendFile);
        color = findViewById(R.id.color);
        cambiar = findViewById(R.id.cambiar);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);
        this.solicitarPermisos();

       this.configurarBluetooth();
        this.setUpView();


    }

    private void solicitarPermisos() {
        int PermisoLectura = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int PermisoEscritura = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int PermisoLocalizacion = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int PermisoAdmin = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN);
        int PermisoBluetooth = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);

        if (PermisoLectura == PackageManager.PERMISSION_GRANTED && PermisoEscritura == PackageManager.PERMISSION_GRANTED && PermisoAdmin == PackageManager.PERMISSION_GRANTED && PermisoBluetooth == PackageManager.PERMISSION_GRANTED && PermisoLocalizacion == PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "Permisos concedidos", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH}, REQUEST_CODE);
        }


    }


    private void setUpView() {

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
        sendF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              MyBluetoothService bluetooh = new MyBluetoothService();

                try {
                    bluetooh.iniciar(btSocket,StringtoBytes(readFile()));
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


    }



    private void nuevaVentana(View view){
        Intent in = new Intent(this, SeleccionBoton.class);
        startActivity(in);
    }

    private void saveFile(String datos) { //MODIFICAR PARA GUARDAR EN EL MISMO FICHERO
        //  String datos = etFile.getText().toString();

        FileOutputStream fileOutputStream = null;
        if (!fich.exists()) {
            try {
                fich.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "Error statusFile" + e.getMessage());
            }
        }
        FileWriter flwriter = null;
        try {
            flwriter = new FileWriter(fich, true);
            BufferedWriter bfwriter = new BufferedWriter(flwriter);

            //escribe los datos en el archivo
            bfwriter.write(datos + "\n");

            bfwriter.close();
            //    System.out.println("Archivo modificado satisfactoriamente..");

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
        }

/*
        try {
            fileOutputStream = openFileOutput(fich);
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
        path= String.valueOf(getFilesDir());*/

    }
    private byte[] StringtoBytes(String st){
        byte[] bytes = st.getBytes();
        return bytes;
    }

    private String readFile() throws FileNotFoundException {
        String linea = "";
        Scanner scanner;
        String conf = "";
        scanner = new Scanner(fich);
        while (scanner.hasNextLine()) {
            linea = scanner.nextLine();
            System.out.println(linea);
            conf+=linea;

        }

        scanner.close();

       return conf;


       /* FileInputStream fileInputStream = null;
        try{
            fileInputStream = openFileInput(direccion);
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
            }*/
    }


    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                   System.out.println("color:"+ mDefaultColor);
                //   saveFile(getApplicationContext(),Integer.toHexString(mDefaultColor));
                guardarColor(Integer.toHexString(mDefaultColor));
                try {
                    readFile();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                // mLayout.setBackgroundColor(mDefaultColor);
            }
        });
        colorPicker.show();
    }

    private void guardarColor(String RGB) {
        this.saveFile(HSLtoString(HSLtoHSLEsp(RGBtoHSL(RGB))));
    }

    public float[] HSLtoHSLEsp(float[] hsl){

        float[] hslEsp= new float[3];
        hslEsp[0]=(hsl[0]*255)/360;
        hslEsp[1]=(hsl[1]*255)/100;
        hslEsp[2]=(hsl[2]*255)/100;


        return hslEsp;
    }

    public String HSLtoString(float[] hsl) {
        String HSL = "";
        for (int x = 0; x < hsl.length; x++) {
            HSL = HSL + " " + String.valueOf(hsl[x]);
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

    public void configurarBluetooth() {
      bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            System.out.println("El dispositivo no tiene bluetooth");
            return;
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        bluetoothAdapter.startDiscovery();


      /*  Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivityForResult(discoverableIntent, 1);



        Intent intent = getIntent();


            //     Intent intent = this.registerReceiver(null,new IntentFilter(Intent.ACTION_DOCK_EVENT));
            //Get the MAC address from the DeviceListActivty via EXTRA
             BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            //   String deviceName = device.getName();
            //   String address = device.getAddress();




       try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
        }
*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);

    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName(); System.out.println(deviceName+ " ");
                String deviceHardwareAddress = device.getAddress();  System.out.println(deviceHardwareAddress);
                try {
                    btSocket = createBluetoothSocket(device);
                } catch (IOException e) {
                    Toast.makeText(getBaseContext(), "La creacción del Socket fallo", Toast.LENGTH_LONG).show();
                }

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();


        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver);
    }

    }
