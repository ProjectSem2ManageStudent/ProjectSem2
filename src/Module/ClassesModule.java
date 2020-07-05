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
public class ClassesModule {
    
 private StringProperty ClassesId;
    private StringProperty Classesname;
    private StringProperty TeacherId;

    public ClassesModule() {
        this.ClassesId = new SimpleStringProperty();;
        this.Classesname = new SimpleStringProperty();;
        this.TeacherId = new SimpleStringProperty();;
    }

    public StringProperty pgetClassesId() {
        return ClassesId;
    }

    public void psetClassesId(StringProperty ClassesId) {
        this.ClassesId = ClassesId;
    }

    public StringProperty pgetClassesname() {
        return Classesname;
    }

    public void psetClassesname(StringProperty Classesname) {
        this.Classesname = Classesname;
    }

    public StringProperty pgetTeacherId() {
        return TeacherId;
    }

    public void psetTeacherId(StringProperty TeacherId) {
        this.TeacherId = TeacherId;
    }
    
    

    public String getId() {
        return ClassesId.get();
    }

   

    public void setId(String id) {
        this.ClassesId.set(id);
    }
 public String getName() {
        return Classesname.get();
    }
    public void setName(String name) {
        this.Classesname.set(name);
    }

     public String getTeacherId() {
        return TeacherId.get();
    }

    public void setTeacherId(String id) {
        this.TeacherId.set(id);
    }
    


    public static ObservableList<ClassesModule> selectAll() {
        ObservableList<ClassesModule> modules = FXCollections.observableArrayList();

        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM classes");) {
            while (rs.next()) {
                ClassesModule m = new ClassesModule();
                m.setId(rs.getString("classesid"));
                m.setName(rs.getString("classesname"));
                m.setTeacherId(rs.getString("teacherid"));
                modules.add(m);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        return modules;
    }

    public static ClassesModule insert(ClassesModule newModule) throws SQLException {
        String sql = "INSERT into classes (classesid,classesname,teacherid) "
                + "VALUES (?,?,?,?,?)";
        ResultSet key = null;
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, newModule.getId());
            stmt.setString(2, newModule.getName());
            stmt.setString(3, newModule.getTeacherId());
            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                return newModule;
            } else {
                System.err.println("No classes is inserted");
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

    public static boolean delete(ClassesModule deleModule) {
        String sql = "DELETE FROM classes WHERE classesid = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, deleModule.getId());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No information of the classes is deleted");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

    public static boolean update(ClassesModule updateModule) {
        String sql = "UPDATE classes SET "
                + "classesname = ? ,"
                + "teacherid = ? "
                + "WHERE classesid = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, updateModule.getName());
             stmt.setString(2, updateModule.getTeacherId());
            stmt.setString(3, updateModule.getId());

            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No information of the classes is updated");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

}

