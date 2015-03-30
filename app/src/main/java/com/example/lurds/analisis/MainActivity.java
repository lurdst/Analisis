package com.example.lurds.analisis;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    Wifi wifi;

    Spinner puntoAcceso;
    EditText altura, distancia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifi = new Wifi(this);

        Log.d("onCreate()","despues wifi");
        puntoAcceso = (Spinner) findViewById(R.id.puntoAcceso);
        altura = (EditText) findViewById(R.id.editAltura);
        distancia = (EditText) findViewById(R.id.editDistancia);

        ArrayList<String> pAcceso = wifi.PuntosAcceso();
        pAcceso.add("Todos los puntos de Acceso");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,pAcceso);
        puntoAcceso.setAdapter(adapter);
    }

    public void escanearOnClick(View v){
        Log.d("escanearOnClick()","escanear");
        String alturaIntroducida=altura.getText().toString();
        if (alturaIntroducida.matches("")){
            Log.d("escanearOnClick()", "La altura no puede quedar en blanco");
            Toast.makeText(this, "debes introducir la altura", Toast.LENGTH_SHORT).show();
            return;
        }
        String distanciaIntroducida = distancia.getText().toString();
        if (distanciaIntroducida.matches("")){
            Log.d("escanearOnClick()", "La distancia no puede quedar en blanco");
            Toast.makeText(this, "debes introducir la distancia", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Integer> resEscaneo=wifi.Escanear();
        String PASeleccionado = puntoAcceso.getSelectedItem().toString();
        String linea = " ";
        if(PASeleccionado.matches("Todos los puntos de Acceso")){
            linea = alturaIntroducida + " " + distanciaIntroducida + " ";
            //Recorremos el Map y vamos añadendo el BSSID y su potencia asociada.
            for (String key : resEscaneo.keySet()) {
                linea = linea + key + " " + resEscaneo.get(key) + " ";
            }
            linea = linea + "\n";
        }
        else{
            if(resEscaneo.containsKey(PASeleccionado)){
                linea = alturaIntroducida + " " + distanciaIntroducida + " ";
                linea = linea + PASeleccionado + " " + resEscaneo.get(PASeleccionado) + " ";
                linea = linea + "\n";
            }
        }

        String nomarchivo = "Datos";
        try {
            String FICHERO = Environment.getExternalStorageDirectory() + "/" + nomarchivo + ".txt";
            FileOutputStream f = new FileOutputStream(FICHERO, true);
            f.write(linea.getBytes());
            f.close();
            Toast.makeText(this, "Dato almacenado correctamente", Toast.LENGTH_SHORT).show();
        } catch (IOException ioe) {
        }

    }

    public void datosOnClick(View v){
        Log.d("datosOnClick()","datos");
    }

    public void graficoOnClick(View v){
        Log.d("graficoOnClick()","grafico");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
