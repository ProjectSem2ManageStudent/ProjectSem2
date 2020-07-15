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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class AddScheduleController implements Initializable{

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

    
    private boolean errors = false;
    private ScheduleModule scheduleModule = null;

    private static final String DATE_PATTERN = "yyyy/MM/dd";
    public static final DateTimeFormatter DATE_FORMATTER =
        DateTimeFormatter.ofPattern(DATE_PATTERN);

    private boolean hasParseError = false;

    public boolean hasParseError(){
        return hasParseError;
    }

    public String toString(LocalDate localDate) {
       return DATE_FORMATTER.format(localDate);
    }

    public LocalDate validateDateTime(String formattedString) {

            try {
                LocalDate date=LocalDate.from(DATE_FORMATTER.parse(formattedString));
                hasParseError=false;
                return date;
            } catch (DateTimeParseException parseExc){
                hasParseError=true;
                return null;
            }
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
//          cbbTeacher.setValue("lvback");
        
    }
    
    public void initialize(ScheduleModule scheduleModule) {
        this.scheduleModule = scheduleModule;
        String msg = "";
        
        if (this.scheduleModule == null) { //insert a new book
            msg = "Create a new module";
        } else { //update an existing book
            msg = "Update an existing module";
            cbbSubject.setValue(scheduleModule.getSubjectId());
            cbbClassNo.setValue(scheduleModule.getClassNo());
            cbbTeacher.setValue(scheduleModule.getTeacherId());
            dpStartDate.setValue(scheduleModule.getStartDate());
            dpStartDate.setValue(scheduleModule.getEndDate());
            txtTeachingTime.setText(scheduleModule.getTeachingTime());
        }

        lbHeader.setText(msg);
    }
    
  
    private ScheduleModule exportModuleFromFields() {
        ScheduleModule module = new ScheduleModule();
        module.setSubjectId(cbbSubject.getValue().toString());
        module.setClassNo(cbbClassNo.getValue().toString());
        module.setTeacherId(cbbTeacher.getValue().toString());
        module.setStartDate(dpStartDate.getValue());
        module.setEndDate(dpEndDate.getValue());
        module.setTeachingTime(txtTeachingTime.getText());
        return module;
    }

    private void ValidateTeachingTime() {
        String teachingTime = txtTeachingTime.getText();
        if (teachingTime.isEmpty()) {
            errors = false;
            lbError.setText("Teaching time can`t be empty!");
        } 

        else {
            errors = true;
            lbError.setText("");
        }    
    }
    
    private void ValidateStartDate(){
     if (dpStartDate.getValue() == null) {
            lbError.setText("No valid start date!\n");
        } else {
            System.out.println(dpStartDate.getValue().toString());
//            if (!java.util.Date.validEnglishDate(dpStartDate.getValue().toString())) {
            lbError.setText("No valid from date. Use the format yyyy-MM-dd.\n");
//        }
        }
    }
    
    @FXML
    void btnAddClick(ActionEvent event) {
        do {
//            ValidateStartDate();
//        validateDateTime(dpStartDate.getValue().toString());
        ValidateTeachingTime();
            if (errors == false) {
                break;
            } else {
                break;
            }

        } while (1 == 1);

        if (errors == true) {
            try {
                if (scheduleModule == null) { //insert a new book
                    ScheduleModule insertScheduleModule = exportModuleFromFields();
                    insertScheduleModule = ScheduleModule.insert(insertScheduleModule);

                    String msg = " Create new Schedule successfully!" + insertScheduleModule.getId();
                    lbError.setText(msg);
                } else { //update an existing book
                    ScheduleModule updateScheduleModule = exportModuleFromFields();
                    updateScheduleModule.setId(this.scheduleModule.getId());

                    boolean result = ScheduleModule.update(updateScheduleModule);
                    if (result) {
                        lbError.setText("Update Schedule successfully!");
                    } else {
                        lbError.setText("Update Schedule failed!");
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
}
