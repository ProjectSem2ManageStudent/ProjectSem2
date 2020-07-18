/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin.Schedule;

import Module.ClassesModule;
import Module.ScheduleModule;
import Module.SubjectModule;
import Module.TeachersModule;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class InsertController implements Initializable{

    /**
     * Initializes the controller class.
     */

    @FXML
    private Label lbHeader;

    @FXML
    private Label lbError;

   @FXML
    private DatePicker dpStartDate;

    @FXML
    private DatePicker dpEndDate;

    @FXML
    private TextField txtTeachingTime;

    @FXML
    private ComboBox<String> cbbSubject;

    @FXML
    private ComboBox<String> cbbClassNo;

    @FXML
    private ComboBox<String> cbbTeacher;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Label lbMsg;

    @FXML
    void selectClass(ActionEvent event) {
    }

    @FXML
    void selectSubject(ActionEvent event) {
    }

    @FXML
    void selectTeacher(ActionEvent event) {
    }


    @FXML
    void btnBackClick(ActionEvent event) {

    }

    @FXML
    void btnResetClick(ActionEvent event) {
        resetForm();
    }
    
    private boolean errors = false;
    private ScheduleModule scheduleModule = null;

   public static final LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate;
    }
  
    public void initialize(URL url, ResourceBundle rb) {
      ObservableList<SubjectModule> subjectDatas = SubjectModule.selectAll();
        Set<String> listSubject = new HashSet<String>();
        subjectDatas.forEach((data) ->{
            listSubject.add(data.getId());
        });
        listSubject.forEach((subjectId) ->{
            cbbSubject.getItems().add(subjectId);
        });
        
        ObservableList<ClassesModule> ClassNoDatas = ClassesModule.selectAll();
         Set<String> listClassNo = new HashSet<String>();
        ClassNoDatas.forEach((data) ->{
            listClassNo.add(data.getClassNo());
        });
        listClassNo.forEach((classNo) ->{
            cbbClassNo.getItems().add(classNo);
        });
        
        ObservableList<TeachersModule> TeacherDatas = TeachersModule.selectAll();
         Set<String> listTeacher = new HashSet<String>();
        TeacherDatas.forEach((data) ->{
            listTeacher.add(data.getTeacherId());
        });
        listTeacher.forEach((teacherId) ->{
            cbbTeacher.getItems().add(teacherId);
        });
        
    }
  private boolean validateForm(){
        String msg = null;
        boolean flag = true;
       if (cbbSubject.getValue() == null){
           msg = "Vui lòng chọn môn học!";
           flag = false;
       }else if (cbbClassNo.getValue() == null){
           msg = "Vui lòng chọn lớp học!";
           flag = false;
       }else if (cbbTeacher.getValue() == null){
           msg = "Vui lòng chọn giảng viên!";
           flag = false;
       }else if(dpStartDate.getValue() == null){
            msg = "Vui lòng chọn ngày bắt đầu môn!";
            flag = false;
       }else if(dpEndDate.getValue() == null){
            msg = "Vui lòng chọn ngày kết thúc môn!";
            flag = false;
        }else if(txtTeachingTime.getText().trim().isEmpty()){
            msg = "Vui lòng nhập thời gian học!";
            flag = false;
        }
        lbError.setText(msg);
        return flag;
    }
  
    private void resetForm(){
       txtTeachingTime.setText("");
       cbbSubject.getEditor().clear();
       cbbClassNo.getEditor().clear();
       cbbTeacher.getEditor().clear();
       dpEndDate.getEditor().clear();
       dpStartDate.getEditor().clear();
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
    private ScheduleModule getData(){
        ScheduleModule scheduleModule = new ScheduleModule();        
        scheduleModule.setSubjectId(cbbSubject.getSelectionModel().getSelectedItem());
        scheduleModule.setClassNo(cbbClassNo.getSelectionModel().getSelectedItem());
        scheduleModule.setTeacherId(cbbTeacher.getSelectionModel().getSelectedItem());
        scheduleModule.setStartDate(dpStartDate.getValue());
        scheduleModule.setEndDate(dpEndDate.getValue());
        scheduleModule.setTeachingTime(Integer.parseInt(txtTeachingTime.getText()));
        return scheduleModule;
    }
      private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Schedule");
        alert.setHeaderText(null);
        alert.setContentText(text); 
        alert.showAndWait();
    }
    
    @FXML
    void btnAddClick(ActionEvent event) throws SQLException {
        if(validateForm()){            
            ScheduleModule data = getData();
            if(ScheduleModule.insert(data)){
                this.showAlert("Thêm mới thành công!");
                this.resetForm();
            }

//            if(TeachersModule.update(data)){
//                this.showAlert("Cập nhật thành công!");
//                this.resetForm();
//            }
        }
    }

}