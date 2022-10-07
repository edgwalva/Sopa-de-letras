/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import modelo.Categoria;

/**
 *
 * @author Grupo_12
 */
public class Diccionario {
    private String idioma;
    private Map<String,String> palabras;
    private Categoria categoria;

    public Diccionario(String idioma) {
        this.idioma = idioma;
        this.categoria = Categoria.GENERAL;
        this.palabras = obtenerDiccionario(idioma,categoria);        
    }
    
    public Diccionario(String idioma, Categoria categoria) {
        this(idioma);
        this.categoria = categoria;
        this.palabras = obtenerDiccionario(idioma,categoria);
    }    

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Map<String, String> getPalabras() {
        return palabras;
    }

    public void setPalabras(Map<String, String> palabras) {
        this.palabras = palabras;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.idioma);
        hash = 31 * hash + Objects.hashCode(this.palabras);
        hash = 31 * hash + Objects.hashCode(this.categoria);
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
        final Diccionario other = (Diccionario) obj;
        if (!Objects.equals(this.idioma, other.idioma)) {
            return false;
        }
        if (!Objects.equals(this.palabras, other.palabras)) {
            return false;
        }
         return this.categoria == other.categoria;
    }        
    
    private Map<String,String> obtenerDiccionario(String idioma, Categoria cat){
        Map<String,String> diccionario = new HashMap<>();
        
        return diccionario;
    }        
    
}
