package com.example.app;


import android.content.Context;
import android.widget.Toast;
import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyBluetoothService {
    private BluetoothManager bluetoothManager;
    private Context cont;
    private String espMAC = "24:62:AB:F3:00:5A";
    private String message;
    public void iniciar(BluetoothManager mang, String conf, Context main) {
        bluetoothManager = mang;
        cont = main;
        message = conf;
    this.connectDevice(espMAC);
    }

    private SimpleBluetoothDeviceInterface deviceInterface;
    private void connectDevice(String mac) {
        bluetoothManager.openSerialDevice(mac)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConnected, this::onError);

    }

    private void onConnected(BluetoothSerialDevice connectedDevice) {

        deviceInterface = connectedDevice.toSimpleDeviceInterface();
        deviceInterface.setListeners(this::onMessageReceived, this::onMessageSent, this::onError);
        deviceInterface.sendMessage(message);
        bluetoothManager.closeDevice(espMAC);
    }

    private void onMessageSent(String message) {
        Toast.makeText(cont, "Mensaje enviado", Toast.LENGTH_LONG).show();
    }

    private void onMessageReceived(String message) {
        Toast.makeText(cont, "Se ha recibido un mensaje:" + message, Toast.LENGTH_LONG).show();
    }

    private void onError(Throwable error) {
        Toast.makeText(cont, "Error en el Bluetooth", Toast.LENGTH_LONG).show();
    }



}