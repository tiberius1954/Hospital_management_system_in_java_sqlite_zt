package Main;

import java.awt.*;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;
import Classes.Allvalid;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Patient;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Cities;
import Databaseop.Databaseop.Department;;

public class Patients extends JFrame {
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
	protected String[] bloodgnames = dd.bloodnames();

	Patients() {
		init();
		dd.patienttable_update(ptable, pheader, "");
		cmbbloodg.setModel(new DefaultComboBoxModel(bloodgnames));
		dd.countrycombofill(cmbcountries);
		dd.cmbcitiescombofill(cmbcities);
		hh.iconhere(this);
	}
	
	Patients(JFrame parent, String wfrom) {
		pframe = parent;
		sfrom = wfrom;
		init();
		dd.patienttable_update(ptable, pheader, "");
		cmbbloodg.setModel(new DefaultComboBoxModel(bloodgnames));
		dd.countrycombofill(cmbcountries);
		dd.cmbcitiescombofill(cmbcities);
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

		setTitle("Patients");
		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(1210, 735);
		setLayout(null);
		setLocationRelativeTo(null);
		lbheader = hh.fflabel("Patients");
		lbheader.setBounds(20, 10, 150, 35);
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

		add(cardPanel);

		cards.show(cardPanel, "table");
		// cards.show(cardPanel, "edit");
		setVisible(true);
	}

	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);
		ttpanel.setBackground(new Color(178, 216, 216));
		ttpanel.setBounds(10, 10, 1140, 580);
		searchPanel = new JPanel(null);
		searchPanel.setBounds(220, 10, 685, 45);
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

		rtel = new JRadioButton("Phone");
		rtel.setHorizontalTextPosition(SwingConstants.LEFT);
		rtel.setActionCommand("tel");
		rtel.setBounds(265, 10, 80, 25);
		rtel.setFont(new Font("Tahoma", Font.BOLD, 16));
		rtel.setBackground(Color.ORANGE);
		btnGroup.add(rtel);
		searchPanel.add(rname);
		searchPanel.add(rpatid);
		searchPanel.add(rtel);

		txsearch = hh.cTextField(25);
		txsearch.setBounds(350, 10, 200, 25);
		searchPanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(550, 10, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");
		searchPanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				dd.patienttable_update(ptable, pheader, "");
			}
		});
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setBounds(582, 10, 90, 27);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		searchPanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});
		rname.setSelected(true);
		ptable = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) ptable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		ptable.setTableHeader(new JTableHeader(ptable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		ptable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) ptable.getModel();
				try {
					int row = ptable.getSelectedRow();
					if (row > -1) {
						String mypid = model.getValueAt(row, 0).toString();
						pat = dd.getpatientrecord(mypid);
						String remark = pat.getRemark();
						txaremarkt.setText(remark);
						dd.reltabletview(reltablet, mypid);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		hh.madeheader(ptable);
		ptable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ptable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		pPane = new JScrollPane(ptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pheader = new String[] { "PatientID", "Name", "Gender", "Dob", "Phone", "Email", "Blood", 
				"Country", "cid", "City", "Street", "Create at", "Status" };

		ptable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {
						{ null, null, null, null, null, null, null, null, null, null, null, null, null } },
				pheader));

		pPane.setViewportView(ptable);
		pPane.setBounds(30, 70, 1110, 300);
		pPane.setBorder(hh.myRaisedBorder);
		ttpanel.add(pPane);

		int[] wcols0 = { 8};
		hh.cellswidth_0(ptable, wcols0);

		int[][] wcols = { { 0, 90 }, { 1, 200 }, { 2, 70 }, { 3, 100 }, { 4, 200 }, { 5, 300 }, { 6, 60 },
				{ 7, 150 }, { 9, 200 }, { 10, 300 }, { 11, 100 }, { 12, 80 } };
		hh.cellswidth(ptable, wcols);

		lbrelatives = hh.clabel("Relatives");
		lbrelatives.setBounds(240, 390, 100, 25);
		ttpanel.add(lbrelatives);

		lbremarks = hh.clabel("Remarks");
		lbremarks.setBounds(670, 390, 100, 25);
		ttpanel.add(lbremarks);

		reltablet = hh.ztable();
		reltablet.setTableHeader(new JTableHeader(reltablet.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(reltablet);
		reltablet.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				reltablet.scrollRectToVisible(reltablet.getCellRect(reltablet.getRowCount() - 1, 0, true));
			}
		});

		reltPane = new JScrollPane(reltablet, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		reltablet.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "rid", "Name", "Type", "Phone", "rtip" }));
		hh.setJTableColumnsWidth(reltablet, 500, 0, 50, 25, 25, 0);
		reltPane.setViewportView(reltablet);
		reltPane.setBounds(30, 420, 500, 170);
		reltPane.setBorder(hh.borderf);
		ttpanel.add(reltPane);

		txaremarkt = hh.cTextarea();
		txaremarkt.setEditable(false);
		txaremarkt.setFocusable(false);

		remtPane = new JScrollPane(txaremarkt, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		remtPane.setBorder(hh.borderf);
		remtPane.setBounds(550, 420, 390, 170);
		remtPane.setViewportView(txaremarkt);
		ttpanel.add(remtPane);
		
		btnsendto = gr.sbcs("");
		btnsendto.setBounds(970, 385, 145, 35);
		btnsendto.setBackground(hh.narancs);
		if (sfrom == "weekpanel" ) {
			btnsendto.setText("Send to  back");
			ttpanel.add(btnsendto);
		}else if (sfrom == "choosepatient" ) {
				btnsendto.setText("Send to  back");
				ttpanel.add(btnsendto);
		} else if (sfrom == "choose_patient_department" ) {
			    btnsendto.setText("Send to  back");
		     	ttpanel.add(btnsendto);
	  }else if (sfrom == "admissions" ) {
		btnsendto.setText("Send to  back");
		ttpanel.add(btnsendto);
      }else if (sfrom == "where_is_the_patient" ) {
  	    	btnsendto.setText("Send to  back");
  		    ttpanel.add(btnsendto);
        }

		btnsendto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_send();
			}
		});

		btnnew = gr.sbcs("New");
		btnnew.setBounds(970, 435, 145, 35);
		btnnew.setForeground(Color.black);
		btnnew.setBackground(hh.lpiros);
		ttpanel.add(btnnew);
		btnnew.addActionListener(e -> data_new());

		btnupdate = gr.sbcs("Update");
		btnupdate.setBounds(970, 480, 145, 35);
		btnupdate.setBackground(hh.vvzold);
		ttpanel.add(btnupdate);
		btnupdate.addActionListener(e -> data_update());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(970, 525, 145, 35);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);
		ttpanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());
		return ttpanel;
	}

	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setBackground(new Color(178, 216, 216));
		eepanel.setBounds(10, 10, 1140, 580);
		JPanel lpanel = new JPanel(null);
		lpanel.setBounds(10, 10, 570, 590);
		lpanel.setBorder(hh.ztroundborder(hh.cianzt));
		lpanel.setBackground(new Color(178, 216, 216));
		eepanel.add(lpanel);
		JPanel rpanel = new JPanel(null);
		rpanel.setBounds(585, 10, 570, 590);
		rpanel.setBorder(hh.ztroundborder(hh.cianzt));
		rpanel.setBackground(new Color(178, 216, 216));
		eepanel.add(rpanel);
		lbpatientid = hh.clabel("PatientID");
		lbpatientid.setBounds(10, 10, 100, 25);
		lpanel.add(lbpatientid);

		txpid = cTextField(150);
		txpid.setBounds(120, 10, 120, 25);
		lpanel.add(txpid);
		
		lberror = hh.clabel("");
		lberror.setHorizontalAlignment(JLabel.LEFT);
		lberror.setBounds(250, 10, 240, 25);
		lberror.setForeground(hh.vpiros);
		lpanel.add(lberror);

		lbpname = hh.clabel("Name");
		lbpname.setBounds(10, 50, 100, 25);
		lpanel.add(lbpname);

		txpname = hh.cTextField(200);
		txpname.setBounds(120, 50, 300, 25);
		lpanel.add(txpname);
		txpname.addKeyListener(hh.MUpper());

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

		lbbloodg = hh.clabel("Blood group");
		lbbloodg.setBounds(10, 210, 100, 25);
		lpanel.add(lbbloodg);

		cmbbloodg = hh.cbcombo();
		cmbbloodg.setBounds(120, 210, 100, 25);
		lpanel.add(cmbbloodg);

		lbcountries = hh.clabel("Country");
		lbcountries.setBounds(10, 250, 100, 25);
		lpanel.add(lbcountries);

		cmbcountries = hh.cbcombo();
		cmbcountries.setBounds(120, 250, 250, 25);
		lpanel.add(cmbcountries);

		lbcities = hh.clabel("City");
		lbcities.setBounds(10, 290, 100, 25);
		lpanel.add(lbcities);

		cmbcities = hh.cbcombo();
		cmbcities.setName("cities");
		cmbcities.setFont(hh.textf);
		cmbcities.setBounds(120, 290, 250, 27);
		lpanel.add(cmbcities);

		btnnewcity = gr.sbcs("New");
		btnnewcity.setFocusable(false);
		btnnewcity.setForeground(Color.black);
		btnnewcity.setBackground(Color.ORANGE);
		btnnewcity.setBounds(380, 290, 80, 32);
		btnnewcity.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnnewcity.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		lpanel.add(btnnewcity);
		btnnewcity.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Lcities ci = new Lcities(myframe, "patients");
				ci.setLayout(null);
				ci.setLocationRelativeTo(null);
				ci.setVisible(true);
			}
		});

		lbstreet = hh.clabel("Street");
		lbstreet.setBounds(10, 330, 100, 25);
		lpanel.add(lbstreet);

		txstreet = hh.cTextField(350);
		txstreet.setBounds(120, 330, 440, 25);
		lpanel.add(txstreet);
		txstreet.addKeyListener(hh.MUpper());

		lbdob = hh.clabel("DOB");
		lbdob.setBounds(10, 370, 100, 25);
		lpanel.add(lbdob);

		txdob = hh.DatetextField();
		txdob.setBounds(120, 370, 105, 30);
		lpanel.add(txdob);

		lbcreateat = hh.clabel("Create at");
		lbcreateat.setBounds(10, 410, 100, 25);
		lpanel.add(lbcreateat);

		txcreateat = hh.DatetextField();
		txcreateat.setBounds(120, 410, 105, 25);
		lpanel.add(txcreateat);

		lbstatus = hh.clabel("Status");
		lbstatus.setBounds(10, 450, 100, 25);
		lpanel.add(lbstatus);

		cmbstatus = hh.cbcombo();
		cmbstatus.setModel(new DefaultComboBoxModel(new String[] { "Active", "Deactive", "Deceased" }));
		cmbstatus.setBounds(120, 450, 150, 27);
		cmbstatus.setFont(hh.textf);
		lpanel.add(cmbstatus);		

		btnsave = gr.sbcs("Save all");
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		btnsave.setBounds(120, 510, 120, 32);
		btnsave.setFocusable(false);
		lpanel.add(btnsave);
		btnsave.addActionListener(e -> data_save());

		btncancel = gr.sbcs("Cancel all");
		btncancel.setForeground(Color.black);
		btncancel.setBackground(hh.vvzold);
		btncancel.setBounds(250, 510, 120, 32);
		btncancel.setFocusable(false);
		lpanel.add(btncancel);
		btncancel.addActionListener(e -> cancelall());

		lbremark = hh.clabel("Remark");
		lbremark.setBounds(220, 10, 100, 25);
		rpanel.add(lbremark);

		txaremarke = hh.cTextarea();
		remePane = new JScrollPane(txaremarke, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		remePane.setBounds(50, 40, 490, 180);
		remePane.setBorder(hh.borderf);
		remePane.setViewportView(txaremarke);
		rpanel.add(remePane);

		lbrelativese = hh.clabel("Relatives");
		lbrelativese.setBounds(230, 234, 100, 25);
		rpanel.add(lbrelativese);

		relPanel = new JPanel(null);
		relPanel.setBounds(50, 270, 490, 150);
		relPanel.setBorder(hh.borderf);
		relPanel.setBackground(new Color(178, 216, 216));
		rpanel.add(relPanel);

		lbrname = hh.clabel("Name");
		lbrname.setBounds(10, 20, 60, 25);
		relPanel.add(lbrname);

		txrname = hh.cTextField(200);
		txrname.setBounds(90, 20, 240, 25);
		relPanel.add(txrname);
		txrname.addKeyListener(hh.MUpper());

		lbrtype = hh.clabel("Type");
		lbrtype.setBounds(10, 60, 60, 25);
		relPanel.add(lbrtype);

		txrtype = hh.cTextField(150);
		txrtype.setBounds(90, 60, 240, 25);
		relPanel.add(txrtype);
		txrtype.addKeyListener(hh.MUpper());

		lbrphone = hh.clabel("Phone");
		lbrphone.setBounds(10, 100, 60, 25);
		relPanel.add(lbrphone);

		txrphone = hh.cTextField(150);
		txrphone.setBounds(90, 100, 240, 25);
		relPanel.add(txrphone);

		btnadd = gr.sbcs("Add");
		btnadd.setForeground(Color.black);
		btnadd.setBackground(hh.lpiros);
		btnadd.setBounds(350, 35, 120, 32);
		btnadd.setFocusable(false);
		relPanel.add(btnadd);
		btnadd.addActionListener(e -> buttaddfv());

		btnremove = gr.sbcs("Remove");
		btnremove.setForeground(Color.black);
		btnremove.setBackground(Color.green);
		btnremove.setBounds(350, 80, 120, 32);
		btnremove.setFocusable(false);
		relPanel.add(btnremove);
		btnremove.addActionListener(e -> buttremovefv());

		reltablee = hh.ztable();
		reltablee.setTableHeader(new JTableHeader(reltablee.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(reltablee);
		reltablee.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				reltablee.scrollRectToVisible(reltablee.getCellRect(reltablee.getRowCount() - 1, 0, true));
			}
		});

		relePane = new JScrollPane(reltablee, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		reltablee.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "rid", "Name", "Type", "Phone", "rtip" }));
		hh.setJTableColumnsWidth(reltablee, 510, 0, 50, 25, 25, 0);
		relePane.setViewportView(reltablee);
		relePane.setBounds(50, 435, 490, 140);
		relePane.setBorder(hh.borderf);
		rpanel.add(relePane);
		return eepanel;
	}

	private void buttaddfv() {
		DefaultTableModel d1 = (DefaultTableModel) reltablee.getModel();
		String rname = txrname.getText();
		String rtype = txrtype.getText();
		String rphone = txrphone.getText();
		if (!hh.zempty(rname) && !hh.zempty(rtype) && !hh.zempty(rphone)) {
			d1.addRow(new Object[] { " ", rname, rtype, rphone, "" });
			clearFields(2);
		} else {
			JOptionPane.showMessageDialog(null, " Fields are empty !");
		}
		txrname.requestFocus(true);
		if (reltablee.getRowCount() > 0) {
			int row = reltablee.getRowCount() - 1;
			reltablee.setRowSelectionInterval(row, row);
		}
	}

	private void buttremovefv() {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) reltablee.getModel();
		int sIndex = reltablee.getSelectedRow();
		if (sIndex < 0) {
			return;
		}
		String rid = reltablee.getValueAt(sIndex, 0).toString();
		d1.removeRow(sIndex);
		if (!hh.zempty(rid)) {
			String sql = "delete from relative where rid ='" + rid + "'";
			try {
				flag = dh.Insupdel(sql);
			} catch (Exception e1) {
				System.out.println("sql error!!!");
			}
		}
		clearFields(2);
	}

	private void clearFields(int num) {
		if (num == 1) {
			rowid = "";
			myrow = 0;
			txpid.setText("");
			txpname.setText("");
			txphone.setText("");
			txemail.setText("");
			txstreet.setText("");
			txcreateat.setText("");
			txdob.setText("");
			txaremarke.setText("");
			cmbstatus.setSelectedIndex(0);
			cmbcities.setSelectedIndex(0);
			cmbcountries.setSelectedIndex(0);
			cmbgender.setSelectedIndex(0);
			cmbbloodg.setSelectedIndex(0);
			DefaultTableModel m1 = (DefaultTableModel) reltablee.getModel();
			m1.setRowCount(0);
			txrname.setText("");
			txrtype.setText("");
			txrphone.setText("");
			txpid.setEnabled(true);
		} else if (num == 2) {
			txrname.setText("");
			txrtype.setText("");
			txrphone.setText("");
		}
	}

	private void data_new() {
		clearFields(1);
		txcreateat.setText(hh.currentDate());
		cards.show(cardPanel, "edit");
		txpid.requestFocus(true);
	}

	private void data_update() {
		int row = ptable.getSelectedRow();
		if (row >= 0) {
			clearFields(1);
			rowid = ptable.getValueAt(row, 0).toString();
			myrow = row;
			pat = dd.getpatientrecord(rowid);
			txpid.setText(pat.getPid());
			txpname.setText(pat.getPname());
			txphone.setText(pat.getPhone());
			txemail.setText(pat.getEmail());
			txstreet.setText(pat.getStreet());
			txcreateat.setText(pat.getCreateat());
			txdob.setText(pat.getDob());
			txaremarke.setText(pat.getRemark());
			cmbstatus.setSelectedItem(pat.getStatus());
			int cid = pat.getCid();
			hh.setSelectedValue(cmbcities, cid);
			cmbcountries.setSelectedItem(pat.getCountry());
			cmbgender.setSelectedItem(pat.getGender());
			cmbbloodg.setSelectedItem(pat.getBlood());
			dd.reltabletview(reltablee, rowid);
			cards.show(cardPanel, "edit");
			txpid.setEnabled(false);
			txpname.requestFocus(true);
		}
	}

	private void data_delete() {
		int row = ptable.getSelectedRow();
		if (row >= 0) {
			rowid = ptable.getValueAt(row, 0).toString();
			String sql = "delete from patient where pid =";
			int flag = dd.data_delete(ptable, sql);
			if (flag > 0) {
				sql = "delete from relative where rpid ='" + rowid + "'";
				try {
					flag = dh.Insupdel(sql);
				} catch (Exception e1) {
					System.out.println("sql error!!!");
				}
			}
		}
	}

	private void cancelall() {
		clearFields(1);
		cards.show(cardPanel, "table");
	}

	private void data_save() {
		String sql = "";
		DefaultTableModel d1 = (DefaultTableModel) ptable.getModel();
		Boolean newrec = false;
		String pid = txpid.getText();
		String pname = txpname.getText();
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
		if (dob.equals("____-__-__")) {
			dob = "";
		}
		String createat = txcreateat.getText();
		String remark = txaremarke.getText();
		String status = (String) cmbstatus.getSelectedItem();
		String blood = (String) cmbbloodg.getSelectedItem();
		if (rowid == "") {
			newrec = true;
		}

		if (pvalidation(pid, pname, phone, country, street, city, dob, createat, newrec) == false) {
			return;
		}

		if (rowid != "") {
			sql = "update  patient set pid = '" + pid + "', pname= '" + pname + "', gender = '"
					+ gender + "', phone = '" + phone + "',  blood='" + blood + "',  email='" + email + "', country='"
					+ country + "', cid= " + cid + ", street= '" + street + "', dob='" + dob + "', createat='" + createat
					+ "', remark='" + remark + "', status= '" + status + "' where pid = '" + rowid+"'";
		} else {
			sql = "insert into patient (pid, pname, gender, phone, blood, email, country, "
					+ " cid, street, dob, createat, remark, status) values (" + "'" + pid + "','" + pname + "','"
					+ gender + "','" + phone + "','" + blood + "','" + email + "','" + country + "'," + cid + ",'"
					+ street + "','" + dob + "','" + createat + "','" + remark + "','" + status + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {					
					d1.addRow(new Object[] { pid, pname, gender, dob, phone, email, blood, country, cid,
							city, street, createat, status });
					hh.gotolastrow(ptable);				
					relative_save(newrec, pid);
				} else {
					d1.setValueAt(pid, myrow, 0);
					d1.setValueAt(pname, myrow, 1);
					d1.setValueAt(gender, myrow, 2);
					d1.setValueAt(dob, myrow, 3);
					d1.setValueAt(phone, myrow, 4);
					d1.setValueAt(email, myrow, 5);
					d1.setValueAt(blood, myrow, 6);
					d1.setValueAt(country, myrow, 7);
					d1.setValueAt(cid, myrow, 8);
					d1.setValueAt(city, myrow, 9);
					d1.setValueAt(street, myrow, 10);
					d1.setValueAt(createat, myrow, 11);
					d1.setValueAt(status, myrow, 12);
					relative_save(newrec, pid);
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql if  error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		dd.reltabletview(reltablet, pid);
		txaremarkt.setText(remark);
		clearFields(1);
		cards.show(cardPanel, "table");
	}

	private void relative_save(Boolean newrec, String pid) {
		String sql = "";
		if (newrec == true) {
			if (reltablee.getRowCount() > 0) {
				for (int i = 0; i < reltablee.getRowCount(); i++) {
					String rname = reltablee.getValueAt(i, 1).toString();
					String rtype = reltablee.getValueAt(i, 2).toString();
					String rphone = reltablee.getValueAt(i, 3).toString();
					sql = "insert into relative (rname, rtype, rphone, rpid) values('" + rname + "','" + rtype + "','"
							+ rphone + "','" + pid + "')";
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
			if (reltablee.getRowCount() > 0) {
				for (int i = 0; i < reltablee.getRowCount(); i++) {
					String rname = reltablee.getValueAt(i, 1).toString();
					String rtype = reltablee.getValueAt(i, 2).toString();
					String rphone = reltablee.getValueAt(i, 3).toString();
					String rid = reltablee.getValueAt(i, 0).toString();
					if (hh.zempty(rid)) {
						sql = "insert into relative (rname, rtype, rphone, rpid) values('" + rname + "','" + rtype
								+ "','" + rphone + "','" + pid + "')";
					} else {
						sql = "update relative set rname = '" + rname + "', rtype= '" + rtype + "', rphone= '" + rphone
								+ "' where rid = '" + rid + "'";
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
		} else if (rpatid.isSelected()) {
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
			sql = "where  lower(p.pname) LIKE '%" + ss + "%' order by p.pname " + " COLLATE NOCASE ASC";
		} else if (jelem == 3) {
			sql = " where p.phone LIKE '%" + ss + "%' order by p.phone COLLATE NOCASE ASC";
		} else if (jelem == 2) {
			sql = " where p.pid LIKE '%" + ss + "%' ";
		}
		dd.patienttable_update(ptable, pheader, sql);
	}

	private Boolean pvalidation(String pid, String pname, String phone, String country, String street, String city,
			String dob, String createat, Boolean newrec) {
		Boolean ret = true;
		ArrayList<String> err = new ArrayList<String>();

		if (!allv.namevalid(pname)) {
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

		if (!allv.createatvalid(createat)) {
			err.add(allv.mess);
			ret = false;
		}
		if (!allv.patientidvalid(pid, newrec)) {
			err.add(allv.mess);
			ret = false;
		}

		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
		return ret;
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
			JTextField Txt = (JTextField) e.getSource();
			tartalom = Txt.getText();
			if (Txt == txpid) {
				Txt.setText(tartalom);	
				vissza = allv.patientidvalid(tartalom,true);
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
	private void data_send() {
		DefaultTableModel d1 = (DefaultTableModel) ptable.getModel();
		int row = ptable.getSelectedRow();
		int number = 0;
		String cnum = "";
		if (row > -1) {
			String patientid = d1.getValueAt(row, 0).toString();	
			String pname = d1.getValueAt(row, 1).toString();
			if (sfrom == "weekpanel") {
				((Weekpanel2) pframe).passtopatient(patientid, pname);
			} else if (sfrom == "choosepatient"){
				((Choosepatient) pframe).passtopatient(patientid, pname);
			} else if (sfrom == "choose_patient_department"){
				((Choose_patient_department) pframe).passtopatient(patientid, pname);
			} else if (sfrom == "admissions"){
				((Admissionb) pframe).passtopatient(patientid, pname);
			}		else if (sfrom == "where_is_the_patient"){
				((Where_is_the_patient) pframe).passtopatient(patientid, pname);
			}			
			pframe.setVisible(true);
			dispose();
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Patients();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	CardLayout cards;
	JPanel tablepanel, cardPanel, ePanel, tPanel, lPanel, rPanel, searchPanel, relPanel;
	JLabel lbheader, lbsearch, lbremarks, lbrelatives, lbrelativese;
	JButton btnsearch, btnclear, btnnew, btnupdate, btndelete, btnnewcity, btnadd, btnremove, btnsave, btncancel,
			btnback, btnsendto;
	JRadioButton rname, rtel, rpatid;
	ButtonGroup btnGroup = new ButtonGroup();
	JTable ptable, reltablet, reltablee;
	JScrollPane pPane, reltPane, remtPane, remePane, relePane;
	JTextArea txaremarkt, txaremarke;
	JLabel lbpatientid, lbpname, lbphone, lbemail, lbgender, lbbloodg, lbcountries, lbcities, lbstreet, lbdob,
			lbcreateat, lbstatus, lbremark, lbrname, lbrtype, lbrphone, lberror;
	JTextField txsearch, txpid, txpname, txphone, txemail, txstreet, txrname, txrtype, txrphone;
	JFormattedTextField txdob, txcreateat;
	JComboBox cmbgender, cmbbloodg, cmbcountries, cmbcities, cmbstatus;
}
