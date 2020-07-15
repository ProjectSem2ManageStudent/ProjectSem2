/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin.Class;

import Module.ClassesModule;
import java.net.URL;
import java.util.ResourceBundle;
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
public class AddClassController {

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
    void btnAddClick(ActionEvent event) {
           do {      
            Validate();
            if(errors == false) break;
            else break;
            
        } while ( 1 == 1);
        
        if(errors == true){
            try {
                if (classModule == null) { //insert a new book
                    ClassesModule insertClassModule = exportModuleFromFields();
                    insertClassModule = ClassesModule.insert(insertClassModule);

                    String msg = " Create new Class successfully! " + insertClassModule.getClassNo();
                    lbError.setText(msg);
                } else { //update an existing book
                    ClassesModule updateClassModule = exportModuleFromFields();
                    updateClassModule.setClassNo(this.classModule.getClassNo());

                    boolean result = ClassesModule.update(updateClassModule);
                    if (result) {
                        lbError.setText("Update Class successfully!");
                    } else {
                        lbError.setText("Update Class failed!");
                    }
                }
            } catch (Exception e) {
                lbError.setText(e.getMessage());
            }
        }
    }

    @FXML
    void btnCancelClick(ActionEvent event) {
        System.exit(0);
    }
    
    public void initialize() {
//        txtClassNo.textProperty().addListener((observable, oldValue, newValue) -> {
//            validationName();
//        });
//        
//        txtDuration.textProperty().addListener(((observable, oldValue, newValue) -> {
//            validationDuration();
//        }));
//        
    }
    
    
    public void initialize(ClassesModule classModule) {
        this.classModule = classModule;
        String msg = "";
        if (this.classModule == null) { //insert a new book
            msg = "Create a new module";
        } else { //update an existing book
            msg = "Update an existing module";
            txtClassNo.setText(classModule.getClassNo());
            txtClassName.setText(classModule.getClassName());
        }

        lbHeader.setText(msg);
    }
    
}
