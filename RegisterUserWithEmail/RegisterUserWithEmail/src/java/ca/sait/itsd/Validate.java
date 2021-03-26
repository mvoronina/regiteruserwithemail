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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author john
 */
public class Validate {
    
    
        public boolean validateLogin(String username, String password) {
            boolean result=false;
            
            Connection conn=null;
            
            try {

                conn=getConnection();
                
                String sql="select * from users where username=? and password=? and active=1;";
                PreparedStatement ps = conn.prepareCall(sql);
                ps.setString(1,username);
                ps.setString(2,password);
                
                ResultSet rs = ps.executeQuery();
                
                int count=0;
                while (rs.next()) {
                    count++;
                }
                
                //Row was found and returned containing the provided username and password, good login attempt
                if (count>0) {
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
                    Logger.getLogger(Validate.class.getName()).log(Level.SEVERE, null, ex);
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
