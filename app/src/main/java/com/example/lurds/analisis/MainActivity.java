package com.example.lurds.analisis;

import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    Wifi wifi;

    Spinner puntoAcceso;
    EditText altura, distancia;

    ArrayList<muestra> datos = new ArrayList<muestra>();

    String nomarchivo = "Datos";
    FileInputStream f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        puntoAcceso = (Spinner) findViewById(R.id.puntoAcceso);
        altura = (EditText) findViewById(R.id.editAltura);
        distancia = (EditText) findViewById(R.id.editDistancia);

    }

    @Override
    protected void onResume(){
        super.onResume();

        //Cargamos los puntos de acceso en el desplegable
        wifi = new Wifi(this);
        Log.d("onResume()","despues wifi");
        ArrayList<String> pAcceso = wifi.PuntosAcceso();
        pAcceso.add("Todos los puntos de Acceso");
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,pAcceso);
        puntoAcceso.setAdapter(adapter);

        //Obtenemos los datos del .txt
        //cargarDatos getDatos = new cargarDatos();
        //getDatos.execute();
    }

    @Override
    protected void onPause(){
        super.onPause();
        wifi.Stop();
        setDatos();
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

        if(PASeleccionado.matches("Todos los puntos de Acceso")){
            muestra aux = new muestra( Integer.parseInt(alturaIntroducida), Integer.parseInt(distanciaIntroducida), resEscaneo);
            datos.add(aux);
        }
        else{
            if(resEscaneo.containsKey(PASeleccionado)){
                muestra aux = new muestra( Integer.parseInt(alturaIntroducida), Integer.parseInt(distanciaIntroducida));
                aux.put(PASeleccionado, resEscaneo.get(PASeleccionado));
            }
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


    private void setDatos() {
        //Elimino el fichero si existe
        File fichero = new File(Environment.getExternalStorageDirectory(),nomarchivo);
        if(fichero.exists()) {
            fichero.delete();
        }
        // Declaramos el Iterador
        Iterator<muestra> iterator = datos.iterator();
        //Recorro la lista
        while(iterator.hasNext()){
            //Recupero el elemento de la lista
            muestra elemento = iterator.next();
            //Creo una linea para cada elemento de la lista
            String linea = elemento.getX()+ " " + elemento.getY();
            //recorrer el map que contiene los ssid y los niveles
            Iterator iteratorMap = elemento.getPotencias().keySet().iterator();
            while (iteratorMap.hasNext()) {
                Object key = iteratorMap.next();
                //Añado los elementos del map a la linea
                linea = linea + " " + key + " " + elemento.getPotencias().get(key);
            }
            //Añado caracter final de linea a la linea
            linea = linea + "\n";
            //Escribo la linea en el fichero
            escribir(linea);
        }

    }

    private void escribir(String linea) {
        try {
            String FICHERO = Environment.getExternalStorageDirectory() + "/" + nomarchivo + ".txt";
            FileOutputStream f = new FileOutputStream(FICHERO, true);
            f.write(linea.getBytes());
            f.close();
        } catch (IOException ioe) {

        }
    }

    private boolean abrirFichero(){
        String FICHERO = Environment.getExternalStorageDirectory() + "/" + nomarchivo + ".txt";
        boolean exito= false;
        Log.d("abrirFichero()", "hemos obtenido el nombre del fichero");
        try {
            f = new FileInputStream(FICHERO);
            exito=true;
            Log.d("abrirFichero()","fichero abierto");
        } catch (FileNotFoundException e) {
            Log.d("abrirFichero()","salta la excepción");
            exito = false;
            FicheroNoExiste();
            e.printStackTrace();
        }
        return exito;
    }

    private void FicheroNoExiste() {
        Toast toast1 =
                Toast.makeText(getApplicationContext(), "No hay datos", Toast.LENGTH_SHORT);
        toast1.show();
    }

    private class cargarDatos extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            //Todo el codigo
            Log.d("cargarDatos", "ejecutandose en segundo plano");
            Log.d("getDatos()", "principio");
            //Abro fichero para lectura
            if (abrirFichero()) {
                Log.d("getDatos()", "fichero abierto");
                //almaceno en entrada lo que tiene el fichero
                BufferedReader entrada = new BufferedReader(new InputStreamReader(f));
                try {
                    //leo la primera linea
                    String linea = entrada.readLine();
                    Log.d("getDatos()", "leer primera linea: " + linea);
                    //indico que la sepadación entre palabras es el espacio
                    String separador = " ";
                    Log.d("getDatos()", "separador");
                    //Si la linea no está vacía
                    while (linea != null) {
                        //separo todas las palabras de la linea
                        String[] palabras = linea.split(separador);
                        Log.d("getDatos()", "palabras");
                        //Creo el map para almecenar los pares ssid potencia
                        Map<String, Integer> Vector = new HashMap<String, Integer>();
                        Log.d("getDatos()", "map creado");
                        //Almaceno en el map todas las redes y sus potencias
                        int tam = palabras.length;
                        Log.d("getDatos()", "tamaño palabras= " + tam);
                        for (int i = 2; i < tam; i = i + 2) {
                            Log.d("getDatos()", "dentro del for " + i);
                            Vector.put(palabras[i], Integer.parseInt(palabras[i + 1]));
                            Log.d("getDatos()", "añadido al map");
                        }
                        //creo la muestra de esa linea
                        muestra muestra = new muestra(Integer.parseInt(palabras[0]), Integer.parseInt(palabras[1]), Vector);
                        Log.d("getDatos()", "muestra creada");
                        //la almaceno en la lista
                        datos.add(muestra);
                        Log.d("getDatos()", "muestra añadida");
                        //leo la siguiente linea
                        linea = entrada.readLine();
                        Log.d("getDatos()", "leo siguiente linea: " + linea);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("getDatos()", "final");
            }
                return null;

        }
    }

}
