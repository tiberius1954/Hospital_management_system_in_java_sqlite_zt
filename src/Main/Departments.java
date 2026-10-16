package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
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

import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Departments extends JFrame {

	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	String rowid = "";
	int myrow = 0;
	int myid = 0;

	Departments() {
		init();
		txdepname.requestFocus(true);
		deptable_update("");
		hh.iconhere(this);
	}

	private void init() {

		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Departments");

		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(600, 475);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel("Departments");
		lbheader.setBounds(10, 10, 250, 30);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		mPanel = new JPanel(null);
		mPanel.setBounds(10, 50, 560, 370);
		mPanel.setBackground(new Color(178, 216, 216));
		add(mPanel);

		lbname = hh.clabel("Department name");
		lbname.setBounds(10, 20, 150, 30);
		mPanel.add(lbname);

		txdepname = hh.cTextField(40);
		txdepname.setBounds(170, 20, 280, 30);
		mPanel.add(txdepname);
		txdepname.addKeyListener(hh.MUpper());

		lblocation = hh.clabel("Location");
		lblocation.setBounds(10, 60, 150, 30);
		mPanel.add(lblocation);

		txlocation = hh.cTextField(40);
		txlocation.setBounds(170, 60, 280, 30);
		mPanel.add(txlocation);
		txlocation.addKeyListener(hh.MUpper());

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(170, 110, 90, 35);
		btnsave.setBackground(hh.lpiros);
		mPanel.add(btnsave);
		btnsave.addActionListener(e -> savebutt());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(265, 110, 90, 35);
		btncancel.setBackground(Color.green);
		mPanel.add(btncancel);
		btncancel.addActionListener(e -> data_cancel());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(360, 110, 90, 35);
		btndelete.setBackground(Color.yellow);
		mPanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());

		deptable = hh.ztable();
		deptable.setTableHeader(new JTableHeader(deptable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		deptable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = deptable.getSelectedRow();
				if (row >= 0) {
					rowid = deptable.getValueAt(row, 0).toString();
					txdepname.setText(deptable.getValueAt(row, 1).toString());
					myrow = row;
				}
			}
		});

		hh.madeheader(deptable);
		deptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				deptable.scrollRectToVisible(deptable.getCellRect(deptable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane1 = new JScrollPane(deptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		deptable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "depid", "Department name", "Location" }));
		hh.setJTableColumnsWidth(deptable, 530, 0, 50, 50);
		jScrollPane1.setViewportView(deptable);
		jScrollPane1.setBounds(10, 155, 540, 200);
		mPanel.add(jScrollPane1);
		setVisible(true);
	}

	private void savebutt() {
		DefaultTableModel d1 = (DefaultTableModel) deptable.getModel();
		String sql = "";
		int gid = 0;
		String depname = txdepname.getText();
		String location = txlocation.getText();
		if (hh.zempty(depname) == true) {
			return;
		}
		try {
			if (rowid != "") {
				sql = "update departments set depname = '" + depname + "', location= '" + location + "' where depid = "
						+ rowid;
			} else {
				sql = "insert into departments (depname, location) values  ('" + depname + "','" + location + "')";
			}
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(depid) AS max_id from departments");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, depname, location });
					hh.gotolastrow(deptable);
					if (deptable.getRowCount() > 0) {
						int row = deptable.getRowCount() - 1;
						deptable.setRowSelectionInterval(row, row);
					}

				} else {
					table_rowrefresh(depname, location);
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

	private int data_delete() {
		String sql = " delete from departments where depid=";
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) deptable.getModel();
		int sIndex = deptable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String gid = d1.getValueAt(sIndex, 0).toString();
		if (gid.equals("")) {
			return flag;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			int selections[] = deptable.getSelectedRows();
			int lastIndex = selections[0];
			String vsql = sql + "'" + gid + "'";
			try {
				flag = dh.Insupdel(vsql);
				d1.removeRow(sIndex);
				if (deptable.getRowCount() > 0) {
					deptable.setRowSelectionInterval(lastIndex - 1, lastIndex - 1);
				}
			} catch (Exception e1) {
				if (deptable.getRowCount() > 0)
					deptable.setRowSelectionInterval(0, 0);
			}
			clearFields();
		}
		return flag;
	}

	private void table_rowrefresh(String depname, String location) {
		DefaultTableModel d1 = (DefaultTableModel) deptable.getModel();
		d1.setValueAt(depname, myrow, 1);
		d1.setValueAt(location, myrow, 2);
	}

	private void data_cancel() {
		clearFields();
		txdepname.requestFocus(true);
	}

	private void clearFields() {
		txdepname.setText("");
		txlocation.setText("");
		myrow = 0;
		rowid = "";
		txdepname.requestFocus(true);
	}

	private void deptable_update(String what) {
		DefaultTableModel m1 = (DefaultTableModel) deptable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select depid, depname, location from departments order by upper(depname)";
		} else {
			Sql = "select  depid, depname, location  from departments where " + what + " order by upper(depname)";
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String depid = rs.getString("depid");
				String depname = rs.getString("depname");
				String location = rs.getString("location");
				m1.addRow(new Object[] { depid, depname, location });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		hh.setJTableColumnsWidth(deptable, 530, 0, 50, 50);
		deptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				deptable.scrollRectToVisible(deptable.getCellRect(deptable.getRowCount() - 1, 0, true));
			}
		});
		if (deptable.getRowCount() > 0) {
			int row = deptable.getRowCount() - 1;
			deptable.setRowSelectionInterval(row, row);
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Departments();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JTable deptable;
	JLabel lbheader, lbname, lblocation;
	JTextField txdepname, txlocation;
	JButton btnsave, btncancel, btndelete;
	JPanel mPanel;
	JScrollPane jScrollPane1;
}
