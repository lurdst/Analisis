package com.example.lurds.analisis;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import android.graphics.Color;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Lourdes Terrero Brea on 18/05/2015.
 */
public class grafica extends ActionBarActivity {
    private XYPlot mySimpleXYPlot;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.d("Grafica", "on Create()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafica);

        Bundle bundle= getIntent().getExtras();

        ArrayList<muestra> datos = (ArrayList<muestra>) bundle.getSerializable("datos");

        //Inicializamos el objeto XYPlot buscandolo desde el layout:
        mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

        // Creamos dos arrays de prueba. En el caso real debemos reemplazar
        // estos datos por los que realmente queremos mostrar
        Number[] series1Numbers = {1, 8, 5, 2, 7, 4};
        Number[] series2Numbers = {4, 6, 3, 8, 2, 10};

        // Aniadimos Linea Numero UNO:
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(series1Numbers),  // Array de datos
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Solo valores verticales
                "Series1"); // Nombre de la primera serie

        // Repetimos para la segunda serie
        XYSeries series2 = new SimpleXYSeries(Arrays.asList(series2Numbers
        ), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series2");

        // Modificamos los colores de la primera serie
        LineAndPointFormatter series1Format = new LineAndPointFormatter(
                Color.rgb(0,200,0),                   // Color de la linea
                Color.rgb(0, 100, 0),                   // Color del punto
                Color.rgb(150, 190, 150),              //Color relleno
                new PointLabelFormatter(Color.rgb(0, 200, 0)));

        // Una vez definida la serie (datos y estilo), la aniadimos al panel
        mySimpleXYPlot.addSeries(series1, series1Format);

        // Repetimos para la segunda serie
        mySimpleXYPlot.addSeries(series2, new LineAndPointFormatter(
                Color.rgb(0, 0, 200),
                Color.rgb(0, 0, 100),
                Color.rgb(150, 150, 190),
                new PointLabelFormatter(Color.rgb(0, 200, 0))));

    }
}
