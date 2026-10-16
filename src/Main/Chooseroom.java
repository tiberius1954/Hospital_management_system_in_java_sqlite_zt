package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import Classes.Calhelper;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;


public class Chooseroom extends JFrame{
	ResultSet rs;
	Connection con = null;
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	DatabaseHelper dh = new DatabaseHelper();	
	Grlib gr = new Grlib();
	Databaseop dd = new Databaseop();	
	String docdid = "";
	JFrame myframe = this;
	String doctorname = "";
	int mydepid = 18;
	String mydepname = "";
	String sfrom = "";
	JFrame pframe;	
	
	Chooseroom(){
		init();
		hh.iconhere(this);
		String swhere = "depid = '" + mydepid+"'";	
		rtable_update(swhere);
	}
	Chooseroom( int pdepid, JFrame parent, String wfrom){
		pframe = parent;
		sfrom = wfrom;
		mydepid = pdepid;
		hh.iconhere(this);
		init();
		String swhere = " depid = '" + mydepid + "' and capacity > occupied ";	
		rtable_update(swhere);
	}	
	
	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(700, 500);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setTitle("Choose free room");
		lbheader = new JLabel("Choose free room");
		lbheader.setBounds(13, 10, 250, 30);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		mPanel = new JPanel(null);
		mPanel.setBounds(10, 50, 662, 400);
		mPanel.setBackground(new Color(178, 216, 216));
		add(mPanel);
		
		searchPanel = new JPanel(null);
		searchPanel.setBounds(110, 10, 430, 45);
		searchPanel.setBorder(hh.linesz);
		searchPanel.setBackground(hh.blue2);
		mPanel.add(searchPanel);

		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(0, 10, 70, 25);
		searchPanel.add(lbsearch);

		btnGroup = new ButtonGroup();
		rfree = new JRadioButton("Free rooms");
		rfree.setHorizontalTextPosition(SwingConstants.LEFT);
		rfree.setActionCommand("freeroom");
		rfree.setBounds(75, 10, 120, 25);
		rfree.setFont(new Font("Tahoma", Font.BOLD, 16));
		rfree.setBackground(Color.ORANGE);
		btnGroup.add(rfree);
		rall = new JRadioButton("All rooms");
		rall.setHorizontalTextPosition(SwingConstants.LEFT);
		rall.setActionCommand("allroom");
		rall.setBounds(200, 10, 120, 25);
		rall.setFont(new Font("Tahoma", Font.BOLD, 16));
		rall.setBackground(Color.ORANGE);
		btnGroup.add(rall);
		searchPanel.add(rfree);
		searchPanel.add(rall);
		
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setBounds(330, 10, 90, 27);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		searchPanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});
		rfree.setSelected(true);
		
		rtable = hh.ztable();
		rtable.setTableHeader(new JTableHeader(rtable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(rtable);
		rtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		rtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				rtable.scrollRectToVisible(rtable.getCellRect(rtable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane1 = new JScrollPane(rtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		String []pheader =new String[] { "roomid","Room name","Capacity","Occupied","Roomtype",
         "Status"};		
		rtable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {
						{ null, null, null, null, null, null }},
				       pheader));
		
		jScrollPane1.setViewportView(rtable);
		jScrollPane1.setBounds(30, 75, 600, 240);
		jScrollPane1.setBorder(hh.borderf);
		mPanel.add(jScrollPane1);

		TableColumnModel ccm = rtable.getColumnModel();
		ccm.getColumn(0).setWidth(0);
		ccm.getColumn(0).setMinWidth(0);
		ccm.getColumn(0).setMaxWidth(0);		
		int[][] wcols = { { 1, 150 }, { 2, 80 }, { 3, 80 }, { 4, 120 }, { 5, 167 }};
		hh.cellswidth(rtable, wcols);		
		
		btnselect = gr.sbcs("Select");
		btnselect.setForeground(Color.black);
		btnselect.setBackground(hh.lpiros);
		btnselect.setBounds(250, 340, 150, 32);
		btnselect.setFocusable(false);
		mPanel.add(btnselect);		
		btnselect.addActionListener(e -> row_select());
		
		setVisible(true);		
	}
	private void row_select() {			
		int row = rtable.getSelectedRow();
		if (row >= 0) {
			String roomid = rtable.getValueAt(row, 0).toString();
			String roomname = rtable.getValueAt(row, 1).toString();
			
			 if (sfrom == "admission"){
					((Admissionb) pframe).passtoadm(roomid, roomname);
				}					
				pframe.setVisible(true);
				dispose();
			}
	}

	
	private void rtable_update(String swhere) {
		DefaultTableModel m1 = (DefaultTableModel) rtable.getModel();
		m1.setRowCount(0);
		
		String sql = "select roomid, roomname, capacity,  occupied, roomtype,"
				+ " status from rooms where " + swhere;				 
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String roomid = rs.getString("roomid");
				String roomname = rs.getString("roomname");
				String capacity= rs.getString("capacity");
				String occupied = rs.getString("occupied");
				String roomtype = rs.getString("roomtype");
				String status = rs.getString("status");						
				m1.addRow(new Object[] { roomid, roomname, capacity, occupied, roomtype,
						status });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = {  "roomid","Room name","Capacity","Occupied","Roomtype",
		        "Status"};
		((DefaultTableModel) rtable.getModel()).setColumnIdentifiers(fej);
		TableColumnModel ccm = rtable.getColumnModel();
		ccm.getColumn(0).setWidth(0);
		ccm.getColumn(0).setMinWidth(0);
		ccm.getColumn(0).setMaxWidth(0);
		
		int[][] wcols = { { 1, 150 }, { 2, 80 }, { 3, 80 }, { 4, 120 }, { 5, 167 }};		
		hh.cellswidth(rtable, wcols);				
	}	
	
	private void sqlgyart() {
		int jelem = 0;
		if (rfree.isSelected()) {
			jelem = 1;
		} else if (rall.isSelected()) {
			jelem = 2;		
		} else {
			String qual = "Did not choose !";
			JOptionPane.showMessageDialog(null, qual);
		}
		if (jelem < 1) {
			return;
		}
		String sql = "";
		if (jelem == 1) {
			sql = " depid = '" + mydepid + "' and capacity > occupied ";
		} else if (jelem == 2) {
			sql = " depid = '" + mydepid + "'";
		} 
		rtable_update(sql);
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Chooseroom();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
JTable rtable;
JLabel lbheader;
JPanel mPanel, searchPanel;
JScrollPane jScrollPane1;
JButton btnselect, btnsearch;
JLabel lbsearch;
JRadioButton rall, rfree;
ButtonGroup btnGroup = new ButtonGroup();
}
