/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javafx.scene.paint.Color;
import modelo.Categoria;
import modelo.Tablero;

/**
 *
 * @author Grupo_12
 */
public class Configuracion {
    private int numJugadores;
    private Categoria temaJuego;
    private String idioma;
    private Color colorSombreado;
    private Color colorFondo;
    private Tablero tablero;
    private final Map<String,String> dicIdiomas = new HashMap<>();

    public Configuracion() {
        idiomasDisponibles();
        numJugadores = 1;
        temaJuego = Categoria.GENERAL;
        idioma = dicIdiomas.get("es");        
        colorSombreado = Color.web("#B8F5B2");
        colorFondo = Color.web("#C7D0EA");
        tablero = new Tablero(7,7);
    }
    
    public Configuracion(int numJugadores, Categoria temaJuego, String idioma, Color colorSombreado, Color colorFondo, Tablero tablero) {
        idiomasDisponibles();
        this.numJugadores = numJugadores;
        this.temaJuego = temaJuego;
        if (idioma.length()==2)
            this.idioma = dicIdiomas.get(idioma);
        else
            this .idioma = idioma;
        this.colorSombreado = colorSombreado;
        this.colorFondo = colorFondo;
        this.tablero = tablero;
    }

    public Color getColorFondo() {
        return colorFondo;
    }

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }

    public int getNumJugadores() {
        return numJugadores;
    }

    public void setNumJugadores(int numJugadores) {
        this.numJugadores = numJugadores;
    }

    public Categoria getTemaJuego() {
        return temaJuego;
    }

    public void setTemaJuego(Categoria temaJuego) {
        this.temaJuego = temaJuego;
    }
 
    public Color getColorSombreado() {
        return colorSombreado;
    }

    public void setColorSombreado(Color colorSombreado) {
        this.colorSombreado = colorSombreado;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.numJugadores;
        hash = 37 * hash + Objects.hashCode(this.temaJuego);
        hash = 37 * hash + Objects.hashCode(this.idioma);
        hash = 37 * hash + Objects.hashCode(this.colorSombreado);
        hash = 37 * hash + Objects.hashCode(this.colorFondo);
        hash = 37 * hash + Objects.hashCode(this.tablero);
        hash = 37 * hash + Objects.hashCode(this.dicIdiomas);
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
        final Configuracion other = (Configuracion) obj;
        if (this.numJugadores != other.numJugadores) {
            return false;
        }
        if (!Objects.equals(this.idioma, other.idioma)) {
            return false;
        }
        if (this.temaJuego != other.temaJuego) {
            return false;
        }
        if (!Objects.equals(this.colorSombreado, other.colorSombreado)) {
            return false;
        }
        if (!Objects.equals(this.colorFondo, other.colorFondo)) {
            return false;
        }
        if (!Objects.equals(this.tablero, other.tablero)) {
            return false;
        }
        if (!Objects.equals(this.dicIdiomas, other.dicIdiomas)) {
            return false;
        }
        return true;
    }

    
    private void idiomasDisponibles(){
        dicIdiomas.put("en", "Ingles");
        dicIdiomas.put("es", "Español");        
    }

    @Override
    public String toString() {
        return "Configuracion{" + "numJugadores=" + numJugadores + ", temaJuego=" + temaJuego + ", idioma=" + idioma +  ", colorSombreado=" + colorSombreado + ", colorFondo=" + colorFondo + ", tablero=" + tablero + ", dicIdiomas=" + dicIdiomas + '}';
    }
        
}
