package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import Classes.Allvalid;
import Classes.Calhelper;
import Classes.Employee;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Cities;
import Databaseop.Databaseop.Department;

public class Medical_record_reg extends JFrame {
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
	String actdate = hh.currentDate();
	String rowid = "";
	int myrow = 0;
	String mypatientid = "";
	String mypatientname = "";

	Medical_record_reg() {
		init();
		hh.iconhere(this);
		mrtable_update("");
	
	}

	Medical_record_reg(String ppatientid, String ppatientname, int pdepid, String pdepname) {
		mypatientid = ppatientid;
		mypatientname = ppatientname;
		mydepid = pdepid;
		mydepname=pdepname;
		init();
		hh.iconhere(this);
		mrtable_update(" where m.pid='" + mypatientid + "' and m.depid = '" +mydepid +"'" );	
		dd.cmbdoctor1combofill(cmbdoctors, mydepid);
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
					// cmbguest.requestFocus();
				}
			}
		});

		setTitle("Medical records - " + mypatientname);
		setSize(1180, 735);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0, 128, 128));

		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setLayout(cards);
		cardPanel.setBounds(15, 60, 1132, 620);
		cardPanel.setBorder(hh.border4);
		tPanel = maketpanel();
		tPanel.setName("table");
		cardPanel.add(tPanel, "table");

		ePanel = makeepanel();
		ePanel.setName("edit");
		cardPanel.add(ePanel, "edit");

		add(cardPanel);
		cards.show(cardPanel, "table");
		// cards.show(cardPanel, "edit");
//  cards.show(cardPanel, "etable");			

		lbheader = hh.fflabel("Medical record - " + mypatientname +" - " + mydepname);
		lbheader.setBounds(30, 10, 800, 35);
//		lbheader.setForeground(Color.orange);
		add(lbheader);
		setVisible(true);
	}

	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);
		ttpanel.setBackground(hh.vvvkek);

		mrtable = hh.ztable();
		mrtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) mrtable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		mrtable.setTableHeader(new JTableHeader(mrtable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		mrtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) mrtable.getModel();
				try {
					int row = mrtable.getSelectedRow();
					if (row > -1) {
						String mymrid = model.getValueAt(row, 0).toString();
						meddetails(mymrid);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		hh.madeheader(mrtable);
		mrtable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		mrPane = new JScrollPane(mrtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		String[] mrheader = new String[] { "mrid", "Date","docid", "Doctor",
				"posid", "Position", "appid", "Appointment datetime", "created_at" };

		mrtable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null } }, mrheader));
		
		int[] wcols0 = {0,2,4, 6};
		hh.cellswidth_0(mrtable, wcols0);
		int[][] wcols = { { 1, 130 }, { 3, 250 }, { 5, 250 }, { 7, 170 },{ 8, 120 } };
		hh.cellswidth(mrtable, wcols);	

		mrPane.setViewportView(mrtable);
		mrPane.setBounds(30, 30, 925, 300);
		mrPane.setBorder(hh.myRaisedBorder);
		ttpanel.add(mrPane);

		btnnew = gr.sbcs("New");
		btnnew.setBounds(970, 130, 130, 30);
		btnnew.setForeground(Color.black);
		btnnew.setBackground(hh.lpiros);
		ttpanel.add(btnnew);
		btnnew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				make_new_record();
				cards.show(cardPanel, "edit");
			}
		});
		btnupdate = gr.sbcs("Update");
		btnupdate.setBounds(970, 170, 130, 30);
		btnupdate.setForeground(Color.black);
		btnupdate.setBackground(Color.green);
		ttpanel.add(btnupdate);
		btnupdate.addActionListener(e -> data_update());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(970, 210, 130, 30);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);
		ttpanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());

		lbdiagnosis = hh.clabel("Diagnosis");
		lbdiagnosis.setBounds(130, 350, 120, 25);
		ttpanel.add(lbdiagnosis);

		tadiagnosis = hh.cTextarea();
		tadiagnosis.setEditable(false);
		tadiagnosis.setBackground(hh.tcolor);
		jspn = new JScrollPane(tadiagnosis, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspn.setBounds(32, 380, 350, 220);
		jspn.setViewportView(tadiagnosis);
		jspn.setBorder(hh.borderf);
		ttpanel.add(jspn);

		lbtreatment = hh.clabel("Treatment");
		lbtreatment.setBounds(495, 350, 120, 25);
		ttpanel.add(lbtreatment);

		tatreatment = hh.cTextarea();
		tatreatment.setEditable(false);
		tatreatment.setBackground(hh.tcolor);
		jspn1 = new JScrollPane(tatreatment, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspn1.setBounds(392, 380, 350, 220);
		jspn1.setViewportView(tatreatment);
		jspn1.setBorder(hh.borderf);
		ttpanel.add(jspn1);

		lbprescription = hh.clabel("Prescription");
		lbprescription.setBounds(862, 350, 120, 25);
		ttpanel.add(lbprescription);

		taprescription = hh.cTextarea();
		taprescription.setEditable(false);
		taprescription.setBackground(hh.tcolor);
		jspn2 = new JScrollPane(taprescription, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspn2.setBounds(752, 380, 350, 220);
		jspn2.setViewportView(taprescription);
		jspn2.setBorder(hh.borderf);
		ttpanel.add(jspn2);
		return ttpanel;
	}

	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setBackground(hh.vvvkek);		

		lbappointment = hh.clabel("Appointment");
		lbappointment.setBounds(10, 30, 110, 25);
		eepanel.add(lbappointment);

		txappchoose = hh.dfTextField(200);
		txappchoose.setBounds(130, 30, 425, 32);
		eepanel.add(txappchoose);
		
		txapdatetime = new JTextField(100);

		btnappoint = gr.sbcs("Appointments");
		btnappoint.setForeground(Color.black);
		btnappoint.setBackground(Color.ORANGE);
		btnappoint.setBounds(560, 30, 150, 32);
		btnappoint.setFocusable(false);
		eepanel.add(btnappoint);
		btnappoint.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {		
				if (mydepid > 0) {				
					Chooseappointment ca = new Chooseappointment(mypatientid, mypatientname, mydepid, myframe,
							"medical_record");
					ca.setLayout(null);
					ca.setLocationRelativeTo(null);
					ca.setVisible(true);
				}
			}
		});

		txappointid = hh.dfTextField(10);	

		lbdoctor = hh.clabel("Doctors");
		lbdoctor.setBounds(700, 30, 80, 25);
		eepanel.add(lbdoctor);

		cmbdoctors = gr.grcombo();
		cmbdoctors.setFocusable(true);
		cmbdoctors.setName("doctors");
		cmbdoctors.setFont(hh.textf);
		cmbdoctors.setBounds(790, 30, 260, 30);
		eepanel.add(cmbdoctors);

		lbdate = hh.clabel("Date");
		lbdate.setBounds(72, 78, 45, 25);
		eepanel.add(lbdate);

		txdate = hh.DatetextField();
		txdate.setBounds(130, 75, 110, 30);
		eepanel.add(txdate);

		lbdiagnosisw = hh.clabel("Diagnosis");
		lbdiagnosisw.setBounds(130, 130, 120, 25);
		eepanel.add(lbdiagnosisw);

		tadiagnosisw = hh.cTextarea();
		tadiagnosisw.setBackground(hh.tcolor);
		jspnw = new JScrollPane(tadiagnosisw, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspnw.setBounds(32, 160, 350, 300);
		jspnw.setViewportView(tadiagnosisw);
		jspnw.setBorder(hh.borderf);
		eepanel.add(jspnw);

		lbtreatmentw = hh.clabel("Treatment");
		lbtreatmentw.setBounds(495, 130, 120, 25);
		eepanel.add(lbtreatmentw);

		tatreatmentw = hh.cTextarea();
		tatreatmentw.setBackground(hh.tcolor);
		jspn1w = new JScrollPane(tatreatmentw, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspn1w.setBounds(392, 160, 350, 300);
		jspn1w.setViewportView(tatreatmentw);
		jspn1w.setBorder(hh.borderf);
		eepanel.add(jspn1w);

		lbprescriptionw = hh.clabel("Prescription");
		lbprescriptionw.setBounds(850, 130, 120, 25);
		eepanel.add(lbprescriptionw);

		taprescriptionw = hh.cTextarea();
		taprescriptionw.setBackground(hh.tcolor);
		jspn2w = new JScrollPane(taprescriptionw, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspn2w.setBounds(752, 160, 350, 300);
		jspn2w.setViewportView(taprescriptionw);
		jspn2w.setBorder(hh.borderf);
		eepanel.add(jspn2w);

		btnsave = gr.sbcs("Save");
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		btnsave.setBounds(440, 510, 120, 32);
		btnsave.setFocusable(false);
		eepanel.add(btnsave);
		btnsave.addActionListener(e -> data_save());

		btncancel = gr.sbcs("Cancel");
		btncancel.setForeground(Color.black);
		btncancel.setBackground(hh.vvzold);
		btncancel.setBounds(570, 510, 120, 32);
		btncancel.setFocusable(false);
		eepanel.add(btncancel);
		btncancel.addActionListener(e -> mrcancel());
		return eepanel;
	}

	private void data_save() {
		String sql = "";
		int myid = 0;
		DefaultTableModel d1 = (DefaultTableModel) mrtable.getModel();
		Employee employee;
		String diagnosis = tadiagnosisw.getText();
		String treatment = tatreatmentw.getText();
		String prescription = taprescriptionw.getText();
		String pid = mypatientid;
		String patientname = mypatientname;
		String appid = txappointid.getText();
		String sdate = txdate.getText();
		if (sdate.equals("____-__-__")) {
			sdate = "";
		}
		String apdatetime = txapdatetime.getText();
		String docid = (String) ((Employee) cmbdoctors.getSelectedItem()).getEmpid();
		String docname = (String) ((Employee) cmbdoctors.getSelectedItem()).getName();
		String posid = (String) ((Employee) cmbdoctors.getSelectedItem()).getPosid();
		String position = (String) ((Employee) cmbdoctors.getSelectedItem()).getPosition();
		String createat = actdate;
		if (mvalidation(pid, sdate, docid, posid, mydepid) == false) {
			return;
		}

		if (rowid != "") {
			sql = "update  medical_records set pid = '" + pid + "', date= '" + sdate + "', " + " appid = '" + appid
					+ "', docid = '" + docid + "',  posid='" + posid + "',  diagnosis='" + diagnosis + "', treatment='"
					+ treatment + "', prescription= '" + prescription + "', depid='" + mydepid + "', created_at='"
					+ createat + "' where mrid = " + rowid;
		} else {
			sql = "insert into medical_records (pid, date, appid, docid, posid, diagnosis, treatment, "
					+ " prescription, depid, created_at) values ('" + pid + "','" + sdate + "','" + appid + "','"
					+ docid + "','" + posid + "','" + diagnosis + "','" + treatment + "','" + prescription + "','"
					+ mydepid + "','" + createat + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					myid = dd.table_maxid("SELECT MAX(mrid) AS max_id from medical_records");
					d1.addRow(new Object[] { myid, sdate, docid, docname, posid, position, appid,
							apdatetime, createat });					
					hh.gotolastrow(mrtable);
				} else {
					myid = hh.stoi(rowid);
					// d1.setValueAt(myid, myrow, 0);
					d1.setValueAt(sdate, myrow, 1);		
					d1.setValueAt(docid, myrow, 2);
					d1.setValueAt(docname, myrow, 3);
					d1.setValueAt(posid, myrow, 4);
					d1.setValueAt(position, myrow, 5);
					d1.setValueAt(appid, myrow, 6);
					d1.setValueAt(apdatetime, myrow, 6);
					d1.setValueAt(createat, myrow, 8);
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql if  error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		meddetails(hh.itos(myid));
		clearFields();
		cards.show(cardPanel, "table");
	}

	private void mrcancel() {
		clearFields();
		cards.show(cardPanel, "table");
	}

	private void data_update() {
		int row = mrtable.getSelectedRow();
		if (row >= 0) {
			clearFields();
			rowid = mrtable.getValueAt(row, 0).toString();
			myrow = row;
			txdate.setText(mrtable.getValueAt(row, 1).toString());
			txappointid.setText(mrtable.getValueAt(row, 8).toString());
			String appid = mrtable.getValueAt(row, 8).toString();
			String docname = mrtable.getValueAt(row, 5).toString();
			String position = mrtable.getValueAt(row, 7).toString();		
			String[] rett = dd.getappointment(appid);
			String sdate = rett[0];
			String stime = rett[1];
			String stitle = docname.trim() + " " + position.trim() + " " + sdate + " " + stime;
			txappchoose.setText(stitle);	
			String seged =sdate +" " +stime;
			txapdatetime.setText(seged);
			String docid = mrtable.getValueAt(row, 4).toString();
			String posid = mrtable.getValueAt(row, 6).toString();		
			hh.setSelectedValue_st2(cmbdoctors, docid, posid);
			cmbdoctors.updateUI();
			tadiagnosisw.setText(tadiagnosis.getText());
			tatreatmentw.setText(tatreatment.getText());
			taprescriptionw.setText(taprescription.getText());
			cards.show(cardPanel, "edit");
		}
	}

	private void data_delete() {
		String sql = " delete from medical_records where mrid= ";
		int flag = dd.data_delete(mrtable, sql);
		if (flag > 0) {
			tadiagnosis.setText("");
			tatreatment.setText("");
			taprescription.setText("");
		}
	}

	private Boolean mvalidation(String ppid, String psdate, String pdocid, String pposid, int pdepid) {
		Boolean ret = true;
		ArrayList<String> err = new ArrayList<String>();

		if (hh.zempty(ppid)) {
			err.add(" Patient name is empty");
			ret = false;
		}
		if (hh.zempty(psdate)) {
			err.add(" Date is empty");
			ret = false;
		}
		if (!hh.validatedate(psdate, "N")) {
			err.add(" Invalid date ");
			ret = false;
		}
		if (hh.zempty(pdocid)) {
			err.add(" Doctor name is empty");
			ret = false;
		}
		if (pdepid < 1) {
			err.add(" there is no department");
			ret = false;
		}

		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
		return ret;
	}

	private void make_new_record() {
		clearFields();
		SwingUtilities.invokeLater(() -> cmbdoctors.requestFocusInWindow());
	//	cmbdoctors.requestFocus();
	}

	private void mrtable_update(String swhere) {
		DefaultTableModel m1 = (DefaultTableModel) mrtable.getModel();
		m1.setRowCount(0);
		String seged ="concat( 'a.app_date', ' ',  'a.app_time') as apdatetime,";

		String sql = "select m.mrid, m.date, m.docid, e.name, m.posid, po.name as posname, m.appid,"
				+ " a.app_date  || ' ' || a.app_time  as apdatetime,"			
				+ " m.created_at  from medical_records m "
				+ " left join appointments a on m.appid = a.appid  "
				+ " left join employees e on m.docid = e.empid " 
				+ " left join positions po on m.posid = po.posid "
				+ swhere;
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String mrid = rs.getString("mrid");
				String date = rs.getString("date");				
				String apdatetime = rs.getString("apdatetime");			
				String docid = rs.getString("docid");
				String dname = rs.getString("name");
				String posid = rs.getString("posid");
				String posname = rs.getString("posname");
				String appid = rs.getString("appid");
				String created = rs.getString("created_at");
				m1.addRow(new Object[] { mrid, date, docid, dname, posid, posname, appid, apdatetime, created });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = { "mrid", "Date",  "docid", "Doctor", "posid", "Position", "appid",
				"Appointment datetime","created_at" };
		((DefaultTableModel) mrtable.getModel()).setColumnIdentifiers(fej);
		
		int[] wcols0 = {0,2,4, 6};
		hh.cellswidth_0(mrtable, wcols0);

		int[][] wcols = { { 1, 130 }, { 3, 250 }, { 5, 250 }, { 7, 170 },{ 8, 120 } };
		hh.cellswidth(mrtable, wcols);		

		if (mrtable.getRowCount() > 0) {
			int row = mrtable.getRowCount() - 1;
			mrtable.setRowSelectionInterval(row, row);
		}
	}

	private void clearFields() {
		rowid = "";
		myrow = 0;	
		tadiagnosisw.setText("");
		tatreatmentw.setText("");
		taprescriptionw.setText("");
		txdate.setText("");
		txappointid.setText("");
		txappchoose.setText("");
		txapdatetime.setText("");
		Employee A = new Employee("", "","","");
		cmbdoctors.getModel().setSelectedItem(A);
	}

	private void meddetails(String mrid) {
		String sql = "select diagnosis, treatment, prescription from medical_records  where mrid='" + mrid + "'";
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String diagnosis = rs.getString("diagnosis");
				tadiagnosis.setText(diagnosis);
				String treatment = rs.getString("treatment");
				tatreatment.setText(treatment);
				String prescription = rs.getString("prescription");
				taprescription.setText(prescription);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
	}

	public void passtopatient(String patientid, String patientname) {
		txpatient.setText(patientname);
		txpatientid.setText(patientid);

	}

	public void passtoappoint(String appid, String docid, String docname, String posid, String position, String sdate,
			String stime) {
		txappointid.setText(appid);
		hh.setSelectedValue_st2(cmbdoctors, docid, posid);
		cmbdoctors.updateUI();
		String stitle = docname.trim() + " " + position.trim() + " " + sdate + " " + stime;
		txappchoose.setText(stitle);
		String seg = sdate +" " +stime;
        txapdatetime.setText(seg);
	}
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Medical_record_reg();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	CardLayout cards;
	JLabel lbheader, lbsearch, lbdiagnosis, lbtreatment, lbprescription, lbpatient, lbdoctor, lbdate;
	JLabel lbdiagnosisw, lbtreatmentw, lbprescriptionw, lbappointment, lbdepname;
	JPanel cardPanel, tPanel, ePanel, etPanel;
	JTextField txsearch, txpatientid, txpatient, txdate, txappointid, txappchoose, txapdatetime;
	JComboBox cmbsearch, cmbdoctors, cmbdepartments;
	JButton btnsearch, btnclear, btnnew, btndelete, btnupdate, btnpatient, btnappoint, btnsave, btncancel;
	JTable mrtable;
	JScrollPane mrPane, jspn, jspn1, jspn2, jspnw, jspn1w, jspn2w;
	JTextArea tadiagnosis, tatreatment, taprescription;
	JTextArea tadiagnosisw, tatreatmentw, taprescriptionw;

}