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

import javax.swing.*;
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

public class Chooseadmission extends JFrame{
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
	String mypatientname="";
	String sfrom = "";
	JFrame pframe;	
	
	Chooseadmission(){
		init();
	}
	
	Chooseadmission( String patientid, String patientname, int pdepid, JFrame parent, String wfrom){
		pframe = parent;
		sfrom = wfrom;
		mydepid = pdepid;
		mypatientname = patientname;
		init();
		String swhere = " where pid ='" + patientid +"' and depid = " + mydepid;	
		atable_update(swhere);
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
		setTitle("Choose admission");
		lbheader = new JLabel("Choose admission - " + mypatientname);
		lbheader.setBounds(10, 10, 800, 30);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		mPanel = new JPanel(null);
		mPanel.setBounds(10, 50, 860, 400);
		mPanel.setBackground(new Color(178, 216, 216));
		add(mPanel);
		
		atable = hh.ztable();
		atable.setTableHeader(new JTableHeader(atable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(atable);
		atable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		atable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				atable.scrollRectToVisible(atable.getCellRect(atable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane1 = new JScrollPane(atable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	
		String []pheader =new String[] { "adid","Admission date", "docid","Doctor","posid","Position","Reason"};		
		atable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {
						{ null, null, null, null, null, null, null }},
				       pheader));

		
		jScrollPane1.setViewportView(atable);
		jScrollPane1.setBounds(10, 20, 840, 280);
		jScrollPane1.setBorder(hh.borderf);
		mPanel.add(jScrollPane1);
		int[] wcols0 = {0, 2,4};
		hh.cellswidth_0(atable, wcols0);
		
		int[][] wcols = { { 1, 110 }, { 3, 200 }, { 5, 200 }, { 6, 330 }};
		hh.cellswidth(atable, wcols);		
		
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
		int row = atable.getSelectedRow();
		if (row >= 0) {
			String adid = atable.getValueAt(row, 0).toString();
			String add_date = atable.getValueAt(row, 1).toString();
			String docid = atable.getValueAt(row, 2).toString();
			String docname = atable.getValueAt(row, 3).toString();
			 String posid  =atable.getValueAt(row, 4).toString();	
			 String position = atable.getValueAt(row, 5).toString();		
			
			if (sfrom == "visit_record"){
			((Visit_record_reg) pframe).passtovisit(adid, docid, docname, posid, position, add_date);
			
			}					
			pframe.setVisible(true);
			dispose();
		}
	}

	private void atable_update(String swhere) {
		DefaultTableModel m1 = (DefaultTableModel) atable.getModel();
		m1.setRowCount(0);

		String sql = "select a.adid, a.add_date, a.docid, e.name,  a.posid, po.name as posname,"
				+ "  a.reason   from admission a "
				+ "  left join employees e on a.docid = e.empid "
				+ " left join positions po on a.posid = po.posid " + swhere;
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String adid = rs.getString("adid");
				String add_date = rs.getString("add_date");
				String docid = rs.getString("docid");
				String name= rs.getString("name");
				String posid = rs.getString("posid");
				String posname = rs.getString("posname");			
				String reason = rs.getString("reason");				
				m1.addRow(new Object[] { adid,add_date, docid, name, posid, posname, reason});				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = {   "adid","Admission date", "docid","Doctor","posid","Position","Reason"};
		((DefaultTableModel) atable.getModel()).setColumnIdentifiers(fej);
				
		int[] wcols0 = {0, 2,4};
		hh.cellswidth_0(atable, wcols0);
		
		int[][] wcols = { { 1, 110 }, { 3, 200 }, { 5, 200 }, { 6, 330 }};
		hh.cellswidth(atable, wcols);						
	}		
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Chooseadmission();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	JTable atable;
	JLabel lbheader;
	JPanel mPanel;
	JScrollPane jScrollPane1;
	JButton btnselect;
}
