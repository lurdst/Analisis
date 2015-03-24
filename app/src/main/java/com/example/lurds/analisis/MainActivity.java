package com.example.lurds.analisis;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    Wifi wifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifi = new Wifi(this);

        Log.d("onCreate()","despues wifi");
        Spinner puntoAcceso = (Spinner) findViewById(R.id.puntoAcceso);

        ArrayList<String> pAcceso = wifi.PuntosAcceso();
        pAcceso.add("Todos los puntos de Acceso");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,pAcceso);
        puntoAcceso.setAdapter(adapter);
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
