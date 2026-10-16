package Main;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.*;
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
import Databaseop.Databaseop.Position;
import Databaseop.Databaseop.Tests;

public class Patienttests extends JFrame {
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

	Patienttests() {
		init();
		hh.iconhere(this);
		ttable_update("");
		dd.cmbtestnamescombofill(cmbtestnames);
	}

	Patienttests(int pdepid, String pdepname, String ppatientid, String ppatientname) {
		mypid = ppatientid;
		mypatientname = ppatientname;
		mydepid = pdepid;
		mydepname = pdepname;
		init();
		hh.iconhere(this);
	   ttable_update(" where p.pid='" + mypid + "' and depid=" + mydepid);
		dd.cmbtestnamescombofill(cmbtestnames);
		dd.cmbmeployee_departmentcombofill(cmbdoctors, "d", mydepid);
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
		setTitle("Patient tests");
		setSize(1200, 620);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0, 128, 128));
		lbheader = new JLabel("Patient test - " + mypatientname + " - " + mydepname);
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

		lbname = hh.clabel("Test Name");
		lbname.setBounds(10, 100, 90, 25);
		ePanel.add(lbname);

		cmbtestnames = gr.grcombo();
		cmbtestnames.setFocusable(true);
		cmbtestnames.setName("testnames");
		cmbtestnames.setBounds(110, 100, 270, 30);
		ePanel.add(cmbtestnames);
		cmbtestnames.addItemListener(e -> {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				System.out.println("Selected (ItemListener): " + e.getItem());
				JComboBox<String> source = (JComboBox<String>) e.getSource();
				// String selectedItem = (String) source.getSelectedItem();
			}
		});

		lbresult = hh.clabel("Test result");
		lbresult.setBounds(10, 140, 90, 25);
		ePanel.add(lbresult);

		txaresultw = hh.cTextarea();
		resultpane = new JScrollPane(txaresultw, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		resultpane.setBounds(110, 140, 350, 120);
		resultpane.setViewportView(txaresultw);
		resultpane.setBorder(hh.borderf);
		ePanel.add(resultpane);

		lbcomment = hh.clabel("Comments");
		lbcomment.setBounds(10, 275, 90, 25);
		ePanel.add(lbcomment);

		txacommentw = hh.cTextarea();
		commentpane = new JScrollPane(txacommentw, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		commentpane.setBounds(110, 275, 350, 150);
		commentpane.setViewportView(txacommentw);
		commentpane.setBorder(hh.borderf);
		ePanel.add(commentpane);

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(100, 445, 100, 30);
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
		btncancel.setBounds(205, 445, 100, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				clearFields();
			}
		});

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(310, 445, 100, 30);
		btndelete.setBackground(Color.yellow);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				data_delete();
			}
		});

		ttable = hh.ztable();
		ttable.setTableHeader(new JTableHeader(ttable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(ttable);
		ttable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		ttable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ttable.scrollRectToVisible(ttable.getCellRect(ttable.getRowCount() - 1, 0, true));
			}
		});
		ttable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				 if (evt.getClickCount() == 2) {
				int row = ttable.getSelectedRow();
				if (row >= 0) {
					rowid = ttable.getValueAt(row, 0).toString();
					int number = 0;
					String cnum = ttable.getValueAt(row, 4).toString();
					String cnump = ttable.getValueAt(row, 6).toString();
					if (!hh.zempty(cnum)) {
						hh.setSelectedValue_st2(cmbdoctors, cnum, cnump);
						cmbdoctors.updateUI();
					}
					cnum = ttable.getValueAt(row, 2).toString();
					if (!hh.zempty(cnum)) {
						number = Integer.parseInt(cnum);
						hh.setSelectedValue(cmbtestnames, number);
						cmbtestnames.updateUI();
					}
					txdate.setText(ttable.getValueAt(row, 1).toString());
					String result = txaresult.getText();
					txaresultw.setText(result);
					String comment = txacomment.getText();
					txacommentw.setText(comment);
					myrow = row;
				}
				 }
			}
		});
	

		jScrollPane1 = new JScrollPane(ttable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ttable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null } },
				new String[] { "peid", "Date", "tid", "Test name", "docid", "Doctor Name", "posid", "Position" }));

		jScrollPane1.setViewportView(ttable);
		jScrollPane1.setBounds(10, 25, 650, 250);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);		
		
		int[] wcols0 = {0, 2,4, 6};
		hh.cellswidth_0(ttable, wcols0);

		int[][] wcols = { { 1, 90 }, { 3, 200 }, { 5, 200 }, { 7, 156 } };
		hh.cellswidth(ttable, wcols);

		ttable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				DefaultTableModel model = (DefaultTableModel) ttable.getModel();
				try {
					int row = ttable.getSelectedRow();
					int sor = model.getRowCount();
					if (row > -1 && sor > 0) {					
						String mpeid = model.getValueAt(row, 0).toString();
						patdetails(mpeid);
					}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		lbresult1 = hh.clabel("Test result");
		lbresult1.setBounds(130, 290, 90, 25);
		tPanel.add(lbresult1);

		txaresult = hh.cTextarea();
		txaresult.setEditable(false);
		tresultpane = new JScrollPane(txaresult, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tresultpane.setBounds(17, 320, 315, 150);
		tresultpane.setViewportView(txaresult);
		tresultpane.setBorder(hh.borderf);
		tPanel.add(tresultpane);

		lbcomment1 = hh.clabel("Comments");
		lbcomment1.setBounds(450, 290, 90, 25);
		tPanel.add(lbcomment1);

		txacomment = hh.cTextarea();
		txacomment.setEditable(false);
		tcommentpane = new JScrollPane(txacomment, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		tcommentpane.setBounds(340, 320, 315, 150);
		tcommentpane.setViewportView(txacomment);
		tcommentpane.setBorder(hh.borderf);
		tPanel.add(tcommentpane);

		setVisible(true);
	}

	private void savebuttrun() {
		DefaultTableModel d1 = (DefaultTableModel) ttable.getModel();
		String sql = "";
		int tid = 0;
		String tname = "";
		String docid = "";
		String dname = "";
		String posid = "";
		String position = "";
		String date = txdate.getText();
		if (date.equals("____-__-__")) {
			date = "";
		}

		if (cmbtestnames.getSelectedItem() != null) {
			Tests test = (Tests) cmbtestnames.getSelectedItem();
			tid = test.getTid();
			tname = test.getTname();
		}
		if (cmbdoctors.getSelectedItem() != null) {
			Employee emp = (Employee) cmbdoctors.getSelectedItem();
			docid = emp.getEmpid();
			dname = emp.getName();
			posid = emp.getPosid();
			position = emp.getPosition();
		}
		String result = txaresultw.getText();
		String comment = txacommentw.getText();

		if (hh.zempty(tname) || hh.zempty(dname) || hh.zempty(date) || hh.zempty(result)) {
			JOptionPane.showMessageDialog(null, "Please fill test name, doctor name,  date, result  fields");
			return;
		}
		if (rowid != "") {
			sql = "update patienttests set tid=" + tid + ",  testdate='" + date + "', result='" + result + "', pid='"
					+ mypid + "', docid='" + docid + "', posid='" + posid + "', comment='" + comment + "'," + " depid="
					+ mydepid + "  where peid = '" + rowid +"'";
		} else {
			sql = "insert into patienttests  (tid, testdate,  result, pid, docid, posid, comment, depid "
					+ ") values  (" + tid + ",'" + date + "','" + result + "', '" + mypid + "','" + docid + "','"
					+ posid + "', '" + comment + "'," + mydepid + ")";
		}

		try {
			int flag = dh.Insupdel(sql);
			if (flag > 0) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(peid) AS max_id from patienttests");
					d1.insertRow(d1.getRowCount(),
							new Object[] { myid, date, tid, tname, docid, dname, posid, position });
					rowid = hh.itos(myid);
					hh.gotolastrow(ttable);
					if (ttable.getRowCount() > 0) {
						int row = ttable.getRowCount() - 1;
						ttable.setRowSelectionInterval(row, row);
					}
				} else {
					table_rowrefresh(date, tid, tname, docid, dname, posid, position);
				}
				patdetails(rowid);
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

	private void table_rowrefresh(String date, int tid, String tname, String docid, String dname, String posid,
			String position) {
		DefaultTableModel d1 = (DefaultTableModel) ttable.getModel();
		d1.setValueAt(date, myrow, 1);
		d1.setValueAt(tid, myrow, 2);
		d1.setValueAt(tname, myrow, 3);
		d1.setValueAt(docid, myrow, 4);
		d1.setValueAt(dname, myrow, 5);
		d1.setValueAt(posid, myrow, 6);
		d1.setValueAt(position, myrow, 7);
	}

	private void data_delete() {
		String sql = " delete from patienttests where peid= ";
		int flag = dd.data_delete(ttable, sql);
		if (flag > 0) {
			clearFields();
		}
	}

	private void clearFields() {
		txaresultw.setText("");
		txacommentw.setText("");
		txdate.setText("");
		myrow = 0;
		rowid = "";	
		cmbtestnames.setSelectedIndex(0);	
		Employee A = new Employee("", "","","");
		cmbdoctors.getModel().setSelectedItem(A);	
		//cmbdoctors.setSelectedIndex(0);
	}

	private void ttable_update(String where) {
		DefaultTableModel m1 = (DefaultTableModel) ttable.getModel();
		m1.setRowCount(0);
		String Sql = "select  p.peid, p.testdate, p.tid, t.tname,  p.docid, e.name, p.posid,"
				+ " ps.name as position 	 from  patienttests p  " 
				+ "  left join employees e  on p.docid = e.empid "
				+ " left join positions ps on p.posid = ps.posid "
				+ " left join test t on p.tid = t.tid "
				+ where;
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String peid = rs.getString("peid");
				String tid = rs.getString("tid");
				String tname = rs.getString("tname");
				String testdate = rs.getString("testdate");
				String docid = rs.getString("docid");
				String name = rs.getString("name");
				String posid = rs.getString("posid");
				String position = rs.getString("position");
				m1.addRow(new Object[] { peid, testdate, tid, tname, docid, name, posid, position });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "peid", "Date", "tid", "Test name", "docid", "Doctor Name", "posid", "Position" };
		((DefaultTableModel) ttable.getModel()).setColumnIdentifiers(fej);
		
		int[] wcols0 = {0, 2,4, 6};
		hh.cellswidth_0(ttable, wcols0);

		int[][] wcols = { { 1, 90 }, { 3, 200 }, { 5, 200 }, { 7, 156 } };
		hh.cellswidth(ttable, wcols);

		ttable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ttable.scrollRectToVisible(ttable.getCellRect(ttable.getRowCount() - 1, 0, true));
			}
		});
		if (ttable.getRowCount() > 0) {
			int row = ttable.getRowCount() - 1;
			ttable.setRowSelectionInterval(row, row);
		}
	}

	private void patdetails(String ppeid) {
		String sql = "select result, comment  from patienttests  where peid='" + ppeid + "'";
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String result = rs.getString("result");
				txaresult.setText(result);
				String comment = rs.getString("comment");
				txacomment.setText(comment);
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
					new Patienttests();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JTable ttable;
	JLabel lbheader, lbdoctor, lbname, lbdate, lbresult, lbcomment, lbcomment1, lbresult1;
	JPanel ePanel, tPanel, bPanel;
	JComboBox cmbdoctors, cmbtestnames;
	JFormattedTextField txdate;
	JTextArea txaresultw, txacommentw, txaresult, txacomment;
	JScrollPane resultpane, commentpane, jScrollPane1, tresultpane, tcommentpane;
	JButton btnsave, btncancel, btndelete;
}
