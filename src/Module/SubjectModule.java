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
public class SubjectModule {
 private StringProperty subjectId;
    private StringProperty subjectname;
    private ObjectProperty<Integer> duration;
    private StringProperty prerequisite;
    private ObjectProperty<Integer> semesterId;

    public SubjectModule() {
        this.subjectId = new SimpleStringProperty();;
        this.subjectname = new SimpleStringProperty();;
        this.duration = new SimpleObjectProperty<>(null);;
        this.prerequisite = new SimpleStringProperty();;
        this.semesterId = new SimpleObjectProperty<>(null);;
    }

    public ObjectProperty<Integer> getDuration() {
        return duration;
    }

    public void setDuration(ObjectProperty<Integer> duration) {
        this.duration = duration;
    }

    public StringProperty getPrerequisite() {
        return prerequisite;
    }

    public void setPrerequisite(StringProperty prerequisite) {
        this.prerequisite = prerequisite;
    }

    public ObjectProperty<Integer> getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(ObjectProperty<Integer> semesterId) {
        this.semesterId = semesterId;
    }
    
    

    public String getId() {
        return subjectId.get();
    }

    public String getName() {
        return subjectname.get();
    }

    public void setId(String id) {
        this.subjectId.set(id);
    }

    public void setName(String name) {
        this.subjectname.set(name);
    }

     public Integer getduration() {
        return duration.get();
    }

    public void setduration(int id) {
        this.duration.set(id);
    }
     public String getpre() {
        return prerequisite.get();
    }

    public void setpre(String id) {
        this.prerequisite.set(id);
    }
     public Integer getsemes() {
        return semesterId.get();
    }

    public void setsemes(int id) {
        this.semesterId.set(id);
    }
    public StringProperty getIdProperty() {
        return this.subjectId;
    }

    public StringProperty getNameProperty() {
        return this.subjectname;
    }


    public static ObservableList<SubjectModule> selectAll() {
        ObservableList<SubjectModule> modules = FXCollections.observableArrayList();

        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM subject");) {
            while (rs.next()) {
                SubjectModule m = new SubjectModule();
                m.setId(rs.getString("subjectid"));
                m.setName(rs.getString("subjectname"));
                m.setduration(rs.getInt("duration"));
                m.setpre(rs.getString("prerequisite"));
                m.setsemes(rs.getInt("semesterid"));

                modules.add(m);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        return modules;
    }

    public static SubjectModule insert(SubjectModule newModule) throws SQLException {
        String sql = "INSERT into subject (subjectid,subjectname,duration,prerequisite,semesterid) "
                + "VALUES (?,?,?,?,?)";
        ResultSet key = null;
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, newModule.getId());
            stmt.setString(1, newModule.getName());
            stmt.setInt(2, newModule.getduration());
            stmt.setString(3, newModule.getpre());
             stmt.setInt(1, newModule.getsemes());
            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                return newModule;
            } else {
                System.err.println("No semester is inserted");
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

    public static boolean delete(SubjectModule deleModule) {
        String sql = "DELETE FROM subject WHERE subjectid = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, deleModule.getId());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No information of the subject is deleted");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

    public static boolean update(SubjectModule updateModule) {
        String sql = "UPDATE subject SET "
                + "subjectname = ? ,"
                + "duration = ? ,"
                + "prerequisite = ? ,"
                + "semesterid = ? "
                + "WHERE subjectid = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, updateModule.getName());
             stmt.setInt(2, updateModule.getduration());
              stmt.setString(3, updateModule.getpre());
               stmt.setInt(4, updateModule.getsemes());
            stmt.setString(5, updateModule.getId());

            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No information of the subject is updated");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

}
