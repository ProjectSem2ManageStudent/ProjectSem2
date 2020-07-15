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
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Admin
 */
public class UserModule {

    private StringProperty userId;
    private StringProperty firstname;
    private StringProperty lastname;
    private StringProperty email;
    private StringProperty password;
    private StringProperty role;

    
    public UserModule() {
        this.userId = new SimpleStringProperty();
        this.firstname = new SimpleStringProperty();
        this.lastname = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.role = new SimpleStringProperty();
    }

    // Property
    public StringProperty getUserId() {
        return userId;
    }

    public void setUserId(StringProperty userId) {
        this.userId = userId;
    }

    public StringProperty getFirstName() {
        return firstname;
    }

    public void setFirstName(StringProperty firstname) {
        this.firstname = firstname;
    }
    
    public StringProperty getLastName() {
        return lastname;
    }

    public void setLastName(StringProperty lastname) {
        this.lastname = lastname;
    }
    
     public StringProperty getEmail() {
        return email;
    }

    public void setEmail(StringProperty email) {
        this.email = email;
    }

    public StringProperty getPassword() {
        return password;
    }

    public void setPassword(StringProperty password) {
        this.password = password;
    }

    public StringProperty getRole() {
        return role;
    }

    public void setRole(StringProperty role) {
        this.role = role;
    }
    // End Property
    
    // Value
    public String getValueUserId() {
        return userId.get();
    }

    public void setValueUserId(String userid) {
        this.userId.set(userid);
    }

    public String getValueFirstName() {
        return firstname.get();
    }

    public void setValueFirstName(String firstName) {
        this.firstname.set(firstName);
    }
    
    public String getValueLastName() {
        return lastname.get();
    }

    public void setValueLastName(String lastname) {
        this.lastname.set(lastname);
    }
    
    public String getValueEmail() {
        return email.get();
    }

    public void setValueEmail(String email) {
        this.email.set(email);
    }

    public String getValuePassWord() {
        return password.get();
    }

    public void setValuePassWord(String password) {
        this.password.set(password);
    }

    public String getValueRole() {
        return role.get();
    }

    public void setValueRole(String role) {
        this.role.set(role);
    }
    // End value    

    public static ObservableList<UserModule> selectAll() {
        ObservableList<UserModule> users = FXCollections.observableArrayList();

        try (
                Connection conn = DbService.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM users");) {
            while (rs.next()) {
                UserModule m = new UserModule();
                m.setValueUserId(rs.getString("userId"));
                m.setValueFirstName(rs.getString("firstname"));
                m.setValueLastName(rs.getString("lastname"));
                m.setValueEmail(rs.getString("email"));
                m.setValuePassWord(rs.getString("password"));
                m.setValueRole(rs.getString("role"));

                users.add(m);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());

        }
        return users;
    }
    
    public static UserModule getDetailUser(String userId) throws SQLException{
        String query  = "SELECT * FROM users where userId = ?"; 
        try(
            Connection conn = DbService.getConnection();
            PreparedStatement stmt
            = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, userId);
            UserModule users = new UserModule();
            ResultSet rs = stmt.executeQuery(); 
            boolean check = false;
            while (rs.next()) { 
                UserModule m = new UserModule();
                m.setValueUserId(rs.getString("userId"));
                m.setValueFirstName(rs.getString("firstname"));
                m.setValueLastName(rs.getString("lastname"));
                m.setValueEmail(rs.getString("email"));
                m.setValuePassWord(rs.getString("password"));
                m.setValueRole(rs.getString("role"));

                users = m;
//                user.firstname = teacher; 
            } 
            return users;
        } catch (Exception e) {
            System.err.println("Lỗi: "+ e.getMessage());
//            return false;
            return null;        }
        
    }
    
    public static boolean checkEmailExi(String email) throws SQLException{
        String query  = "SELECT * FROM users where email = ?"; 
        try(
            Connection conn = DbService.getConnection();
            PreparedStatement stmt
            = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setString(1, email);
            TeachersModule teacher = new TeachersModule();
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

    public static boolean insert(UserModule newUser) throws SQLException {
        String sql = "INSERT into users (userId, firstname, lastname, email, password, role) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        ResultSet key = null;
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt
                = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, newUser.getValueUserId());
            stmt.setString(2, newUser.getValueFirstName());
            stmt.setString(3, newUser.getValueLastName());
            stmt.setString(4, newUser.getValueEmail());
            stmt.setString(5, BCrypt.hashpw(newUser.getValuePassWord(), BCrypt.gensalt(12)));
            stmt.setString(6, newUser.getValueRole());
            int rowInserted = stmt.executeUpdate();

            if (rowInserted == 1) {
                return true;
            } else {
                System.err.println("No user is inserted");
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
    
    public static boolean delete(UserModule deleteUser) {
        String sql = "DELETE FROM users WHERE userId = ? AND email = ?";
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, deleteUser.getValueUserId());
            stmt.setString(1, deleteUser.getValueEmail());

            int rowDeleted = stmt.executeUpdate();

            if (rowDeleted == 1) {
                return true;
            } else {
                System.err.println("No information of the user is deleted");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }
    
    public static boolean update(UserModule updateUser) {
        String sql = "UPDATE users SET "
                + "firstname = ? ,"
                + "lastname = ? ,"
                + "email = ? ,"
                + "password = ? ,"
                + "role = ? "
                + "WHERE userId = ?" ;
                
        try (
                Connection conn = DbService.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, updateUser.getValueFirstName());
            stmt.setString(2, updateUser.getValueLastName());
            stmt.setString(3, updateUser.getValueEmail());
            stmt.setString(4, updateUser.getValuePassWord());
            stmt.setString(5, updateUser.getValueRole());
            stmt.setString(6, updateUser.getValueUserId());

            int rowUpdated = stmt.executeUpdate();

            if (rowUpdated == 1) {
                return true;
            } else {
                System.err.println("No information of the user is updated");
                return false;
            }

        } catch (Exception e) {
//            System.err.println(e);
            return false;
        }
    }
    @Override
    public String toString() {
        return firstname.get() + " " + lastname.get();
    }
    
    @Override
    public boolean equals(Object object) {
        String otherTCountryCode = "";
        if (object instanceof UserModule) {
            otherTCountryCode = ((UserModule)object).getValueFirstName() + " " + ((UserModule)object).getValueLastName();
        } else if(object instanceof String){
            otherTCountryCode = (String)object;
        } else {
            return false;
        }
        if (((this.firstname.get() +" "+ this.lastname.get()) == null && otherTCountryCode != null) || ((this.firstname.get() +" "+ this.lastname.get()) != null && !(this.firstname.get() +" "+ this.lastname.get()).equals(otherTCountryCode))) {
            return false;
        }
        return true;  
    }
}
