/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin.User;

import static Admin.Teacher.InsertController.randomAlphaNumeric;
import Module.TeachersModule;
import Module.UserModule;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class InsertController implements Initializable {

    Alert a = new Alert(Alert.AlertType.NONE);

    @FXML
    private Label txtTitle;

    @FXML
    private TextField tfFirstName;

    @FXML
    private TextField tfLastName;

    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField tfPassWord;

    @FXML
    private ComboBox<UserModule> cbRole;

    @FXML
    private Button btnSave;

    @FXML
    private Label lbError;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnReset;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<UserModule> datas = UserModule.selectAll();
        System.out.println(datas);
        try {
            UserModule user = UserModule.getDetailUser("QUBF");
            cbRole.getSelectionModel().select((UserModule) user);
        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        Set<String> listUser = new HashSet<String>();
//        datas.forEach((data) -> { 
//            listUser.add(data.getValueUserId());
//        });
//        listUser.forEach((userId)->{
//            cbRole.getItems().add(userId);
//        });
        cbRole.setItems(datas);
        cbRole.disableProperty();
        cbRole.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserModule>() {
            @Override
            public void changed(ObservableValue<? extends UserModule> arg0, UserModule oldValue, UserModule newValue) {
                if (newValue != null) {
                    System.out.println("Selected UserId: " + newValue.getValueUserId());
                }
            }
        });
        
        tfFirstName.setText("Vương");
        tfLastName.setText("Lực");
        tfEmail.setText("vuongluc2000@gmail.com");
        tfPassWord.setText("lucbeo123");
//        cbRole.getSelectionModel().select("Student");
    }

    private static final String ALPHA_NUMERIC_STRING = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    private boolean validateForm() throws SQLException {
        String msg = null;
        boolean flag = true;
        Pattern patternEmail = Pattern.compile("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$");
        boolean valEmail = patternEmail.matcher(tfEmail.getText().trim()).matches();

        if (tfFirstName.getText()
                .trim().isEmpty()) {
            msg = "Vui lòng nhập Họ!";
            flag = false;
        } 
        else if (tfFirstName.getText().length() < 2) {
            msg = "Họ quá ngắn! Vui lòng nhập trên 2 kí tự!";
            flag = false;
        }
        else if (tfLastName.getText().trim().isEmpty()) {
            msg = "Vui lòng nhập tên!";
            flag = false;
        }
        else if (tfLastName.getText().length() < 2) {
            msg = "Tên quá ngắn! Vui lòng nhập trên 2 kí tự!";
            flag = false;
        }
        else if (tfEmail.getText()
                .trim().isEmpty()) {
            msg = "Vui lòng nhập Email!";
            flag = false;
        } else if (!valEmail) {
            msg = "Vui lòng nhập đúng định dạng Email!";
            flag = false;
        } else if (tfPassWord.getText().trim().isEmpty()) {
            msg = "Vui lòng nhập mật khẩu!";
            flag = false;
        }
        else if (tfPassWord.getText().length() < 5) {
            msg = "Mật khẩu ít nhất 5 ký tự!";
            flag = false;
        }else if (cbRole.getSelectionModel().isEmpty()) {
            msg = "Vui lòng chọn Role!";
            flag = false;
        }else if(UserModule.checkEmailExi(tfEmail.getText())){
            msg = "Email đã tồn tại!";
            flag = false;
        }
        lbError.setText(msg);
        return flag;

    }

    private UserModule getData() {
        UserModule user = new UserModule();
        try {            
            while( true ){
                String userId = randomAlphaNumeric(4);
                if( UserModule.getDetailUser(userId) == null ){
                    user.setValueUserId(userId);
                    break;
                }                
            }            
        } catch (SQLException ex) {
            Logger.getLogger(Admin.Teacher.InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        user.setValueUserId("bRMs");
        user.setValueFirstName(tfFirstName.getText());
        user.setValueLastName(tfLastName.getText());
        user.setValueEmail(tfEmail.getText());
        user.setValuePassWord(tfPassWord.getText());
//        user.setValueRole(cbRole.getSelectionModel().getSelectedItem().get);
        return user;
    }
    
    private void resetForm(){
        tfFirstName.setText("");
        tfLastName.setText("");
        tfEmail.setText("");
        tfPassWord.setText("");
        cbRole.getSelectionModel().clearSelection();
    }

    @FXML
    void btnSaveClick(ActionEvent event) throws SQLException {
//        if(validateForm()){            
//            UserModule data = getData();
//            if(UserModule.insert(data)){
//                this.showAlert("Thêm mới thành công!");
//                this.resetForm();
//            }
//
////            if(UserModule.update(data)){
////                this.showAlert("Cập nhật thành công!");
////                this.resetForm();
////            }
//        }
        System.out.println(cbRole.getSelectionModel().getSelectedItem().getValueUserId());
    }

    @FXML
    void btnBackClick(ActionEvent event) {

    }

    @FXML
    void btnResetClick(ActionEvent event) {
        this.resetForm();
    }

    private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Teacher");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

}
