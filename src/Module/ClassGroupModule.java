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
 * @author admin
 */
public class ClassGroupModule {
     private StringProperty classNo;
    private StringProperty rollNo;

    public ClassGroupModule() {
          this.rollNo = new SimpleStringProperty();
        this.classNo = new SimpleStringProperty();
    }

    public String getClassNo() {
        return classNo.get();
    }

    public void setClassNo(String classNo) {
        this.classNo.set(classNo);
    }
     public String getRollNo() {
        return rollNo.get();
    }
    public void setRollNo(String rollNo) {
        this.rollNo.set(rollNo);
    }

    public StringProperty pgetClassNo() {
        return classNo;
    }

    public void psetClassNo(StringProperty classNo) {
        this.classNo = classNo;
    }

    public StringProperty pgetRollNo() {
        return rollNo;
    }

    public void psetRollNo(StringProperty rollNo) {
        this.rollNo = rollNo;
    }




    public static ObservableList<ClassGroupModule> selectAll() {
        ObservableList<ClassGroupModule> modules = FXCollections.observableArrayList();

        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM classgroup");) {
            while (rs.next()) {
                ClassGroupModule m = new ClassGroupModule();
                 m.setRollNo(rs.getString("rollNo"));
                m.setClassNo(rs.getString("classNo"));
                modules.add(m);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        return modules;
    }

    public static boolean insert(ClassGroupModule newModule) throws SQLException {
        String sql = "INSERT into classgroup (rollNo,classNo)"
                + "VALUES (?,?)";
        ResultSet key = null;
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, newModule.getRollNo());
            stmt.setString(2, newModule.getClassNo());
            int rowInserted = stmt.executeUpdate();

                if (rowInserted == 1) {
                return true;
            } else {
                System.err.println("No classgroup is inserted");
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

    public static boolean delete(ClassGroupModule deleModule) {
        String sql = "DELETE FROM classgroup WHERE classNo = ? AND rollNo = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, deleModule.getRollNo());
            stmt.setString(2, deleModule.getClassNo());

            int rowDeleted = stmt.executeUpdate();
            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No information of the classgroup is deleted");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

    public static boolean update(ClassGroupModule updateModule) {
        String sql = "UPDATE classgroup SET "
                + "rollNo = ? ,"
                + "classNo = ? ,"
                + "WHERE rollNo = ? AND classNo = ? ";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, updateModule.getRollNo());
            stmt.setString(2, updateModule.getClassNo());
            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No information of the classgroup is updated");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

   
}
