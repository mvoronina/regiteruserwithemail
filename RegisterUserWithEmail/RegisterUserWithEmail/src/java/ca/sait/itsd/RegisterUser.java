/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.sait.itsd;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author john
 */
public class RegisterUser {
    
    
    public boolean registerNewUser(String username, String password, String email) {
        boolean result=false;
        
        Connection conn=null; 
        
        try {
            
            conn = getConnection();
            
            String sql="insert into users set username=?, password=?, email=?, uniqueConfirmID=?";
            
            PreparedStatement ps = conn.prepareCall(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setString(4, UUID.randomUUID().toString());
            
            int rowsAdded = ps.executeUpdate();
            
            result = (rowsAdded > 0);
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegisterUser.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        return result;
    }
    
    public String getConfirmIDforUsername(String username) {
        
        String id=null;
        
        Connection conn=null;
        
        try {
            conn=getConnection();
            
            String sql="select uniqueConfirmID from users where username=?";
            PreparedStatement ps = conn.prepareCall(sql);
            ps.setString(1, username);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                id = rs.getString(1);
            }
            
            rs.close();
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegisterUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
        return id;
        
    }
    
    public boolean confirmRegistration(String emailAddress, String confirmID) {
        boolean result=false;
        
        Connection conn=null;
        
        try {
            
            conn=getConnection();
            
            String sql = "update users set active=1, uniqueConfirmID='' where email=? and uniqueConfirmID=?";

            PreparedStatement ps = conn.prepareCall(sql);

            ps.setString(1, emailAddress);
            ps.setString(2, confirmID);

            int rowsChanged = ps.executeUpdate();

            //Row in DB was found and updated for this email address and confirmation ID
            if (rowsChanged>0) {
                result=true;
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(RegisterUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        return result;
    }
    
    
    private Connection getConnection() {
        
        Connection conn=null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/registerUsers","root","password");
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return conn;
    }
}
