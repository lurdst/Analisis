package com.example.lurds.analisis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lourdes Terrero Brea on 10/04/2015.
 */
public class muestra {
    /**
     * @{
     * @name Coordenadas donde se toma la muestra
     */
    private int x_, y_;
    /**
     * @name conjunto de pares <Mac, potencia>
     */
    private Map<String, Integer> potencias_;

    /**
     * Constructor
     *
     * @param x         coordenada x
     * @param y         coordenada y
     * @param potencias conjunto de potencias escanedas
     */
    public muestra(int x, int y, Map<String, Integer> potencias) {
        x_ = x;
        y_ = y;
        potencias_ = potencias;
    }

    /**
     * Constructor
     *
     * @param x Coordenada x
     * @param y Coordenada y
     */
    public muestra(int x, int y) {
        x_ = x;
        y_ = y;
        potencias_ = new HashMap<String, Integer>();
    }

    /**
     * @return Coordenada x
     */
    public int getX() {
        return x_;
    }

    /**
     * @param x Coordenada x
     */
    public void setX(int x) {
        this.x_ = x;
    }

    /**
     * @return Coordenada y
     */
    public int getY() {
        return y_;
    }

    /**
     * @param y Coordenada y
     */
    public void setY(int y) {
        this.y_ = y;
    }

    /**
     * @return conjunto de potencias
     */
    public Map<String, Integer> getPotencias() {
        return potencias_;
    }

    /**
     * @param potencias conjunto de potencias
     */
    public void setPotencias(Map<String, Integer> potencias) {
        this.potencias_ = potencias;
    }

    /**
     * @param mac      Mac
     * @param potencia Potencia
     * @brief Añade un elemento al conjunto de potencias
     */
    public void put(String mac, int potencia) {
        potencias_.put(mac, potencia);
    }

    /**
     * @param mac Clave del elemento a eleminar del conjunto
     * @brief Elimina un elemento del conjunto de potencias
     */
    public void remove(String mac) {
        potencias_.remove(mac);
    }

    /**
     * @param mac Mac del elemento del que queremos conocer el valor
     * @return Valor correspondiente a la mac dada
     * @brief Obetener el valor de un elemento del conjunto
     */
    public int getPotencia(String mac) {
        return potencias_.get(mac);
    }

    /**
     * @return tamaño del conjunto
     */
    public int size() {
        return potencias_.size();
    }

    /**
     * @return true si el conjunto esta vacio, false en caso contrario
     */
    public boolean isEmpty() {
        return potencias_.isEmpty();
    }

    /**
     * Comprueba si una mac pertenece al conjunto
     *
     * @param mac valor del elemento a buscar
     * @return true si la mac pertenece al conjunto, false en caso contrario
     */
    public boolean containsKey(String mac) {
        return potencias_.containsKey(mac);
    }
}
