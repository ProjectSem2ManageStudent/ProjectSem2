/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class DbService {
     private static final String USERNAME = "root";
    private static final String PASSWORD = "lucbeo123";
//    private static final String CONN_STRING
//            = "jdbc:mysql://localhost/manager_student_javafx";
    private static final String CONN_STRING=  
            "jdbc:mysql://localhost:3306/manager_student_javafx?zeroDateTimeBehavior=convertToNull";
    public static Connection getConnection() throws SQLException {
        
        return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);

    }
}
