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
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Calhelper;
import Classes.Employee;
import Classes.Hhelper;
import Classes.Grlib;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Position;

public class Docapptimes extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	private String rowid = "";
	private int myrow = 0;
	String[] days = cc.daynames();
	int mydepid = 0;
	String mydepname = "";

	Docapptimes() {
		initcomponents();
		hh.iconhere(this);
		intable_update(intable);
	}

	Docapptimes(int pdepid, String pdepname) {
		mydepid = pdepid;
		mydepname = pdepname;
		initcomponents();
		hh.iconhere(this);
		dd.cmbmeployee_departmentcombofill(cmbdoctors, "d", mydepid);
		intable_update(intable);
	}

	private void initcomponents() {
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
		setTitle("Doctor appointment times");	
		setSize(1150, 580);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0, 128, 128));
		lbheader = new JLabel("Doctor appointment times - " + mydepname);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 20));
		lbheader.setBounds(20, 10, 800, 35);
		add(lbheader);

		bPanel = new JPanel(null);
		bPanel.setBounds(10, 50, 1130, 480);
		bPanel.setBackground(hh.vvvkek);
		add(bPanel);

		ePanel = new JPanel(null);
		ePanel.setBounds(5, 10, 395, 450);
		ePanel.setBackground(hh.vvvkek);
		ePanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		bPanel.add(ePanel);

		tPanel = new JPanel(null);
		tPanel.setBounds(408, 10, 710, 450);
		tPanel.setBackground(hh.vvvkek);
		tPanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		bPanel.add(tPanel);

		lbdoctor = hh.clabel("Doctors");
		lbdoctor.setBounds(10, 60, 90, 25);
		ePanel.add(lbdoctor);

		cmbdoctors = gr.grcombo();
		cmbdoctors.setFocusable(true);
		cmbdoctors.setName("doctors");
		cmbdoctors.setBounds(110, 60, 270, 30);
		ePanel.add(cmbdoctors);
	

		lbday = hh.clabel("Day");
		lbday.setBounds(10, 110, 90, 25);
		ePanel.add(lbday);

		cmbdays = gr.grcombo();
		cmbdays.setFocusable(true);
		cmbdays.setName("wdays");
		cmbdays.setBounds(110, 110, 200, 30);
		ePanel.add(cmbdays);
		for (int i = 0; i < 7; i++) {
			cmbdays.addItem(days[i]);
		}

		lbstart = hh.clabel("Start time");
		lbstart.setBounds(10, 160, 90, 25);
		ePanel.add(lbstart);

		String rtime = "07:00";
		startmodel = new SpinnerDateModel();
		starttime = hh.cspinner(startmodel);
		starttime.setBounds(110, 160, 70, 25);
		starttime.setBorder(hh.myRaisedBorder);
		hh.madexxx(starttime, "T");
		starttime.setName("starttime");
		ePanel.add(starttime);
		starttime.setValue(hh.stringtotime(rtime));
		((JSpinner.DefaultEditor) starttime.getEditor()).getTextField().addFocusListener(sFocusListener);
		starttime.addChangeListener((ChangeListener) new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				starttime.requestFocus();
			}
		});

		lbend = hh.clabel("End time");
		lbend.setBounds(10, 210, 90, 25);
		ePanel.add(lbend);

		endmodel = new SpinnerDateModel();
		endtime = hh.cspinner(endmodel);
		endtime.setBounds(110, 210, 70, 25);
		endtime.setBorder(hh.myRaisedBorder);
		hh.madexxx(endtime, "T");
		endtime.setName("endtime");
		ePanel.add(endtime);
		rtime = "18:00";
		endtime.setValue(hh.stringtotime(rtime));
		((JSpinner.DefaultEditor) endtime.getEditor()).getTextField().addFocusListener(sFocusListener);

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(140, 270, 130, 30);
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
		btncancel.setBounds(140, 310, 130, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(140, 350, 130, 30);
		btndelete.setBackground(Color.yellow);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_delete();
			}
		});

		intable = hh.ztable();
		intable.setTableHeader(new JTableHeader(intable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(intable);
		intable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				intable.scrollRectToVisible(intable.getCellRect(intable.getRowCount() - 1, 0, true));
			}
		});
		intable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				 if (evt.getClickCount() == 2) {
				int row = intable.getSelectedRow();
				if (row >= 0) {
					rowid = intable.getValueAt(row, 0).toString();
					int number = 0;
					String cnum = intable.getValueAt(row, 1).toString();
					String cnump =  intable.getValueAt(row, 3).toString();
					if (!hh.zempty(cnum)) {				
						hh.setSelectedValue_st2(cmbdoctors, cnum,cnump);
						cmbdoctors.updateUI();
					}				
					cmbdays.setSelectedItem(intable.getValueAt(row, 3).toString());
					String starttime = intable.getValueAt(row, 6).toString();
					startmodel.setValue(hh.stringtotime(starttime));
					String endtime = intable.getValueAt(row, 7).toString();
					endmodel.setValue(hh.stringtotime(endtime));
					myrow = row;
				}
				 }
			}
		});

		jScrollPane1 = new JScrollPane(intable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		intable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "tid", "empid", "Doctor Name", "posid", "Position", "Day", "Start time", 
				"End time", "departnent" }));
		hh.setJTableColumnsWidth(intable, 690, 0, 0, 38, 0, 25, 15, 11, 11, 0);
		jScrollPane1.setViewportView(intable);
		jScrollPane1.setBounds(10, 25, 690, 340);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);

		setVisible(true);
	}

	private final FocusListener sFocusListener = new FocusListener() {
		@Override
		public void focusGained(FocusEvent e) {
			JComponent c = (JComponent) e.getSource();
		}

		@Override
		public void focusLost(FocusEvent e) {
			JComponent b = (JComponent) e.getSource();
			if (b.getParent().getParent().getName() == "starttime") {
				JTextField intf = ((JSpinner.DefaultEditor) starttime.getEditor()).getTextField();
				String intime = intf.getText();
				if (hh.correcttime(intime) == true) {
					JOptionPane.showMessageDialog(null, "Correct time is 07:00 - 18:00 !");
				}
			} else {
				JTextField outtf = ((JSpinner.DefaultEditor) endtime.getEditor()).getTextField();
				String outtime = outtf.getText();
				if (hh.correcttime(outtime) == true) {
					JOptionPane.showMessageDialog(null, "Correct time is 07:00 - 18:00 !");
				}
			}
		}
	};

	private void savebuttrun() {
		String sql = "";
		String empid = "";
		String dname = "";
		String pname = "";
		String posid = "";
		DefaultTableModel d1 = (DefaultTableModel) intable.getModel();
		if (cmbdoctors.getSelectedItem() != null) {
			Employee emp = (Employee) cmbdoctors.getSelectedItem();
			empid = emp.getEmpid();
			dname = emp.getName();
			posid = emp.getPosid();
			pname = emp.getPosition();
		}		
		
		String dayname = String.valueOf(cmbdays.getSelectedItem());
		JTextField intf = ((JSpinner.DefaultEditor) starttime.getEditor()).getTextField();
		String starttime = intf.getText();
		intf = ((JSpinner.DefaultEditor) endtime.getEditor()).getTextField();
		String endtime = intf.getText();
		if (hh.zempty(dname) || hh.zempty(dayname) || hh.zempty(pname)) {
			JOptionPane.showMessageDialog(null, "Please fill name, position,  dayname  fields");
			return;
		}
		if (hh.twotime(starttime, endtime) == false) {
			return;
		}

			if (rowid != "") {
				sql = "update doctorapptimes set dayname= '" + dayname + "',  empid='" + empid + "', posid='" + posid
						+ "', starttime='" + starttime + "', endtime='" + endtime + "',  depid=" + mydepid + " where tid = '"
						+ rowid +"'";
			} else {
				sql = "insert into doctorapptimes  (dayname, empid,  posid, starttime, endtime,depid) " + "values" + " ('"
						+ dayname + "','" + empid + "','" + posid + "', '" + starttime + "','" + endtime + "', " + mydepid
						+ ")";
			}
		try {
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(tid) AS max_id from doctorapptimes");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, empid, dname, posid, pname, 
							dayname, starttime, endtime, mydepid });
					hh.gotolastrow(intable);
					if (intable.getRowCount() > 0) {
						int row = intable.getRowCount() - 1;
						intable.setRowSelectionInterval(row, row);
					}
				} else {
					table_rowrefresh(empid, dname, posid, pname, dayname, starttime, endtime, mydepid);
				}
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

	private void table_rowrefresh(String empid, String dname, String posid, String pname, String dayname,
			String starttime, String endtime, int depid) {
		DefaultTableModel d1 = (DefaultTableModel) intable.getModel();
		d1.setValueAt(empid, myrow, 1);
		d1.setValueAt(dname, myrow, 2);
		d1.setValueAt(posid, myrow, 3);
		d1.setValueAt(pname, myrow, 4);
		d1.setValueAt(dayname, myrow, 5);
		d1.setValueAt(starttime, myrow, 6);
		d1.setValueAt(endtime, myrow, 7);
		d1.setValueAt(depid, myrow, 8);		
	}

	private int data_delete() {
		String sql = "delete from doctorapptimes  where tid =";
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) intable.getModel();
		int sIndex = intable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String iid = d1.getValueAt(sIndex, 0).toString();
		if (iid.equals("")) {
			return flag;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			String vsql = sql + iid;
			flag = dh.Insupdel(vsql);
			if (flag > 0) {
				d1.removeRow(sIndex);
				clearFields();
			}
		}
		return flag;
	}

	private void clearFields() {
		cmbdoctors.setSelectedIndex(0);	
		cmbdays.setSelectedItem("Monday");
		String rtime = "07:00";
		starttime.setValue(hh.stringtotime(rtime));
		rtime = "18:00";
		endtime.setValue(hh.stringtotime(rtime));
		myrow = 0;
		rowid = "";
	}

	public void intable_update(JTable dtable) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);

		String Sql = "select  a.tid, a.empid, e.name, a.posid, p.name as pname, a.dayname,"
				+ " a.starttime, a.endtime, depid from doctorapptimes a  "
				+ " left join employees  e  on a.empid = e.empid "
				+ "  left join positions p on a.posid = p.posid where depid= " + mydepid
				+ " order by e.name, a.dayname, a.starttime";

		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String tid = rs.getString("tid");
				String empid = rs.getString("empid");
				String name = rs.getString("name");
				String posid = rs.getString("posid");
				String pname = rs.getString("pname");
				String dayname = rs.getString("dayname");
				String starttime = rs.getString("starttime");
				String endtime = rs.getString("endtime");
				String depid = rs.getString("depid");
				m1.addRow(new Object[] { tid, empid, name, posid, pname, dayname, starttime, endtime, depid });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "tid", "empid", "Doctor Name", "posid", "Position", "Day", "Start time", "End time",
				"department" };
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(dtable, 690, 0, 0, 38, 0, 25, 15, 11, 11, 0);

		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		if (dtable.getRowCount() > 0) {
			int row = dtable.getRowCount() - 1;
			dtable.setRowSelectionInterval(row, row);
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Docapptimes in = new Docapptimes();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbday, lbdoctor, lbstart, lbend, lbdepname, lbposition;
	JPanel bPanel, ePanel, tPanel;
	JComboBox cmbdoctors, cmbdays, cmbdepartments, cmbpositions;
	SpinnerDateModel startmodel, endmodel;
	JSpinner starttime, endtime;
	JButton btnsave, btncancel, btndelete;
	JTable intable;
	JScrollPane jScrollPane1;
}
