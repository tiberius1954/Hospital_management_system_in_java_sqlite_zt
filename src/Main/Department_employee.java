package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
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
import Classes.Employee;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Cities;
import Databaseop.Databaseop.Department;
import Databaseop.Databaseop.Position;

public class Department_employee extends JFrame {

	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	String rowid = "";
	int myrow = 0;
	int myid = 0;
	Department depa;
	int mydepid;
	String mydepname;
	String myemptype;

	Department_employee(int depid, String depname, String emptype) {
		mydepid = depid;
		mydepname = depname;
		myemptype = emptype;
		init();	
		dd.cmbemployee_typecombofill(cmbemployees, myemptype); 
		dd.cmbpositioncombofill(cmbpositions, myemptype);
		String swhere =" e.emptype='" +myemptype +"' and de.depid ='" +mydepid +"'";
		depetable_update(swhere);
		hh.iconhere(this);
	}
	
	Department_employee() {
		init();	
		dd.cmbemployee_typecombofill(cmbemployees, myemptype); 
		String swhere =" where  e.emptype='" +myemptype +"' and de.depid ='" +mydepid +"'";
		depetable_update("");
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
		setSize(600, 515);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel("Departments - Employees");
		lbheader.setBounds(10, 10, 330, 30);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		mPanel = new JPanel(null);
		mPanel.setBounds(10, 50, 560, 410);
		mPanel.setBackground(hh.vvvkek);
		add(mPanel);	

		lbempname = hh.clabel("Employee name");
		lbempname.setBounds(10, 30, 150, 30);
		mPanel.add(lbempname);

		cmbemployees = hh.cbcombo();
		cmbemployees.setBounds(170, 30, 280, 30);
		cmbemployees.setName("employee");
		cmbemployees.setFont(hh.textf);
		mPanel.add(cmbemployees);
//		cmbemployees.addActionListener(listener);

		lbposition = hh.clabel("Employee position");
		lbposition.setBounds(10, 80, 150, 30);
		mPanel.add(lbposition);

		cmbpositions = hh.cbcombo();
		cmbpositions.setBounds(170, 80, 280, 30);
		cmbpositions.setName("position");
		cmbpositions.setFont(hh.textf);
		mPanel.add(cmbpositions);

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(170, 130, 90, 35);
		btnsave.setBackground(hh.lpiros);
		mPanel.add(btnsave);
		 btnsave.addActionListener(e -> savebutt());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(265, 130, 90, 35);
		btncancel.setBackground(Color.green);
		mPanel.add(btncancel);
		btncancel.addActionListener(e -> data_cancel());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(360, 130, 90, 35);
		btndelete.setBackground(Color.yellow);
		mPanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());

		depetable = hh.ztable();
		depetable.setTableHeader(new JTableHeader(depetable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		depetable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = depetable.getSelectedRow();
				if (row >= 0) {
					rowid = depetable.getValueAt(row, 0).toString();
					myrow = row;			
					String empid = depetable.getValueAt(row, 4).toString();					
					hh.setSelectedValue_st(cmbemployees, empid);			
					String sposid = depetable.getValueAt(row, 5).toString();
					int posid = hh.stoi(sposid);
					hh.setSelectedValue(cmbpositions, posid);					
				}
			}
		});	
		hh.madeheader(depetable);
		depetable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				depetable.scrollRectToVisible(depetable.getCellRect(depetable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane1 = new JScrollPane(depetable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		depetable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "demid", "depid", "Department name", "Employee name", "empid", 
					"posid", "Position"}));
		hh.setJTableColumnsWidth(depetable, 530, 0, 0, 33, 33, 0,0, 33);
		jScrollPane1.setViewportView(depetable);
		jScrollPane1.setBounds(10, 195, 540, 200);
		mPanel.add(jScrollPane1);
		setVisible(true);
	}

	private void savebutt() {
		DefaultTableModel d1 = (DefaultTableModel) depetable.getModel();
		String sql = "";		
		 String empid = (String) ((Employee) cmbemployees.getSelectedItem()).getEmpid();
		 String name = (String) ((Employee) cmbemployees.getSelectedItem()).getName();
          Position pos =  (Position) cmbpositions.getSelectedItem();
          int posid = pos.getPosid();
          String pname = pos.getName();
          
		if (hh.zempty(name) == true || hh.zempty(pname)) {
			JOptionPane.showMessageDialog(null, "Empty field is found !", "Error message", 1);
			return;
		}	
		if (hh.zempty(rowid)){
		 if (  recordexist(empid, posid) == true) {
			 JOptionPane.showMessageDialog(null, "This record already exist !", "Error message", 1);
				return;
		 }
		 } else {
             // update
			 if (update_valid(empid, posid)== true) {    
			     JOptionPane.showMessageDialog(null, "This record already exist !", "Error message", 1);
				return;
	           }
		 }
		try {
			if (rowid != "") {
				sql = "update department_employee set depid = '" +mydepid + "', empid= '" + empid 
						+ "', posid= " + posid + " where demid = "+ rowid;
			} else {
				sql = "insert into department_employee (depid, empid, posid) values  ('" 
			+ mydepid + "','" + empid + "'," + posid +")";
			}
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(demid) AS max_id from department_employee");
					d1.insertRow(d1.getRowCount(), new Object[] { myid, mydepid, mydepname, name, empid,
							posid, pname });					
					
					hh.gotolastrow(depetable);
					if (depetable.getRowCount() > 0) {
						int row = depetable.getRowCount() - 1;
						depetable.setRowSelectionInterval(row, row);
					}

				} else {
					table_rowrefresh(mydepid, mydepname, name, empid, posid, pname);
					
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

	private void table_rowrefresh(int depid, String depname, String name, String empid,
		int posid, String pname) {
		DefaultTableModel d1 = (DefaultTableModel) depetable.getModel();
		d1.setValueAt(depid, myrow, 1);
		d1.setValueAt(depname, myrow, 2);
		d1.setValueAt(name, myrow, 3);
		d1.setValueAt(empid, myrow, 4);
		d1.setValueAt(posid, myrow, 5);
		d1.setValueAt(pname, myrow, 6);
	}

	private void clearFields() {
		cmbemployees.setSelectedIndex(0);	
		int size = cmbpositions.getItemCount();
		if (size > 0) {
		cmbpositions.setSelectedIndex(0);
		}
	}

	private void depetable_update(String what) {
		DefaultTableModel m1 = (DefaultTableModel) depetable.getModel();
		m1.setRowCount(0);
		String sql;
		if (hh.zempty(what)) {
			sql = "select de.demid, de.depid, d.depname, de.empid, e.name,e.emptype, de.posid, p.name as pname "
					+ " from department_employee de "
					+ " left join departments d  on de.depid = d.depid" 
					+ " left join employees e on de.empid = e.empid "
					+ " left join positions p on de.posid = p.posid" ;

		} else {
			sql = "select de.demid, de.depid, d.depname, de.empid, e.name, e.emptype, de.posid, p.name as pname "
					+ " from department_employee de "
					+ " left join departments d  on de.depid = d.depid "
					+ " left join employees e on de.empid = e.empid "
					+ " left join positions p on de.posid = p.posid " 
					+ "where " + what;
		}

		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String demid = rs.getString("demid");
				String depid = rs.getString("depid");
				String depname = rs.getString("depname");
				String empid = rs.getString("empid");
				String name = rs.getString("name");
				String posid =  rs.getString("posid");
				String pname = rs.getString("pname");
				m1.addRow(new Object[] { demid, depid, depname, name, empid,
						posid, pname });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
	}

	private void data_cancel() {
		clearFields();	
	}

	private int data_delete() {
		String sql = " delete from department_employee where demid=";
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) depetable.getModel();
		int sIndex = depetable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String gid = d1.getValueAt(sIndex, 0).toString();
		if (gid.equals("")) {
			return flag;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			int selections[] = depetable.getSelectedRows();
			int lastIndex = selections[0];
			String vsql = sql + "'" + gid + "'";
			try {
				flag = dh.Insupdel(vsql);
				d1.removeRow(sIndex);
				if (depetable.getRowCount() > 0) {
					depetable.setRowSelectionInterval(lastIndex - 1, lastIndex - 1);
				}
			} catch (Exception e1) {
				if (depetable.getRowCount() > 0)
					depetable.setRowSelectionInterval(0, 0);
			}
			clearFields();
		}
		return flag;
	}


	private Boolean recordexist(String empid, int posid) {
		Boolean ret = false;	
			String sql =  "select count(*) from department_employee where empid ='" + empid 
				+ "' and posid= '"+ posid+"' and depid = '" + mydepid +"'";
		  if(dd.recordfound(sql)==true) {
			  ret = true;
		  }	
		return ret;		
	}
	private Boolean update_valid(String empid, int posid) {
		Boolean ret = false;
		String sql = " select demid from department_employee where depid = '" + mydepid
				+"' and empid ='"+empid +"' and posid = '" +posid +"'";
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String demid= rs.getString("demid");
				if (! demid.equals(rowid)) {
					ret= true;
				}
			}
			dh.CloseConnection();
		} catch (SQLException SQLe) {
			System.out.println("Sql error " + SQLe.toString());
		}
		
		return ret;
	}


	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Department_employee();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JTable depetable;
	JLabel lbheader, lbdepname, lbempname, lbposition;
	JComboBox cmbdepartments, cmbemployees, cmbpositions;
	JButton btnsave, btncancel, btndelete;
	JPanel mPanel;
	JScrollPane jScrollPane1;
}
