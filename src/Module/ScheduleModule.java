/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author admin
 */
public class ScheduleModule {
       
    private ObjectProperty<Integer> Id;
    private StringProperty subjectId;
    private StringProperty classNo;
    private StringProperty teacherId;
    private ObjectProperty<LocalDate> startDate;
    private ObjectProperty<LocalDate> endDate;
    private StringProperty teachingTime;

    
    public ScheduleModule() {
        this.Id = new SimpleObjectProperty<>(null);
        this.subjectId = new SimpleStringProperty();
        this.classNo = new SimpleStringProperty();
        this.teacherId = new SimpleStringProperty();
        this.startDate = new SimpleObjectProperty<>();
        this.endDate = new SimpleObjectProperty<>();
        this.teachingTime = new SimpleStringProperty();;
    }

    public ObjectProperty<Integer> pgetId() {
        return Id;
    }

    public void psetId(ObjectProperty<Integer> Id) {
        this.Id = Id;
    }

    public StringProperty pgetSubjectId() {
        return subjectId;
    }

    public void psetSubjectId(StringProperty subjectId) {
        this.subjectId = subjectId;
    }

    public StringProperty pgetClassNo() {
        return classNo;
    }

    public void psetClassNo(StringProperty classNo) {
        this.classNo = classNo;
    }

    public StringProperty pgetTeacherId() {
        return teacherId;
    }

    public void psetTeacherId(StringProperty teacherId) {
        this.teacherId = teacherId;
    }


    public StringProperty pgetTeachingTime() {
        return teachingTime;
    }

    public void psetTeachingTime(StringProperty teachingTime) {
        this.teachingTime = teachingTime;
    }
    
   public Integer getId() {
        return Id.get();
    }

    public void setId(Integer id) {
        this.Id.set(id);
    }

     public String getSubjectId() {
        return subjectId.get();
    }

    public void setSubjectId(String subjectId) {
        this.subjectId.set(subjectId);
    }
    
    public String getClassNo() {
        return classNo.get();
    }

    public void setClassNo(String classNo) {
        this.classNo.set(classNo);
    }

     public String getTeacherId() {
        return teacherId.get();
    }

    public void setTeacherId(String teacherId) {
        this.teacherId.set(teacherId);
    }

     public LocalDate getStartDate() {
        return startDate.get();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate.set(startDate);
    }
    
    public LocalDate getEndDate() {
        return endDate.get();
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate.set(endDate);
    }
    
       public String getTeachingTime() {
        return teachingTime.get();
    }

    public void setTeachingTime(String teachingTime) {
        this.teachingTime.set(teachingTime);
    }
    

    public static ObservableList<ScheduleModule> selectAll() {
        ObservableList<ScheduleModule> modules = FXCollections.observableArrayList();

        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM schedule");) {
            while (rs.next()) {
                ScheduleModule m = new ScheduleModule();
                m.setId(rs.getInt("Id"));
                m.setSubjectId(rs.getString("subjectId"));
                m.setClassNo(rs.getString("classNo"));
                m.setTeacherId(rs.getString("teacherId"));
                m.setStartDate(rs.getDate("startDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                m.setEndDate(rs.getDate("endDate").toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                m.setTeachingTime(rs.getString("teachingTime"));
                modules.add(m);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        return modules;
    }

    public static ScheduleModule insert(ScheduleModule newModule) throws SQLException {
        String sql = "INSERT into schedule (subjectId,classNo,teacherId,startDate,endDate,teachingTime) "
                + "VALUES (?,?,?,?,?,?)";
        ResultSet key = null;
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, newModule.getSubjectId());
            stmt.setString(2, newModule.getClassNo());
            stmt.setString(3, newModule.getTeacherId());
            stmt.setDate(4, java.sql.Date.valueOf(newModule.getStartDate()));
            stmt.setDate(4, java.sql.Date.valueOf(newModule.getEndDate()));
            stmt.setString(6, newModule.getTeachingTime());
            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                return newModule;
            } else {
                System.err.println("No schedule is inserted");
                return null;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return null;
        } finally {
            if (key != null) {
                key.close();
            }
        }
    }

    public static boolean delete(ScheduleModule deleModule) {
        String sql = "DELETE FROM schedule WHERE Id = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, deleModule.getId());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No information of the schedule is deleted");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

    public static boolean update(ScheduleModule updateModule) {
        String sql = "UPDATE schedule SET "
                + "subjectId = ? ,"
                + "classNo = ? ,"
                + "teacherId = ? ,"
                + "startDate = ? ,"
                + "endDate = ? ,"
                + "teachingTime = ? ,"
                + "WHERE Id = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, updateModule.getSubjectId());
            stmt.setString(2, updateModule.getClassNo());
            stmt.setString(3, updateModule.getTeacherId());
            stmt.setDate(4, java.sql.Date.valueOf(updateModule.getStartDate()));
            stmt.setDate(5, java.sql.Date.valueOf(updateModule.getEndDate()));
            stmt.setString(6, updateModule.getTeachingTime());
            
            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No information of the schedule is updated");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }
}
