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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Admin
 */
public class StudentModule {
    
private StringProperty StudentId;
    private StringProperty LastName;
    private StringProperty FirstName;
    private StringProperty Phone;
    private StringProperty Email;
    private StringProperty BirthDate;
    private StringProperty PassWord;

    public StudentModule() {
        this.StudentId = new SimpleStringProperty();;
        this.LastName = new SimpleStringProperty();;
        this.FirstName = new SimpleStringProperty();
        this.Phone = new SimpleStringProperty();;
        this.Email = new SimpleStringProperty();
        this.BirthDate = new SimpleStringProperty();
    
    }
    public StringProperty pgetStudentId() {
        return StudentId;
    }

    public void psetStudentId(StringProperty StudentId) {
        this.StudentId = StudentId;
    }

    public StringProperty pgetLastName() {
        return LastName;
    }

    public void psetLastName(StringProperty LastName) {
        this.LastName = LastName;
    }

    public StringProperty pgetFirstName() {
        return FirstName;
    }

    public void psetFirstName(StringProperty FirstName) {
        this.FirstName = FirstName;
    }
    public StringProperty pgetPhone() {
        return Phone;
    }

    public void psetPhone(StringProperty Phone) {
        this.Phone = Phone;
    }
    public StringProperty pgetEmail() {
        return Email;
    }

    public void psetEmail(StringProperty Email) {
        this.Email = Email;
    }
    public StringProperty pgetBirthDate() {
        return BirthDate;
    }

    public void psetBirthDate(StringProperty BirthDate) {
        this.BirthDate = BirthDate;
    }

     public StringProperty pgetPassWord() {
        return BirthDate;
    }

    public void psetPassWord(StringProperty PassWord) {
        this.PassWord = PassWord;
    }

    public String getId() {
        return StudentId.get();
    }

    public String getLastName() {
        return LastName.get();
    }

    public void setId(String id) {
        this.StudentId.set(id);
    }

    public void setLastName(String name) {
        this.LastName.set(name);
    }

     public String getFirstName() {
        return FirstName.get();
    }

    public void setFirstName(String id) {
        this.FirstName.set(id);
    }
     public String getPhone() {
        return Phone.get();
    }

    public void setPhone(String id) {
        this.Phone.set(id);
    }
     public String getEmail() {
        return Email.get();
    }

    public void setEmail(String id) {
        this.Email.set(id);
    }
      public String getBirthDate() {
        return BirthDate.get();
    }

    public void setBirthDate(String id) {
        this.BirthDate.set(id);
    }
      public String getPassWord() {
        return PassWord.get();
    }

    public void setPassWord(String PassWord) {
        this.PassWord.set(PassWord);
    }
    


    public static ObservableList<StudentModule> selectAll() {
        ObservableList<StudentModule> modules = FXCollections.observableArrayList();

        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM students");) {
            while (rs.next()) {
                StudentModule m = new StudentModule();
                m.setId(rs.getString("studentid"));
                m.setLastName(rs.getString("lastname"));
                m.setFirstName(rs.getString("firstname"));
                m.setPhone(rs.getString("phone"));
                m.setEmail(rs.getString("email"));
                m.setBirthDate(rs.getString("birthdate"));

                modules.add(m);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        return modules;
    }

    public static StudentModule insert(StudentModule newModule) throws SQLException {
        String sql = "INSERT into students (studentid,lastname,firstname,phone,email,birthdate,123456789) "
                + "VALUES (?,?,?,?,?,?,?)";
        ResultSet key = null;
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

           stmt.setString(1, newModule.getId());
            stmt.setString(2, newModule.getLastName());
            stmt.setString(3, newModule.getFirstName());
            stmt.setString(4, newModule.getPhone());
             stmt.setString(5, newModule.getEmail());
              stmt.setString(6, newModule.getBirthDate());
stmt.setString(7, newModule.getPassWord());
            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                return newModule;
            } else {
                System.err.println("No students is inserted");
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

    public static boolean delete(StudentModule deleModule) {
        String sql = "DELETE FROM students WHERE studentid = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, deleModule.getId());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No information of the students is deleted");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

    public static boolean update(StudentModule updateModule) {
        String sql = "UPDATE students SET "
                + "lastname = ? ,"
                + "firstname = ? ,"
                + "phone = ? ,"
                + "email = ? ,"
                + "birthdate = ? "
                + "WHERE studentid = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, updateModule.getLastName());
             stmt.setString(2, updateModule.getFirstName());
              stmt.setString(3, updateModule.getPhone());
               stmt.setString(4, updateModule.getEmail());
               stmt.setString(5, updateModule.getBirthDate());
            stmt.setString(6, updateModule.getId());

            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No information of the students is updated");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

}

