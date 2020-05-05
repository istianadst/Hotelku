/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelku;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Johan Sutrisno
 */
public class CheckIn extends javax.swing.JFrame {

    /**
     * Creates new form HotelNgapak
     */
    
    private DBC db;
    public CheckIn() throws SQLException {
        initComponents();
        db = new DBC();
        updateTableRoom();
        updateTableExtra();
        updateTableGuest();
    }
    
    public Object[] getGuestInput() throws SQLException{
        Object[] x = new Object[]{
            edIDCard.getText(),
            edGuestName.getText(),
            edRoomNumber.getText(),
            edDuration.getText(),
            cbRoom.getSelectedIndex()+1,
            calcExtra(),
            calcTotal()
        };
        return x;
    }
    
    public void delete() throws SQLException{
        String sql = "DELETE FROM `guest` WHERE id_guest="+tbGuest.getValueAt(tbGuest.getSelectedRow(),0)+"";
        db.query(sql);
        updateTableGuest();
    }
    
    public void updateInsert() throws SQLException{

        Object[] data = getGuestInput();
                
        String sql = "SELECT * FROM `guest` WHERE id_guest='"+data[0].toString()+"'";
        String sql2 ="";
        
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//        Date date = new Date();
//        String currentDate = dtf.format(now);
        
        ResultSet rs =  db.getData(sql);
        try {
            if(rs.next()){
                sql2 = "UPDATE `guest` SET "
                        + "`id_guest`="+data[0]+","
                        + "`id_card`='"+data[1]+"',`"
                        + "guest_name`='"+data[2]+"',`"
                        + "room_number`='"+data[3]+"',"
                        + "`duration`="+data[4]+","
                        + "`id_room`="+data[5]+","
                        + "`extra`="+data[6]+","
                        + "`total`="+data[7]+","
                        + "`isCheckOut`="+data[8]+","
                        + "`tgl_check_in`="+data[9]+","
                        + "`tgl_check_out`="+data[10]+" "
                        + "WHERE id_guest="+data[0]+"";
            }else{
                sql2 ="INSERT INTO `guest`(`id_card`, `guest_name`, `room_number`, `duration`, `id_room`, `extra`, `total`, `isCheckOut`, `tgl_check_in`, `tgl_check_out`)"
                        + "VALUES ("
                        + data[0]+","
                        + "'"+data[1]+"',"
                        + "'"+data[2]+"',"
                        + "'"+data[3]+"',"
                        + data[4]+","
                        + data[5]+","
                        + data[6]+","
                        + "0"+","
                        + "NOW()"+","
                        + "NOW()" +")";
            }
            
            db.query(sql2);
            
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    ArrayList<Integer> roomPrices = new ArrayList<Integer>();
    
    public int calcTotal() throws SQLException{
        
        int roomTotal = Integer.parseInt(edDuration.getText()) * roomPrices.get(cbRoom.getSelectedIndex());
        
        int total = roomTotal + calcExtra();
        
        return total;
    }
    
    public int calcExtra() throws SQLException{
        String sql = "SELECT * FROM `extra` WHERE 1";
        ResultSet rs =  db.getData(sql);
        
        rs.next();
        int bfPrice =Integer.parseInt(rs.getString(3));
        rs.next();
        int ebPrice =Integer.parseInt(rs.getString(3));
        int extra = 0;
        
        if(cBreakfast.isSelected())extra += bfPrice* Integer.parseInt(edDuration.getText());
        if(cExtraBed.isSelected())extra += ebPrice;
        
        return extra;
    }    
    
    //*****************UPDATING ALL TABLE**************************************
    //-------------------------------------------------------------------------
    public void updateTableRoom() throws SQLException{
        String sql = "SELECT * FROM `room` WHERE 1";
        ResultSet rs =  db.getData(sql);
        
        DefaultTableModel model = new DefaultTableModel();
        DefaultComboBoxModel model2 = new DefaultComboBoxModel();
        
        model.addColumn("Type");
        model.addColumn("Price");
        
        boolean next = rs.next();
        while(next){
            model.addRow(new Object[]{rs.getString(2),rs.getString(3)});
            model2.addElement(rs.getString(2));
            roomPrices.add(Integer.parseInt(rs.getString(3)));
            next = rs.next();
        }
   
        tbRoom.setModel(model);
        cbRoom.setModel(model2);
    }
    
    public void updateTableExtra() throws SQLException{
        String sql = "SELECT * FROM `extra` WHERE 1";
        ResultSet rs =  db.getData(sql);
        
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn("Item");
        model.addColumn("Price");
        
        boolean next = rs.next();
        while(next){
            model.addRow(new Object[]{rs.getString(2),rs.getString(3)});
            next = rs.next();
        }
   
        tbExtra.setModel(model);
    }
    
    public void updateTableGuest() throws SQLException{
        String sql = "SELECT * FROM `guest` WHERE isCheckOut=0";
        ResultSet rs =  db.getData(sql);
        
        DefaultTableModel model = new DefaultTableModel();
        
        model.addColumn("Guest ID");
        model.addColumn("ID Card");
        model.addColumn("Guest Name");
        model.addColumn("Room Number");
        model.addColumn("Duration");
        model.addColumn("Room Type");
        model.addColumn("Extra");
        model.addColumn("Total");
        
        boolean next = rs.next();
        while(next){
            model.addRow(new Object[]{rs.getString(1),rs.getString(2),
                rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)
            ,rs.getString(7),rs.getString(8)});
            next = rs.next();
        }
   
        tbGuest.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        edRoomNumber = new javax.swing.JTextField();
        edDuration = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cBreakfast = new javax.swing.JCheckBox();
        cExtraBed = new javax.swing.JCheckBox();
        cbRoom = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        btCalculate = new javax.swing.JButton();
        edTotal = new javax.swing.JTextField();
        btUpdate = new javax.swing.JButton();
        btDelete = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbRoom = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbExtra = new javax.swing.JTable();
        btReset = new javax.swing.JButton();
        btMainMenu = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        edIDCard = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        edGuestName = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbGuest = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        btPembayaran = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        edPembayaran = new javax.swing.JTextField();
        edKembalian = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));

        jPanel2.setBackground(new java.awt.Color(102, 0, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data tamu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel2.setForeground(new java.awt.Color(153, 204, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 204, 255));
        jLabel7.setText("Nomor Kamar");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 204, 255));
        jLabel8.setText("Durasi");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 204, 255));
        jLabel9.setText("Extra");

        edRoomNumber.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        edRoomNumber.setForeground(new java.awt.Color(102, 0, 102));
        edRoomNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edRoomNumberActionPerformed(evt);
            }
        });

        edDuration.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        edDuration.setForeground(new java.awt.Color(102, 0, 102));
        edDuration.setText("1");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 204, 255));
        jLabel4.setText("malam");

        cBreakfast.setBackground(new java.awt.Color(153, 204, 255));
        cBreakfast.setForeground(new java.awt.Color(153, 204, 255));
        cBreakfast.setText("Hall");
        cBreakfast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cBreakfastActionPerformed(evt);
            }
        });

        cExtraBed.setBackground(new java.awt.Color(153, 204, 255));
        cExtraBed.setForeground(new java.awt.Color(153, 204, 255));
        cExtraBed.setText("Swiming pool");
        cExtraBed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cExtraBedActionPerformed(evt);
            }
        });

        cbRoom.setBackground(new java.awt.Color(153, 204, 255));
        cbRoom.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cbRoom.setForeground(new java.awt.Color(102, 0, 102));
        cbRoom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 204, 255));
        jLabel10.setText("Type");

        btCalculate.setBackground(new java.awt.Color(153, 204, 255));
        btCalculate.setForeground(new java.awt.Color(102, 0, 102));
        btCalculate.setText("Hitung Total");
        btCalculate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btCalculateMouseClicked(evt);
            }
        });
        btCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCalculateActionPerformed(evt);
            }
        });

        edTotal.setForeground(new java.awt.Color(102, 0, 102));
        edTotal.setText("Price");
        edTotal.setFocusable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(edDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4))
                            .addComponent(edRoomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cBreakfast)
                                .addGap(18, 18, 18)
                                .addComponent(cExtraBed))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btCalculate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbRoom)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edRoomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cExtraBed)
                    .addComponent(cBreakfast)
                    .addComponent(jLabel9))
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCalculate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(edTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btUpdate.setBackground(new java.awt.Color(102, 0, 102));
        btUpdate.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btUpdate.setForeground(new java.awt.Color(153, 204, 255));
        btUpdate.setText("Check In");
        btUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btUpdateMouseClicked(evt);
            }
        });

        btDelete.setBackground(new java.awt.Color(102, 0, 102));
        btDelete.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btDelete.setForeground(new java.awt.Color(153, 204, 255));
        btDelete.setText("Delete");
        btDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btDeleteMouseClicked(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(102, 0, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi Harga", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(153, 204, 255));

        tbRoom.setForeground(new java.awt.Color(102, 0, 102));
        tbRoom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Type", "Price"
            }
        ));
        jScrollPane1.setViewportView(tbRoom);

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 204, 255));
        jLabel2.setText("Extra");

        tbExtra.setForeground(new java.awt.Color(102, 0, 102));
        tbExtra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item", "Price"
            }
        ));
        jScrollPane2.setViewportView(tbExtra);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btReset.setBackground(new java.awt.Color(102, 0, 102));
        btReset.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btReset.setForeground(new java.awt.Color(153, 204, 255));
        btReset.setText("Reset");
        btReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btResetMouseClicked(evt);
            }
        });

        btMainMenu.setBackground(new java.awt.Color(102, 0, 102));
        btMainMenu.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btMainMenu.setForeground(new java.awt.Color(153, 204, 255));
        btMainMenu.setText("Main Menu");
        btMainMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btMainMenuMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 0, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data tamu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(153, 204, 255))); // NOI18N
        jPanel1.setForeground(new java.awt.Color(153, 204, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 204, 255));
        jLabel5.setText("ID Card ");

        edIDCard.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        edIDCard.setForeground(new java.awt.Color(102, 0, 102));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 204, 255));
        jLabel6.setText("Nama");

        edGuestName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        edGuestName.setForeground(new java.awt.Color(102, 0, 102));
        edGuestName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edGuestNameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(edIDCard, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 75, Short.MAX_VALUE))
                    .addComponent(edGuestName))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edIDCard, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(edGuestName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tbGuest.setBackground(new java.awt.Color(153, 204, 255));
        tbGuest.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tbGuest.setForeground(new java.awt.Color(102, 0, 102));
        tbGuest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Guest ID", "ID Card", "Guest Name", "Room Number", "Duration", "Room Type", "Extra", "Total"
            }
        ));
        jScrollPane3.setViewportView(tbGuest);

        jPanel4.setBackground(new java.awt.Color(102, 0, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pembayaran", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(153, 204, 255))); // NOI18N

        btPembayaran.setBackground(new java.awt.Color(153, 204, 255));
        btPembayaran.setForeground(new java.awt.Color(102, 0, 102));
        btPembayaran.setText("Bayar");
        btPembayaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btPembayaranMouseClicked(evt);
            }
        });
        btPembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPembayaranActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 204, 255));
        jLabel1.setText("Jumlah Uang");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 204, 255));
        jLabel11.setText("Kembalian");

        edPembayaran.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        edPembayaran.setForeground(new java.awt.Color(153, 0, 153));
        edPembayaran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edPembayaranActionPerformed(evt);
            }
        });

        edKembalian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        edKembalian.setForeground(new java.awt.Color(153, 0, 153));
        edKembalian.setText("Kembalian");
        edKembalian.setFocusable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(edKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(edPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(edPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(edKembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btPembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addComponent(btUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btReset, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(368, 368, 368))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(btMainMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btReset, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void edGuestNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edGuestNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edGuestNameActionPerformed

    private void edRoomNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edRoomNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edRoomNumberActionPerformed

    private void btCalculateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btCalculateMouseClicked
        try {
            // TODO add your handling code here:
            edTotal.setText(calcTotal()+"");
        } catch (SQLException ex) {
            Logger.getLogger(CheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btCalculateMouseClicked

    private void btUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btUpdateMouseClicked
        try {
            // TODO add your handling code here:
            updateInsert();
            updateTableGuest();
            JOptionPane.showMessageDialog(rootPane, "Terimakasih anda sudah melakukan Check In");
        } catch (SQLException ex) {
            Logger.getLogger(CheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btUpdateMouseClicked

    private void btDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btDeleteMouseClicked
        try {
            // TODO add your handling code here:
            delete();
        } catch (SQLException ex) {
            Logger.getLogger(CheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btDeleteMouseClicked

    private void cBreakfastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cBreakfastActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cBreakfastActionPerformed

    private void cExtraBedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cExtraBedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cExtraBedActionPerformed

    private void btPembayaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btPembayaranMouseClicked
        try {
            // TODO add your handling code here:
            int pembayaran = Integer.parseInt(edPembayaran.getText());
            int kembalian = pembayaran-calcTotal();
            
            edKembalian.setText(Integer.toString(kembalian));
        } catch (SQLException ex) {
            Logger.getLogger(CheckIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btPembayaranMouseClicked

    private void btPembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPembayaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btPembayaranActionPerformed

    private void btCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCalculateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btCalculateActionPerformed

    private void edPembayaranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edPembayaranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edPembayaranActionPerformed

    private void btMainMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btMainMenuMouseClicked
        // TODO add your handling code here:
        new MainMenu().setVisible(true);
        this.hide();
    }//GEN-LAST:event_btMainMenuMouseClicked

    private void btResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btResetMouseClicked
        // TODO add your handling code here:
        edIDCard.setText(" ");
        edGuestName.setText(" ");
        edRoomNumber.setText(" ");
        edDuration.setText(" ");
        edTotal.setText(" ");
    }//GEN-LAST:event_btResetMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CheckIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CheckIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CheckIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CheckIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new CheckIn().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(CheckIn.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCalculate;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btMainMenu;
    private javax.swing.JButton btPembayaran;
    private javax.swing.JButton btReset;
    private javax.swing.JButton btUpdate;
    private javax.swing.JCheckBox cBreakfast;
    private javax.swing.JCheckBox cExtraBed;
    private javax.swing.JComboBox cbRoom;
    private javax.swing.JTextField edDuration;
    private javax.swing.JTextField edGuestName;
    private javax.swing.JTextField edIDCard;
    private javax.swing.JTextField edKembalian;
    private javax.swing.JTextField edPembayaran;
    private javax.swing.JTextField edRoomNumber;
    private javax.swing.JTextField edTotal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tbExtra;
    private javax.swing.JTable tbGuest;
    private javax.swing.JTable tbRoom;
    // End of variables declaration//GEN-END:variables
}
