/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin.Student;

import Module.StudentModule;
import Module.TeachersModule;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class InsertController implements Initializable {

    
    /**
     * Initializes the controller class.
     * @param dateString
     * @param url
     * @param rb
     * @return 
     */
    public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<StudentModule> data = StudentModule.selectAll();
        System.out.println(data);
//        TeachersModule teacher = new TeachersModule();
//        tfName.setText("a");
//        tfEmail.setText("a");
//        tfAddress.setText("Student");
//        tfPhone.setText("a");
//        datePicker.setValue(LOCAL_DATE("2020-07-15"));
    }    
    
    @FXML
    private TextField tfName;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfAddress;

    @FXML
    private TextField tfPhone;

    @FXML
    private DatePicker datePicker;
    @FXML
    private Label lbError;
    
    private void resetForm(){
        tfName.setText("");
        tfEmail.setText("");
        tfAddress.setText("");
        tfPhone.setText("");
        datePicker.getEditor().clear();
    }
    
    private boolean validateForm(){
        String msg = null;
        boolean flag = true;
        Pattern patternEmail = Pattern.compile("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
        boolean valEmail = patternEmail.matcher(tfEmail.getText().trim()).matches();
        Pattern patternPhone = Pattern.compile("^(0[3|5|7|8|9])+([0-9]{8})$");
        boolean valPhone = patternPhone.matcher(tfPhone.getText().trim()).matches();
        if( tfName.getText().trim().isEmpty() ){
            msg = "Vui lòng nhập tên!";
            flag = false;
        }
        else if ( tfName.getText().length() < 2 ){
            msg = "Tên quá ngắn! Vui lòng nhập trên 2 kí tự!";
            flag = false;
        }
        else if( tfEmail.getText().trim().isEmpty() ){
            msg = "Vui lòng nhập Email!";
            flag = false;
        } 
        else if( !valEmail ){
            msg = "Vui lòng nhập đúng định dạng Email!";
            flag = false;
        } 
        else if( tfAddress.getText().trim().isEmpty() ){
            msg = "Vui lòng nhập địa chỉ!";
            flag = false;
        } 
        else if( tfPhone.getText().trim().isEmpty() ){
           msg = "Vui lòng nhập số điện thoại!";
           flag = false;
        } 
        else if( !valPhone ){
           msg = "Số điện thoại chưa đúng!";
           flag = false;
        } 
        else if(datePicker.getValue() == null){
            msg = "Vui lòng chọn ngày sinh!";
            flag = false;
        } 
        lbError.setText(msg);
        return flag;
    }
    public static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
    
    private StudentModule getData(){
        StudentModule student = new StudentModule();        
//        try {            
//            while( true ){
//                String rollno = randomAlphaNumeric(4);
//                if( !TeachersModule.getDetailTeacher(rollno) ){
//                    student.setRollNo(rollno);
//                    break;
//                }                
//            }            
//        } catch (SQLException ex) {
//            Logger.getLogger(Admin.Teacher.InsertController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        student.setRollNo("wJAi");
        student.setName(tfName.getText());
        student.setAddress(tfAddress.getText());
        student.setEmail(tfEmail.getText());     
        student.setPhone(tfPhone.getText());
        student.setUserId("QUBF");
        student.setBirthDay(datePicker.getValue());
        return student;
    }
    
    @FXML
    void btnBackStudent(ActionEvent event) {

    }
    
    @FXML
    void btnResetStudent(ActionEvent event) {
        this.resetForm();
    }

    @FXML
    void btnSaveStudent(ActionEvent event) throws SQLException{
        if(validateForm()){            
            StudentModule data = getData();
//            if(StudentModule.insert(data)){
//                this.showAlert("Thêm mới thành công!");
//                this.resetForm();
//            }

            if(StudentModule.update(data)){
                this.showAlert("Cập nhật thành công!");
                this.resetForm();
            }
        }
    }
    private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Student");
        alert.setHeaderText(null);
        alert.setContentText(text); 
        alert.showAndWait();
    } 
    
}
