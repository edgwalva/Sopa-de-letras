/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grupo_12;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import listas.CircleDoubleLinkedList;


/**
 *
 * @author Grupo_12
 */
public class Grupo_12 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/vistas/Inicio.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {                        
        CircleDoubleLinkedList<String> prueba = new CircleDoubleLinkedList<>();
        prueba.addLast("a");
        prueba.addLast("b");
        prueba.addLast("c");
        prueba.addLast("d");
        prueba.addLast("e");
        System.out.println(prueba);
        prueba.setLast(0);
        prueba.setLast(0);
        System.out.println(prueba);
        prueba.setLast(prueba.size()-2);
        prueba.setLast(prueba.size()-2);
        System.out.println(prueba);
        launch(args);
    }
    
}
