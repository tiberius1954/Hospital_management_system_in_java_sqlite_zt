																																																																																																																																																																																																																																																																																																																																				package Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import Classes.Calhelper;
import Classes.Employee;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Position;

public class Docapptimecancel extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	private String rowid = "";
	private int myrow = 0;
	JDateChooser startdate = new JDateChooser(new Date());
	JDateChooser enddate = new JDateChooser(new Date());
	int mydepid = 0;
	String mydepname = "";

	Docapptimecancel() {
		initcomponents();
		hh.iconhere(this);
		dd.cmbmeployee_departmentcombofill(cmbdoctors, "d", mydepid);
//		cntable_update(cntable);
		}

	Docapptimecancel(int pdepid, String pdepname) {
		mydepid = pdepid;
		mydepname = pdepname;
		initcomponents();
		hh.iconhere(this);
		dd.cmbmeployee_departmentcombofill(cmbdoctors, "d", mydepid);
		cntable_update(cntable);
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
		setTitle("Doctor appointment times cancel");																																														
		setSize(1150, 580);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0, 128, 128));
		lbheader = hh.fflabel("Doctor appointment times cancel " + mydepname);
		lbheader.setBounds(20, 10, 600, 35);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 20));
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

		lbdoctor = hh.clabel("Doctor");
		lbdoctor.setBounds(10, 40, 90, 25);
		ePanel.add(lbdoctor);

		cmbdoctors = gr.grcombo();
		cmbdoctors.setFocusable(true);
		cmbdoctors.setName("doctors");
		cmbdoctors.setBounds(120, 40, 260, 30);
		ePanel.add(cmbdoctors);
			

		lbstartdate = hh.clabel(" Start date");
		lbstartdate.setBounds(10, 90, 90, 25);
		ePanel.add(lbstartdate);

		startdate.setDateFormatString("yyyy-MM-dd");
		startdate.setFont(new Font("Arial", Font.BOLD, 16));
		startdate.setBounds(120, 90, 120, 30);
		startdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		ePanel.add(startdate);
		startdate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					// countd();
				}
			}
		});

		lbenddate = hh.clabel("End  date");
		lbenddate.setBounds(10, 130, 90, 25);
		ePanel.add(lbenddate);

		enddate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		enddate.setDateFormatString("yyyy-MM-dd");
		enddate.setFont(new Font("Arial", Font.BOLD, 16));
		enddate.setBounds(120, 130, 120, 25);
		ePanel.add(enddate);
		enddate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if ("date".equals(e.getPropertyName())) {
					// countd();
				}
			}
		});

		lbstart = hh.clabel("Start time");
		lbstart.setBounds(10, 170, 90, 25);
		ePanel.add(lbstart);

		String rtime = "07:00";
		startmodel = new SpinnerDateModel();
		starttime = hh.cspinner(startmodel);
		starttime.setBounds(120, 170, 70, 25);
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
		endtime.setBounds(120, 210, 70, 25);
		endtime.setBorder(hh.myRaisedBorder);
		hh.madexxx(endtime, "T");
		endtime.setName("endtime");
		ePanel.add(endtime);
		rtime = "18:00";
		endtime.setValue(hh.stringtotime(rtime));
		((JSpinner.DefaultEditor) endtime.getEditor()).getTextField().addFocusListener(sFocusListener);

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(120, 260, 130, 30);
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
		btncancel.setBounds(120, 300, 130, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(120, 340, 130, 30);
		btndelete.setBackground(Color.yellow);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_delete();
			}
		});

		cntable = hh.ztable();
		cntable.setTableHeader(new JTableHeader(cntable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(cntable);
		cntable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				cntable.scrollRectToVisible(cntable.getCellRect(cntable.getRowCount() - 1, 0, true));
			}
		});
		cntable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				 if (evt.getClickCount() == 2) {
				int row = cntable.getSelectedRow();
				if (row >= 0) {
					rowid = cntable.getValueAt(row, 0).toString();
					int number = 0;
					String cnum = cntable.getValueAt(row, 1).toString();
					String cnump =  cntable.getValueAt(row, 3).toString();
					if (!hh.zempty(cnum)) {				
						hh.setSelectedValue_st2(cmbdoctors, cnum,cnump);
						cmbdoctors.updateUI();
						}							
				    Date date;
					Date date1;
					try {
						String dd = cntable.getValueAt(row, 5).toString();
						date = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
						startdate.setDate(date);
						dd = cntable.getValueAt(row, 6).toString();
						date1 = new SimpleDateFormat("yyyy-MM-dd").parse(dd);
						enddate.setDate(date1);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					String starttime = cntable.getValueAt(row, 7).toString();
					startmodel.setValue(hh.stringtotime(starttime));
					String endtime = cntable.getValueAt(row, 8).toString();
					endmodel.setValue(hh.stringtotime(endtime));
					myrow = row;
				}
				 }
			}
		});

		jScrollPane1 = new JScrollPane(cntable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		cntable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, 
				new String[] { "cid", "empid", "Doctor Name", "posid", "Position", "Start date",
				    		"End date", "Start time", "End time", "depid" }));
		hh.setJTableColumnsWidth(cntable, 555, 0, 0, 38, 0, 18, 13, 13, 9, 9, 0);
		jScrollPane1.setViewportView(cntable);
		jScrollPane1.setBounds(10, 25, 690, 340);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);
		setVisible(true);
	}

	private void savebuttrun() {
		String sql = "";
		int did = 0;
		String dname = "";
		String empid = "";
		String pname = "";
		String posid = "";
		DefaultTableModel d1 = (DefaultTableModel) cntable.getModel();
		
		if (cmbdoctors.getSelectedItem() != null) {
			Employee emp = (Employee) cmbdoctors.getSelectedItem();
			empid = emp.getEmpid();
			dname = emp.getName();
			posid = emp.getPosid();
			pname = emp.getPosition();
		}		
		
		String startdat = ((JTextField) startdate.getDateEditor().getUiComponent()).getText();
		String enddat = ((JTextField) enddate.getDateEditor().getUiComponent()).getText();
		JTextField intf = ((JSpinner.DefaultEditor) starttime.getEditor()).getTextField();
		String starttime = intf.getText();
		intf = ((JSpinner.DefaultEditor) endtime.getEditor()).getTextField();
		String endtime = intf.getText();

		if (hh.zempty(dname) || hh.zempty(pname)|| hh.zempty(startdat) || hh.zempty(enddat)) {
			JOptionPane.showMessageDialog(null, "Please fill name, startdate, enddate  fields");
			return;
		}
		if (hh.twodate(startdat, enddat) == false) {
			return;
		}
		if (cc.dateisworkday(startdat) == false || cc.dateisworkday(enddat) == false) {
			JOptionPane.showMessageDialog(null, "Dates are not workdays");
			return;
		}

		if (hh.twotime(starttime, endtime) == false) {
			return;
		}

		if (rowid != "") {
			sql = "update docappticancel set  empid='" + empid + 
					"', posid='" + posid + "', startdate='" + startdat + "', enddate='" + enddat
					+ "', starttime='" + starttime + "', endtime='" + endtime + "',  depid=" + mydepid +
					" where cid = '" + rowid +"'";
		} else {
			sql = "insert into docappticancel  (empid, posid, startdate, enddate, starttime, endtime,"
					+ "depid) " + "values" + " ('" + empid + "', '" + posid + "', '" + startdat + "','" + enddat 
					+ "','" + starttime + "','" + endtime + "'," + mydepid+")";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(cid) AS max_id from docappticancel");
					d1.insertRow(d1.getRowCount(),
							new Object[] { myid, empid, dname, posid, pname, startdat, enddat, starttime,
									endtime, mydepid });
					
					hh.gotolastrow(cntable);
					if (cntable.getRowCount() > 0) {
						int row = cntable.getRowCount() - 1;
						cntable.setRowSelectionInterval(row, row);
					}
				} else {
					table_rowrefresh(empid, dname, posid, pname, startdat, enddat, starttime, endtime, mydepid);
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

	private void table_rowrefresh(String empid, String dname, String posid, String pname, String startdate,
			String enddate, String starttime, String endtime, int depid) {
		DefaultTableModel d1 = (DefaultTableModel) cntable.getModel();
		d1.setValueAt(empid, myrow, 1);
		d1.setValueAt(dname, myrow, 2);
		d1.setValueAt(posid, myrow, 3);
		d1.setValueAt(pname, myrow, 4);
		d1.setValueAt(startdate, myrow, 5);
		d1.setValueAt(enddate, myrow, 6);
		d1.setValueAt(starttime, myrow, 7);
		d1.setValueAt(endtime, myrow, 8);
		d1.setValueAt(depid, myrow, 9);	
	}

	private int data_delete() {
		String sql = "delete from docappticancel  where cid =";
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) cntable.getModel();
		int sIndex = cntable.getSelectedRow();
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

	public void cntable_update(JTable dtable) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "select  a.cid, a.empid, e.name, a.posid,  p.name as pname, "
				+ " a.startdate, a.enddate, a.starttime, a.endtime, depid from "
				+ " docappticancel a  join employees e  on a.empid = e.empid "
				+ " left join positions p on a.posid = p.posid where depid=" + mydepid
				+ " order by e.name, a.startdate, a.starttime";

		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String cid = rs.getString("cid");
				String empid = rs.getString("empid");
				String name = rs.getString("name");
				String posid = rs.getString("posid");
				String pname = rs.getString("pname");				
				String startdate = rs.getString("startdate");
				String enddate = rs.getString("enddate");
				String starttime = rs.getString("starttime");
				String endtime = rs.getString("endtime");
				String depid = rs.getString("depid");
				
				m1.addRow(new Object[] { cid, empid, name, posid, pname, startdate, enddate, 
						starttime, endtime, depid });				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "cid", "did", "Doctor Name", "posid", "Position", "Startdate", "Enddate", "Start time",
				"End time", "depid" };
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(dtable, 555, 0, 0, 38, 0, 20, 12, 12, 9, 9, 0);

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

	private void clearFields() {	
		cmbdoctors.setSelectedIndex(0);		
		Date date = new Date();
		startdate.setDate(date);
		enddate.setDate(date);
		String rtime = "07:00";
		starttime.setValue(hh.stringtotime(rtime));
		rtime = "18:00";
		endtime.setValue(hh.stringtotime(rtime));
		myrow = 0;
		rowid = "";

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


	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Docapptimecancel in = new Docapptimecancel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbdoctor, lbenddate, lbstartdate, lbstart, lbend, lbposition;
	JComboBox cmbdoctors, cmbpositions;
	JPanel ePanel, tPanel, bPanel;
	SpinnerDateModel startmodel, endmodel;
	JSpinner starttime, endtime;
	JButton btnsave, btncancel, btndelete;
	JTable cntable;
	JScrollPane jScrollPane1;

}
