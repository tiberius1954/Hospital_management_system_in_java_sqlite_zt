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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
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
import Databaseop.Databaseop.Tests;
import Databaseop.Databaseop.Medicine;

public class Patient_prescription extends JFrame{
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
	String mypid = "";
	String mypatientname = "";
	
	Patient_prescription(){
		init();
		hh.iconhere(this);
	}
	Patient_prescription(int pdepid, String pdepname, String ppatientid, String ppatientname) {
		mypid = ppatientid;
		mypatientname = ppatientname;
		mydepid = pdepid;
		mydepname = pdepname;
		init();
		hh.iconhere(this);
	   ptable_update(" where p.pid='" + mypid + "' and depid=" + mydepid);	
    	dd.cmbmeployee_departmentcombofill(cmbdoctors, "d", mydepid);
		dd.cmbmedicinesfill(cmbmedicines);
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
				dispose();
			}
		});
		setTitle("Patient prescription");
		setSize(1200, 620);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0, 128, 128));
		lbheader = new JLabel("Patient prescription - " + mypatientname + " - " + mydepname);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 20));
		lbheader.setBounds(20, 10, 1000, 35);
		add(lbheader);

		bPanel = new JPanel(null);
		bPanel.setBounds(10, 50, 1160, 515);
		bPanel.setBackground(hh.vvvkek);
		add(bPanel);

		ePanel = new JPanel(null);
		ePanel.setBounds(5, 10, 475, 493);
		ePanel.setBackground(hh.vvvkek);
		ePanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		bPanel.add(ePanel);

		tPanel = new JPanel(null);
		tPanel.setBounds(480, 10, 673, 493);
		tPanel.setBackground(hh.vvvkek);
		tPanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		bPanel.add(tPanel);
		
		lbdate = hh.clabel("Date");
		lbdate.setBounds(10, 20, 90, 25);
		ePanel.add(lbdate);

		txdate = hh.DatetextField();
		txdate.setBounds(110, 20, 110, 28);
		ePanel.add(txdate);

		lbdoctor = hh.clabel("Doctors");
		lbdoctor.setBounds(10, 60, 90, 25);
		ePanel.add(lbdoctor);

		cmbdoctors = gr.grcombo();
		cmbdoctors.setFocusable(true);
		cmbdoctors.setName("doctors");
		cmbdoctors.setBounds(110, 60, 270, 30);
		ePanel.add(cmbdoctors);
		// cmbdoctors.addActionListener(listener);
		
		lbmedicine = hh.clabel("Medicine");
		lbmedicine.setBounds(10, 100, 90, 25);
		ePanel.add(lbmedicine);

		cmbmedicines = gr.grcombo();
		cmbmedicines.setFocusable(true);
		cmbmedicines.setName("medicines");
		cmbmedicines.setBounds(110, 100, 270, 30);
		ePanel.add(cmbmedicines);
		// cmbdoctors.addActionListener(listener);
		
		lbdosage = hh.clabel("Dosage");
		lbdosage.setBounds(10, 140, 90, 25);
		ePanel.add(lbdosage);

		txdosage = hh.cTextField(100);
		txdosage.setBounds(110, 140, 270, 28);
		ePanel.add(txdosage);
		
		lbfrequency = hh.clabel("Frequency");
		lbfrequency.setBounds(10, 180, 90, 25);
		ePanel.add(lbfrequency);

		txfrequency = hh.cTextField(100);
		txfrequency.setBounds(110, 180, 270, 28);
		ePanel.add(txfrequency);
		
		lbduration = hh.clabel("Duration");
		lbduration.setBounds(10, 220, 90, 25);
		ePanel.add(lbduration);

		txduration = hh.cTextField(100);
		txduration.setBounds(110, 220, 270, 28);
		ePanel.add(txduration);
		
		lbnotesw = hh.clabel("Notes");
		lbnotesw.setBounds(10, 260, 90, 25);
		ePanel.add(lbnotesw);
		
		txanotesw = hh.cTextarea();
		notespanew= new JScrollPane(txanotesw, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		notespanew.setBounds(110, 260, 350, 150);
		notespanew.setViewportView(txanotesw);
		notespanew.setBorder(hh.borderf);
		ePanel.add(notespanew);
		
		btnsave = gr.sbcs("Save");
		btnsave.setBounds(130, 435, 100, 30);
		btnsave.setBackground(hh.lpiros);
		ePanel.add(btnsave);
		
		btnsave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				savebuttrun();
			}
		});

		btncancel = gr.sbcs("Cancel");
		btncancel.setBackground(Color.green);
		btncancel.setBounds(235, 435, 100, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			clearFields();
			}
		});

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(340, 435, 100, 30);
		btndelete.setBackground(Color.yellow);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			//	data_delete();
			}
		});
		
		ptable = hh.ztable();
		ptable.setTableHeader(new JTableHeader(ptable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(ptable);
		ptable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ptable.scrollRectToVisible(ptable.getCellRect(ptable.getRowCount() - 1, 0, true));
			}
		});
		ptable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				 if (evt.getClickCount() == 2) {
				int row = ptable.getSelectedRow();
				if (row >= 0) {
					rowid = ptable.getValueAt(row, 0).toString();
					int number = 0;
					String cnum = ptable.getValueAt(row, 7).toString();
					String cnump = ptable.getValueAt(row, 9).toString();
					if (!hh.zempty(cnum)) {
						hh.setSelectedValue_st2(cmbdoctors, cnum, cnump);
						cmbdoctors.updateUI();
					}
					cnum = ptable.getValueAt(row, 2).toString();
					if (!hh.zempty(cnum)) {
						number = Integer.parseInt(cnum);
						hh.setSelectedValue(cmbmedicines, number);
						cmbmedicines.updateUI();
					}
					txdate.setText(ptable.getValueAt(row, 1).toString());
					String notes = txanotes.getText();
					txanotesw.setText(notes);		
					txdosage.setText(ptable.getValueAt(row, 4).toString());
					txfrequency.setText(ptable.getValueAt(row, 5).toString());
					txduration.setText(ptable.getValueAt(row, 6).toString());	
					myrow = row;
				}
				 }
			}
		});

		jScrollPane1 = new JScrollPane(ptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ptable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null, null, null} },
				new String[] {  "pres_id", "Date", "medid", "Medication name","Dosage","Frequency",
						"duration","docid", "Doctor Name", "posid", "Position" }));

		jScrollPane1.setViewportView(ptable);
		jScrollPane1.setBounds(10, 25, 650, 250);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);
		
//		((DefaultTableModel) ptable.getModel()).setColumnIdentifiers(fej);
		
		int[] wcols0 = {0, 2, 7,9};
		hh.cellswidth_0(ptable, wcols0);

		int[][] wcols = { { 1, 90 }, { 3, 200 },{ 4, 100 }, { 5, 150 }, { 6, 150 },{ 8, 200 },{ 10, 150 } };
		hh.cellswidth(ptable, wcols);		
		
		ptable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) ptable.getModel();
				try {
					int row = ptable.getSelectedRow();
					int sor = model.getRowCount();
					if (row > -1 && sor > 0) {				
						String presid = model.getValueAt(row, 0).toString();
						presdetails(presid);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		lbnotes = hh.clabel("Notes");
		lbnotes.setBounds(270, 275, 90, 25);
		tPanel.add(lbnotes);

		txanotes = hh.cTextarea();
		txanotes.setEditable(false);
		notespane = new JScrollPane(txanotes, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		notespane.setBounds(160, 300, 350, 170);
		notespane.setViewportView(txanotes);
		notespane.setBorder(hh.borderf);
		tPanel.add(notespane);		
		setVisible(true);
	}
	
	private void savebuttrun(){
		DefaultTableModel d1 = (DefaultTableModel) ptable.getModel();
		String sql = "";
		int medid = 0;
		String mname = "";
		String docid = "";
		String dname = "";
		String posid = "";
		String position = "";
		String presdate = txdate.getText();
		if (presdate.equals("____-__-__")) {
			presdate = "";
		}

		if (cmbmedicines.getSelectedItem() != null) {
			Medicine medicine = (Medicine) cmbmedicines.getSelectedItem();
			medid = medicine.getMedid();
			mname = medicine.getName();
		}
		if (cmbdoctors.getSelectedItem() != null) {
			Employee emp = (Employee) cmbdoctors.getSelectedItem();
			docid = emp.getEmpid();
			dname = emp.getName();
			posid = emp.getPosid();
			position = emp.getPosition();
		}
		String notes = txanotesw.getText();
		String frequency = txfrequency.getText();
		String dosage = txdosage.getText();
		String duration = txduration.getText();
	

		if (hh.zempty(mname) || hh.zempty(dname) || hh.zempty(presdate)) {
			JOptionPane.showMessageDialog(null, "Please fill medication name, doctor name,  date fields");
			return;
		}
		if (rowid != "") {
			sql = "update prescription set medid=" + medid + ",  presdate='" + presdate 
					+ "', notes='" + notes + "', pid='"+  mypid +"', docid='" + docid +"', "
					 + " posid='" + posid + "', depid=" + mydepid + ", dosage='"  + dosage +"', "
					+ " frequency= '"+ frequency +"', duration='"+ duration +"' where pres_id = '" + rowid+"'";
		} else {
			sql = "insert into prescription  (medid, presdate,  notes, pid, docid, posid, depid, "
					+ " dosage, frequency, duration ) values  (" 
					+ medid + ",'" + presdate + "','" + notes + "', '" + mypid + "','" +docid + "','"
					 + posid + "'," + mydepid + ", '" + dosage +"','"+ frequency + "','"
					+ duration +"')";
		}

		try {
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(pres_id) AS max_id from prescription");
					d1.insertRow(d1.getRowCount(),
							new Object[] { myid, presdate, medid, mname, dosage, frequency, duration, 
									docid, dname, posid, position });
					rowid = hh.itos(myid);
					hh.gotolastrow(ptable);
					if (ptable.getRowCount() > 0) {
						int row = ptable.getRowCount() - 1;
						ptable.setRowSelectionInterval(row, row);
					}
				} else {
					table_rowrefresh(presdate, medid, mname, dosage, frequency, duration, 
							docid, dname, posid, position);
				}
				presdetails(rowid);
			} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
		clearFields();
		
	}
private void	table_rowrefresh(String presdate, int medid, String mname, String dosage, 
		String frequency, String duration, String docid, String dname, String posid, String position) {
	DefaultTableModel d1 = (DefaultTableModel) ptable.getModel();
	d1.setValueAt(presdate, myrow, 1);
	d1.setValueAt(hh.itos(medid), myrow, 2);
	d1.setValueAt(mname, myrow, 3);
	d1.setValueAt(dosage, myrow, 4);
	d1.setValueAt(frequency, myrow, 5);
	d1.setValueAt(duration, myrow, 6);
	d1.setValueAt(docid, myrow, 7);
	d1.setValueAt(dname, myrow, 8);
	d1.setValueAt(posid, myrow, 9);
	d1.setValueAt(position, myrow, 10);	
}
	
	private void data_delete() {
		String sql = " delete from prescription where pres_	id = ";
		int flag = dd.data_delete(ptable, sql);
		if (flag > 0) {
			clearFields();
		}
	}

	private void clearFields() {
	myrow = 0;
	rowid = "";	
	txdate.setText("");
	txdosage.setText("");
	 txfrequency.setText("");
	txduration.setText("");
	txanotesw.setText("");
//	txanotes.setText("");
	cmbmedicines.setSelectedIndex(0);	
	Employee A = new Employee("", "","","");
	cmbdoctors.getModel().setSelectedItem(A);	
	//cmbdoctors.setSelectedIndex(0);	
	}
	
	private void ptable_update(String where) {
		DefaultTableModel m1 = (DefaultTableModel) ptable.getModel();
		m1.setRowCount(0);
		String Sql = "select  p.pres_id, p.presdate, p.medid, m.name as medname, p.dosage, p.frequency,"
				+ " p.duration, p.docid, e.name, p.posid,  ps.name as position from  prescription p  " 
				+ "  left join employees e  on p.docid = e.empid "
				+ " left join positions ps on p.posid = ps.posid " 
				+ " left join medicine m on p.medid = m.medid " + where;
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String pres_id = rs.getString("pres_id");
				String presdate= rs.getString("presdate");
				String medid = rs.getString("medid");
				String medname = rs.getString("medname");
				String dosage = rs.getString("dosage");
				String frequency = rs.getString("frequency");
				String duration = rs.getString("duration");
				String docid = rs.getString("docid");
				String dname = rs.getString("name");
				String posid = rs.getString("posid");
				String position = rs.getString("position");
				m1.addRow(new Object[] { pres_id, presdate, medid, medname, dosage,  frequency, duration,
						docid, dname, posid, position });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "pres_id", "Date", "medid", "Medication name","Dosage","Frequency",
				"duration","docid", "Doctor Name", "posid", "Position" };
		((DefaultTableModel) ptable.getModel()).setColumnIdentifiers(fej);
		
		TableColumnModel ccm = ptable.getColumnModel();
		ccm.getColumn(0).setWidth(0);
		ccm.getColumn(0).setMinWidth(0);
		ccm.getColumn(0).setMaxWidth(0);
		ccm.getColumn(2).setWidth(0);
		ccm.getColumn(2).setMinWidth(0);
		ccm.getColumn(2).setMaxWidth(0);
		ccm.getColumn(7).setWidth(0);
		ccm.getColumn(7).setMinWidth(0);
		ccm.getColumn(7).setMaxWidth(0);
		ccm.getColumn(9).setWidth(0);
		ccm.getColumn(9).setMinWidth(0);
		ccm.getColumn(9).setMaxWidth(0);
		int[] wcols0 = {0, 2, 7,9};
		hh.cellswidth_0(ptable, wcols0);

		int[][] wcols = { { 1, 90 }, { 3, 200 },{ 4, 100 }, { 5, 150 }, { 6, 150 },{ 8, 200 },{ 10, 150 } };
		hh.cellswidth(ptable, wcols);

		ptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ptable.scrollRectToVisible(ptable.getCellRect(ptable.getRowCount() - 1, 0, true));
			}
		});
		if (ptable.getRowCount() > 0) {
			int row = ptable.getRowCount() - 1;
			ptable.setRowSelectionInterval(row, row);
		}
	}
	
	private void presdetails(String ppres_id) {
		String sql = "select notes from prescription  where pres_id='" + ppres_id + "'";
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String notes = rs.getString("notes");
				txanotes.setText(notes);				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Patient_prescription();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
JTable ptable;
JPanel bPanel, ePanel, tPanel;
JLabel lbheader, lbdate, lbdoctor, lbmedicine, lbdosage, lbfrequency, lbduration, lbnotesw, lbnotes;
JComboBox cmbdoctors, cmbmedicines;
JFormattedTextField txdate;
JTextField txdosage, txfrequency, txduration;
JTextArea txanotesw, txanotes;
JScrollPane notespanew, notespane,  jScrollPane1;
JButton btnsave, btncancel, btndelete;
}
