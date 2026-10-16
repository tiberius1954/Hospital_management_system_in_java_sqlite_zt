package Main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import Classes.Allvalid;
import Classes.Employee;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Patient;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Position;
import Databaseop.Databaseop.Specialization;
import Databaseop.Databaseop.Cities;

public class Employees extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Allvalid allv = new Allvalid();
	String rowid = "";
	int myrow = 0;
	int myid = 0;
	JFrame myframe = this;
	String sfrom = "";
	JFrame pframe;
	String pheader[];
	Employee empee;
	String mytype = "";
	String headertext="";

	Employees(JFrame parent, String emptype) {
		mytype = emptype;
		pframe=parent;
		init();
		dd.employeetable_update(etable, pheader,  " where emptype='" + mytype+"'");
		dd.countrycombofill(cmbcountries);
		dd.cmbcitiescombofill(cmbcities);
		dd.cmbpositioncombofill(cmbposition, mytype);
		dd.cmbspecicombofill(cmbspecialization, mytype);
		hh.iconhere(this);
	}
	
	Employees() {
		init();
		dd.employeetable_update(etable, pheader, "");
		dd.countrycombofill(cmbcountries);
		dd.cmbcitiescombofill(cmbcities);
		dd.cmbpositioncombofill(cmbposition, mytype);
		dd.cmbspecicombofill(cmbspecialization, mytype);
		hh.iconhere(this);
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
				} else {
					cards.show(cardPanel, "table");
				}
			}
		});
         if (mytype=="d") {
	            	setTitle("Doctors");
	            	headertext="DOCTORS";
         } else if (mytype=="n") {
        	 setTitle("Nurses");
        	 headertext="NURSES";
         } else if(mytype=="s") {
        	 setTitle("Staffs"); 
        	 headertext="STAFFS";
         }
		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(1260, 735);
		setLayout(null);
		setLocationRelativeTo(null);
		lbheader = hh.fflabel(headertext);
		lbheader.setBounds(20, 5, 150, 30);
		lbheader.setForeground(new Color(255, 255, 255));
		add(lbheader);

		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setLayout(cards);
		cardPanel.setBounds(10, 40, 1220, 635);
		cardPanel.setBorder(hh.border4);
		cardPanel.setBackground(new Color(178, 216, 216));

		tPanel = maketpanel();
		tPanel.setName("table");
		cardPanel.add(tPanel, "table");

		ePanel = makeepanel();
		ePanel.setName("edit");
		cardPanel.add(ePanel, "edit");

		add(cardPanel);

		cards.show(cardPanel, "table");
		// cards.show(cardPanel, "edit");
		setVisible(true);

	}

	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);
		ttpanel.setBackground(new Color(178, 216, 216));
		ttpanel.setBounds(10, 10, 1180, 580);
		searchPanel = new JPanel(null);
		searchPanel.setBounds(220, 10, 720, 45);
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
		rempid = new JRadioButton("Employeeid");
		rempid.setHorizontalTextPosition(SwingConstants.LEFT);
		rempid.setActionCommand("employeeid");
		rempid.setBounds(160, 10, 140, 25);
		rempid.setFont(new Font("Tahoma", Font.BOLD, 16));
		rempid.setBackground(Color.ORANGE);
		btnGroup.add(rempid);

		rtel = new JRadioButton("Phone");
		rtel.setHorizontalTextPosition(SwingConstants.LEFT);
		rtel.setActionCommand("tel");
		rtel.setBounds(305, 10, 80, 25);
		rtel.setFont(new Font("Tahoma", Font.BOLD, 16));
		rtel.setBackground(Color.ORANGE);
		btnGroup.add(rtel);
		searchPanel.add(rname);
		searchPanel.add(rempid);
		searchPanel.add(rtel);

		txsearch = hh.cTextField(25);
		txsearch.setBounds(390, 10, 200, 25);
		searchPanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(590, 10, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");
		searchPanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				dd.employeetable_update(etable, pheader, " where emptype='" + mytype+"'");
			}
		});
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setBounds(620, 10, 90, 27);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		searchPanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});
		rname.setSelected(true);
		etable = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) etable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		etable.setTableHeader(new JTableHeader(etable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		etable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) etable.getModel();
				try {
					int row = etable.getSelectedRow();
					if (row > -1) {
						String mypid = model.getValueAt(row, 0).toString();
						empee = dd.getemployee_record(mypid);
						String remark = empee.getRemark();
						txaremarkt.setText(remark);
						dd.spectabletview(spectablet, mypid);
						dd.posetabletview(postablet, mypid);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		hh.madeheader(etable);
		etable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		etable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		ePane = new JScrollPane(etable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pheader = new String[] { "Employeeid", "Name", "Phone", "Email", "Gender", "Country", "cid",
				"City", "Street", "date of join", "Dob", "Status" };

		etable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null, null, null, null } },
				pheader));

		ePane.setViewportView(etable);
		ePane.setBounds(30, 70, 1160, 300);
		ePane.setBorder(hh.myRaisedBorder);
		ttpanel.add(ePane);
		
		int[] wcols0 = { 6};
		hh.cellswidth_0(etable, wcols0);

		int[][] wcols = { { 0, 100 }, { 1, 250 }, { 2, 200 }, { 3, 300 }, { 4, 100 }, { 5, 200 }, { 7, 200 },
				{ 8, 300 }, { 9, 100 }, { 10, 100 }, { 11, 100 } };
		hh.cellswidth(etable, wcols);

		lbspecialization = hh.clabel("Specializations");
		lbspecialization.setBounds(130, 390, 140, 25);
		ttpanel.add(lbspecialization);

		spectablet = hh.ztable();
		spectablet.setTableHeader(new JTableHeader(spectablet.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(spectablet);
		spectablet.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				spectablet.scrollRectToVisible(spectablet.getCellRect(spectablet.getRowCount() - 1, 0, true));
			}
		});

		spectPane = new JScrollPane(spectablet, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		spectablet.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "emsid", "Name", "sid", "empid" }));
		hh.setJTableColumnsWidth(spectablet, 320, 0, 320, 0, 0);
		spectPane.setViewportView(spectablet);
		spectPane.setBounds(30, 420, 320, 170);
		spectPane.setBorder(hh.borderf);
		ttpanel.add(spectPane);

		lbremarks = hh.clabel("Remarks");
		lbremarks.setBounds(470, 390, 100, 25);
		ttpanel.add(lbremarks);

		txaremarkt = hh.cTextarea();
		txaremarkt.setEditable(false);
		txaremarkt.setFocusable(false);

		remtPane = new JScrollPane(txaremarkt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		remtPane.setBorder(hh.borderf);
		remtPane.setBounds(360, 420, 340, 170);
		remtPane.setViewportView(txaremarkt);
		ttpanel.add(remtPane);

		lbpositions = hh.clabel("Positions");
		lbpositions.setBounds(770, 390, 140, 25);
		ttpanel.add(lbpositions);

		postablet = hh.ztable();
		postablet.setTableHeader(new JTableHeader(postablet.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(postablet);
		postablet.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				postablet.scrollRectToVisible(postablet.getCellRect(postablet.getRowCount() - 1, 0, true));
			}
		});

		postPane = new JScrollPane(postablet, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		postablet.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "emsid", "Name", "sid", "empid" }));
		hh.setJTableColumnsWidth(postablet, 320, 0, 320, 0, 0);
		postPane.setViewportView(postablet);
		postPane.setBounds(710, 420, 320, 170);
		postPane.setBorder(hh.borderf);
		ttpanel.add(postPane);

		btnnew = gr.sbcs("New");
		btnnew.setBounds(1050, 435, 125, 35);
		btnnew.setForeground(Color.black);
		btnnew.setBackground(hh.lpiros);
		ttpanel.add(btnnew);
		btnnew.addActionListener(e -> data_new());

		btnupdate = gr.sbcs("Update");
		btnupdate.setBounds(1050, 480, 125, 35);
		btnupdate.setBackground(hh.vvzold);
		ttpanel.add(btnupdate);
		btnupdate.addActionListener(e -> data_update());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(1050, 525, 125, 35);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);
		ttpanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());
		return ttpanel;
	}

	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setBackground(new Color(178, 216, 216));
		eepanel.setBounds(10, 10, 1180, 580);
		JPanel lpanel = new JPanel(null);
		lpanel.setBounds(10, 10, 570, 610);
		lpanel.setBorder(hh.ztroundborder(hh.cianzt));
		lpanel.setBackground(new Color(178, 216, 216));
		eepanel.add(lpanel);
		JPanel rpanel = new JPanel(null);
		rpanel.setBounds(585, 10, 620, 610);
		rpanel.setBorder(hh.ztroundborder(hh.cianzt));
		rpanel.setBackground(new Color(178, 216, 216));
		eepanel.add(rpanel);

		lbemployeeid = hh.clabel("EmployeeID");
		lbemployeeid.setBounds(10, 10, 100, 25);
		lpanel.add(lbemployeeid);

		txempid = cTextField(150);
		txempid.setBounds(120, 10, 120, 25);
		lpanel.add(txempid);
		
		lberror = hh.clabel("");
		lberror.setHorizontalAlignment(JLabel.LEFT);
		lberror.setBounds(250, 10, 240, 25);
		lberror.setForeground(hh.vpiros);
		lpanel.add(lberror);

		lbename = hh.clabel("Name");
		lbename.setBounds(10, 50, 100, 25);
		lpanel.add(lbename);

		txname = hh.cTextField(200);
		txname.setBounds(120, 50, 300, 25);
		lpanel.add(txname);
		txname.addKeyListener(hh.MUpper());

		lbphone = hh.clabel("Phone");
		lbphone.setBounds(10, 90, 100, 25);
		lpanel.add(lbphone);

		txphone = hh.cTextField(200);
		txphone.setBounds(120, 90, 300, 25);
		lpanel.add(txphone);
		txphone.addKeyListener(hh.Onlyphone());

		lbemail = hh.clabel("Email");
		lbemail.setBounds(10, 130, 100, 25);
		lpanel.add(lbemail);

		txemail = hh.cTextField(200);
		txemail.setBounds(120, 130, 300, 25);
		lpanel.add(txemail);

		lbgender = hh.clabel("Gender");
		lbgender.setBounds(10, 170, 100, 25);
		lpanel.add(lbgender);

		cmbgender = hh.cbcombo();
		cmbgender.setModel(new DefaultComboBoxModel(new String[] { "Female", "Male" }));
		cmbgender.setBounds(120, 170, 150, 27);
		cmbgender.setFont(hh.textf);
		lpanel.add(cmbgender);

		lbcountries = hh.clabel("Country");
		lbcountries.setBounds(10, 210, 100, 25);
		lpanel.add(lbcountries);

		cmbcountries = hh.cbcombo();
		cmbcountries.setBounds(120, 210, 250, 25);
		lpanel.add(cmbcountries);

		lbcities = hh.clabel("City");
		lbcities.setBounds(10, 250, 100, 25);
		lpanel.add(lbcities);

		cmbcities = hh.cbcombo();
		cmbcities.setName("cities");
		cmbcities.setFont(hh.textf);
		cmbcities.setBounds(120, 250, 250, 27);
		lpanel.add(cmbcities);

		btnnewcity = gr.sbcs("New");
		btnnewcity.setFocusable(false);
		btnnewcity.setForeground(Color.black);
		btnnewcity.setBackground(Color.ORANGE);
		btnnewcity.setBounds(380, 250, 80, 32);
		btnnewcity.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnnewcity.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		lpanel.add(btnnewcity);
		btnnewcity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Lcities ci = new Lcities(myframe, "employees");
				ci.setLayout(null);
				ci.setLocationRelativeTo(null);
				ci.setVisible(true);
			}
		});

		lbstreet = hh.clabel("Street");
		lbstreet.setBounds(10, 290, 100, 25);
		lpanel.add(lbstreet);

		txstreet = hh.cTextField(350);
		txstreet.setBounds(120, 290, 440, 25);
		lpanel.add(txstreet);
		txstreet.addKeyListener(hh.MUpper());

		lbdob = hh.clabel("DOB");
		lbdob.setBounds(10, 330, 100, 25);
		lpanel.add(lbdob);

		txdob = hh.DatetextField();
		txdob.setBounds(120, 330, 105, 30);
		lpanel.add(txdob);

		lbdateofjoin = hh.clabel("Date of join");
		lbdateofjoin.setBounds(0, 370, 110, 25);
		lpanel.add(lbdateofjoin);

		txdateofjoin = hh.DatetextField();
		txdateofjoin.setBounds(120, 370, 105, 25);
		lpanel.add(txdateofjoin);

		lbstatus = hh.clabel("Status");
		lbstatus.setBounds(10, 410, 100, 25);
		lpanel.add(lbstatus);

		cmbstatus = hh.cbcombo();
		cmbstatus.setModel(new DefaultComboBoxModel(new String[] { "Active", "Deactive", "Deceased" }));
		cmbstatus.setBounds(120, 410, 150, 27);
		cmbstatus.setFont(hh.textf);
		lpanel.add(cmbstatus);

		btnsave = gr.sbcs("Save all");
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		btnsave.setBounds(120, 470, 120, 32);
		btnsave.setFocusable(false);
		lpanel.add(btnsave);
		btnsave.addActionListener(e -> data_save());

		btncancel = gr.sbcs("Cancel all");
		btncancel.setForeground(Color.black);
		btncancel.setBackground(hh.vvzold);
		btncancel.setBounds(250, 470, 120, 32);
		btncancel.setFocusable(false);
		lpanel.add(btncancel);
		btncancel.addActionListener(e -> cancelall());

		lbremark = hh.clabel("Remark");
		lbremark.setBounds(240, 5, 100, 25);
		rpanel.add(lbremark);

		txaremarke = hh.cTextarea();
		remePane = new JScrollPane(txaremarke, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		remePane.setBounds(70, 30, 490, 180);
		remePane.setBorder(hh.borderf);
		remePane.setViewportView(txaremarke);
		rpanel.add(remePane);

		specePanel = new JPanel(null);
		specePanel.setBounds(40, 220, 545, 180);
		specePanel.setBorder(hh.borderf);
		specePanel.setBackground(new Color(178, 216, 216));
		rpanel.add(specePanel);

		lbspecial = hh.clabel("Specialization");
		lbspecial.setBounds(20, 15, 130, 25);
		specePanel.add(lbspecial);

		cmbspecialization = hh.cbcombo();
		cmbspecialization.setBounds(160, 15, 280, 25);
		specePanel.add(cmbspecialization);

		btnadd = gr.sbcs("Add");
		btnadd.setForeground(Color.black);
		btnadd.setBackground(hh.lpiros);
		btnadd.setBounds(400, 75, 120, 32);
		btnadd.setFocusable(false);
		specePanel.add(btnadd);
		btnadd.addActionListener(e -> buttadds());

		btnremove = gr.sbcs("Remove");
		btnremove.setForeground(Color.black);
		btnremove.setBackground(Color.green);
		btnremove.setBounds(400, 115, 120, 32);
		btnremove.setFocusable(false);
		specePanel.add(btnremove);
		btnremove.addActionListener(e -> buttremoves());

		spectablee = hh.ztable();
		spectablee.setTableHeader(new JTableHeader(spectablee.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		hh.madeheader(spectablee);
		spectablee.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				spectablee.scrollRectToVisible(spectablee.getCellRect(spectablee.getRowCount() - 1, 0, true));
			}
		});

		specePane = new JScrollPane(spectablee, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		spectablee.setModel(
				new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "emsid", "Name", "sid" }));
		hh.setJTableColumnsWidth(spectablee, 350, 0, 350, 0);
		specePane.setViewportView(spectablee);
		specePane.setBounds(20, 55, 360, 110);
		specePane.setBorder(hh.borderf);
		specePanel.add(specePane);

		posePanel = new JPanel(null);
		posePanel.setBounds(40, 412, 545, 180);
		posePanel.setBorder(hh.borderf);
		posePanel.setBackground(new Color(178, 216, 216));
		rpanel.add(posePanel);

		lbposition = hh.clabel("Position");
		lbposition.setBounds(20, 15, 130, 25);
		posePanel.add(lbposition);

		cmbposition = hh.cbcombo();
		cmbposition.setBounds(160, 15, 280, 25);
		posePanel.add(cmbposition);

		btnaddp = gr.sbcs("Add");
		btnaddp.setForeground(Color.black);
		btnaddp.setBackground(hh.lpiros);
		btnaddp.setBounds(400, 75, 120, 32);
		btnaddp.setFocusable(false);
		posePanel.add(btnaddp);
		btnaddp.addActionListener(e -> buttaddp());

		btnremovep = gr.sbcs("Remove");
		btnremovep.setForeground(Color.black);
		btnremovep.setBackground(Color.green);
		btnremovep.setBounds(400, 115, 120, 32);
		btnremovep.setFocusable(false);
		posePanel.add(btnremovep);
		btnremovep.addActionListener(e -> buttremovep());

		posetablee = hh.ztable();
		posetablee.setTableHeader(new JTableHeader(posetablee.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		hh.madeheader(posetablee);
		posetablee.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				spectablee.scrollRectToVisible(posetablee.getCellRect(posetablee.getRowCount() - 1, 0, true));
			}
		});

		posePane = new JScrollPane(posetablee, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		posetablee.setModel(
				new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "eposid", "Name", "posid" }));
		hh.setJTableColumnsWidth(posetablee, 350, 0, 350, 0);
		posePane.setViewportView(posetablee);
		posePane.setBounds(20, 55, 360, 110);
		posePane.setBorder(hh.borderf);
		posePanel.add(posePane);
		return eepanel;	}

	private void clearFields(int num) {
		if (num == 1) {
			rowid = "";
			myrow = 0;
			txempid.setText("");
			txempid.setEnabled(true);
			txname.setText("");
			txphone.setText("");
			txemail.setText("");
			txstreet.setText("");
			txdateofjoin.setText("");
			txdob.setText("");
			txaremarke.setText("");
			cmbstatus.setSelectedIndex(0);
			cmbcities.setSelectedIndex(0);
			cmbcountries.setSelectedIndex(0);
			cmbgender.setSelectedIndex(0);
			cmbspecialization.setSelectedIndex(0);
			cmbposition.setSelectedIndex(0);
			DefaultTableModel m1 = (DefaultTableModel) spectablee.getModel();
			m1.setRowCount(0);
			DefaultTableModel m2 = (DefaultTableModel) posetablee.getModel();
			m2.setRowCount(0);
		}
	};
	private void cancelall() {
		clearFields(1);
		cards.show(cardPanel, "table");		
	}

	private void data_new() {
		clearFields(1);
		txdateofjoin.setText(hh.currentDate());
		cards.show(cardPanel, "edit");
		txempid.requestFocus(true);
	}

	private void data_update() {
		int row = etable.getSelectedRow();
		if (row >= 0) {
			clearFields(1);
			txempid.setEnabled(false);
			rowid = etable.getValueAt(row, 0).toString();
			myrow = row;
			empee = dd.getemployee_record(rowid);
			txempid.setText(empee.getEmpid());
			txname.setText(empee.getName());
			txphone.setText(empee.getPhone());
			txemail.setText(empee.getEmail());
			txstreet.setText(empee.getStreet());
			txdateofjoin.setText(empee.getDateofjoin());
			txdob.setText(empee.getDob());
			txaremarke.setText(empee.getRemark());
			cmbstatus.setSelectedItem(empee.getStatus());
			int cid = empee.getCid();
			hh.setSelectedValue(cmbcities, cid);
			cmbcountries.setSelectedItem(empee.getCountry());
			cmbgender.setSelectedItem(empee.getGender());
			dd.posetabletview(posetablee, rowid);
			dd.spectabletview(spectablee, rowid);
			cards.show(cardPanel, "edit");
			txname.requestFocus();
		}
	}

	private void data_delete() {
		String srowid ="";
		int row = etable.getSelectedRow();
		if (row >= 0) {
			srowid = etable.getValueAt(row, 0).toString();
			String sql = "delete from employees where empid =";
			int flag = dd.data_delete(etable, sql);
			if (flag > 0) {			
				try {
					sql = "delete from employee_position where empid ='" + srowid + "'";
					flag = dh.Insupdel(sql);
					sql = "delete from employee_specialization where empid ='" + srowid + "'";
					flag = dh.Insupdel(sql);
				} catch (Exception e1) {
					System.out.println("sql error!!!");
				}
			}
		}
	}

	private void data_save() {
		String sql = "";
		DefaultTableModel d1 = (DefaultTableModel) etable.getModel();
		Boolean newrec = false;
		String empid = txempid.getText();
		String name = txname.getText();
		String phone = txphone.getText();
		String email = txemail.getText();
		String gender = (String) cmbgender.getSelectedItem();
		String country = "";
		if (cmbcountries.getSelectedItem() != null) {
			country = (String) cmbcountries.getSelectedItem();
		}
		int cid = (int) ((Cities) cmbcities.getSelectedItem()).getCid();
		String city = (String) ((Cities) cmbcities.getSelectedItem()).toString();
		String street = txstreet.getText();
		String dob = txdob.getText();
		String dateofjoin = txdateofjoin.getText();
		String remark = txaremarke.getText();
		String status = (String) cmbstatus.getSelectedItem();
		String emptype = mytype;
		if (rowid == "") {
			newrec = true;
		}

		if (evalidation(empid, name, phone, country, street, city, dob, dateofjoin, newrec) == false) {
			return;
		}

		if (rowid != "") {
			sql = "update  employees set empid = '" + empid + "', name= '" + name + "', " + " gender = '" + gender
					+ "', phone = '" + phone + "',  emptype='" + mytype + "',  email='" + email + "', country='"
					+ country + "', cid= " + cid + ", street= '" + street + "', dob='" + dob + "', dateofjoin='"
					+ dateofjoin + "', remark='" + remark + "', status= '" + status + "' where empid = '" + rowid +"'";
		} else {
			newrec = true;
			sql = "insert into employees (empid, name, gender, phone, emptype, email, country, "
					+ " cid, street, dob, dateofjoin, remark, status) values (" + " '" + empid + "','" + name + "','"
					+ gender + "','" + phone + "','" + mytype + "','" + email + "','" + country + "'," + cid + ",'"
					+ street + "','" + dob + "','" + dateofjoin + "','" + remark + "','" + status + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					d1.addRow(new Object[] { empid, name, phone, email, gender, country, cid, city, street, dateofjoin,
							dob, status });
					hh.gotolastrow(etable);
					position_save(newrec, empid);
					speci_save(newrec, empid);
				} else {
					d1.setValueAt(empid, myrow, 0);
					d1.setValueAt(name, myrow, 1);
					d1.setValueAt(phone, myrow, 2);
					d1.setValueAt(email, myrow, 3);
					d1.setValueAt(gender, myrow, 4);
					d1.setValueAt(country, myrow, 5);
					d1.setValueAt(cid, myrow, 6);
					d1.setValueAt(city, myrow, 7);
					d1.setValueAt(street, myrow, 8);
					d1.setValueAt(dateofjoin, myrow, 9);
					d1.setValueAt(dob, myrow, 10);
					d1.setValueAt(status, myrow, 11);
					position_save(newrec, empid);
					speci_save(newrec, empid);
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql if  error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		dd.spectabletview(spectablet, empid);
		dd.posetabletview(postablet, empid);
		txaremarkt.setText(remark);
		clearFields(1);
		cards.show(cardPanel, "table");
	}

	private void buttaddp() {
		DefaultTableModel d1 = (DefaultTableModel) posetablee.getModel();
		int posid = (int) ((Position) cmbposition.getSelectedItem()).getPosid();
		String name = (String) ((Position) cmbposition.getSelectedItem()).getName();

		if (!hh.zempty(name)) {
			d1.addRow(new Object[] { " ", name, posid });
		} else {
			JOptionPane.showMessageDialog(null, " Name is empty !");
		}
		cmbposition.requestFocus(true);
		if (posetablee.getRowCount() > 0) {
			int row = posetablee.getRowCount() - 1;
			posetablee.setRowSelectionInterval(row, row);
		}
	}

	private void buttremovep() {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) posetablee.getModel();
		int sIndex = posetablee.getSelectedRow();
		if (sIndex < 0) {
			return;
		}
		String eposid = posetablee.getValueAt(sIndex, 0).toString();
		d1.removeRow(sIndex);
		if (!hh.zempty(eposid)) {
			String sql = "delete from employee_position where eposid ='" + eposid + "'";
			try {
				flag = dh.Insupdel(sql);
			} catch (Exception e1) {
				System.out.println("sql error!!!");
			}
		}
	}

	private void buttadds() {
		DefaultTableModel d1 = (DefaultTableModel) spectablee.getModel();
		int sid = (int) ((Specialization) cmbspecialization.getSelectedItem()).getSid();
		String name = (String) ((Specialization) cmbspecialization.getSelectedItem()).getName();

		if (!hh.zempty(name)) {
			d1.addRow(new Object[] { " ", name, sid, "" });
		} else {
			JOptionPane.showMessageDialog(null, " Name is empty !");
		}
		cmbspecialization.requestFocus(true);
		if (spectablee.getRowCount() > 0) {
			int row = spectablee.getRowCount() - 1;
			spectablee.setRowSelectionInterval(row, row);
		}
	}

	private void buttremoves() {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) spectablee.getModel();
		int sIndex = spectablee.getSelectedRow();
		if (sIndex < 0) {
			return;
		}
		String emsid = spectablee.getValueAt(sIndex, 0).toString();
		d1.removeRow(sIndex);
		if (!hh.zempty(emsid)) {
			String sql = "delete from employee_specialization where emsid ='" + emsid + "'";
			try {
				flag = dh.Insupdel(sql);
			} catch (Exception e1) {
				System.out.println("sql error!!!");
			}
		}
		// clearFields(2);
	}

	private Boolean evalidation(String empid, String name, String phone, String country, String street, String city,
			String dob, String dateofjoin, Boolean newrec) {
		Boolean ret = true;
		ArrayList<String> err = new ArrayList<String>();

		if (!allv.namevalid(name)) {
			err.add(allv.mess);
			ret = false;
		}
		if (!allv.countryvalid(country)) {
			err.add(allv.mess);
			ret = false;
		}
		if (!allv.cityvalid(city)) {
			err.add(allv.mess);
			ret = false;
		}
		if (!allv.streetvalid(street)) {
			err.add(allv.mess);
			ret = false;
		}
		if (!allv.phonevalid(phone)) {
			err.add(allv.mess);
			ret = false;
		}
		if (!allv.dobvalid(dob)) {
			err.add(allv.mess);
			ret = false;
		}

		if (!allv.dateofjoinvalid(dateofjoin)) {
			err.add(allv.mess);
			ret = false;
		}
		if (!allv.empidvalid(empid, newrec)) {
			err.add(allv.mess);
			ret = false;
		}

		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
		return ret;
	}

	private void position_save(Boolean newrec, String empid) {
		String sql = "";
		if (newrec == true) {
			if (posetablee.getRowCount() > 0) {
				for (int i = 0; i < posetablee.getRowCount(); i++) {
					String posid = posetablee.getValueAt(i, 2).toString();
					sql = "insert into employee_positions (posid , empid) values('" + posid + "','" + empid + "')";
					try {
						int flag = dh.Insupdel(sql);
					} catch (Exception e) {
						System.err.println("SQLException: " + e.getMessage());
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "sql insert hiba");
					}
				}
			}
		} else {
			if (posetablee.getRowCount() > 0) {
				for (int i = 0; i < posetablee.getRowCount(); i++) {
					String posid = posetablee.getValueAt(i, 2).toString();
					String eposid = posetablee.getValueAt(i, 0).toString();
					if (hh.zempty(eposid)) {
						sql = "insert into employee_positions (posid, empid) values('" + posid + "','" + empid + "')";
					} else {
						sql = "update employee_positions set posid = '" + posid + "'where eposid = '" + eposid + "'";
					}
					try {
						int flag = dh.Insupdel(sql);
					} catch (Exception e) {
						System.err.println("SQLException: " + e.getMessage());
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "sql insert hiba");
					}
				}
			}
		}
	}

	private void speci_save(Boolean newrec, String empid) {
		String sql = "";
		if (newrec == true) {

			if (spectablee.getRowCount() > 0) {
				for (int i = 0; i < spectablee.getRowCount(); i++) {
					String sid = spectablee.getValueAt(i, 2).toString();
					sql = "insert into employee_specialization (sid , empid) values('" + sid + "','" + empid + "')";
					try {
						int flag = dh.Insupdel(sql);
					} catch (Exception e) {
						System.err.println("SQLException: " + e.getMessage());
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "sql insert hiba");
					}
				}
			}
		} else {
			if (spectablee.getRowCount() > 0) {
				for (int i = 0; i < spectablee.getRowCount(); i++) {
					String sid = spectablee.getValueAt(i, 2).toString();
					String emsid = spectablee.getValueAt(i, 0).toString();
					if (hh.zempty(emsid)) {
						sql = "insert into employee_specialization (sid, empid) values('" + sid + "','" + empid + "')";
					} else {
						sql = "update employee_specialization set sid = '" + sid + "'where emsid = '" + emsid + "'";
					}
					try {
						int flag = dh.Insupdel(sql);
					} catch (Exception e) {
						System.err.println("SQLException: " + e.getMessage());
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "sql insert hiba");
					}
				}
			}
		}
	}

	private void sqlgyart() {
		int jelem = 0;
		if (rname.isSelected()) {
			jelem = 1;
		} else if (rempid.isSelected()) {
			jelem = 2;
		} else if (rtel.isSelected()) {
			jelem = 3;
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
			sql = "where  lower(e.name) LIKE '%" + ss + "%' and  emptype='" + mytype +"'"
					+ " order by e.name " + " COLLATE NOCASE ASC";
		} else if (jelem == 3) {
			sql = " where e.phone LIKE '%" + ss + "%'  and emptype='" + mytype +  "'"
					+ " order by e.phone COLLATE NOCASE ASC";
		} else if (jelem == 2) {
			sql = " where e.empid LIKE '%" + ss + "%'  and emptype='" + mytype + "'";
		}
		dd.employeetable_update(etable, pheader, sql);
	}

	public void passtocmbcity(int number) {
		dd.cmbcitiescombofill(cmbcities);
		hh.setSelectedValue(cmbcities, number);
		cmbcities.updateUI();
	}

	public JTextField cTextField(int hossz) {
		JTextField textField = new JTextField(hossz);
		textField.setFont(hh.textf);
		textField.setBorder(hh.borderf);
		textField.setBackground(hh.feher);
		textField.setPreferredSize(new Dimension(250, 30));
		textField.setCaretColor(Color.RED);
		textField.setDisabledTextColor(Color.magenta);
		textField.putClientProperty("caretAspectRatio", 0.1);
		// textField.setHorizontalAlignment(JTextField.RIGHT)
		textField.addFocusListener(dFocusListener);
		return textField;
	}

	private final FocusListener dFocusListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
			JComponent c = (JComponent) e.getSource();
			c.setBorder(hh.borderz);
		}

		@Override
		public void focusLost(FocusEvent e) {
			String tartalom = "";
			boolean vissza = true;
			Boolean felt = false;
			if (hh.zempty(rowid)) {
				felt = true;
			}
			JTextField Txt = (JTextField) e.getSource();
			tartalom = Txt.getText();
			if (Txt == txempid && ! hh.zempty(tartalom)) {
				Txt.setText(tartalom);
				vissza = allv.empidvalid(tartalom, felt);
			}
			if (vissza) {
				Txt.setBorder(hh.borderf);
				lberror.setText("");
			} else {
				Txt.setBorder(hh.borderp);
				tartalom = allv.mess;
				lberror.setText(tartalom);
			}
		}
	};

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Employees();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	CardLayout cards;
	JPanel tablepanel, cardPanel, ePanel, tPanel, lPanel, rPanel, searchPanel, specePanel, posePanel;
	JLabel lbheader, lbsearch, lbremarks, lbspecialization, lbpositions, lbemployeeid, lbename, lbemail, lbphone, lbdob,
			lbgender, lbcountries, lbcities, lbstreet, lbdateofjoin, lbstatus;
	JLabel lbremark, lbspecializations, lbspecial, lbposition, lberror;
	JButton btnsearch, btnclear, btnnew, btnupdate, btndelete, btnnewcity, btnadd, btnremove, btnsave, btncancel,
			btnaddp, btnremovep;
	JTextField txsearch, txempid, txname, txphone, txemail, txdob, txstreet, txdateofjoin;
	JRadioButton rname, rtel, rempid;
	ButtonGroup btnGroup = new ButtonGroup();
	JTable etable, spectablet, postablet, spectablee, posetablee;
	JScrollPane ePane, spectPane, remtPane, postPane, remePane, specePane, posePane;
	JTextArea txaremarkt, txaremarke;
	JComboBox cmbgender, cmbcountries, cmbcities, cmbstatus, cmbspecialization, cmbposition;

}
