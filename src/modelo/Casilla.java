/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Objects;

/**
 *
 * @author Grupo_12
 * @param <E>
 */
public class Casilla<E> {
    private int x;
    private int y;
    private E dato;
    private boolean marcada;
    private int numVecesMarcada;

    public Casilla(int x, int y, E dato) {
        this.x = x;
        this.y = y;
        this.dato = dato;
        marcada = false;
        numVecesMarcada = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public E getDato() {
        return dato;
    }

    public void setDato(E dato) {
        this.dato = dato;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + this.x;
        hash = 17 * hash + this.y;
        hash = 17 * hash + Objects.hashCode(this.dato);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Casilla<?> other = (Casilla<?>) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return Objects.equals(this.dato, other.dato);
    }

    public boolean isMarcada() {
        return marcada;
    }

    public void setMarcada(boolean marcada) {
        this.marcada = marcada;
    }

    public int getNumVecesMarcada() {
        return numVecesMarcada;
    }

    public void setNumVecesMarcada(int numVecesMarcada) {
        this.numVecesMarcada = numVecesMarcada;
    }

    @Override
    public String toString() {
        return "Casilla{" + "x=" + x + ", y=" + y + ", dato=" + dato + '}';
    }
    
    public void incrementarNumeroDeVecesMarcada(){
        this.numVecesMarcada++;
        marcada = true;
    }
}
