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
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Lcities extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	String rowid = "";
	int myrow = 0;
	JFrame pframe;
	String sfrom = "";

	Lcities(JFrame parent, String wfrom) {
		sfrom = wfrom;
		pframe = parent;
		init();
		hh.iconhere(this);
		ctable_update(ctable, "");
	}

	Lcities() {
		init();	
		ctable_update(ctable, "");
		hh.iconhere(this);
	}

	private void init() {
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
		setTitle("Cities");
		setSize(480, 570);
		setLayout(null);
		setLocationRelativeTo(null);
	//	getContentPane().setBackground(hh.citrom);
		getContentPane().setBackground(new Color(0, 128, 128));

		mPanel = new JPanel(null);
		mPanel.setBounds(10, 50, 440, 450);
//		mPanel.setBorder(hh.ztroundborder(hh.szold));
	//	mPanel.setBackground(hh.citrom);
		mPanel.setBackground(new Color(178, 216, 216));
		add(mPanel);

		lbheader = hh.fflabel("Cities");
		lbheader.setForeground(Color.white);
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		lbheader.setBounds(20, 10, 150, 35);
		add(lbheader);

		lbname = hh.clabel("City name");
		lbname.setBounds(10, 70, 100, 30);
		mPanel.add(lbname);

		txname = hh.cTextField(40);
		txname.setBounds(120, 70, 280, 30);
		mPanel.add(txname);
		txname.addKeyListener(hh.MUpper());

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(120, 125, 90, 35);
		btnsave.setBackground(hh.lpiros);
		mPanel.add(btnsave);
		btnsave.addActionListener(e -> savebutt());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(215, 125, 90, 35);
		btndelete.setBackground(Color.yellow);
		mPanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(310, 125, 90, 35);
		btncancel.setBackground(Color.green);
		mPanel.add(btncancel);
		btncancel.addActionListener(e -> data_cancel());

		ctable = hh.ztable();
		ctable.setTableHeader(new JTableHeader(ctable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		ctable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = ctable.getSelectedRow();
				if (row >= 0) {
					rowid = ctable.getValueAt(row, 0).toString();
					txname.setText(ctable.getValueAt(row, 1).toString());
					myrow = row;
				}
			}
		});

		hh.madeheader(ctable);
		ctable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ctable.scrollRectToVisible(ctable.getCellRect(ctable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane = new JScrollPane(ctable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ctable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "cid", "Name" }));
		hh.setJTableColumnsWidth(ctable, 360, 0, 100);
		jScrollPane.setViewportView(ctable);
		jScrollPane.setBounds(50, 180, 360, 200);
	
		mPanel.add(jScrollPane);
		{
			btnsendto = hh.cbutton("");
			btnsendto.setBounds(140, 395, 200, 30);
			btnsendto.setBackground(hh.narancs);
			if (sfrom == "patients" || sfrom=="employees") {
				btnsendto.setText("Send to back");
				mPanel.add(btnsendto);
			}
		}
		btnsendto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_send();
			}
		});
		setVisible(true);
	}

	private void savebutt() {
		DefaultTableModel d1 = (DefaultTableModel) ctable.getModel();
		String sql = "";
		int gid = 0;
		String name = txname.getText();
		if (hh.zempty(name) == true) {
			return;
		}
		try {
			if (rowid != "") {
				sql = "update  cities set name= '" + name + "' where cid = " + rowid;
			} else {
				sql = "insert into cities (name) values" + " ('" + name + "')";
			}
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(cid) AS max_id from cities");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, name });
					hh.gotolastrow(ctable);
					if (ctable.getRowCount() > 0) {
						int row = ctable.getRowCount() - 1;
						ctable.setRowSelectionInterval(row, row);
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
		DefaultTableModel d1 = (DefaultTableModel) ctable.getModel();
		d1.setValueAt(name, myrow, 1);
	}

	private void clearFields() {
		txname.setText("");
		myrow = 0;
		rowid = "";
	}

	private void data_cancel() {
		clearFields();
		txname.requestFocus(true);
	}

	private int data_delete() {
		String sql = " delete from cities where cid=";
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) ctable.getModel();
		int sIndex = ctable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String gid = d1.getValueAt(sIndex, 0).toString();
		if (gid.equals("")) {
			return flag;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			int selections[] = ctable.getSelectedRows();
			int lastIndex = selections[0];
			String vsql = sql + "'" + gid + "'";
			try {
				flag = dh.Insupdel(vsql);
				d1.removeRow(sIndex);
				if (ctable.getRowCount() > 0) {
					ctable.setRowSelectionInterval(lastIndex - 1, lastIndex - 1);
				}
			} catch (Exception e1) {
				if (ctable.getRowCount() > 0)
					ctable.setRowSelectionInterval(0, 0);
			}
			clearFields();
		}
		return flag;
	}

	private void data_send() {
		DefaultTableModel d1 = (DefaultTableModel) ctable.getModel();
		int row = ctable.getSelectedRow();
		int number = 0;
		String cnum = "";
		if (row > -1) {
			cnum = d1.getValueAt(row, 0).toString();
			if (!hh.zempty(cnum)) {
				number = Integer.parseInt(cnum);
			}
			if (sfrom == "patients") {
				((Patients) pframe).passtocmbcity(number);
			}else if(sfrom == "employees") {
				((Employees) pframe).passtocmbcity(number);
			}

			pframe.setVisible(true);
			dispose();
		}
	}

	private void ctable_update(JTable dtable, String what) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String Sql = "";
		if (what == "") {
			Sql = "select  cid, name from cities order by name";
		} else {
			Sql = "select  cid, name  from cities where " + what + " order by name";
		}
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String cid = rs.getString("cid");
				String name = rs.getString("name");
				m1.addRow(new Object[] { cid, name });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "cid", "Name" };
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(ctable, 360, 0, 100);
//			DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) dtable.getDefaultRenderer(Object.class);
		// renderer.setHorizontalAlignment(SwingConstants.LEFT);

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
					new Lcities();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JTable ctable;
	JLabel lbheader, lbname;
	JPanel mPanel;
	JTextField txname;
	JButton btnsave, btncancel, btndelete, btnsendto;
	JScrollPane jScrollPane;
}
