package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import Classes.Admission;
import Classes.Allvalid;
import Classes.Employee;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Patient;
import Classes.Room;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Cities;

public class Admissionb extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Allvalid allv = new Allvalid();
	Admission adm;
	String rowid = "";
	int myrow = 0;
	String rrowid = "";
	int rmyrow = 0;	
	int myid = 0;
	JFrame myframe = this;
	String sfrom = "";
	JFrame pframe;
	String[] status = new String[] { "", "In progress", "Completed" };
	int mydepid = 0;
	String mydepname = "";
	String mypid="";
	String myadid ="";

	Admissionb() {
		init();
		dd.cmbmeployee_departmentcombofill(cmbdoctors, "d", mydepid);	
		hh.iconhere(this);
		atable_update("");	
	}

	Admissionb(int pdepid, String pdepname) {
		mydepid = pdepid;
		mydepname = pdepname;
		init();
		dd.cmbmeployee_departmentcombofill(cmbdoctors, "d", mydepid);
		dd.cmbmeployee_departmentcombofill(cmbnurses, "n", mydepid);
		hh.iconhere(this);
		atable_update(" where depid = '" + mydepid +"'");
	}

	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				if (hh.whichpanel(cardPanel) == "table") {
					dispose();
				} else if (hh.whichpanel(cardPanel) == "edit") {
					cards.show(cardPanel, "table");
				}else if (hh.whichpanel(cardPanel) == "gedit") {
						cards.show(cardPanel, "table");
				}
			}
		});

		setTitle("Admissions");
		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(1210, 735);
		setLayout(null);
		setLocationRelativeTo(null);
		lbheader = hh.fflabel("Admissions " + mydepname);
		lbheader.setBounds(20, 10, 400, 35);
		lbheader.setForeground(new Color(255, 255, 255));
		add(lbheader);

		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setLayout(cards);
		cardPanel.setBounds(10, 50, 1170, 620);
		cardPanel.setBorder(hh.border4);
		cardPanel.setBackground(new Color(178, 216, 216));

		tPanel = maketpanel();
		tPanel.setName("table");
		cardPanel.add(tPanel, "table");

		ePanel = makeepanel();
		ePanel.setName("edit");
		cardPanel.add(ePanel, "edit");
		
		gePanel = makegepanel();
		gePanel.setName("gedit");
		cardPanel.add(gePanel, "gedit");
		
		add(cardPanel);

	cards.show(cardPanel, "table");
//	 cards.show(cardPanel, "edit");
	//	cards.show(cardPanel, "gedit");
		setVisible(true);
	}

	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);
		ttpanel.setBackground(new Color(178, 216, 216));
		ttpanel.setBounds(10, 10, 1140, 580);
		searchPanel = new JPanel(null);
		searchPanel.setBounds(270, 10, 605, 45);
		searchPanel.setBorder(hh.linesz);
		searchPanel.setBackground(hh.blue2);
		ttpanel.add(searchPanel);

		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(0, 10, 70, 25);
		searchPanel.add(lbsearch);

		btnGroup = new ButtonGroup();
		rname = new JRadioButton("Name");
		rname.setHorizontalTextPosition(SwingConstants.LEFT);
		rname.setActionCommand("name");
		rname.setBounds(75, 10, 80, 25);
		rname.setFont(new Font("Tahoma", Font.BOLD, 16));
		rname.setBackground(Color.ORANGE);
		btnGroup.add(rname);
		rpatid = new JRadioButton("Patientid");
		rpatid.setHorizontalTextPosition(SwingConstants.LEFT);
		rpatid.setActionCommand("patientid");
		rpatid.setBounds(160, 10, 100, 25);
		rpatid.setFont(new Font("Tahoma", Font.BOLD, 16));
		rpatid.setBackground(Color.ORANGE);
		btnGroup.add(rpatid);

		searchPanel.add(rname);
		searchPanel.add(rpatid);
		
		txsearch = hh.cTextField(25);
		txsearch.setBounds(270, 10, 200, 25);
		searchPanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(471, 10, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");
		searchPanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				atable_update(" where depid = '" + mydepid +"'");
			}
		});
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setBounds(503, 10, 90, 27);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		searchPanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});
		rname.setSelected(true);
		atable = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) atable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		atable.setTableHeader(new JTableHeader(atable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		atable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) atable.getModel();
				try {
					int row = atable.getSelectedRow();
					if (row > -1) {
						String sadid = model.getValueAt(row, 0).toString();
						adm = dd.getadmissionrecord(sadid);
						String reason = adm.getReason();
						txareasonr.setText(reason);
						roomhistory(sadid);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		hh.madeheader(atable);
		atable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		atable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		aPane = new JScrollPane(atable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		String pheader[] = new String[] { "adid", "Admission date", "Discharge date", "Patientid", "Patient name",
				"docid", "Doctor", "posid", "nurseid", "Nurse","nposid", "Status" };

		atable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null, null, null,null } }, pheader));

		aPane.setViewportView(atable);
		aPane.setBounds(30, 70, 1110, 300);
		aPane.setBorder(hh.myRaisedBorder);
		ttpanel.add(aPane);

		int[] wcols0 = { 0, 5, 7, 8, 10};
		hh.cellswidth_0(atable, wcols0);

		int[][] wcols = { { 1, 120 }, { 2, 120 }, { 3, 80 }, { 4, 220 }, { 6, 220 }, { 9, 200 }, { 11, 150 } };
		hh.cellswidth(atable, wcols);
		
		
		roomtablet = hh.ztable();
		roomtablet.setTableHeader(new JTableHeader(roomtablet.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});		
		
		lbroomtime = hh.clabel("Room history");
		lbroomtime.setBounds(620, 380, 150, 25);
		ttpanel.add(lbroomtime);
		
		hh.madeheader(roomtablet);
		roomtablet.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				roomtablet.scrollRectToVisible(roomtablet.getCellRect(roomtablet.getRowCount() - 1, 0, true));
			}
		});

		roomtpane = new JScrollPane(roomtablet, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		roomtablet.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] {  "Room name", "Start date", "End date" }));
		hh.setJTableColumnsWidth(roomtablet, 460,  50, 25, 25);
		roomtpane.setViewportView(roomtablet);
		roomtpane.setBounds(480, 410, 460, 170);
		roomtpane.setBorder(hh.borderf);
		ttpanel.add(roomtpane);

		lbreason = hh.clabel("Reason");
		lbreason.setBounds(170, 380, 100, 25);
		ttpanel.add(lbreason);

		txareasonr = hh.cTextarea();
		txareasonr.setEditable(false);
		txareasonr.setFocusable(false);

		txapaner = new JScrollPane(txareasonr, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		txapaner.setBorder(hh.borderf);
		txapaner.setBounds(20, 410, 440, 170);
		txapaner.setViewportView(txareasonr);
		ttpanel.add(txapaner);

		btnnew = gr.sbcs("New");
		btnnew.setBounds(970, 410, 145, 35);
		btnnew.setForeground(Color.black);
		btnnew.setBackground(hh.lpiros);
		ttpanel.add(btnnew);
		btnnew.addActionListener(e -> data_new());

		btnupdate = gr.sbcs("Update");
		btnupdate.setBounds(970, 450, 145, 35);
		btnupdate.setBackground(hh.vvzold);
		ttpanel.add(btnupdate);
		btnupdate.addActionListener(e -> data_update());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(970, 490, 145, 35);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);
		ttpanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());
		
		btnroomassign = gr.sbcs("Room assign");
		btnroomassign.setBounds(970, 530, 145, 35);
		btnroomassign.setForeground(Color.black);
		btnroomassign.setBackground(hh.magenta);
		ttpanel.add(btnroomassign);
		btnroomassign.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				DefaultTableModel model = (DefaultTableModel) atable.getModel();
				try {
					int row = atable.getSelectedRow();
					if (row > -1) {
						myadid = model.getValueAt(row, 0).toString();
						mypid = model.getValueAt(row, 3).toString();
						String patientname = model.getValueAt(row, 4).toString(); 	
						lbroomheader.setText(patientname +"'s rooms");
						roomview(myadid);
						cards.show(cardPanel, "gedit");
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
				
			}
		});
		return ttpanel;
	}

	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setBackground(new Color(178, 216, 216));
		eepanel.setBounds(10, 10, 1140, 580);
		JPanel lpanel = new JPanel(null);
		lpanel.setBounds(300, 10, 600, 580);
		lpanel.setBorder(hh.ztroundborder(hh.cianzt));
		lpanel.setBackground(new Color(178, 216, 216));
		eepanel.add(lpanel);
		
		lbpatientid = hh.clabel("Patient name");
		lbpatientid.setBounds(30, 20, 130, 25);
		lpanel.add(lbpatientid);

		txpatientname = hh.dfTextField(150);
		txpatientname.setBounds(170, 20, 280, 30);
		lpanel.add(txpatientname);

		btnpatient = gr.sbcs("Patients");
		btnpatient.setForeground(Color.black);
		btnpatient.setBackground(Color.ORANGE);
		btnpatient.setBounds(460, 20, 100, 32);
		btnpatient.setFocusable(false);
		lpanel.add(btnpatient);
		btnpatient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Patients pa = new Patients(myframe, "admissions");
				pa.setLayout(null);
				pa.setLocationRelativeTo(null);
				pa.setVisible(true);
			}
		});

		txpatientid = hh.dfTextField(50);

		lbadddate = hh.clabel("Admission date");
		lbadddate.setBounds(30, 70, 130, 30);
		lpanel.add(lbadddate);

		txadd_date = hh.DatetextField();
		txadd_date.setBounds(170, 70, 105, 30);
		lpanel.add(txadd_date);

		lbdischargedate = hh.clabel("Discharge date");
		lbdischargedate.setBounds(30, 120, 130, 30);
		lpanel.add(lbdischargedate);

		txdischargedate = hh.DatetextField();
		txdischargedate.setBounds(170, 120, 105, 30);
		lpanel.add(txdischargedate);

		lbstatus = hh.clabel("Status");
		lbstatus.setBounds(30, 170, 130, 25);
		lpanel.add(lbstatus);

		cmbstatus = gr.grcombo();
		cmbstatus.setModel(new DefaultComboBoxModel(status));
		cmbstatus.setBounds(170, 170, 200, 30);
		cmbstatus.setFont(hh.textf);
		lpanel.add(cmbstatus);

		lbdoctor = hh.clabel("Doctor");
		lbdoctor.setBounds(30, 220, 130, 25);
		lpanel.add(lbdoctor);

		cmbdoctors = gr.grcombo();
		cmbdoctors.setBounds(170, 220, 300, 30);
		cmbdoctors.setFont(hh.textf);
		cmbdoctors.setName("doctors");
		lpanel.add(cmbdoctors);

		lbnurse = hh.clabel("Nurse");
		lbnurse.setBounds(30, 270, 130, 25);
		lpanel.add(lbnurse);

		cmbnurses = gr.grcombo();
		cmbnurses.setBounds(170, 270, 300, 30);
		cmbnurses.setName("nurses");
		cmbnurses.setFont(hh.textf);
		lpanel.add(cmbnurses);

		lbreasonw = hh.clabel("Reason");
		lbreasonw.setBounds(30, 320, 130, 25);
		lpanel.add(lbreasonw);

		txareasonw = hh.cTextarea();
		txapanew = new JScrollPane(txareasonw, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		txapanew.setBorder(hh.borderf);
		txapanew.setBounds(170, 320, 380, 180);
		txapanew.setViewportView(txareasonw);
		lpanel.add(txapanew);

		btnsave = gr.sbcs("Save");
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		btnsave.setBounds(240, 520, 120, 32);
		btnsave.setFocusable(false);
		lpanel.add(btnsave);
		btnsave.addActionListener(e -> data_save());

		btncancel = gr.sbcs("Cancel");
		btncancel.setForeground(Color.black);
		btncancel.setBackground(hh.vvzold);
		btncancel.setBounds(370, 520, 120, 32);
		btncancel.setFocusable(false);
		lpanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				 clearFields(2);
				 clearFields(1);				
			}
	});
		return eepanel;
	}
	
	private JPanel makegepanel(){
		JPanel gepanel = new JPanel(null);
		gepanel.setBackground(new Color(178, 216, 216));
		gepanel.setBounds(0, 52, 1200, 560);
		
		roomPanel = new JPanel(null);
		roomPanel.setBounds(35, 100, 540, 250);
		roomPanel.setBorder(hh.borderf);
		roomPanel.setBackground(new Color(178, 216, 216));
		gepanel.add(roomPanel);

		JPanel rtablepanel = new JPanel(null);
		rtablepanel.setBounds(590, 100, 545, 250);
		// rtablepanel.setBorder(hh.ztroundborder(hh.cianzt));
		rtablepanel.setBorder(hh.borderf);
		rtablepanel.setBackground(new Color(178, 216, 216));
		gepanel.add(rtablepanel);
		
	
		lbroomheader = hh.fflabel("");
		lbroomheader.setBounds(200, 40, 400, 35);
		lbroomheader.setForeground(new Color(0, 0, 0));		
		gepanel.add(lbroomheader);

		lbrooms = hh.clabel("Rooms");
		lbrooms.setBounds(10, 30, 100, 30);
		roomPanel.add(lbrooms);

		txroomname = hh.cTextField(20);
		txroomname.setBounds(120, 30, 180, 28);		
		txroomname.setEditable(false);
		roomPanel.add(txroomname);
		
		txroomid = hh.cTextField(10);

		btnrooms = gr.sbcs("Rooms");
		btnrooms.setForeground(Color.black);
		btnrooms.setBackground(Color.ORANGE);
		btnrooms.setBounds(310, 30, 130, 32);
		btnrooms.setFocusable(false);
		roomPanel.add(btnrooms);
		btnrooms.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Chooseroom cho= new Chooseroom(mydepid, myframe, "admission");		
				cho.setVisible(true);
			}
	});		
		
		lbassdate = hh.clabel("Start date");
		lbassdate.setBounds(10, 80, 100, 30);
		roomPanel.add(lbassdate);

		txstartdate = hh.DatetextField();
		txstartdate.setBounds(120, 80, 105, 30);
		roomPanel.add(txstartdate);

		lbenddate = hh.clabel("End date");
		lbenddate.setBounds(10, 130, 100, 30);
		roomPanel.add(lbenddate);

		txenddate = hh.DatetextField();
		txenddate.setBounds(120, 130, 105, 30);
		roomPanel.add(txenddate);

		btnsaver = gr.sbcs("Save");
		btnsaver.setForeground(Color.black);
		btnsaver.setBackground(hh.lpiros);
		btnsaver.setBounds(310, 80, 130, 32);
		btnsaver.setFocusable(false);
		roomPanel.add(btnsaver);
		btnsaver.addActionListener(e -> data_saver());		
		
		btncancelr = gr.sbcs("Cancel");
		btncancelr.setForeground(Color.black);
		btncancelr.setBackground(Color.green);
		btncancelr.setBounds(310, 120, 130, 32);
		btncancelr.setFocusable(false);
		roomPanel.add(btncancelr);
		btncancelr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields(2);
			}
	});				
		
		btnremover = gr.sbcs("delete");
		btnremover.setForeground(Color.black);
		btnremover.setBackground(Color.yellow);
		btnremover.setBounds(310, 160, 130, 32);
		btnremover.setFocusable(false);
		roomPanel.add(btnremover);
		btnremover.addActionListener(e -> buttremovefv());	
		
		btnback = gr.sbcs("Back");
		btnback.setBounds(310, 200, 130, 32);
		btnback.setBackground(Color.ORANGE);
		roomPanel.add(btnback);
		
		btnback.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				cards.show(cardPanel, "table");
			}
		});

		roomtablee = hh.ztable();
		roomtablee.setTableHeader(new JTableHeader(roomtablee.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(roomtablee);
		roomtablee.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				roomtablee.scrollRectToVisible(roomtablee.getCellRect(roomtablee.getRowCount() - 1, 0, true));
			}
		});
		roomtablee.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = roomtablee.getSelectedRow();
				if (row >= 0) {
				    rrowid = roomtablee.getValueAt(row, 0).toString();	
				    String roomid = roomtablee.getValueAt(row, 1).toString();
				    txroomid.setText(roomid);
					String roomname = roomtablee.getValueAt(row, 2).toString();	
					txroomname.setText(roomname);
					String startdate =  roomtablee.getValueAt(row, 3).toString();	
					txstartdate.setText(startdate);
					String enddate =  roomtablee.getValueAt(row, 4).toString();	
					txenddate.setText(enddate);				
					}					
       			   rmyrow = row;				
			}
		});

		roomepane = new JScrollPane(roomtablee, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		roomtablee.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "assid", "roomid", "Room name", "Start date", "End date", "adid" }));
		hh.setJTableColumnsWidth(roomtablee, 520, 0, 0, 40, 30, 30, 0);
		roomepane.setViewportView(roomtablee);
		roomepane.setBounds(10, 25, 520, 200);
		roomepane.setBorder(hh.borderf);
		rtablepanel.add(roomepane);
		return gepanel;
	}

	private void data_save() {
		String sql = "";		
		DefaultTableModel d1 = (DefaultTableModel) atable.getModel();
		Boolean newrec = false;
		String pid = txpatientid.getText();
		String patientname = txpatientname.getText();
		String addate = txadd_date.getText();
		if (addate.equals("____-__-__")) {
			addate = "";
		}
		String dischargedate = txdischargedate.getText();
		if (dischargedate.equals("____-__-__")) {
			dischargedate = "";
		}
		String reason = txareasonw.getText();
		String status = (String) cmbstatus.getSelectedItem();
		String docid = (String) ((Employee) cmbdoctors.getSelectedItem()).getEmpid();
		String docname = (String) ((Employee) cmbdoctors.getSelectedItem()).getName();
		String posid = (String) ((Employee) cmbdoctors.getSelectedItem()).getPosid();
		
		String nurseid = (String) ((Employee) cmbnurses.getSelectedItem()).getEmpid();
		String nursename = (String) ((Employee) cmbnurses.getSelectedItem()).getName();
		String nposid = (String) ((Employee) cmbnurses.getSelectedItem()).getPosid();		

		if (advalidation(patientname, addate, docname) == false) {
			return;
		}
		if (rowid == "") {
			newrec = true;
		}

		if (rowid != "") {
			sql = "update  admission set add_date = '" + addate + "', discharge_date= '" + dischargedate + "', "
					+ " reason = '" + reason + "', pid = '" + pid + "',  docid='" + docid + "',  posid='" + posid
					+ "', depid= " + mydepid + ", status= '" + status + "', nurseid='" + nurseid 
					+ "', nposid = '"  + nposid + "' where adid = " + rowid;
		} else {
			sql = "insert into admission (add_date, discharge_date, reason, pid, docid, posid, depid,"
					+ " status, nurseid, nposid) values ( '" + addate + "','" + dischargedate + "','" + reason 
					+ "','" + pid + "','" + docid + "','" + posid + "'," + mydepid + ",'" + status 
					+ "','" + nurseid +"','"+ nposid + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(adid) AS max_id from admission");
					rowid = hh.itos(myid);
					d1.addRow(new Object[] { myid, addate, dischargedate, pid, patientname,
							docid, docname, posid, nurseid, nursename, nposid, status });
					hh.gotolastrow(atable);		
				} else {
					d1.setValueAt(addate, myrow, 1);
					d1.setValueAt(dischargedate, myrow, 2);
					d1.setValueAt(pid, myrow, 3);
					d1.setValueAt(patientname, myrow, 4);
					d1.setValueAt(docid, myrow, 5);
					d1.setValueAt(docname, myrow, 6);
					d1.setValueAt(posid, myrow, 7);
					d1.setValueAt(nurseid, myrow, 8);
					d1.setValueAt(nursename, myrow, 9);
					d1.setValueAt(nposid, myrow, 10);
					d1.setValueAt(status, myrow, 11);				
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql if  error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		txareasonr.setText(reason);
		clearFields(2);
		cards.show(cardPanel, "table");
	}

	private Boolean advalidation(String patientname, String addate, String docname) {
		Boolean ret = true;
		ArrayList<String> err = new ArrayList<String>();

		if (hh.zempty(docname)) {
			err.add("Doctor name is empty !");
			return false;
		}
		if (hh.zempty(patientname)) {
			err.add("Patient name is empty !");
			return false;
		}
		if (!allv.datevalid(addate)) {
			err.add(allv.mess);
			ret = false;
		}

		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
		return ret;
	}	

	private void data_new() {
		clearFields(1);
		clearFields(2);
		txadd_date.setText(hh.currentDate());
		cards.show(cardPanel, "edit");
		txadd_date.requestFocus(true);
	}

	private void data_update() {
		int row = atable.getSelectedRow();
		if (row >= 0) {
			clearFields(1);
			clearFields(2);
			clearFields(3);
			rowid = atable.getValueAt(row, 0).toString();
			myrow = row;
			adm = dd.getadmissionrecord(rowid);
			txadd_date.setText(adm.getAdd_date());
			txdischargedate.setText(adm.getDischarge_date());
			txpatientid.setText(adm.getPid());
			txpatientname.setText(adm.getPatientname());
			txareasonw.setText(adm.getReason());
			cmbstatus.setSelectedItem(adm.getStatus());
			String docid = adm.getDocid();
			String posid = adm.getPosid();
			hh.setSelectedValue_st2(cmbdoctors, docid, posid);				
			cmbdoctors.updateUI();
			String nurseid = adm.getNurseid();
			String nposid = adm.getNposid();
			
			hh.setSelectedValue_st2(cmbnurses, nurseid, nposid);				
			cmbnurses.updateUI();		
			cards.show(cardPanel, "edit");
			txadd_date.requestFocus(true);		
		}
	}

//	public void roomscombofill(JComboBox mycombo) {
//		mycombo.removeAllItems();
//		Room A = new Room(0, "");
//		mycombo.addItem(A);
//		String sql = "select roomid, roomname from rooms where capacity > occupied " + " and depid =" + mydepid;
//		try {
//			ResultSet rs = dh.GetData(sql);
//			while (rs.next()) {
//				A = new Room(rs.getInt("roomid"), rs.getString("roomname"));
//				mycombo.addItem(A);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		dh.CloseConnection();
//	}

	private void data_saver() {
		String sql;
		DefaultTableModel d1 = (DefaultTableModel) roomtablee.getModel();
		String startdate = txstartdate.getText();
		if (startdate.equals("____-__-__")) {
			startdate = "";
		}	
		String enddate = txenddate.getText();
		if (enddate.equals("____-__-__")) {
			enddate = "";
		}
        String roomname = txroomname.getText();	
        String roomid = txroomid.getText();
        
		if (hh.zempty(roomname) ||  hh.zempty(startdate)) {	
			JOptionPane.showMessageDialog(null, " Fields are empty !");
		}		
			if (rrowid != "") {
				sql = "update patients_in_rooms set pid= '" + mypid + "',  roomid='" + roomid
						+ "', start_date='" + startdate+ "', end_date='" + enddate 
						+ "',  adid= '" + myadid + "', createdat= '" + hh.currentDate() 
						+ "' where assid = "+ rrowid;
						
			} else {
				sql = "insert into patients_in_rooms  (pid, roomid,  start_date, end_date, adid, createdat) "
			     + "values" + " ('"
						+ mypid + "','" + roomid + "','" + startdate + "', '" + enddate + "','" + myadid 
						+ "', '" + hh.currentDate()+ "')";
			}			
			
			try {
				int flag = dh.Insupdel(sql);
				if (flag > 0) {
					hh.ztmessage("Success", "Message");
					if (rrowid == "") {
						int myid = dd.table_maxid("SELECT MAX(assid) AS max_id from patients_in_rooms");
						d1.insertRow(d1.getRowCount(), new Object[] { myid ,roomid , roomname,
								startdate, enddate, myadid});						
						hh.gotolastrow(roomtablee);
						if (roomtablee.getRowCount() > 0) {
							int row = roomtablee.getRowCount() - 1;
						    roomtablee.setRowSelectionInterval(row, row);
						}
					rrowid = hh.itos(myid);
					} else {
						d1.setValueAt(roomid, rmyrow, 1);
						d1.setValueAt(roomname, rmyrow, 2);
						d1.setValueAt(startdate, rmyrow, 3);
						d1.setValueAt(enddate, rmyrow, 4);	
						d1.setValueAt(myadid, rmyrow, 5);
					}
					room_question (rrowid);
				} else {
					JOptionPane.showMessageDialog(null, "sql error !");
				}
			} catch (Exception e) {
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "sql insert hiba");
			}
				if (roomtablee.getRowCount() > 0) {
							int row = roomtablee.getRowCount() - 1;
							roomtablee.setRowSelectionInterval(row, row);
				}			
			 roomhistory(myadid);
			 clearFields(2);	
	}
	private void buttremovefv() {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) roomtablee.getModel();
		int sIndex = roomtablee.getSelectedRow();
		if (sIndex < 0) {
			return;
		}
		String assid = roomtablee.getValueAt(sIndex, 0).toString();
		String sadid = roomtablee.getValueAt(sIndex, 5).toString();
		String roomid = roomtablee.getValueAt(sIndex, 1).toString();
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
		d1.removeRow(sIndex);
		if (!hh.zempty(assid)) {
			String sql = "delete from patients_in_rooms where assid ='" + assid + "'";
			try {
				flag = dh.Insupdel(sql);
				roomhistory(sadid);
				room_question (roomid);
			} catch (Exception e1) {
				System.out.println("sql error!!!");
			}
		}	
		clearFields(2);
	}
	}

	private void clearFields(int inumber) {
		if (inumber == 2) {
			txstartdate.setText("");
			txenddate.setText("");
			txroomname.setText("");
			txroomid.setText("");		
			rrowid = "";
			rmyrow = 0;
		} else if (inumber == 1) {
			rowid = "";
			myrow = 0;
			txpatientid.setText("");
			txpatientname.setText("");
			txadd_date.setText("");
			txdischargedate.setText("");
			cmbstatus.setSelectedIndex(0);
			Employee A = new Employee("", "", "", "");
			cmbdoctors.getModel().setSelectedItem(A);
			cmbnurses.getModel().setSelectedItem(A);
		}
	}

	private void atable_update(String swhere) {
		DefaultTableModel m1 = (DefaultTableModel) atable.getModel();
		m1.setRowCount(0);
		String Sql = "select  a.adid, a.add_date, a.discharge_date, a.pid, p.pname,"
				+ " a.docid, e.name, a.posid, a.nurseid, e1.name as nname, a.nposid, a.status from admission a "
				+ "  join patient p  on a.pid = p.pid " 
				+ "  join employees e  on a.docid = e.empid "
				+ "  join employees e1  on a.nurseid	 = e1.empid " + swhere;
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String adid = rs.getString("adid");
				String addate = rs.getString("add_date");
				String dischargedate = rs.getString("discharge_date");
				if (dischargedate.equals("____-__-__")) {
					dischargedate = "";
				}
				String pid = rs.getString("pid");
				String pname = rs.getString("pname");
				String docname = rs.getString("name");
				String docid = rs.getString("docid");
				String posid = rs.getString("posid");
				String nursename = rs.getString("nname");
				String nurseid = rs.getString("nurseid");
				String nposid = rs.getString("nposid");
				String status = rs.getString("status");
				m1.addRow(new Object[] { adid, addate, dischargedate, pid, pname, docid, docname, posid, nurseid,
						nursename, nposid, status });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "adid", "Admission date", "Discharge date", "Patientid", "Patient name", "docid", "Doctor",
				"posid", "nurseid", "Nurse","nposid", "Status" };
		((DefaultTableModel) atable.getModel()).setColumnIdentifiers(fej);

		int[] wcols0 = { 0, 5, 7, 8, 10};
		hh.cellswidth_0(atable, wcols0);

		int[][] wcols = { { 1, 120 }, { 2, 120 }, { 3, 80 }, { 4, 220 }, { 6, 220 }, { 9, 200 }, { 11, 150 } };
		hh.cellswidth(atable, wcols);

		atable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				atable.scrollRectToVisible(atable.getCellRect(atable.getRowCount() - 1, 0, true));
			}
		});
		if (atable.getRowCount() > 0) {
			int row = atable.getRowCount() - 1;
			atable.setRowSelectionInterval(row, row);
		}
	}
	private void roomhistory(String padid) {
		DefaultTableModel m1 = (DefaultTableModel) roomtablet.getModel();
		m1.setRowCount(0);
		String sql = " select r.roomname, p.start_date, p.end_date  from patients_in_rooms p"
				+ " left join rooms r on p.roomid = r.roomid"
				+ " where adid='" + padid + "'";
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String roomname = rs.getString("roomname");
				String startdate = rs.getString("start_date");
				String enddate = rs.getString("end_date");			
				m1.addRow(new Object[] { roomname, startdate, enddate });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
	}
	public void roomview(String myadid) {
		DefaultTableModel m1 = (DefaultTableModel) roomtablee.getModel();
		m1.setRowCount(0);	
		String sql = " select p.assid, p.roomid, r.roomname, p.start_date, p.end_date, p.adid  from "
				+ " patients_in_rooms p "
				+  " left join rooms r on p.roomid = r.roomid "
				+ " where adid='" + myadid + "'";
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String assid = rs.getString("assid");
				String roomid = rs.getString("roomid");
				String roomname= rs.getString("roomname");
				String start_date = rs.getString("start_date");
				String end_date = rs.getString("end_date");
				m1.addRow(new Object[] { assid, roomid, roomname, start_date, end_date, myadid });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
	}	
	private void data_delete() {
		int row = atable.getSelectedRow();
		if (row >= 0) {
			rowid = atable.getValueAt(row, 0).toString();
			String sql = "delete from admission where adid =";
			int flag = dd.data_delete(atable, sql);
			if (flag > 0) {
				sql = "delete from patients_in_rooms where adid ='" + rowid + "'";
				try {
					flag = dh.Insupdel(sql);
				} catch (Exception e1) {
					System.out.println("sql error!!!");
				}
			}
		}
	}
	
	private void sqlgyart() {
		int jelem = 0;
		if (rname.isSelected()) {
			jelem = 1;
		} else if (rpatid.isSelected()) {
			jelem = 2;			
		} else {
			String qual = "Did not choose !";
			JOptionPane.showMessageDialog(null, qual);
		}
		if (jelem < 1 || hh.zempty(txsearch.getText())) {
			return;
		}
		String sql = "";
		String ss = txsearch.getText().trim().toLowerCase();

		if (jelem == 1) {			
			sql = " where  lower(p.pname) LIKE '%" + ss + "%'  and a.depid = '" + mydepid +"'"
					+ " order by p.pname " + " COLLATE NOCASE ASC";
		} else if (jelem == 2) {
			sql = " where a.pid LIKE '%" + ss + "%'   and depid = '" + mydepid +"'";
		}
		atable_update(sql);
	}

	public void passtopatient(String patientid, String patientname) {
		txpatientname.setText(patientname);
		txpatientid.setText(patientid);
	}
	public void passtoadm(String roomid, String roomname) {
		txroomid.setText(roomid);
		txroomname.setText(roomname);
		txstartdate.requestFocus(true);
	}
	

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Admissionb();
				} catch (Exception e) {
					e.printStackTrace();
				}	
				}
		});
	}
	
	private void room_question (String sroomid) {	
    SwingUtilities.invokeLater(() -> { 
    	Roomdialog dialog = new Roomdialog(myframe, "Room Occupying", sroomid);
        dialog.setVisible(true);       	
    });    
	}
	
	private void mehet(String mit, int ii) {
		System.out.println(ii);
	}

	CardLayout cards;
	JPanel tablepanel, cardPanel, ePanel, tPanel, searchPanel, roomPanel, gePanel;
	JLabel lbheader, lbsearch, lbreason, lbpatientid, lbrooms, lbadddate, lbdischargedate, lbstatus;
	JLabel lbdoctor, lbnurse, lbreasonw, lbassdate, lbenddate, lbroomtime, lbroomheader;

	JTable atable, roomtablee, roomtablet;
	JButton btnsearch, btnclear, btnnew, btndelete, btnupdate, btnpatient, btnrooms, btnsave,
	btncancel, btnadd, btnsaver,  btnremover, btncancelr, btnback, btnroomassign;
	JRadioButton rname, rpatid;
	ButtonGroup btnGroup = new ButtonGroup();
	JTextField txsearch, txpatientid, txpatientname, txadd_date, txdischargedate, txstartdate, txenddate,
	txroomname, txroomid;
	JScrollPane aPane, txapaner, txapanew, roomtpane, roomepane;
	JTextArea txareasonw, txareasonr;
	JComboBox  cmbstatus, cmbdoctors, cmbnurses;
}
