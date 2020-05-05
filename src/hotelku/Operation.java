/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelku;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Johan Sutrisno
 */
public class Operation {
    private DBC db;
    private String tableName;
    private JTable table;

    public Operation(DBC db, JTable table) {
        this.db = db;
        this.tableName = tableName;
    }
    
    //UPDATING TABLE prosedur
    public void updateTableRoom() throws SQLException{
        String sql = "SELECT * FROM `room` WHERE 1";
        ResultSet rs =  db.getData(sql);
        
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn(new Object[]{"Type","Price"});
        
        while(rs.next()){
            model.addRow(new Object[]{rs.getString(1),rs.getString(2)});
        }
   
        table.setModel(model);
    }
    
    
    //INSERTING DATA
    public void updateInsert(Object[] data){
        
        String sql = "SELECT * FROM `guest` WHERE id_guest='"+data[0].toString()+"'";
        String sql2 ="";
        
        ResultSet rs =  db.getData(sql);
        try {
            rs.next();
            if(rs.getString(1)!=null){
                sql2 = "UPDATE `guest` SET "
                        + "`id_guest`=[value-1],"
                        + "`id_card`=[value-2],`"
                        + "guest_name`=[value-3],`"
                        + "room_number`=[value-4],"
                        + "`duration`=[value-5],"
                        + "`id_room`=[value-6],"
                        + "`extra`=[value-7],"
                        + "`total`=[value-8] "
                        + "WHERE id_guest='XXX'";
            }else{
                sql2 ="INSERT INTO `guest`(`id_guest`, `id_card`, `guest_name`, `room_number`, `duration`, `id_room`, `extra`, `total`) "
                        + "VALUES ("
                        + "[value-1],"
                        + "[value-2],"
                        + "[value-3],"
                        + "[value-4],"
                        + "[value-5],"
                        + "[value-6],"
                        + "[value-7],"
                        + "[value-8";
            }
            
            db.query(sql2);
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
