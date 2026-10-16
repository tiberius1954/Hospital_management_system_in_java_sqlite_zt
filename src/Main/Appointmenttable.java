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

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JDateChooser;
import Classes.Allvalid;
import Classes.Employee;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Patient;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import java.util.Date;

public class Appointmenttable extends JFrame {

	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Allvalid allv = new Allvalid();
	Patient pat;
	String rowid = "";
	int myrow = 0;
	int myid = 0;
	JFrame myframe = this;
	String sfrom = "";
	JFrame pframe;
	String pheader[];
	JDateChooser sdate = new JDateChooser(new Date());
	JDateChooser vdate = new JDateChooser(new Date());
	int mydepid = 0;
	String mydepname = "";

	Appointmenttable() {
		mydepid = 18;
		mydepname = "";
		init();
		dd.cmbdoctor1combofill(cmbdoctors, mydepid);
		hh.iconhere(this);
	}

	Appointmenttable(int pdepid, String pdepname) {
		mydepid = pdepid;
		mydepname = pdepname;
		init();	
		dd.cmbmeployee_departmentcombofill(cmbdoctors, "d", mydepid);
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
		setSize(1200, 620);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0, 128, 128));
		lbheader = hh.fflabel("Appointment table " + mydepname);
		lbheader.setBounds(20, 10, 500, 35);
		add(lbheader);

		mainPanel = new JPanel(null);
		mainPanel.setBounds(20, 50, 1150, 505);
		mainPanel.setBackground(new Color(178, 216, 216));
		add(mainPanel);

		searchPanel = new JPanel(null);
		searchPanel.setBounds(60, 35, 1045, 45);
		searchPanel.setBorder(hh.linesz);
		searchPanel.setBackground(new Color(178, 216, 216));
		mainPanel.add(searchPanel);

		lbdoctor = hh.clabel("Doctors");
		lbdoctor.setBounds(0, 10, 70, 25);
		searchPanel.add(lbdoctor);

		cmbdoctors = gr.grcombo();
		cmbdoctors.setFocusable(true);
		cmbdoctors.setName("doctors");
		cmbdoctors.setFont(hh.textf);
		cmbdoctors.setBounds(75, 10, 285, 28);
		searchPanel.add(cmbdoctors);

		lbpatientid = hh.clabel("Patientid");
		lbpatientid.setBounds(365, 10, 80, 25);
		searchPanel.add(lbpatientid);

		txpatientid = hh.cTextField(150);
		txpatientid.setBounds(455, 10, 125, 25);
		searchPanel.add(txpatientid);

		lbidoszak = hh.clabel("Term");
		lbidoszak.setBounds(580, 10, 50, 25);
		searchPanel.add(lbidoszak);
		sdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		sdate.setDateFormatString("yyyy-MM-dd");
		sdate.setFont(new Font("Arial", Font.BOLD, 16));
		sdate.setBounds(640, 10, 120, 25);
		searchPanel.add(sdate);
		lbjel = hh.clabel(" - ");
		lbjel.setBounds(760, 10, 20, 25);
		searchPanel.add(lbjel);
		vdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		vdate.setDateFormatString("yyyy-MM-dd");
		vdate.setFont(new Font("Arial", Font.BOLD, 16));
		vdate.setBounds(780, 10, 120, 25);
		searchPanel.add(vdate);

		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(hh.skek);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(910, 10, 60, 25);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		searchPanel.add(btnsearch);
		btnsearch.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});
		btnclear = hh.cbutton("Clear");
		btnclear.setForeground(hh.skek);
		btnclear.setBackground(Color.ORANGE);
		btnclear.setBounds(975, 10, 60, 25);
		btnclear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		searchPanel.add(btnclear);
		btnclear.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 mclear();
			}
		});

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

		aptable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "appid", "Doctor", "Date", "Time","Patientid", "Patient name", "Purpose", "Phone", "Email", "Created" }));
		jScrollPane1.setViewportView(aptable);
		jScrollPane1.setBounds(30, 120, 1100, 320);
		jScrollPane1.setBorder(hh.borderf);
		mainPanel.add(jScrollPane1);

		int[] wcols0 = { 0};
		hh.cellswidth_0(aptable, wcols0);

		int[][] wcols = { { 1, 200 }, { 2, 90 }, { 3, 50 }, {4,120},{ 5, 250 }, { 6, 350 }, { 7, 200 }, 
				{ 8, 250 }, { 9, 100 } };
		hh.cellswidth(aptable, wcols);

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(540, 510, 130, 30);
		btndelete.setBackground(Color.yellow);
		add(btndelete);

		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				String sql = " delete from appointments where appid= ";
				dd.data_delete(aptable, sql);
			}
		});

		add(mainPanel);
		setVisible(true);
	}

	private void sqlgyart() {
		String swhere = "";
		String empid = "";
		String posid = "";

		String spatient = txpatientid.getText();
		String ssdate = ((JTextField) sdate.getDateEditor().getUiComponent()).getText();
		String vvdate = ((JTextField) vdate.getDateEditor().getUiComponent()).getText();
	
		if (cmbdoctors.getSelectedItem() != null) {
			Employee emp = (Employee) cmbdoctors.getSelectedItem();
			empid = emp.getEmpid();		
			posid = emp.getPosid();		
		}		

		if (!hh.zempty(spatient)) {
			swhere = "app_date >= date('" + ssdate + "') and app_date <=" + " date('" + vvdate + "')" 
		+ " and a.pid='"+ spatient + "' and a.depid='" + mydepid +"'";
		} else {
			if (!hh.zempty(empid)) {
				swhere = " docid='" + empid + "' and posid='" + posid + "' and a.depid ='" + mydepid +"'"
						+ " and app_date >= date('" + ssdate + "') and app_date <=" + " date('" + vvdate + "')";
			} else {
				swhere = " app_date >= date('" + ssdate + "') and app_date <=" + " date('" + vvdate + "')"
						+ " and a.depid='" + mydepid +"'";
			}
		}
		table_update(swhere);
		return;
	}

	private void table_update(String swhere) {
		DefaultTableModel m1 = (DefaultTableModel) aptable.getModel();
		m1.setRowCount(0);
		
	String sql = "select a.appid, e.name as dname, a.app_date, a.app_time, a.pid,  p.pname, a.purpose,"
			+ " p.phone, p.email,created_at   from appointments a "
		    + " left join patient p on a.pid = p.pid  left join employees e on a.docid = e.empid "
	    	+ " where " + swhere;
	try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String appid = rs.getString("appid");
				String dname = rs.getString("dname");
				String appdate = rs.getString("app_date");
				String apptime = rs.getString("app_time");
				String pid = rs.getString("pid");
				String pname = rs.getString("pname");
				String purpose = rs.getString("purpose");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String created = rs.getString("created_at");
				m1.addRow(new Object[] { appid, dname, appdate, apptime, pid, pname, purpose, phone, email, created });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = { "appid", "Doctor", "Date", "Time","Patientid", "Patient name", "Purpose", "Phone", "Email", "Created" };
		((DefaultTableModel) aptable.getModel()).setColumnIdentifiers(fej);
		TableColumnModel ccm = aptable.getColumnModel();
		ccm.getColumn(0).setWidth(0);
		ccm.getColumn(0).setMinWidth(0);
		ccm.getColumn(0).setMaxWidth(0);
		int[][] wcols = { { 1, 200 }, { 2, 90 }, { 3, 50 }, {4,120},{ 5, 250 }, { 6, 350 }, { 7, 200 }, 
				{ 8, 250 }, { 9, 100 } };
		hh.cellswidth(aptable, wcols);	
	}
	private void  mclear() {
		txpatientid.setText("");
		cmbdoctors.setSelectedIndex(0);	
		DefaultTableModel m1 = (DefaultTableModel) aptable.getModel();
		m1.setRowCount(0);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Appointmenttable app = new Appointmenttable();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbdoctor, lbidoszak, lbjel, lbpatientid;
	JComboBox cmbdoctors;
	JPanel searchPanel, mainPanel;
	JButton btnsearch, btndelete, btnclear;
	JScrollPane jScrollPane1;
	JTable aptable;
	JTextField txpatientid;
}
