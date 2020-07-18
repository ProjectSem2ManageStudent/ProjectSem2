/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin.Class;

import Admin.Teacher.InsertController;
import static Admin.Teacher.InsertController.randomAlphaNumeric;
import Module.ClassesModule;
import Module.TeachersModule;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class AddClassController implements Initializable  {

    /**
     * Initializes the controller class.
     */
    
    private boolean errors = false;
    private ClassesModule classModule = null;

     Alert a = new Alert(AlertType.NONE); 
    @FXML
    private Label lbHeader;

    @FXML
    private TextField txtClassNo;

    @FXML
    private TextField txtClassName;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

        @FXML
    private Label lbError;

    private void Validate(){
        String classNo = txtClassNo.getText();
        String className = txtClassName.getText();
        if(classNo.isEmpty()) {
            errors = false;
            lbError.setText("Class No can`t be empty!");
        }else if (className.isEmpty()){
             errors = false;
            lbError.setText("Class Name can`t be empty!");
        }
        else{
            errors = true;
            lbError.setText("");
        }
    }
    
     private ClassesModule exportModuleFromFields() {
        ClassesModule module = new ClassesModule();
        module.setClassNo(txtClassNo.getText());
        module.setClassName(txtClassName.getText());
        return module;
    }

    @FXML
    void btnBackClick(ActionEvent event) {

    }

    @FXML
    void btnResetClick(ActionEvent event) {
        
        resetForm();
    }
    
    private boolean validateForm(){
        String msg = null;
        boolean flag = true;
         if( txtClassNo.getText().trim().isEmpty() ){
            msg = "Vui lòng nhập mã lớp!";
            flag = false;
        }
        else if ( txtClassNo.getText().length() < 2 ){
            msg = "Mã lớp học quá ngắn! Vui lòng nhập trên 2 kí tự!";
            flag = false;
        }
        else if( txtClassName.getText().trim().isEmpty() ){
            msg = "Vui lòng nhập tên lớp!";
            flag = false;
        } 
      
        lbError.setText(msg);
        return flag;
    }
    private void resetForm(){
       txtClassNo.setText("");
       txtClassName.setText("");
    }
        
    @FXML
    void btnAddClick(ActionEvent event) throws SQLException {
       if(validateForm()){       
           
             ClassesModule data = getData();
            if(ClassesModule.insert(data)){
                this.showAlert("Thêm mới thành công!");
                this.resetForm();
            }
            if(ClassesModule.update(data)){
                this.showAlert("Cập nhật thành công!");
                this.resetForm();
            }
        }
    }

    
    private ClassesModule getData(){
        ClassesModule classModule = new ClassesModule();        
        classModule.setClassNo(txtClassNo.getText());
        classModule.setClassName(txtClassName.getText());
        return classModule;
    }
    
     
      private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Teacher");
        alert.setHeaderText(null);
        alert.setContentText(text); 
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
   ObservableList<ClassesModule> data = ClassesModule.selectAll();
        System.out.println(data);    }
    
}
