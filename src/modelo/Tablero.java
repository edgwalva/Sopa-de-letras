/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import listas.ArrayList;

/**
 *
 * @author Grupo_12
 */
public class Tablero {
    private int filas;
    private int columnas;
    private ArrayList<String> palabrasMarcadas;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        palabrasMarcadas = new ArrayList<>();
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public ArrayList<String> getPalabrasMarcadas() {
        return palabrasMarcadas;
    }

    public void setPalabrasMarcadas(ArrayList<String> palabrasMarcadas) {
        this.palabrasMarcadas = palabrasMarcadas;
    }
    
    public void agregarPalabraMarcada(String palabra){
        palabrasMarcadas.addLast(palabra);
    }
    
}

