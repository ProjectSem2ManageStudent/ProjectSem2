/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin.Schedule;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("insert.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Schedule");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

              
    }
    
}