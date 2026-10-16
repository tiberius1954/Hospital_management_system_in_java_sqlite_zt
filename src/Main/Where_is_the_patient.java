package Main;

import java.awt.*;
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
import java.util.Date;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;

import Classes.Calhelper;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Department;

public class Where_is_the_patient extends JFrame {
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
	JDateChooser sdate = new JDateChooser(new Date());
	JDateChooser vdate = new JDateChooser(new Date());

	Where_is_the_patient() {
		init();
		dd.cmbdepartmentcombofill(cmbdepartments);
		hh.iconhere(this);
	}

	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.piros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		UIManager.put("ComboBox.disabledForeground", Color.magenta);
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setSize(1000, 620);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Where is the patient");
		getContentPane().setBackground(new Color(0, 128, 128));
		lbheader = new JLabel("Where is the patient");
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 20));
		lbheader.setBounds(20, 10, 400, 35);
		add(lbheader);
		
		bPanel = new JPanel(null);
		bPanel.setBounds(15, 50, 953, 480);
		bPanel.setBackground(hh.vvvkek);
		add(bPanel);		
		
		lbdepname = hh.clabel("Department name");
		lbdepname.setBounds(10, 20, 150, 30);
		bPanel.add(lbdepname);

		cmbdepartments = hh.cbcombo();
		cmbdepartments.setBounds(170, 20, 280, 25);
		cmbdepartments.setFont(hh.textf);
		cmbdepartments.setName("department");
		bPanel.add(cmbdepartments);
		
		lbpatient = hh.clabel("Patient name");
		lbpatient.setBounds(460, 20, 120, 25);
		bPanel.add(lbpatient);

		txpatientname = hh.dfTextField(150);
		txpatientname.setBounds(590, 20, 200, 25);
		bPanel.add(txpatientname);

		btnpatient = gr.sbcs("Patients");
		btnpatient.setForeground(Color.black);
		btnpatient.setBackground(Color.ORANGE);
		btnpatient.setBounds(800, 20, 100, 32);
		btnpatient.setFocusable(false);
		bPanel.add(btnpatient);
		btnpatient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Patients pa = new Patients(myframe, "where_is_the_patient");
				pa.setLayout(null);
				pa.setLocationRelativeTo(null);
				pa.setVisible(true);
			}
		});
		
		txpatientid = hh.dfTextField(10);		

		lbterm = hh.clabel("Term");
		lbterm.setBounds(220, 70, 70, 25);
		bPanel.add(lbterm);
		sdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		sdate.setDateFormatString("yyyy-MM-dd");
		sdate.setFont(new Font("Arial", Font.BOLD, 16));
		sdate.setBounds(305, 70, 120, 25);
		bPanel.add(sdate);
		lbjel = hh.clabel(" - ");
		lbjel.setBounds(422, 70, 20, 25);
		bPanel.add(lbjel);

		vdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		vdate.setDateFormatString("yyyy-MM-dd");
		vdate.setFont(new Font("Arial", Font.BOLD, 16));
		vdate.setBounds(446, 70, 120, 25);
		bPanel.add(vdate);

		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(hh.skek);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(576, 70, 90, 25);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		bPanel.add(btnsearch);
		btnsearch.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    sqlgyart();
			}
		});
	
		btnclear = hh.cbutton("Clear");
		btnclear.setForeground(hh.skek);
		btnclear.setBackground(Color.ORANGE);	
		btnclear.setBounds(671, 70, 90, 25);
		bPanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			txpatientid.setText("");
			txpatientname.setText("");
			cmbdepartments.requestFocus();
			DefaultTableModel m1 = (DefaultTableModel) rtable.getModel();
			m1.setRowCount(0);
			cmbdepartments.setSelectedIndex(0);
			}
		});
		
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
	
		String []pheader =new String[] { "Patientid","Patient name","roomid","Room name",
				"Roomtype", "Start date", "End date","Created at","depid","Department"};		
		rtable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {
						{ null, null, null, null, null, null, null, null,null, null }},
				       pheader));
		
		jScrollPane1.setViewportView(rtable);
		jScrollPane1.setBounds(30, 125, 900, 310);
		jScrollPane1.setBorder(hh.borderf);
		bPanel.add(jScrollPane1);

		int[] wcols0 = { 2,8};
		hh.cellswidth_0(rtable, wcols0);

		int[][] wcols = { { 0, 90 }, { 1, 200 }, { 3, 100 }, { 4, 100 }, { 5, 90 }, { 6, 90 }, { 7, 90 }, { 9, 200 }};
		hh.cellswidth(rtable, wcols);			
       setVisible(true);
	}
	
	private void rtable_update(String swhere) {
		DefaultTableModel m1 = (DefaultTableModel) rtable.getModel();
		m1.setRowCount(0);
		
		String sql = "select i.pid, p.pname, i.roomid, r.roomname, r.roomtype,"
				+ " i.start_date, i.end_date, i.createdat, r.depid, d.depname from patients_in_rooms i "
				+ "  left join patient p  on i.pid = p.pid "
				+ "  left join rooms r  on i.roomid = r.roomid "
				+ "  left join departments d  on r.depid = d.depid "
			     + swhere;				 
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String pid = rs.getString("pid");
				String patientname = rs.getString("pname");
				String roomid = rs.getString("roomid");
				String roomname = rs.getString("roomname");			
				String roomtype = rs.getString("roomtype");
				String createdat = rs.getString("createdat");
				String startdate = rs.getString("start_date");
				String enddate = rs.getString("end_date");
				String depid =  rs.getString("depid");
				String depname =  rs.getString("depname");
				m1.addRow(new Object[] {pid, patientname, roomid, roomname, roomtype,
						startdate,enddate, createdat, depid, depname });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = {   "Patientid","Patient name","roomid","Room name",
				"Roomtype", "Start date", "End date","Created at","depid","Department"};		
		((DefaultTableModel) rtable.getModel()).setColumnIdentifiers(fej);
		
		int[] wcols0 = { 2,8};
		hh.cellswidth_0(rtable, wcols0);	
		int[][] wcols = { { 0, 90 }, { 1, 200 }, { 3, 100 }, { 4, 100 }, { 5, 90 }, { 6, 90 }, { 7, 90 }, { 9, 200 }};
		hh.cellswidth(rtable, wcols);				
	}	
	
	private void sqlgyart(){
		String ssdate, vvdate, ssd;
		String sql="";
	   String ss="";
	   ssdate = ((JTextField) sdate.getDateEditor().getUiComponent()).getText();
	   vvdate = ((JTextField) vdate.getDateEditor().getUiComponent()).getText();
		int depid = (int) ((Department) cmbdepartments.getSelectedItem()).getDepid();		
		String pid = txpatientid.getText();
		if (depid > 0  && !hh.zempty(pid) ){
			ss = "r.depid== " + depid +" and i.pid =='"+ pid  +"'";
		} else if (depid > 0) {
			ss = "r.depid== " + depid +"";
		} else if (!hh.zempty(pid)) {
			ss= "i.pid =='"+ pid  +"'";
		}
		 ssd =  " start_date >= date('" + ssdate +"') and start_date <="
					+ " date('" + vvdate +"')";	
		if ( ! hh.zempty(ss)) {
			ss = ss +" and " + ssd;
		} else {
			ss = ssd;
		}
		sql = " where "	+ ss;
		rtable_update(sql);		
	}	
	
	public void passtopatient(String patientid, String patientname) {
		txpatientname.setText(patientname);
		txpatientid.setText(patientid);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Where_is_the_patient();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbjel, lbterm, lbdepname, lbpatient;
	JButton btnsearch, btnpatient, btnclear;
    JPanel bPanel;
    JComboBox cmbdepartments;
    JTextField txpatientname, txpatientid;
    JScrollPane jScrollPane1;
    JTable rtable;
}
