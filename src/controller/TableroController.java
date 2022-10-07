/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Random;    
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import listas.ArrayList;
import listas.CircleDoubleLinkedList;
import modelo.Casilla;
import modelo.Jugador;
import grupo_12.Grupo_12;
import util.Configuracion;

/**
 * FXML Controller class
 *
 * @author Grupo_12
 */
public class TableroController implements Initializable {

    @FXML
    private HBox hbMenuTablero;
    @FXML
    private HBox hbBottom;
    @FXML
    private VBox vbConfig;
    @FXML
    private GridPane gridIzq;
    @FXML
    private GridPane GridSup;
    @FXML
    private GridPane gridLetras;
    @FXML
    private GridPane GridInf;
    @FXML
    private GridPane gridDer;
    @FXML
    private TextField txtJugador1;
    @FXML
    private VBox vbJugador2;
    @FXML
    private TextField txtJugador2;
    @FXML
    private VBox vbTiempo;
    @FXML
    private Button btnAgregarFila;
    @FXML
    private Button btnAgregarCol;
    @FXML
    private Label lblError;
    @FXML
    private Button btnEliminarFila;
    @FXML
    private Button btnEliminarCol;
    @FXML
    private ListView<String> listViewJugador1;
    @FXML
    private ListView<String> listViewJugador2;
    @FXML
    private Label lblJugador1;
    @FXML
    private Label lblJugador2;
    @FXML
    private Button btnMezclar;
    @FXML
    private TextField txtTurno;
    @FXML
    private VBox vbTurno;
    @FXML
    private Button btnRegresar;
    
    private final String letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    private final String numeros = "0123456789";
    private Map<Character,ArrayList<String>> dicPalabras;
    private TableroController tableroController;
    private Configuracion conf;
    private ArrayList<CircleDoubleLinkedList<Casilla<Character>>> filas;
    private ArrayList<CircleDoubleLinkedList<Casilla<Character>>> columnas;
    private Jugador jugador1;
    private Jugador jugador2;
    private int eliminarAgregar = 2;
    private int indFilaEliminar;
    private int indColEliminar;
    private boolean continuar;
    private ArrayList<String> palabrasEncontradas;
    private Stack<StackPane> letrasSeleccionadas;
    private ArrayList<StackPane> indices;
    private boolean extremo;
    private String ultimaPalabra;
    private ArrayList<ImageView> flechasD;
    private ArrayList<ImageView> flechasI;
    private ArrayList<ImageView> flechasArr;
    private ArrayList<ImageView> flechasAb;
    private ArrayList<String> palabrasDisponibles;   
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        tableroController = this;    
        letrasSeleccionadas = new Stack<>();
        filas = new ArrayList<>();        
        columnas = new ArrayList<>();
        palabrasEncontradas = new ArrayList<>();
        indices = new ArrayList<>();
        flechasD = new ArrayList<>();
        flechasI = new ArrayList<>();
        flechasArr = new ArrayList<>();
        flechasAb = new ArrayList<>();
        espaciadoTablero(this.GridInf);
        espaciadoTablero(this.GridSup);
        espaciadoTablero(this.gridDer);
        espaciadoTablero(this.gridIzq);
        espaciadoTablero(this.gridLetras);
        palabrasDisponibles = new ArrayList<>();
        dragRoot();        
    }    
        
    private void espaciadoTablero(GridPane grid){        
        grid.setVgap(5);
        grid.setHgap(5);
    }
    private void llenarTablero(){        
        for (int i = 0; i < conf.getTablero().getColumnas(); i++) {
            CircleDoubleLinkedList<Casilla<Character>> casillasC = new CircleDoubleLinkedList<>();
            columnas.addFirst(casillasC);
        }        
        for (int i = 0; i < conf.getTablero().getFilas(); i++) {
            CircleDoubleLinkedList<Casilla<Character>> casillasF = new CircleDoubleLinkedList<>();            
            for (int j = 0; j < conf.getTablero().getColumnas(); j++) {    
                char dato = generarLetra();
                Casilla<Character> casillaF = new Casilla(i,j,dato);
                Casilla<Character> casillaC = new Casilla(i,j,dato);
                Label lbl = new Label(String.valueOf(dato));
                StackPane stkPane = new StackPane();
                stkPane.getChildren().add(lbl);     
                stkPane.setStyle("-fx-border-color: black");
                configPalabraMarcada(stkPane,lbl);
                this.gridLetras.add(stkPane, j, i);
                casillasF.addLast(casillaF);                
                columnas.get(j).addLast(casillaC);
            }
            filas.addLast(casillasF);
        }
        System.out.println("Filas "+filas);
        System.out.println("Columna "+columnas);
    }
    
    private void crearCasillasVacias(){
        gridLetras.setBackground(new Background(new BackgroundFill(conf.getColorFondo(), CornerRadii.EMPTY, Insets.EMPTY)));
        for (int i = 0; i < conf.getTablero().getFilas(); i++) {
            if(i>=3){
                agregarFila(gridDer,VPos.BOTTOM);
                agregarFila(gridIzq,VPos.BOTTOM);
                agregarFila(gridLetras,VPos.CENTER);    
            }                
            agregarFlechas(gridDer,0,i,"derecha");
            agregarFlechas(gridIzq,0,i,"izquierda");
        }     
        for (int i = 0; i < conf.getTablero().getColumnas(); i++) {
            if(i>=3){
                agregarColumna(GridSup);
                agregarColumna(GridInf);
                agregarColumna(gridLetras);
            }                                        
            agregarFlechas(GridInf,i,0,"abajo");
            agregarFlechas(GridSup,i,0,"arriba");
        }        
    }
    
    private void agregarFila(GridPane grid, VPos ali){
        RowConstraints row =  new RowConstraints();
        row.setPercentHeight(-1);
        row.setFillHeight(true);
        row.setVgrow(Priority.SOMETIMES);
        row.setValignment(ali);        
        row.setMaxHeight(USE_COMPUTED_SIZE);
        row.setMinHeight(10);
        row.setPrefHeight(30);
        grid.setAlignment(Pos.CENTER);
        grid.getRowConstraints().add(row);     
        bottomConfig();        
    }
    
    private void agregarColumna(GridPane grid){
        ColumnConstraints column = new ColumnConstraints();                
        column.setPercentWidth(-1);
        column.setFillWidth(true);
        column.setMaxWidth(USE_COMPUTED_SIZE);
        column.setHgrow(Priority.SOMETIMES);        
        column.setMinWidth(10);
        column.setPrefWidth(100);
        column.setHalignment(HPos.CENTER);
        grid.setAlignment(Pos.CENTER);
        grid.getColumnConstraints().add(column);     
        bottomConfig();
    }
    
    public void recibirParametros(Configuracion c, Map<Character,ArrayList<String>> palabras, boolean extremo){
        conf = c;   
        this.extremo = extremo;
        this.dicPalabras = palabras;
        crearCasillasVacias();
        llenarTablero();
        bottomConfig();
        configurarJugador();
        configurarExtremo();
    }
    
    private void configurarJugador(){
        jugador1 = new Jugador("Jugador 1");
        this.txtJugador1.setText("0");
        this.vbJugador2.setVisible(false);
        this.vbTurno.setVisible(false);
        if(conf.getNumJugadores()==2){
            this.vbTurno.setVisible(true);
            jugador2 = new Jugador("Jugador 2");
            this.vbJugador2.setVisible(true);
            this.txtJugador2.setText("0");
            jugador1.setJugando(true);
            this.txtTurno.setText(jugador1.getNickname());
        }
    }
    
    private void configurarExtremo() {
        if(!extremo)
            this.vbTiempo.setVisible(false);
    }
    
    private void bloquearBottom(){
        this.hbBottom.setDisable(true);
    }
    
    @FXML
    private void agregarNuevaFilaValores(ActionEvent event) {   
        bottomConfig();
        if(eliminarAgregar>0){
            this.conf.getTablero().setFilas(conf.getTablero().getFilas()+1);
            agregarFila(gridDer,VPos.BOTTOM);
            agregarFila(gridIzq,VPos.BOTTOM);
            agregarFila(gridLetras,VPos.CENTER);         
            CircleDoubleLinkedList<Casilla<Character>> casillasFila = new CircleDoubleLinkedList<>();
            for (int i = 0; i < conf.getTablero().getColumnas(); i++) {
                char dato = generarLetra();
                Casilla<Character> casillaF = new Casilla(conf.getTablero().getFilas(),i,dato);
                Casilla<Character> casillaC = new Casilla(conf.getTablero().getFilas(),i,dato);
                casillasFila.addLast(casillaF);
                columnas.get(i).addLast(casillaC);
                StackPane stk = new StackPane();
                stk.setStyle("-fx-border-color: black");
                Label lbl = new Label(String.valueOf(dato));
                stk.getChildren().add(lbl);
                configPalabraMarcada(stk,lbl);
                gridLetras.add(stk, i,conf.getTablero().getFilas()-1);
            }
            filas.addLast(casillasFila);            
            agregarFlechas(gridDer,0,conf.getTablero().getFilas()-1,"derecha");
            agregarFlechas(gridIzq,0,conf.getTablero().getFilas()-1,"izquierda");
        }
       
        eliminarAgregar--;
        if(eliminarAgregar==0)  bloquearBottom();        
    }

    @FXML
    private void agregarNuevaColumnaValores(ActionEvent event) {
        bottomConfig();
        if(eliminarAgregar>0){
            this.conf.getTablero().setColumnas(conf.getTablero().getColumnas()+1);
            agregarColumna(GridSup);            
            agregarColumna(GridInf);
            agregarColumna(gridLetras);            
            CircleDoubleLinkedList<Casilla<Character>> casillasCol = new CircleDoubleLinkedList<>();
            for (int i = 0; i < conf.getTablero().getFilas(); i++) {
                Casilla<Character> casillaC = new Casilla(i,conf.getTablero().getColumnas(),generarLetra());
                Casilla<Character> casillaF = new Casilla(i,conf.getTablero().getColumnas(),generarLetra());
                casillasCol.addLast(casillaC);
                filas.get(i).addLast(casillaF);
                StackPane stk = new StackPane();
                stk.setStyle("-fx-border-color: black");
                Label lbl = new Label(String.valueOf(casillaC.getDato()));
                stk.getChildren().add(lbl);
                configPalabraMarcada(stk,lbl);
                gridLetras.add(stk, conf.getTablero().getColumnas()-1, i);
            }
            columnas.addLast(casillasCol);
            agregarFlechas(GridInf,this.conf.getTablero().getColumnas()-1,0,"abajo");
            agregarFlechas(GridSup,this.conf.getTablero().getColumnas()-1,0,"arriba");
        }
        eliminarAgregar--;
        if(eliminarAgregar==0)  bloquearBottom();
        System.out.println(columnas);
        System.out.println(filas);
    }
    
    private void agregarFlechas(GridPane grid, int col, int fila, String ubicacion){
        ImageView img = new ImageView(new Image("/recursos/imagenes/"+ubicacion+".png"));
        img.setFitHeight(15.0);
        img.setFitWidth(20.0);           
        grid.add(img, col, fila);
        if(ubicacion.equalsIgnoreCase("abajo"))
            this.flechasAb.addLast(img);
        else if (ubicacion.equalsIgnoreCase("arriba"))
            this.flechasArr.addLast(img);
        else if(ubicacion.equalsIgnoreCase("derecha"))
            this.flechasD.addLast(img);
        else if (ubicacion.equalsIgnoreCase("izquierda"))
            this.flechasI.addLast(img);
        controlFlechas(img,ubicacion,grid);        
    }
    
    private void controlFlechas(ImageView img,String ubicacion, GridPane grid){
        continuar = true;                
        if(ubicacion.equalsIgnoreCase("abajo") || ubicacion.equalsIgnoreCase("arriba")){             
            img.setOnMouseClicked(e->{
                int i;
                if(ubicacion.equalsIgnoreCase("abajo"))
                    i = this.flechasAb.indexOf(img);
                else
                    i = this.flechasArr.indexOf(img);
                continuar = true;
                Platform.runLater(()->{                   
                    for(Casilla c: columnas.get(i)){
                        if(c.getNumVecesMarcada()!=0){
                            continuar = false;
                            break;
                        }                            
                    }
                    if(continuar){
                        this.lblError.setText("");
                        if(ubicacion.equalsIgnoreCase("abajo"))
                            columnas.get(i).setLast(columnas.get(i).size()-2);
                        else columnas.get(i).setLast(0);
                        for (int j = 0; j < conf.getTablero().getFilas(); j++) {                            
                            char dato = columnas.get(i).get(j).getDato();
                            columnas.get(i).get(j).setX(j);    
                            filas.get(j).get(i).setDato(dato);
                            Label lbl = new Label(String.valueOf(dato));
                            ((StackPane)getNodeFromGridPane(gridLetras,i,j)).getChildren().set(0, lbl);
                        }                        
                    }else
                        this.lblError.setText("*No puede mover filas o columnas\ncon elementos sombreados*");
                    
                });
            });
        }else if (ubicacion.equalsIgnoreCase("derecha") || ubicacion.equalsIgnoreCase("izquierda")){                        
            img.setOnMouseClicked(e->{
                int i;
                if(ubicacion.equalsIgnoreCase("derecha"))
                    i = this.flechasD.indexOf(img);
                else
                    i = this.flechasI.indexOf(img);
                continuar = true;
                Platform.runLater(()->{                
                    for(Casilla c: filas.get(i)){
                        if(c.getNumVecesMarcada()!=0){
                            continuar = false;
                            break;
                        }                            
                    }
                if(continuar){
                    this.lblError.setText("");
                    if(ubicacion.equalsIgnoreCase("derecha"))
                        filas.get(i).setLast(filas.get(i).size()-2);
                    else filas.get(i).setLast(0);
                    for (int j = 0; j < conf.getTablero().getColumnas(); j++) {                            
                        char dato = filas.get(i).get(j).getDato();
                        columnas.get(j).get(i).setDato(dato);
                        filas.get(i).get(j).setY(j);                                
                        Label lbl = new Label(String.valueOf(dato));
                        ((StackPane)getNodeFromGridPane(gridLetras,j,i)).getChildren().set(0, lbl);
                    }
                }else
                    this.lblError.setText("*No puede mover filas o columnas\ncon elementos sombreados*");
            });
            });
        }        
    }
    
    private javafx.scene.Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
    return null;
    }
    
    public Character generarLetra(){
        Random rd = new Random();
        int ind = rd.nextInt(letras.length());   
        return letras.charAt(ind);
    }
    
    private void configPalabraMarcada(StackPane stkPane, Label lbl){        
        stkPane.setOnMouseDragEntered(
            event -> {
                StackPane stk;                    
                event.consume();                              
                if(letrasSeleccionadas.contains(stkPane)){                    
                    int ind = gridLetras.getChildren().indexOf(stkPane);                     
                    stk = letrasSeleccionadas.pop(); 
                    if(!indices.contains(stkPane) && !indices.contains(stk))
                        stkPane.setBackground(new Background(new BackgroundFill(conf.getColorFondo(), CornerRadii.EMPTY, Insets.EMPTY)));                                                             
                    //Color color = (Color) stk.getBackground().getFills().get(0).getFill();
                    //System.out.println("COLOR: "+conf.getColorSombreado().equals(color));
                    if(!indices.contains(stkPane) && !indices.contains(stk))
                        stk.setBackground(new Background(new BackgroundFill(conf.getColorFondo(), CornerRadii.EMPTY, Insets.EMPTY)));                                                                                               
                }else{     
                    if(indices.contains(stkPane))
                        stkPane.setBackground(new Background(new BackgroundFill(conf.getColorSombreado().darker(), CornerRadii.EMPTY, Insets.EMPTY)));                
                    this.letrasSeleccionadas.push(stkPane);                    
                }             
                if(indices.contains(stkPane))
                    stkPane.setBackground(new Background(new BackgroundFill(conf.getColorSombreado().darker(), CornerRadii.EMPTY, Insets.EMPTY)));                
                else
                    stkPane.setBackground(new Background(new BackgroundFill(conf.getColorSombreado(), CornerRadii.EMPTY, Insets.EMPTY)));
        });     
        stkPane.setOnMouseReleased(e->{                        
            if(!validarPalabras(letrasSeleccionadas)){
                while(!letrasSeleccionadas.isEmpty()){
                    StackPane eliminar = letrasSeleccionadas.pop();   
                    if(!indices.contains(eliminar))
                        eliminar.setBackground(new Background(new BackgroundFill(conf.getColorFondo(), CornerRadii.EMPTY, Insets.EMPTY)));
                    else if (indices.count(eliminar)>1 && palabrasEncontradas.contains(ultimaPalabra))
                        eliminar.setBackground(new Background(new BackgroundFill(conf.getColorSombreado(), CornerRadii.EMPTY, Insets.EMPTY)));
                    else if (indices.count(eliminar)>1)
                        eliminar.setBackground(new Background(new BackgroundFill(conf.getColorSombreado().darker(), CornerRadii.EMPTY, Insets.EMPTY)));
                    else if (indices.count(eliminar)==1)
                        eliminar.setBackground(new Background(new BackgroundFill(conf.getColorSombreado(), CornerRadii.EMPTY, Insets.EMPTY)));
                }
            }else{
                if(conf.getNumJugadores()==2){
                    if(jugador1.isJugando()){
                        jugador1.setJugando(false);
                        jugador2.setJugando(true);
                        this.txtTurno.setText(jugador2.getNickname());
                    }else{
                        jugador1.setJugando(true);
                        jugador2.setJugando(false);
                        this.txtTurno.setText(jugador1.getNickname());
                    }
                }
                
            }
            
            
            letrasSeleccionadas.clear();                 
        });        
    }
    
    private void dragRoot(){
        this.gridLetras.setOnDragDetected(
        event -> {
          if (event.getButton() == MouseButton.PRIMARY) {
            event.consume();
            this.gridLetras.startFullDrag();
          }
        });
    }       
    
    @FXML
    private boolean eliminarFila(){     
        bottomConfig();
        boolean borrar = true;        
        int numFilas = conf.getTablero().getFilas();
        for (int i = numFilas-1; i >=0 ; i--) {    
            borrar = true;                
            for (Casilla c: filas.get(i)) {
                if(c.getNumVecesMarcada()!=0){
                    borrar = false;
                    break;
                }
            }
            if(borrar){
                indFilaEliminar = i;                 
                Platform.runLater(()->{
                    deleteRow(gridDer,indFilaEliminar);
                    deleteRow(gridIzq,indFilaEliminar);
                    deleteRow(gridLetras,indFilaEliminar);
                });                
                this.flechasD.remove(indFilaEliminar);
                this.flechasI.remove(indFilaEliminar);
                System.out.println("INDICE ANTERIOR: "+filas.size());
                filas.remove(indFilaEliminar); 
                System.out.println("INDICE DESPUES: "+filas.size());
                for (int j = 0; j < conf.getTablero().getColumnas(); j++) {
                    columnas.get(j).remove(indFilaEliminar);
                }
                conf.getTablero().setFilas(numFilas-1);                
                if(indFilaEliminar!=numFilas-1)
                    reestructurarListas();
                System.out.println("Nueva Lsita: "+filas);
                eliminarAgregar--;
                if(eliminarAgregar<=0)  bloquearBottom();
                return true;                
            }
        }
        if(!borrar){
            lblError.setText("*No se puede eliminar ninguna fila\nTodas tienen letras marcadas*");
        }
        return false;
    }
          
    @FXML
    private boolean eliminarColumna() {   
        bottomConfig();
        boolean borrar = true;        
        int numCol = conf.getTablero().getColumnas();
        for (int i = numCol-1; i >=0 ; i--) {
            borrar = true;               
            for (Casilla c: columnas.get(i)) {
                if(c.getNumVecesMarcada()!=0){
                    borrar = false;
                    break;
                }
            }
            if(borrar){
                indColEliminar = i;                 
                Platform.runLater(()->{
                    deleteColumn(GridSup,indColEliminar);
                    deleteColumn(GridInf,indColEliminar);
                    deleteColumn(gridLetras,indColEliminar);
                });
                this.flechasArr.remove(indColEliminar);
                this.flechasAb.remove(indColEliminar);
                columnas.remove(indColEliminar);   
                for (int j = 0; j < conf.getTablero().getFilas(); j++) {
                    filas.get(j).remove(indColEliminar);
                }
                conf.getTablero().setColumnas(numCol-1);
                if(indColEliminar!=numCol-1)
                    reestructurarListas();
                eliminarAgregar--;
                if(eliminarAgregar<=0)  bloquearBottom();
                return true;               
            }
        }
        if(!borrar){
            lblError.setText("*No se puede eliminar ninguna fila\nTodas tienen letras marcadas*");
        }
        return false;
    }
    
    private void reestructurarListas(){
        for (int i = 0; i < conf.getTablero().getFilas(); i++) {
            for (int j = 0; j < conf.getTablero().getColumnas(); j++) {
                filas.get(i).get(j).setX(i);
                filas.get(i).get(j).setY(j);
                columnas.get(j).get(i).setX(i);
                columnas.get(j).get(i).setY(j);
            }
        }
    }
    
    private void deleteRow(GridPane grid, int row) {
    Set<javafx.scene.Node> deleteNodes = new HashSet<>();
    grid.getChildren().forEach((child) -> {
        // get index from child
        Integer rowIndex = GridPane.getRowIndex(child);
        // handle null values for index=0
        int r = rowIndex == null ? 0 : rowIndex;

        if (r > row) {
            // decrement rows for rows after the deleted row
            GridPane.setRowIndex(child, r-1);
        } else if (r == row) {
            // collect matching rows for deletion
            deleteNodes.add(child);
        }
        });

    // remove nodes from row
    grid.getChildren().removeAll(deleteNodes);
    }
    
    private void deleteColumn(GridPane grid, int col) {
        Set<javafx.scene.Node> deleteNodes = new HashSet<>();
        grid.getChildren().forEach((child) -> {
            // get index from child
            Integer colIndex = GridPane.getColumnIndex(child);
            // handle null values for index=0
            int r = colIndex == null ? 0 : colIndex;

            if (r > col) {
                // decrement rows for rows after the deleted col
                GridPane.setColumnIndex(child, r-1);
            } else if (r == col) {
                // collect matching rows for deletion
                deleteNodes.add(child);
            }
            });

        // remove nodes from col
        grid.getChildren().removeAll(deleteNodes);
    }
    
    private void bottomConfig(){
        if(conf.getTablero().getFilas()==3)
            this.btnEliminarFila.setDisable(true);
        else this.btnEliminarFila.setDisable(false);
        
        if(conf.getTablero().getColumnas()==3)
            this.btnEliminarCol.setDisable(true);
        else this.btnEliminarCol.setDisable(false);
    }
    
    private boolean validarPalabras(Stack<StackPane> pilaLetras){
        if(pilaLetras.isEmpty()) return false;
        ArrayList<String> palabra = new ArrayList<>();        
        Stack<StackPane> pilaLetras_COPIA = (Stack<StackPane>)pilaLetras.clone();
        while(!pilaLetras_COPIA.isEmpty()){
            String letra = ((Label)pilaLetras_COPIA.pop().getChildren().get(0)).getText();
            palabra.addFirst(letra);
        }
        StringBuilder sb = new StringBuilder();
        for (String s: palabra) {
            sb.append(s.trim());
        }
        ArrayList<String> subLista = dicPalabras.get(sb.toString().charAt(0));
        System.out.println("SUBSTRING: "+ultimaPalabra);   
        int limite = conf.getTablero().getColumnas() * conf.getTablero().getFilas();
        if(palabrasEncontradas.size() == (limite/2)){
            this.gridLetras.setDisable(true);
            Alert a = new Alert(AlertType.ERROR,"Ha terminado el juego c:");
            a.show();
        }     
        if(subLista.contains(sb.toString())){
            this.ultimaPalabra = sb.toString();
            if(!palabrasEncontradas.contains(ultimaPalabra)){
                asignarPuntosJugador(ultimaPalabra.length());    
                escribirPalabrasJugador(ultimaPalabra);
            }                
            this.palabrasEncontradas.addLast(ultimaPalabra);            
            while(!pilaLetras.isEmpty()){
                StackPane stk = pilaLetras.pop();                                                
                this.indices.addFirst(stk);                
                if(indices.count(stk)==1){
                    marcarCasilla(stk,gridLetras.getChildren().indexOf(stk));
                }                    
            }            
        }else{
            asignarPuntosJugador(-sb.toString().length());
            actualizarErrores();
            this.lblError.setText(sb.toString()+"\nno es una palabra valida");
        }
            
           
        return subLista.contains(ultimaPalabra);
    }    
    
    private void escribirPalabrasJugador(String pal){
        if(conf.getNumJugadores()==2){
            if(jugador1.isJugando())
                this.listViewJugador1.getItems().add(0, pal);
            else
                this.listViewJugador2.getItems().add(0, pal);
            
        }else{
            this.listViewJugador1.getItems().add(0, pal);
            this.listViewJugador2.setVisible(false);
        }
    }
    
    private boolean marcarCasilla(StackPane stk, int num){        
        int cont = 0;        
        for (int i = 0; i < conf.getTablero().getFilas(); i++) {
            for (int j = 0; j < conf.getTablero().getColumnas(); j++) {
                if(cont==num){
                    filas.get(i).get(j).incrementarNumeroDeVecesMarcada();
                    columnas.get(j).get(i).incrementarNumeroDeVecesMarcada();
                    return true;
                }
                cont++;
            }                            
        }
        return false;
    }

    private void asignarPuntosJugador(int puntos){
        if (conf.getNumJugadores()==2){            
            if(jugador1.isJugando()){
                int puntajeActual = jugador1.getPuntaje();
                jugador1.setPuntaje(puntajeActual+puntos);
                this.txtJugador1.setText(String.valueOf(jugador1.getPuntaje()));
            }else{
                int puntajeActual = jugador2.getPuntaje();
                jugador2.setPuntaje(puntajeActual+puntos);
                this.txtJugador2.setText(String.valueOf(jugador2.getPuntaje()));
            }
                
        }else{
            int puntajeActual = jugador1.getPuntaje();
            jugador1.setPuntaje(puntajeActual+puntos);
            this.txtJugador1.setText(String.valueOf(jugador1.getPuntaje()));
        }
    }
    
    private void actualizarErrores(){
        if(conf.getNumJugadores()==2){
            actualizarTurnos();
            if(jugador1.isJugando())
                actualizarErrores(jugador1,this.lblJugador1);
            else
                actualizarErrores(jugador2,this.lblJugador2);
            if(jugador1.getNumErores()==0 || jugador2.getNumErores()==0){
                gridLetras.setDisable(true);
                this.hbBottom.setDisable(true);
                Alert a = new Alert(AlertType.INFORMATION,"Han excedido el maximo de errores");
                if(jugador1.getPuntaje()>jugador2.getPuntaje())
                    a.setContentText("HA GANADO EL JUGADOR 1");
                else if(jugador2.getPuntaje()>jugador1.getPuntaje())
                    a.setContentText("HA GANADO EL JUGADOR 2");
                else
                    a.setContentText("Ha habido un empate");
            a.show();
                this.btnMezclar.setDisable(true);
            }
        }else{
            actualizarErrores(jugador1,this.lblJugador1);
            if(jugador1.getNumErores()==0){
                gridLetras.setDisable(true);
                this.hbBottom.setDisable(true);
                Alert alert = new Alert(AlertType.INFORMATION,"Ha excedido el maximo de errores");
                Optional<ButtonType> result = alert.showAndWait();
                alert.getButtonTypes().set(0, ButtonType.CLOSE);
                result.ifPresent(e->btnRegresar.fire());
                //alert.show();
                this.btnMezclar.setDisable(true);
            }
        }
    }
    
    private void actualizarErrores(Jugador jugador, Label lbl){
        int erroresActuales = jugador.getNumErores();
        erroresActuales--;
        jugador.setNumErores(erroresActuales);
        lbl.setText(jugador.getNickname()+ " -> "+erroresActuales+" errores restantes");      
        /*if(jugador.getNumErores()==0 && conf.getNumJugadores()==2){
            Alert a = new Alert(AlertType.ERROR,"Ha excedido el maximo de errores");
            a.setContentText(jugador.getNickname()+" ya no podrá seguir jugando");            
            a.show();    
        }
        */
    }

    @FXML
    private void mezclarLetras(ActionEvent event) {
        for (int i = 0; i < conf.getTablero().getFilas(); i++) {
            for (int j = 0; j < conf.getTablero().getColumnas(); j++) {
                if(!filas.get(i).get(j).isMarcada()){
                    char nuevaLetra = generarLetra();
                    filas.get(i).get(j).setDato(nuevaLetra);
                    columnas.get(j).get(i).setDato(nuevaLetra);
                    Label lbl = new Label(String.valueOf(nuevaLetra));
                    ((StackPane)getNodeFromGridPane(gridLetras,j,i)).getChildren().set(0, lbl);
                }                
            }
        }
    }
    
    private void actualizarTurnos(){
        if(conf.getNumJugadores()==2){
            if(jugador1.isJugando() && jugador2.getNumErores()>0){
                jugador1.setJugando(false);
                jugador2.setJugando(true);
                this.txtTurno.setText("Jugador 2");
            }else if (jugador2.isJugando() && jugador1.getNumErores()>0){
            jugador1.setJugando(true);
            jugador2.setJugando(false);
            this.txtTurno.setText("Jugador 1");
            }
            if(jugador1.isJugando() && jugador2.getNumErores()==0){
                jugador1.setJugando(true);
                this.txtTurno.setText("Jugador 1");
            }else if(jugador2.isJugando() && jugador1.getNumErores()==0){
                jugador2.setJugando(true);
                this.txtTurno.setText("Jugador 2");
            }                
        }
    }
    
    @FXML
    private void regresar(){
        FXMLLoader loader = new FXMLLoader(Grupo_12.class.getResource("/vistas/Inicio.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();            
            stage.setScene(scene);
            stage.show();               
            cerrarVentana();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } 
    }
    
    private void cerrarVentana(){
        Stage myStage = (Stage) this.btnRegresar.getScene().getWindow();
        myStage.close(); 
    }
    
    private void llenarTableroEspecifico(){
        int total = conf.getTablero().getColumnas()*conf.getTablero().getFilas();        
        for (int i = 0; i < (total/2); i++) {            
            String newWord;
            ArrayList<String> subLista = dicPalabras.get(generarLetra());                        
            newWord = subLista.get(valorRandom(subLista.size()));
            while(newWord.length()>conf.getTablero().getColumnas() && newWord.length()>conf.getTablero().getFilas()){
                subLista = dicPalabras.get(generarLetra());                        
                newWord = subLista.get(valorRandom(subLista.size()));
            }
            this.palabrasDisponibles.addLast(newWord);
        }
    }
    
    private int valorRandom(int maximo){
        Random rd = new Random();        
        return rd.nextInt(maximo);
    }
}