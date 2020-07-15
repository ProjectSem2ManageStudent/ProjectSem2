/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin.Schedule;

import Module.TeachersModule;
import java.awt.List;
import java.io.Console;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author admin
 */
public class FXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        AnchorPane root = FXMLLoader.load(getClass().getResource("AddSchedule.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Schedule");
        primaryStage.setScene(scene);
        primaryStage.show();
        TeachersModule modules = new TeachersModule();
        System.out.println(modules.selectAll().size());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

              
    }
    
}
