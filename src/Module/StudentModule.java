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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public class StudentModule {
    
    private StringProperty rollno;
    private StringProperty name;
    private StringProperty address;
    private StringProperty email;
    private StringProperty phone;
    private ObjectProperty<LocalDate> birthDay;
    private StringProperty userId;

    public StudentModule() {
        this.rollno = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.birthDay = new SimpleObjectProperty<>();
        this.userId = new SimpleStringProperty();
    }

//    Property
    public StringProperty pgetRollNo() {
        return rollno;
    }

    public void psetRollNo(StringProperty rollno) {
        this.rollno = rollno;
    }

    public StringProperty pgetName() {
        return name;
    }

    public void psetName(StringProperty name) {
        this.name = name;
    }

    public StringProperty pgetAddress() {
        return address;
    }

    public void psetAddress(StringProperty address) {
        this.address = address;
    }

    public StringProperty pgetPhone() {
        return phone;
    }

    public void psetPhone(StringProperty phone) {
        this.phone = phone;
    }

    public StringProperty pgetEmail() {
        return email;
    }

    public void psetEmail(StringProperty email) {
        this.email = email;
    }

    public ObjectProperty pgetBirthDay() {
        return birthDay;
    }

    public void psetBirthDay(ObjectProperty birthDay) {
        this.birthDay = birthDay;
    }

    public StringProperty pgetUserId() {
        return userId;
    }

    public void psetUserId(StringProperty userId) {
        this.userId = userId;
    }

//    value
//    getter
    public String getRollNo() {
        return rollno.get();
    }

    public String getName() {
        return name.get();
    }

    public String getAddress() {
        return address.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public String getEmail() {
        return email.get();
    }

    public LocalDate getBirthDay() {
        return birthDay.get();
    }

    public String getUserId() {
        return userId.get();
    }

//    setter
    public void setRollNo(String rollno) {
        this.rollno.set(rollno);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay.set(birthDay);
    }

    public void setUserId(String userId) {
        this.userId.set(userId);
    }

    public static ObservableList<StudentModule> selectAll() {
        ObservableList<StudentModule> modules = FXCollections.observableArrayList();

        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM student")) {
            while (rs.next()) {
                java.sql.Date birthDay = (java.sql.Date) rs.getObject("birthday");
                StudentModule m = new StudentModule();
                m.setRollNo(rs.getString("rollno"));
                m.setName(rs.getString("name"));
                m.setAddress(rs.getString("address"));
                m.setPhone(rs.getString("phone"));
                m.setEmail(rs.getString("email"));
                m.setBirthDay(birthDay.toLocalDate());
                m.setUserId(rs.getString("userId"));
                modules.add(m);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        return modules;
    }
    
    public static boolean getDetailStudent(String rollno) throws SQLException{
        String query  = "SELECT * FROM student where rollno = ?"; 
        try(
            Connection conn = DbService.getConnection();
            PreparedStatement stmt
            = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, rollno);
            StudentModule teacher = new StudentModule();
            ResultSet rs = stmt.executeQuery(); 
            boolean check = false;
            while (rs.next()) { 
                check = true; 
            } 
            return check;
        } catch (Exception e) {
            System.err.println("Lỗi: "+ e.getMessage());
            return false;
        }
        
    }

    public static boolean insert(StudentModule newModule) throws SQLException {
        String sql = "INSERT into student (rollno, name, address, phone, email, birthday, userId) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        ResultSet key = null;
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, newModule.getRollNo());
            stmt.setString(2, newModule.getName());
            stmt.setString(3, newModule.getAddress());
            stmt.setString(4, newModule.getPhone());
            stmt.setString(5, newModule.getEmail());
            stmt.setDate(6, java.sql.Date.valueOf(newModule.getBirthDay()));
            stmt.setString(7, newModule.getUserId());
            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                return true;
            } else {
                System.err.println("No teachers is inserted");
                return false;
            }

        } catch (Exception e) {
            System.err.println(e);
            return false;
        } finally {
            if (key != null) {
                key.close();
            }
        }
    }


    public static boolean delete(StudentModule deleModule) {
        String sql = "DELETE FROM student WHERE teacherId = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, deleModule.getRollNo());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No information of the teachers is deleted");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

    public static boolean update(StudentModule updateModule) {
        String sql = "UPDATE student SET "
                + "name = ? ,"
                + "address = ? ,"
                + "phone = ? ,"
                + "email = ? ,"
                + "birthday = ? "
//                + "userId = ?"
                + "WHERE rollno = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, updateModule.getName());
            stmt.setString(2, updateModule.getAddress());
            stmt.setString(3, updateModule.getPhone());
            stmt.setString(4, updateModule.getEmail());
            stmt.setDate(5, java.sql.Date.valueOf(updateModule.getBirthDay()));
//            stmt.setString(6, updateModule.getUserId());
            stmt.setString(6, updateModule.getRollNo());
            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No information of the teachers is updated");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

}

