/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelku;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author 
 */
public class DBC {
    private String dbUrl = "jdbc:mysql://localhost/hotelku";
    private String dbUser = "root";
    private String dbPassword = "";
    
    Connection conn;
    Statement st;
    ResultSet rs;
    
    public DBC() {
        try{
            conn = (Connection) DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            st = (Statement) conn.createStatement();
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    //fungsi
    public ResultSet getData(String SQLString){
	try {
            rs = st.executeQuery(SQLString);
	}catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
        }
	return rs;
    }
    
    public boolean query(String SQLString){
	try {
            st.executeUpdate(SQLString);
	}catch (Exception e) {
            JOptionPane.showMessageDialog(null,e);
            return false;
        }
	return true;
    }
}
