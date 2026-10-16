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
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import Classes.Calhelper;
import Classes.Employee;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Department;

public class Rooms extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	private String rowid = "";
	private int myrow = 0;
	String[] types = new String[] { "", "General", "Deluxe", "Private" };
	String[] status = new String[] { "", "Empty", "Empty slots", "Full", "Reserved", "Unavailable" };

	Rooms() {
		init();
		hh.iconhere(this);
		dd.cmbdepartmentcombofill(cmbdepartments);
		rotable_update("");
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
		setTitle("Rooms");
		setSize(1167, 580);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0, 128, 128));
		lbheader = new JLabel("Rooms");
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

		lbname = hh.clabel("Room name");
		lbname.setBounds(10, 20, 110, 25);
		ePanel.add(lbname);

		txroomname = hh.cTextField(250);
		txroomname.setBounds(130, 20, 200, 25);
		ePanel.add(txroomname);
		txroomname.addKeyListener(hh.MUpper());

		lbtype = hh.clabel("Roomtype");
		lbtype.setBounds(10, 60, 110, 25);
		ePanel.add(lbtype);

		cmbroomtypes = gr.grcombo();
		cmbroomtypes.setModel(new DefaultComboBoxModel(types));
		cmbroomtypes.setBounds(130, 60, 150, 27);
		cmbroomtypes.setFont(hh.textf);
		ePanel.add(cmbroomtypes);

		lbcapacity = hh.clabel("Capacity");
		lbcapacity.setBounds(10, 100, 110, 25);
		ePanel.add(lbcapacity);

		txcapacity = hh.cTextField(150);
		txcapacity.setBounds(130, 100, 70, 25);
		txcapacity.setHorizontalAlignment(JTextField.RIGHT);
		ePanel.add(txcapacity);
		txcapacity.addKeyListener(hh.Onlynum());

		lboccupied = hh.clabel("Occupied");
		lboccupied.setBounds(10, 140, 110, 25);
		ePanel.add(lboccupied);

		txoccupied = hh.cTextField(70);
		txoccupied.setBounds(130, 140, 70, 25);
		txoccupied.setHorizontalAlignment(JTextField.RIGHT);
		ePanel.add(txoccupied);
		txoccupied.addKeyListener(hh.Onlynum());

		lbstatus = hh.clabel("Status");
		lbstatus.setBounds(10, 180, 110, 25);
		ePanel.add(lbstatus);

		cmbstatus = gr.grcombo();
		cmbstatus.setModel(new DefaultComboBoxModel(status));
		cmbstatus.setBounds(130, 180, 150, 27);
		cmbstatus.setFont(hh.textf);
		ePanel.add(cmbstatus);

		lbdepartment = hh.clabel("Departments");
		lbdepartment.setBounds(10, 220, 110, 25);
		ePanel.add(lbdepartment);

		cmbdepartments = gr.grcombo();
		cmbdepartments.setBounds(130, 220, 250, 27);
		cmbdepartments.setName("department");
		cmbdepartments.setFont(hh.textf);
		ePanel.add(cmbdepartments);

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(140, 270, 120, 30);
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
		btncancel.setBounds(140, 310, 120, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(140, 350, 120, 30);
		btndelete.setBackground(Color.yellow);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_delete();
			}
		});

		rotable = hh.ztable();
		rotable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		rotable.setTableHeader(new JTableHeader(rotable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(rotable);
		rotable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				rotable.scrollRectToVisible(rotable.getCellRect(rotable.getRowCount() - 1, 0, true));
			}
		});
		rotable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				int row = rotable.getSelectedRow();
				if (row >= 0) {
				rowid = rotable.getValueAt(row, 0).toString();				
					String roomname = rotable.getValueAt(row, 1).toString();	
					txroomname.setText(roomname);
					String capacity =  rotable.getValueAt(row, 2).toString();	
					txcapacity.setText(capacity);
					String occupied =  rotable.getValueAt(row, 3).toString();	
					txoccupied.setText(occupied);
					String roomtype =  rotable.getValueAt(row, 4).toString();	
					cmbroomtypes.setSelectedItem(roomtype);
					String sdepid = rotable.getValueAt(row, 5).toString();
					if (!hh.zempty(sdepid)) {
					int depid = hh.stoi(sdepid);					
					hh.setSelectedValue(cmbdepartments, depid);
					cmbdepartments.updateUI();	
					}
					String status =  rotable.getValueAt(row, 7).toString();	
					cmbstatus.setSelectedItem(status);
       			myrow = row;
				}
			}
		});

		jScrollPane1 = new JScrollPane(rotable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		rotable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][]{ { null, null, null, null, null, null, null, null }}, 
				new String[] { "roomid","Room name", "Capacity", "Occupied", "Roomtype",
						"depid", "Department", "Status" }));		
		jScrollPane1.setViewportView(rotable);
		jScrollPane1.setBounds(10, 25, 690, 340);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);
		
		int[] wcols0 = { 0, 5};
		hh.cellswidth_0(rotable, wcols0);

		int[][] wcols = { { 1, 120 }, { 2, 70 }, { 3, 70 }, { 4, 110 }, { 6, 220 }, { 7, 120 } };
		hh.cellswidth(rotable, wcols);
		
		setVisible(true);
	}

	private void savebuttrun() {
		DefaultTableModel d1 = (DefaultTableModel) rotable.getModel();
		String sql = "";
		String roomname = txroomname.getText();
		String capacity = txcapacity.getText();
		String occupied = txoccupied.getText();
		String roomtype = cmbroomtypes.getSelectedItem().toString();
		String status = cmbstatus.getSelectedItem().toString();
		int depid = (int) ((Department) cmbdepartments.getSelectedItem()).getDepid();
		String depname = (String) ((Department) cmbdepartments.getSelectedItem()).toString();
		
		if (roomvalidation(roomname, capacity, occupied, roomtype, depname)==false) {
			return;
		}	
		
		if (rowid != "") {
			sql = "update rooms set roomname= '" + roomname + "',  capacity='" + capacity 
					+ "', occupied='" + occupied + "', roomtype='" + roomtype + "', depid=" + depid
					+ ",  status = '" +status + "' where roomid = "+ rowid;
		} else {
			sql = "insert into rooms  (roomname, capacity,  occupied, roomtype, depid, status ) " 
		+ "values" + " ('"
					+ roomname + "','" +capacity + "','" + occupied + "', '" + roomtype + "'," + depid 
					+ ", '" + status +"')";
		}
		
		try {
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(roomid) AS max_id from rooms");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, roomname, capacity, occupied, 
							roomtype, depid, depname, status});
					hh.gotolastrow(rotable);
					if (rotable.getRowCount() > 0) {
						int row = rotable.getRowCount() - 1;
						rotable.setRowSelectionInterval(row, row);
					}
				} else {
					table_rowrefresh( roomname, capacity, occupied, roomtype, depid, depname, status);
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
	 private void table_rowrefresh(String roomname, String capacity , String occupied, String roomtype, 
			 int depid, String depname, String status) {
			DefaultTableModel d1 = (DefaultTableModel) rotable.getModel();
			d1.setValueAt(roomname, myrow, 1);
			d1.setValueAt(capacity, myrow, 2);
			d1.setValueAt(occupied, myrow, 3);
			d1.setValueAt(roomtype, myrow, 4);
			d1.setValueAt(depid, myrow, 5);	
			d1.setValueAt(depname, myrow, 6);	
			d1.setValueAt(status, myrow, 7);
		}
	 
	 private Boolean roomvalidation(String roomname, String capacity, String occupied, 
			 String roomtype, String depname) {
		    Boolean ret= true;
		    ArrayList<String> err = new ArrayList<String>();
			if (hh.zempty(roomname)) {
				err.add("Room name is empty");
				ret = false;
			}
			if (hh.zempty(roomtype)) {
				err.add("Room type is empty");
				ret = false;
			}
			if (hh.zempty(depname)) {
				err.add("Department  name is empty");
				ret = false;
			}
			if (hh.zempty(capacity) || capacity.equals("0")) {
				err.add("Capacity is empty");
				ret = false;
			} 
			if (!hh.zempty(occupied)) {
				int intocc = hh.stoi(occupied);
				int intcap = hh.stoi(capacity);
				if( intocc> intcap) {
				  err.add("Occupied greater than capacity ");
				  ret= false;
				}				
			}
		 
			if (err.size() > 0) {
				JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
			}
		 return ret;
		 
	 }
	private void  data_delete() {
		String sql = " delete from rooms where roomid= ";
		int flag =dd.data_delete(rotable, sql);
		if (flag > 0) {
		clearFields();
	}
	}

	private void rotable_update(String swhere) {
		DefaultTableModel m1 = (DefaultTableModel) rotable.getModel();
		m1.setRowCount(0);
		String Sql = "select  r.roomid, r.roomname, r.capacity ,r.occupied, r.roomtype, r.depid,"
				+ " d.depname, r.status  from rooms r " + "  left join departments d on r.depid = d.depid";
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String roomid = rs.getString("roomid");
				String roomname = rs.getString("roomname");
				String capacity = rs.getString("capacity");
				String occupied = rs.getString("occupied");
				String roomtype = rs.getString("roomtype");
				String depid = rs.getString("depid");
				String depname = rs.getString("depname");
				String status = rs.getString("status");
				m1.addRow(new Object[] { roomid, roomname, capacity, occupied, roomtype, depid, depname, status });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "roomid", "Room name", "Capacity", "Occupied", "Roomtype", "depid", "Department", "Status" };
		((DefaultTableModel) rotable.getModel()).setColumnIdentifiers(fej);
		
		
		int[] wcols0 = {0, 5};
		hh.cellswidth_0(rotable, wcols0);

		int[][] wcols = { { 1, 120 }, { 2, 70 }, { 3, 70 }, { 4, 100 }, { 6, 220 }, { 7, 120 } };
		hh.cellswidth(rotable, wcols);	

		rotable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				rotable.scrollRectToVisible(rotable.getCellRect(rotable.getRowCount() - 1, 0, true));
			}
		});
		if (rotable.getRowCount() > 0) {
			int row = rotable.getRowCount() - 1;
			rotable.setRowSelectionInterval(row, row);
		}
	}

	private void clearFields() {
		txroomname.setText("");
		txcapacity.setText("");
		txoccupied.setText("");
		cmbstatus.setSelectedIndex(0);
		cmbroomtypes.setSelectedIndex(0);
		cmbdepartments.setSelectedIndex(0);
		myrow = 0;
		rowid = "";
		 txroomname.requestFocus();
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Rooms();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JTable rotable;
	JLabel lbheader, lbname, lbtype, lbcapacity, lboccupied, lbstatus, lbdepartment;
	JTextField txroomname, txcapacity, txoccupied;
	JComboBox cmbroomtypes, cmbstatus, cmbdepartments;
	JPanel ePanel, bPanel, tPanel;
	JButton btnsave, btncancel, btndelete;
	JScrollPane jScrollPane1;
}
