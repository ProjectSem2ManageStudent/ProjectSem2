/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin.User;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Admin
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
            AnchorPane mainScreen = (AnchorPane) loader.load(getClass().getResource("insert.fxml"));
            Scene scene = new Scene(mainScreen);
            primaryStage.setTitle("Users");
            primaryStage.setScene(scene);
            primaryStage.show();
//            String password = "lucbeo1234";
//            boolean valuate = BCrypt.checkpw(password, "$2a$12$irpvpwR3P7r.A4AI4Ujb3unz9zBv.7vMiUXtG0fLKKfHXDAriX/P2");
//            System.out.println(valuate);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
