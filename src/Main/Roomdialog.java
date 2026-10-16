package Main;

import javax.swing.*;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.HierarchyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class Roomdialog extends JDialog {
	//protected static final String txoccupied = null;
	Hhelper hh = new Hhelper();
	Grlib gr = new Grlib();
	ResultSet rs;
	Connection con = null;
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	 JLabel lbname, lbcapacity, lboccupied, lbheader; 
     JTextField txroomname, txcapacity, txoccupied;	
     JButton btnadd, btnclose;
	JPanel mpanel, fpanel;
	
    public Roomdialog(Frame parent, String title, String proomid) {      
        super(parent, title, true);
        setSize(430, 380);
        setLayout(null);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(new Color(0, 128, 128));     
       
        fpanel = new JPanel(null);
        fpanel.setBounds(15,50, 380,270);
        fpanel.setBackground(hh.vvvkek);
        add(fpanel);
        
        mpanel = new JPanel(null);
        mpanel.setBounds(20,10, 340,250);
        mpanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
        mpanel.setBackground(hh.vvvkek);     
        fpanel.add(mpanel);        
        
            lbheader = new JLabel("Room Occupying");
      		lbheader.setForeground(new Color(255, 255, 255));
      		lbheader.setFont(new Font("tahoma", Font.BOLD, 20));
      		lbheader.setBounds(20, 10, 200, 35);
      		add(lbheader);
      
        lbname = hh.clabel("Room name");
		lbname.setBounds(10, 20, 110, 25);		
		mpanel.add(lbname);
		
		txroomname = hh.cTextField(250);
		txroomname.setBounds(130, 20, 200, 25);
		txroomname.setEditable(false);
		mpanel.add(txroomname);
		
		lbcapacity = hh.clabel("Capacity");
		lbcapacity.setBounds(10, 60, 110, 25);
		mpanel.add(lbcapacity);

		txcapacity = hh.cTextField(150);
		txcapacity.setBounds(130, 60, 70, 25);
		txcapacity.setHorizontalAlignment(JTextField.RIGHT);
		txcapacity.setEditable(false);
		mpanel.add(txcapacity);
		
		lboccupied = hh.clabel("Occupied");
		lboccupied.setBounds(10, 100, 110, 25);
		mpanel.add(lboccupied);

		txoccupied = hh.cTextField(70);
		txoccupied.setBounds(130, 100, 70, 25);
		txoccupied.setHorizontalAlignment(JTextField.RIGHT);
		mpanel.add(txoccupied);	
		txoccupied.addHierarchyListener(e -> {
		    if(e.getComponent().isShowing() && (e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0)
		        SwingUtilities.invokeLater(e.getComponent()::requestFocusInWindow);
		});		
         
		btnadd = gr.sbcs("Add");
		btnadd.setBounds(130, 150, 110, 30);
		btnadd.setBackground(hh.lpiros);
		mpanel.add(btnadd);

		btnadd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				 txoccupied.requestFocus();
		         data_save(proomid);
		         dispose();
			}
		});
		
		btnclose = gr.sbcs("Close");
		btnclose.setBounds(130, 190, 110, 30);		
		btnclose.setBackground(Color.green);
		mpanel.add(btnclose);		
        
        btnclose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });     
    data_collect(proomid); 
  }
    
    private  void data_collect(String proomid) {
    	try {
			String [] warr = dd.getroomdata(proomid);
			
			txroomname.setText(warr[0]);
			txcapacity.setText(warr[1]);
			txoccupied.setText(warr[2]);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    }
    private void data_save(String proomid) {
    	String occupied = txoccupied.getText();
    	String sql = "update  rooms set occupied = '" + occupied +"' where roomid ='"+proomid +"'";		
    	int flag = dh.Insupdel(sql);
		dh.CloseConnection();    	
    }
}
