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
public class ScoreModule {
    
 private ObjectProperty<Integer> id;
  private ObjectProperty<Double> Score;
    private StringProperty ModuleId;
    private StringProperty StudentId;

    public ScoreModule() {
        this.id = new SimpleObjectProperty<>(null);
        this.Score = new SimpleObjectProperty<>(null);
        this.ModuleId = new SimpleStringProperty();
        this.StudentId = new SimpleStringProperty();;
    
    }
    public ObjectProperty<Integer> pgetScoreId() {
        return id;
    }

    public void psetScoreId(ObjectProperty<Integer> id) {
        this.id = id;
    }

    public ObjectProperty<Double> pgetScore() {
        return Score;
    }

    public void psetScore(ObjectProperty<Double> Score) {
        this.Score = Score;
    }

    public StringProperty pgetModuleId() {
        return ModuleId;
    }

    public void psetModuleId(StringProperty ModuleId) {
        this.ModuleId = ModuleId;
    }
    public StringProperty pgetStudentId() {
        return StudentId;
    }

    public void psetStudentId(StringProperty StudentId) {
        this.StudentId = StudentId;
    }

    

    public Integer getId() {
        return id.get();
    }

    

    public void setId(Integer id) {
        this.id.set(id);
    }
public Double getScore() {
        return Score.get();
    }
    public void setScore(Double name) {
        this.Score.set(name);
    }



    public void settModuleId(String id) {
        this.ModuleId.set(id);
    }
     public String gettModuleId() {
        return ModuleId.get();
    }

    public void setStudentId(String StudentId) {
        this.StudentId.set(StudentId);
    }
     public String getStudentId() {
        return StudentId.get();
    }
    


    public static ObservableList<ScoreModule> selectAll() {
        ObservableList<ScoreModule> modules = FXCollections.observableArrayList();

        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM score");) {
            while (rs.next()) {
                ScoreModule m = new ScoreModule();
                m.setId(rs.getInt("id"));
                m.setScore(rs.getDouble("score"));
                m.settModuleId(rs.getString("moduleid"));
                m.setStudentId(rs.getString("studentid"));

                modules.add(m);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        return modules;
    }

    public static ScoreModule insert(ScoreModule newModule) throws SQLException {
        String sql = "INSERT into score (score,moduleid,studentid) "
                + "VALUES (?,?,?)";
        ResultSet key = null;
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setDouble(1, newModule.getScore());
stmt.setString(2, newModule.gettModuleId());
            stmt.setString(3, newModule.getStudentId());
            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newModule.setId(newKey);
                return newModule;
            } else {
                System.err.println("No teachers is inserted");
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

    public static boolean delete(ScoreModule deleModule) {
        String sql = "DELETE FROM teachers WHERE teacherid = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, deleModule.getId());

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

    public static boolean update(ScoreModule updateModule) {
        String sql = "UPDATE score SET "
                + "score = ? ,"
                + "moduleid = ? ,"
                + "studentid = ? ,"
                + "WHERE id = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setDouble(1, updateModule.getScore());
             stmt.setString(2, updateModule.gettModuleId());
              stmt.setString(3, updateModule.getStudentId());
               stmt.setInt(4, updateModule.getId());

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