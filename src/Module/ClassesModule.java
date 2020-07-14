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
public class ClassesModule {

    private StringProperty classNo;
    private StringProperty classesName;

    public ClassesModule() {
        this.classNo = new SimpleStringProperty();
        this.classesName = new SimpleStringProperty();
    }

    public StringProperty pgetClassNo() {
        return classNo;
    }
    public StringProperty psetClassNo(StringProperty classNo) {
        return this.classNo = classNo;
    }

    public StringProperty pgetClassesname() {
        return classesName;
    }
    
    public StringProperty psetClassesname(StringProperty className) {
        return this.classesName = className;
    }

    public String getClassNo() {
        return classNo.get();
    }

    public void setClassNo(String classNo) {
        this.classNo.set(classNo);
    }

    public String getClassName() {
        return classesName.get();
    }

    public void setClassName(String className) {
        this.classesName.set(className);
    }

    public static ObservableList<ClassesModule> selectAll() {
        ObservableList<ClassesModule> modules = FXCollections.observableArrayList();

        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM class");) {
            while (rs.next()) {
                ClassesModule m = new ClassesModule();
                m.setClassNo(rs.getString("classNo"));
                m.setClassName(rs.getString("className"));
                modules.add(m);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        return modules;
    }

    public static ClassesModule insert(ClassesModule newModule) throws SQLException {
        String sql = "INSERT into class (classNo, className) "
                + "VALUES (?,?)";
        ResultSet key = null;
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(2, newModule.getClassNo());
            stmt.setString(3, newModule.getClassName());
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
        String sql = "DELETE FROM class WHERE classNo = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, deleModule.getClassNo());

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
                + "className = ? "
                + "WHERE classNo = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, updateModule.getClassName());
            stmt.setString(3, updateModule.getClassNo());

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
