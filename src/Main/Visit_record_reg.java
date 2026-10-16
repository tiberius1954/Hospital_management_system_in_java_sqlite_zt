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

public class Visit_record_reg extends JFrame {
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
	int mydepid = 1;
	String mydepname = "";
	String actdate = hh.currentDate();
	String rowid = "";
	int myrow = 0;
	String mypatientid = "P000009";

	String mypatientname = "";

	Visit_record_reg() {
		init();
		hh.iconhere(this);
		// vtable_update("");
	}

	Visit_record_reg(String ppatientid, String ppatientname, int pdepid, String pdepname) {
		mypatientid = ppatientid;
		mypatientname = ppatientname;
		mydepid = pdepid;
		mydepname = pdepname;
		init();
		hh.iconhere(this);
    	vtable_update(" where v.pid='" + mypatientid + "' and v.depid ='" + pdepid+"'");	
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
		setTitle("Visit records - " + mypatientname);
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

		lbheader = hh.fflabel("Visit record - " + mypatientname + " - " +mydepname);
		lbheader.setBounds(30, 10, 800, 35);
//		lbheader.setForeground(Color.orange);
		add(lbheader);
		setVisible(true);
	}

	private JPanel maketpanel() {
		JPanel ttpanel = new JPanel(null);
		ttpanel.setBackground(hh.vvvkek);

		vtable = hh.ztable();
		vtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) vtable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		vtable.setTableHeader(new JTableHeader(vtable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		vtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) vtable.getModel();
				try {
					int row = vtable.getSelectedRow();
					if (row > -1) {
						String mymrid = model.getValueAt(row, 0).toString();
						 visit_details(mymrid);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		hh.madeheader(vtable);
		vtable.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		vPane = new JScrollPane(vtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		String[] vheader = new String[] { "vid", "Visit Date", "docid", "Doctor", "posid", "Position", "Next visit",
				"adid", "Admission date" };

		vtable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null } }, vheader));

		int[] wcols0 = { 0, 2, 4, 7 };
		hh.cellswidth_0(vtable, wcols0);

		int[][] wcols = { { 1, 110 }, { 3, 250 }, { 5, 200 }, { 6, 200 },{ 8, 150 } };
		hh.cellswidth(vtable, wcols);

		vPane.setViewportView(vtable);
		vPane.setBounds(30, 30, 915, 300);
		vPane.setBorder(hh.myRaisedBorder);
		ttpanel.add(vPane);

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

		lbsymptoms = hh.clabel("Symptoms");
		lbsymptoms.setBounds(130, 350, 120, 25);
		ttpanel.add(lbsymptoms);

		tasymptoms = hh.cTextarea();
		tasymptoms.setEditable(false);
		tasymptoms.setBackground(hh.tcolor);
		jspn = new JScrollPane(tasymptoms, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspn.setBounds(32, 380, 350, 220);
		jspn.setViewportView(tasymptoms);
		jspn.setBorder(hh.borderf);
		ttpanel.add(jspn);

		lbdisease = hh.clabel("Disease");
		lbdisease.setBounds(495, 350, 120, 25);
		ttpanel.add(lbdisease);

		tadisease = hh.cTextarea();
		tadisease.setEditable(false);
		tadisease.setBackground(hh.tcolor);
		jspn1 = new JScrollPane(tadisease, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspn1.setBounds(392, 380, 350, 220);
		jspn1.setViewportView(tadisease);
		jspn1.setBorder(hh.borderf);
		ttpanel.add(jspn1);

		lbdiagnosis = hh.clabel("Diagnosis");
		lbdiagnosis.setBounds(862, 350, 120, 25);
		ttpanel.add(lbdiagnosis);

		tadiagnosis = hh.cTextarea();
		tadiagnosis.setEditable(false);
		tadiagnosis.setBackground(hh.tcolor);
		jspn2 = new JScrollPane(tadiagnosis, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspn2.setBounds(752, 380, 350, 220);
		jspn2.setViewportView(tadiagnosis);
		jspn2.setBorder(hh.borderf);
		ttpanel.add(jspn2);
		return ttpanel;
	}

	private JPanel makeepanel() {
		JPanel eepanel = new JPanel(null);
		eepanel.setBackground(hh.vvvkek);

		lbadmission = hh.clabel("Admission");
		lbadmission.setBounds(10, 30, 110, 25);
		eepanel.add(lbadmission);

		txadmission = hh.dfTextField(200);
		txadmission.setBounds(130, 30, 425, 32);
		eepanel.add(txadmission);

		btnadmission = gr.sbcs("Admissions");
		btnadmission.setForeground(Color.black);
		btnadmission.setBackground(Color.ORANGE);
		btnadmission.setBounds(560, 30, 150, 32);
		btnadmission.setFocusable(false);
		eepanel.add(btnadmission);
		btnadmission.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if (mydepid > 0) {
					Chooseadmission ca = new Chooseadmission(mypatientid, mypatientname, mydepid, myframe,
							"visit_record");
					ca.setLayout(null);
					ca.setLocationRelativeTo(null);
					ca.setVisible(true);
				}
			}
		});

		txadid = hh.dfTextField(10);
		txaddate=new JTextField(30);

		lbdoctor = hh.clabel("Doctors");
		lbdoctor.setBounds(700, 30, 80, 25);
		eepanel.add(lbdoctor);

		cmbdoctors = gr.grcombo();
		cmbdoctors.setFocusable(true);
		cmbdoctors.setName("doctors");
		cmbdoctors.setFont(hh.textf);
		cmbdoctors.setBounds(790, 30, 260, 30);
		eepanel.add(cmbdoctors);

		lbdate = hh.clabel("Visit date");
		lbdate.setBounds(20, 78, 100, 25);
		eepanel.add(lbdate);

		txdate = hh.DatetextField();
		txdate.setBounds(130, 75, 110, 30);
		eepanel.add(txdate);

		lbndate = hh.clabel("Next Visit date");
		lbndate.setBounds(250, 78, 130, 25);
		eepanel.add(lbndate);

		txndate = hh.DatetextField();
		txndate.setBounds(389, 75, 110, 30);
		eepanel.add(txndate);

		lbsymptomw = hh.clabel("Symptoms");
		lbsymptomw.setBounds(130, 130, 110, 25);
		eepanel.add(lbsymptomw);

		tasymptomw = hh.cTextarea();
		tasymptomw.setBackground(hh.tcolor);
		jspnw = new JScrollPane(tasymptomw, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspnw.setBounds(32, 160, 350, 300);
		jspnw.setViewportView(tasymptomw);
		jspnw.setBorder(hh.borderf);
		eepanel.add(jspnw);

		lbdiseasew = hh.clabel(" Disease");
		lbdiseasew.setBounds(495, 130, 120, 25);
		eepanel.add(lbdiseasew);

		tadiseasew = hh.cTextarea();
		tadiseasew.setBackground(hh.tcolor);
		jspn1w = new JScrollPane(tadiseasew, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspn1w.setBounds(392, 160, 350, 300);
		jspn1w.setViewportView(tadiseasew);
		jspn1w.setBorder(hh.borderf);
		eepanel.add(jspn1w);

		lbdiagnosisw = hh.clabel("Diagnosis");
		lbdiagnosisw.setBounds(850, 130, 120, 25);
		eepanel.add(lbdiagnosisw);

		tadiagnosisw = hh.cTextarea();
		tadiagnosisw.setBackground(hh.tcolor);
		jspn2w = new JScrollPane(tadiagnosisw, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jspn2w.setBounds(752, 160, 350, 300);
		jspn2w.setViewportView(tadiagnosisw);
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
		// btncancel.addActionListener(e -> mrcancel());
		return eepanel;
	}

	private void data_save() {
		String sql = "";
		int myid = 0;
		DefaultTableModel d1 = (DefaultTableModel) vtable.getModel();
		Employee employee;
		String disease = tadiseasew.getText();
		String symptom = tasymptomw.getText();
		String diagnosis = tadiagnosisw.getText();
		String pid = mypatientid;
		String patientname = mypatientname;
		String adid = txadid.getText();
		String addate = txaddate.getText();
		String sdate = txdate.getText();
		String sndate = txndate.getText();
		if (sndate.equals("____-__-__")) {
			sndate = "";
		}

		String docid = (String) ((Employee) cmbdoctors.getSelectedItem()).getEmpid();
		String docname = (String) ((Employee) cmbdoctors.getSelectedItem()).getName();
		String posid = (String) ((Employee) cmbdoctors.getSelectedItem()).getPosid();
		String position = (String) ((Employee) cmbdoctors.getSelectedItem()).getPosition();

		if (vvalidation(sdate, docid, posid, mydepid) == false) {
			return;
		}

		if (rowid != "") {
			sql = "update  visit_records set pid = '" + pid + "', vdate= '" + sdate + "', " + " adid = '" + adid
					+ "', docid = '" + docid + "',  posid='" + posid + "', next_vdate='" + sndate + "' , symptoms='"
					+ symptom + "', disease='" + disease + "', diagnosis= '" + diagnosis + "', depid='" + mydepid
					+ "' where vid = " + rowid;
		} else {
			sql = "insert into visit_records (pid, vdate, adid, docid, posid, next_vdate, symptoms, disease,"
					+ " diagnosis, depid) values ('" + pid + "','" + sdate + "','" + adid + "','" + docid + "','"
					+ posid + "','" + sndate + "','" + symptom + "','" + disease + "','" + diagnosis + "','" + mydepid
					+ "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					myid = dd.table_maxid("SELECT MAX(vid) AS max_id from visit_records");
					d1.addRow(new Object[] { myid, sdate, docid, docname, posid, position, sndate, adid, addate });
					hh.gotolastrow(vtable);
				} else {
					myid = hh.stoi(rowid);
					d1.setValueAt(sdate, myrow, 1);
					d1.setValueAt(docid, myrow, 2);
					d1.setValueAt(docname, myrow, 3);
					d1.setValueAt(posid, myrow, 4);
					d1.setValueAt(position, myrow, 5);
					d1.setValueAt(sndate, myrow, 6);
					d1.setValueAt(adid, myrow, 7);
					d1.setValueAt(addate, myrow, 8);
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql if  error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
	    visit_details(hh.itos(myid));
		clearFields();
		cards.show(cardPanel, "table");
	}
	
	private void  data_update() {
		int row = vtable.getSelectedRow();
		if (row >= 0) {
			clearFields();
			rowid = vtable.getValueAt(row, 0).toString();
			myrow = row;
			//vid, vdate, docid, dname, posid, posname, nextvdate, adid
			
			txdate.setText(vtable.getValueAt(row, 1).toString());
			String vdate = vtable.getValueAt(row, 1).toString();
			txndate.setText(vtable.getValueAt(row, 6).toString());
			
			txadid.setText(vtable.getValueAt(row, 7).toString());	
			txaddate.setText(vtable.getValueAt(row, 8).toString());	
			String docname = vtable.getValueAt(row, 3).toString();
			String docid = vtable.getValueAt(row, 2).toString();
			String posid = vtable.getValueAt(row, 4).toString();	
			String position = vtable.getValueAt(row, 5).toString();		
			
			String stitle = docname.trim() + " " + position.trim() +" "+ vdate;
			txadmission.setText(stitle);		
				
			hh.setSelectedValue_st2(cmbdoctors, docid, posid);
			cmbdoctors.updateUI();
			tasymptomw.setText(tasymptoms.getText());
			tadiseasew.setText(tadisease.getText());
			tadiagnosisw.setText(tadiagnosis.getText());
			cards.show(cardPanel, "edit");
		}
	}
	private void data_delete() {
		String sql = " delete from visit_records where vid= ";
		int flag = dd.data_delete(vtable, sql);
		if (flag > 0) {
			tasymptoms.setText("");
			tadisease.setText("");
			tadiagnosis.setText("");
		}		
	}

	private Boolean vvalidation(String psdate, String pdocid, String pposid, int pdepid) {
		Boolean ret = true;
		ArrayList<String> err = new ArrayList<String>();

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
	private void visit_details(String vid) {
		String sql = "select symptoms, disease, diagnosis from visit_records  where vid='" + vid + "'";
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String symptoms= rs.getString("symptoms");
				tasymptoms.setText(symptoms);
				String disease = rs.getString("disease");
				tadisease.setText(disease);
				String diagnosis = rs.getString("diagnosis");
				tadiagnosis.setText(diagnosis);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
	}


	private void vtable_update(String swhere) {
		DefaultTableModel m1 = (DefaultTableModel) vtable.getModel();
		m1.setRowCount(0);

		String sql = "select v.vid, v.vdate, v.docid, e.name, v.posid, po.name as posname, v.next_vdate, "
				+ "v.adid, a.add_date  from visit_records v " 
				+ " left join employees e on v.docid = e.empid "
				+ " left join positions po on v.posid = po.posid " 
				+ " left join admission a on v.adid = a.adid " 
				+ swhere;
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String vid = rs.getString("vid");
				String vdate = rs.getString("vdate");
				String docid = rs.getString("docid");
				String dname = rs.getString("name");
				String posid = rs.getString("posid");
				String posname = rs.getString("posname");
				String nextvdate = rs.getString("next_vdate");
				String adid = rs.getString("adid");
				String addate = rs.getString("add_date");
				m1.addRow(new Object[] { vid, vdate, docid, dname, posid, posname, nextvdate, adid, addate });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = { "vid", "Visit Date", "docid", "Doctor", "posid", "Position", "Next visit", "adid", "Admission date" };
		((DefaultTableModel) vtable.getModel()).setColumnIdentifiers(fej);

		int[] wcols0 = { 0, 2, 4, 7 };
		hh.cellswidth_0(vtable, wcols0);
		int[][] wcols = { { 1, 110 }, { 3, 250 }, { 5, 200 }, { 6, 200 },{ 8, 150 } };
		hh.cellswidth(vtable, wcols);

		if (vtable.getRowCount() > 0) {
			int row = vtable.getRowCount() - 1;
			vtable.setRowSelectionInterval(row, row);
		}
	}

	private void make_new_record() {
		clearFields();
		SwingUtilities.invokeLater(() -> cmbdoctors.requestFocusInWindow());
		// cmbdoctors.requestFocus();
	}

	private void clearFields() {
		rowid = "";
		myrow = 0;
		tasymptomw.setText("");
		tadiagnosisw.setText("");
		tadiseasew.setText("");
		txdate.setText("");
		txndate.setText("");
		txadid.setText("");
		txaddate.setText("");
       txadmission.setText("");
		Employee A = new Employee("", "", "", "");
		cmbdoctors.getModel().setSelectedItem(A);
	}

	public void passtovisit(String adid, String docid, String docname, String posid, String position, 
			 String addate) {
		txadid.setText(adid);
		txaddate.setText(addate);
		hh.setSelectedValue_st2(cmbdoctors, docid, posid);
		cmbdoctors.updateUI();
		String stitle = docname.trim() + " " + position.trim() + " " + addate;
		txadmission.setText(stitle);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Visit_record_reg();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	CardLayout cards;
	JPanel cardPanel, tPanel, ePanel, etPanel;
	JLabel lbheader, lbsearch, lbsymptoms, lbdisease, lbdiagnosis, lbadmission, lbdoctor, lbdate;
	JLabel lbsymptomw, lbdiseasew, lbdiagnosisw, lbndate;
	JTable vtable;
	JScrollPane vPane, jspn, jspn1, jspn2, jspnw, jspn1w, jspn2w;
	JButton btnsearch, btnclear, btnnew, btndelete, btnupdate, btnadmission, btnsave, btncancel;
	JTextArea tasymptoms, tadisease, tadiagnosis, tasymptomw, tadiseasew, tadiagnosisw;
	JTextField txadmission, txadid, txdate, txndate, txaddate;
	JComboBox cmbdoctors;
}
