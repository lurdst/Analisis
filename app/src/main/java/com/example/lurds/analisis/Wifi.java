package com.example.lurds.analisis;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lourdes Terrero Brea on 23/03/2015.
 */
public class Wifi {
    /**
     * Variable para almacenar la interfaz wifi del dispositivo
     */
    private WifiManager manWifi;
    /**
     * Lista para almacenar los resultados de escanear las redes wifi
     */
    private List<ScanResult> wifiList;
    /**
     * Conjunto para almenar las redes detectadas <MAC, potencia>
     */
    private Map<String, Integer> potencias;

    private ArrayList<String> pAcceso = new ArrayList<String>();

    /**
     * @brief Constructor.
     * <p/>
     * Obtiene la interfaz wifi para trabajar con ella. Compruba si el wifi del dispositivo está activado, si no es así lo activa.
     */
    public Wifi(Context context) {
        potencias = new HashMap<String, Integer>();

        //Llamamos a la interfaz wifi del dispositivo
        manWifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        // Si el wifi no esta activado lo activamos
        if (!manWifi.isWifiEnabled()) {
            manWifi.setWifiEnabled(true);
        }
    }

    /**
     * @return Conjunto <Mac, potencia> de todas las redes disponibles en ese instante.
     */
    public Map<String, Integer> Escanear() {
        //Escanemos las redes
        manWifi.startScan();
        //Guardamos el resultado bruto en una Lista
        List<ScanResult> wifiList = manWifi.getScanResults();

        //Recorremos la lista y añadimos al conjunto cada una de las redes
        for (int i = 0; i < wifiList.size(); i++) {
            int level = wifiList.get(i).level;
            String BSSID = wifiList.get(i).BSSID;
            potencias.put(BSSID, level);
        }
        return potencias;
    }

    public ArrayList<String> PuntosAcceso(){
        //Escanemos las redes
        manWifi.startScan();
        //Guardamos el resultado bruto en una Lista
        List<ScanResult> wifiList = manWifi.getScanResults();

        //Recorremos la lista y añadimos al conjunto cada una de las redes
        for (int i = 0; i < wifiList.size(); i++) {
            String BSSID = wifiList.get(i).BSSID;
            pAcceso.add(BSSID);
        }
        return pAcceso;

    }

    public void Stop(){
        manWifi.disconnect();
    }
}

