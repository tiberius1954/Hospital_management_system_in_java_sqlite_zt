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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Specializations extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	String rowid = "";
	int myrow = 0;
	int myid = 0;
	String mytype = "s";
	String myheader = "";

	Specializations() {
		init();
		txname.requestFocus(true);
		stable_update("");
		hh.iconhere(this);
	}

	Specializations(String mtype) {
		mytype = mtype;
		if (mytype == "s") {
			myheader = "Staff";
		} else if (mytype == "n") {
			myheader = "Nurse";
		} else if (mytype == "d") {
			myheader = "Doctor";
		}
		init();
		txname.requestFocus(true);
		stable_update("");
		hh.iconhere(this);
	}

	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Specializations");

		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(497, 475);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel(myheader + " specializations");
		lbheader.setBounds(10, 10, 280, 30);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		mPanel = new JPanel(null);
		mPanel.setBounds(10, 50, 460, 370);
		// mPanel.setBorder(hh.ztroundborder(hh.szold));
		mPanel.setBackground(new Color(178, 216, 216));
		add(mPanel);

		lbname = hh.clabel("Name");
		lbname.setBounds(10, 20, 70, 30);
		mPanel.add(lbname);

		txname = hh.cTextField(40);
		txname.setBounds(90, 20, 335, 30);
		mPanel.add(txname);
		txname.addKeyListener(hh.MUpper());

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(90, 75, 90, 35);
		btnsave.setBackground(hh.lpiros);
		mPanel.add(btnsave);
		btnsave.addActionListener(e -> savebutt());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(185, 75, 90, 35);
		btncancel.setBackground(Color.green);
		mPanel.add(btncancel);
		btncancel.addActionListener(e -> data_cancel());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(280, 75, 90, 35);
		btndelete.setBackground(Color.yellow);
		mPanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());

		stable = hh.ztable();
		stable.setTableHeader(new JTableHeader(stable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		stable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = stable.getSelectedRow();
				if (row >= 0) {
					rowid = stable.getValueAt(row, 0).toString();
					txname.setText(stable.getValueAt(row, 1).toString());
					myrow = row;
				}
			}
		});

		hh.madeheader(stable);
		stable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				stable.scrollRectToVisible(stable.getCellRect(stable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane1 = new JScrollPane(stable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		stable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "sid", "Specialization name" }));
		hh.setJTableColumnsWidth(stable, 360, 0, 100);
		jScrollPane1.setViewportView(stable);
		jScrollPane1.setBounds(30, 130, 400, 205);
		mPanel.add(jScrollPane1);
		setVisible(true);
	}

	private int data_delete() {
		String sql = " delete from specializations where sid=";
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) stable.getModel();
		int sIndex = stable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String gid = d1.getValueAt(sIndex, 0).toString();
		if (gid.equals("")) {
			return flag;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			int selections[] = stable.getSelectedRows();
			int lastIndex = selections[0];
			String vsql = sql + "'" + gid + "'";
			try {
				flag = dh.Insupdel(vsql);
				d1.removeRow(sIndex);
				if (stable.getRowCount() > 0) {
					stable.setRowSelectionInterval(lastIndex - 1, lastIndex - 1);
				}
			} catch (Exception e1) {
				if (stable.getRowCount() > 0)
					stable.setRowSelectionInterval(0, 0);
			}
			clearFields();
		}
		return flag;
	}

	private void savebutt() {
		DefaultTableModel d1 = (DefaultTableModel) stable.getModel();
		String sql = "";
		int gid = 0;
		String name = txname.getText();
		if (hh.zempty(name) == true) {
			return;
		}
		try {
			if (rowid != "") {

				sql = "update specializations set name = '" + name + "', type= '" + mytype + "' where sid = " + rowid;
			} else {
				sql = "insert into specializations (name, type) values  ('" + name + "','" + mytype + "')";
			}
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(sid) AS max_id from specializations");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, name, mytype });
					hh.gotolastrow(stable);
					if (stable.getRowCount() > 0) {
						int row = stable.getRowCount() - 1;
						stable.setRowSelectionInterval(row, row);
					}

				} else {
					table_rowrefresh(name);
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

	private void table_rowrefresh(String name) {
		DefaultTableModel d1 = (DefaultTableModel) stable.getModel();
		d1.setValueAt(name, myrow, 1);
	}

	private void data_cancel() {
		clearFields();
		txname.requestFocus(true);
	}

	private void clearFields() {
		txname.setText("");
		myrow = 0;
		rowid = "";
		txname.requestFocus(true);
	}

	private void stable_update(String what) {
		DefaultTableModel m1 = (DefaultTableModel) stable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  sid, name from specializations where type ='" + mytype + "'" + " order by upper( name)";
		} else {
			what = what + "and type ='" + mytype + "' ";
			Sql = "select  sid, name  from specializations where " + what + " order by upper(name)";
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String sid = rs.getString("sid");
				String name = rs.getString("name");
				m1.addRow(new Object[] { sid, name });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		hh.setJTableColumnsWidth(stable, 360, 0, 100);
		stable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				stable.scrollRectToVisible(stable.getCellRect(stable.getRowCount() - 1, 0, true));
			}
		});
		if (stable.getRowCount() > 0) {
			int row = stable.getRowCount() - 1;
			stable.setRowSelectionInterval(row, row);
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Specializations("s");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbname;
	JTextField txname;
	JTable stable;
	JButton btnsave, btncancel, btndelete;
	JPanel mPanel;
	JScrollPane jScrollPane1;
}
