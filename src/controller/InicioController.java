/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import listas.ArrayList;
import modelo.Categoria;
import modelo.Tablero;
import grupo_12.Grupo_12;
import util.Configuracion;

/**
 * FXML Controller class
 *
 * @author Grupo_12
 */
public class InicioController implements Initializable {

    @FXML
    private Button btnNormal;
    @FXML
    private Button btnExtreme;
    @FXML
    private Button btnNumerica;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnDefecto;
    @FXML
    private ColorPicker pckColorSombra;    
    @FXML
    private RadioButton rdEspanol;
    @FXML
    private RadioButton rdIngles;
    @FXML
    private ComboBox<Categoria> cbTemaJuego;
    @FXML
    private Spinner<Integer> spnJugadores;
    @FXML
    private Spinner<Integer> spnFilas;
    @FXML
    private Spinner<Integer> spnCol;

    private Configuracion config;
    @FXML
    private ColorPicker pckColorFondo;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        llenarCboCategorias();
        agruparRadioButton();
        cargarValoresPredeterminados();
        System.out.println(config);
        this.btnNumerica.setVisible(false);
        this.btnExtreme.setVisible(false);
    }    
    private void llenarCboCategorias(){
        this.cbTemaJuego.getItems().clear();
        this.cbTemaJuego.getItems().addAll(Categoria.values());
    }
    private void agruparRadioButton(){
        ToggleGroup group = new ToggleGroup();
        this.rdEspanol.setToggleGroup(group);
        this.rdIngles.setToggleGroup(group);
    }
    
    private void confSpinner(Spinner<Integer> sp, int min){
        ObservableList<Integer> filaColumna = FXCollections.observableArrayList();
        SpinnerValueFactory<Integer> factoryMin = new SpinnerValueFactory.ListSpinnerValueFactory<>(filaColumna);
        sp.setValueFactory(factoryMin);               
        for (int i = 3; i <= 20; i++) {
            filaColumna.add(i);
        }
        factoryMin.setWrapAround(true);
        factoryMin.setValue(7);        
    }
        
    @FXML
    private void cargarValoresPredeterminados(){
        config = new Configuracion();        
        spnJugadores.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(config.getNumJugadores(), 2, 1));
        cbTemaJuego.setValue(config.getTemaJuego());
        if(config.getIdioma().equalsIgnoreCase("ingles"))
            this.rdIngles.setSelected(true);
        else this.rdEspanol.setSelected(true);         
        
        this.pckColorSombra.setValue(config.getColorSombreado());
        this.pckColorFondo.setValue(config.getColorFondo());
        confSpinner(spnFilas,config.getTablero().getFilas());
        confSpinner(spnCol,config.getTablero().getColumnas());
    }
    
    @FXML
    private void empezarNormal(ActionEvent event) {
        cambiarTablero(crearDiccionario(), false);        
    }

    @FXML
    private void empezarExtreme(ActionEvent event) {
        cambiarTablero(crearDiccionario(), true);
    }

    @FXML
    private void empezarNumerica(ActionEvent event) {
    }


    @FXML
    private void guardarConf(ActionEvent event) {
        String idioma_ = rdEspanol.isSelected() ? "es":"en";
        config = new Configuracion(this.spnJugadores.getValue(),this.cbTemaJuego.getValue(),idioma_,this.pckColorSombra.getValue(),
                this.pckColorFondo.getValue(),new Tablero(this.spnFilas.getValue(),this.spnCol.getValue()) );
    }

    private void cerrarVentana(){
        Stage myStage = (Stage) this.btnDefecto.getScene().getWindow();
        myStage.close(); 
    }
        
    private void cambiarTablero(Map<Character,ArrayList<String>> palabras, boolean extremo){
        System.out.println(palabras);
        FXMLLoader loader = new FXMLLoader(Grupo_12.class.getResource("/vistas/Tablero.fxml"));
        Parent root;
        try {
            root = loader.load();
            TableroController tablero = loader.getController();            
            tablero.recibirParametros(config, palabras,extremo);            
            Scene scene = new Scene(root);
            Stage stage = new Stage();            
            stage.setScene(scene);
            stage.show();               
            cerrarVentana();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } 
    }
    
    public Map<Character,ArrayList<String>> crearDiccionario(){
        Map<Character,ArrayList<String>> dicPalabras = new HashMap<>();
        String letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        for (int i = 0; i < letras.length(); i++) {
            dicPalabras.put(letras.charAt(i), new ArrayList<>());
        }String archivo = this.config.getIdioma().trim().toLowerCase()+"/"+config.getTemaJuego().name().toLowerCase().trim();
        try(BufferedReader br = new BufferedReader(new FileReader("src/recursos/palabras/"+archivo+".txt"))){
            String l;
            while ((l = br.readLine())!=null){
                String palabra = l.trim().toUpperCase();
                if (dicPalabras.containsKey(palabra.charAt(0)) && palabra.length()>2 && !palabra.contains("-")){
                    if(palabra.contains(" "))
                        dicPalabras.get(palabra.charAt(0)).addLast(palabra.replaceAll(" ", ""));
                    else
                        dicPalabras.get(palabra.charAt(0)).addLast(palabra);
                }
                    
            }
        } catch (FileNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return dicPalabras;
    }    
}
