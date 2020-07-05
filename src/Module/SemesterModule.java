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
public class SemesterModule {
  private ObjectProperty<Integer> id;
    private StringProperty name;


    public SemesterModule() {
        id = new SimpleObjectProperty<>(null);
        name = new SimpleStringProperty();
    }

    public Integer getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObjectProperty<Integer> getIdProperty() {
        return this.id;
    }

    public StringProperty getNameProperty() {
        return this.name;
    }


    public static ObservableList<SemesterModule> selectAll() {
        ObservableList<SemesterModule> modules = FXCollections.observableArrayList();

        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM semester");) {
            while (rs.next()) {
                SemesterModule m = new SemesterModule();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));

                modules.add(m);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        return modules;
    }

    public static SemesterModule insert(SemesterModule newModule) throws SQLException {
        String sql = "INSERT into semester (name) "
                + "VALUES (?)";
        ResultSet key = null;
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, newModule.getName());

            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                key = stmt.getGeneratedKeys();
                key.next();
                int newKey = key.getInt(1);
                newModule.setId(newKey);
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

    public static boolean delete(SemesterModule deleModule) {
        String sql = "DELETE FROM semester WHERE id = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, deleModule.getId());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No information of the semester is deleted");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

    public static boolean update(SemesterModule updateModule) {
        String sql = "UPDATE semester SET "
                + "name = ? "
                + "WHERE id = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, updateModule.getName());
            stmt.setInt(2, updateModule.getId());

            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No information of the semester is updated");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }

}
