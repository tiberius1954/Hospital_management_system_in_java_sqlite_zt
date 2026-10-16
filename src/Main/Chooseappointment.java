package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import Classes.Allvalid;
import Classes.Calhelper;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Chooseappointment  extends JFrame {	
		ResultSet rs;
		Connection con = null;
		Hhelper hh = new Hhelper();
		Calhelper cc = new Calhelper();
		DatabaseHelper dh = new DatabaseHelper();
		Allvalid allv = new Allvalid();
		Grlib gr = new Grlib();
		Databaseop dd = new Databaseop();	
		String docdid = "";
		JFrame myframe = this;
		String doctorname = "";
		int mydepid = 0;
		String mydepname = "";
		String sfrom = "";
		JFrame pframe;	
		String mypatientname="";
	
	Chooseappointment(){
		init();
	}
	Chooseappointment(String patientid, String patientname, int pdeptid, JFrame parent, String wfrom){
		pframe = parent;
		sfrom = wfrom;
		mydepid = pdeptid;
		mypatientname = patientname;
		init();
		String swhere = " where pid ='" + patientid +"' and depid = " + mydepid;	
		aptable_update(swhere);
	}	
	
	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(900, 500);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setTitle("Choose appointment");
		lbheader = new JLabel("Choose appointment -" + mypatientname);
		lbheader.setBounds(10, 10, 800, 30);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		mPanel = new JPanel(null);
		mPanel.setBounds(10, 50, 860, 400);
		mPanel.setBackground(new Color(178, 216, 216));
		add(mPanel);
		
		aptable = hh.ztable();
		aptable.setTableHeader(new JTableHeader(aptable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(aptable);
		aptable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		aptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				aptable.scrollRectToVisible(aptable.getCellRect(aptable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane1 = new JScrollPane(aptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	
		String []pheader =new String[] { "appid","docid","Doctor","posid","Position", "Date",
				"Time","Purpose"};		
		aptable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {
						{ null, null, null, null, null, null, null, null }},
				       pheader));

		
		jScrollPane1.setViewportView(aptable);
		jScrollPane1.setBounds(10, 20, 840, 280);
		jScrollPane1.setBorder(hh.borderf);
		mPanel.add(jScrollPane1);
		
		int[] wcols0 = {0, 1,3};
		hh.cellswidth_0(aptable, wcols0);
		int[][] wcols = { { 2, 200 }, { 4, 140 }, { 5, 100 }, { 6, 60 }, { 7, 338 }};
		hh.cellswidth(aptable, wcols);		
		
		btnselect = gr.sbcs("Select");
		btnselect.setForeground(Color.black);
		btnselect.setBackground(hh.lpiros);
		btnselect.setBounds(390, 330, 120, 32);
		btnselect.setFocusable(false);
		mPanel.add(btnselect);		
		btnselect.addActionListener(e -> row_select());
		
		setVisible(true);		
	}
	private void row_select() {		
		int row = aptable.getSelectedRow();
		if (row >= 0) {
			String appid = aptable.getValueAt(row, 0).toString();
			String docid = aptable.getValueAt(row, 1).toString();
			String docname = aptable.getValueAt(row, 2).toString();
			 String posid  =aptable.getValueAt(row, 3).toString();	
			 String position = aptable.getValueAt(row, 4).toString();
			 String sdate =  aptable.getValueAt(row, 5).toString();
			 String stime = aptable.getValueAt(row, 6).toString();
			 if (sfrom == "medical_record"){
					((Medical_record_reg) pframe).passtoappoint(appid, docid, docname, posid, position,
							sdate, stime);
				}					
				pframe.setVisible(true);
				dispose();
			}
	}

	
	private void aptable_update(String swhere) {
		DefaultTableModel m1 = (DefaultTableModel) aptable.getModel();
		m1.setRowCount(0);

		String sql = "select a.appid, a.docid, e.name,  a.posid, po.name as posname,"
				+ " a.app_date, a.app_time, a.purpose   from appointments a "
				+ "  left join employees e on a.docid = e.empid "
				+ " left join positions po on a.posid = po.posid " + swhere;
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String appid = rs.getString("appid");
				String docid = rs.getString("docid");
				String name= rs.getString("name");
				String posid = rs.getString("posid");
				String posname = rs.getString("posname");
				String appdate = rs.getString("app_date");
				String apptime = rs.getString("app_time");
				String purpose = rs.getString("purpose");				
				m1.addRow(new Object[] { appid, docid, name, posid, posname, appdate,
						apptime, purpose });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = {  "appid","docid","Doctor","posid","Position", "Date", "Time","Purpose"};
		((DefaultTableModel) aptable.getModel()).setColumnIdentifiers(fej);	

		int[] wcols0 = {0, 1,3};
		hh.cellswidth_0(aptable, wcols0);
		int[][] wcols = { { 2, 200 }, { 4, 140 }, { 5, 100 }, { 6, 60 }, { 7, 338 }};
		hh.cellswidth(aptable, wcols);				
	}	
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Chooseappointment();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	JLabel lbheader;
	JPanel mPanel;
	JTable aptable;
	JScrollPane jScrollPane1;
	JButton btnselect;
}
