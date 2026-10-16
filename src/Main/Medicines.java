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
import Classes.Calhelper;
import Classes.Employee;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Medicines extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	private String rowid = "";
	private int myrow = 0;	
	String [] types = new String[] { "Capsule", "Liquid", "Injection", "Ointment"};
	
	Medicines(){
		init();
		hh.iconhere(this);
	medtable_update();
	}
	
	private void init(){
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
		setTitle("Medicines");	
		setSize(1150, 580);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0, 128, 128));
		lbheader = new JLabel("Medicines");
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
		
		lbname = hh.clabel("Name");
		lbname.setBounds(10, 60, 110, 25);
		ePanel.add(lbname);
		
		txname = hh.cTextField(250);
		txname.setBounds(130, 60, 250, 25);
		ePanel.add(txname);
		
		lbbrand = hh.clabel("Brand");
		lbbrand.setBounds(10, 110, 110, 25);
		ePanel.add(lbbrand);
		
		txbrand = hh.cTextField(250);
		txbrand.setBounds(130, 110, 250, 25);
		ePanel.add(txbrand);
		
		lbtype = hh.clabel("Type");
		lbtype.setBounds(10, 160, 110, 25);
		ePanel.add(lbtype);		

		cmbtypes = gr.grcombo();
		cmbtypes.setModel(new DefaultComboBoxModel(types));	
		cmbtypes.setBounds(130, 160, 150, 27);
		cmbtypes.setFont(hh.textf);
		ePanel.add(cmbtypes);
		
		lbdosage = hh.clabel("Dosage");
		lbdosage.setBounds(10, 210, 110, 25);
		ePanel.add(lbdosage);
		
		txdosage = hh.cTextField(250);
		txdosage.setBounds(130, 210, 100, 25);	
		txdosage.setHorizontalAlignment(JTextField.RIGHT);
		ePanel.add(txdosage);
		txdosage.addKeyListener(hh.Onlynum());
		
		lbexpirydate = hh.clabel("Expiry date");
		lbexpirydate.setBounds(10, 260, 110, 25);
		ePanel.add(lbexpirydate);	
		
		txexpirydate =  hh.DatetextField();
		txexpirydate.setBounds(130, 260, 105, 30);
		ePanel.add(txexpirydate);			
		
		btnsave = gr.sbcs("Save");
		btnsave.setBounds(160, 310, 120, 30);
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
		btncancel.setBounds(160, 350, 120, 30);
		ePanel.add(btncancel);
		btncancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			clearFields();
			}
		});

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(160, 390, 120, 30);
		btndelete.setBackground(Color.yellow);
		ePanel.add(btndelete);
		btndelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
		     data_delete();
			}
		});

		medtable = hh.ztable();
		medtable.setTableHeader(new JTableHeader(medtable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(medtable);
		medtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				medtable.scrollRectToVisible(medtable.getCellRect(medtable.getRowCount() - 1, 0, true));
			}
		});
		medtable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {

				int row = medtable.getSelectedRow();
				if (row >= 0) {
					rowid = medtable.getValueAt(row, 0).toString();				
					String type = medtable.getValueAt(row, 3).toString();					
					cmbtypes.setSelectedItem(type);
					String name = medtable.getValueAt(row, 1).toString();
					txname.setText(name);
					String brand = medtable.getValueAt(row, 2).toString();
					txbrand.setText(brand);
					String dosage = medtable.getValueAt(row, 4).toString();
					txdosage.setText(dosage);
					String expiry_date = medtable.getValueAt(row, 5).toString();
					txexpirydate.setText(expiry_date);
					myrow = row;
				}
			}
		});

		jScrollPane1 = new JScrollPane(medtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		medtable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "medid", "Name", "Brand", "Type", "Dosage", "Expiry date" }));
		hh.setJTableColumnsWidth(medtable, 690,  0, 30, 30, 15, 12, 13);
		jScrollPane1.setViewportView(medtable);
		jScrollPane1.setBounds(10, 25, 690, 340);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);
		
		int[] cols = { 4};
		hh.cellsright(medtable, cols);		
		setVisible(true);
	}	
	
	 private void  savebuttrun() {
			DefaultTableModel d1 = (DefaultTableModel) medtable.getModel();
				String sql = "";
				String name = txname.getText();
				String brand = txbrand.getText();
				String type = cmbtypes.getSelectedItem().toString();
				String dosage = txdosage.getText();
			    String expiry_date = txexpirydate.getText();
			    if (expiry_date.equals("____-__-__")) {
					expiry_date = "";
				}
			    
				if (hh.zempty(name) || hh.zempty(brand) || hh.zempty(type) || hh.zempty(dosage)) {
					JOptionPane.showMessageDialog(null, "Please fill name, brand,  type, dosage  fields");
					return;
				}
				if (rowid != "") {
					sql = "update medicine set name= '" + name + "',  brand='" + brand + "', type='" + type
							+ "', dosage='" + dosage + "', expiry_date='" + expiry_date + "'"
									+ "  where medid = "+ rowid;
				} else {
					sql = "insert into medicine  (name, brand,  type, dosage, expiry_date) " + "values" + " ('"
							+ name + "','" + brand + "','" + type + "', '" + dosage + "','" + expiry_date + "')";
				}
				
				try {
					int flag = dh.Insupdel(sql);
					if (flag > 0) {
						hh.ztmessage("Success", "Message");
						if (rowid == "") {
							int myid = dd.table_maxid("SELECT MAX(medid) AS max_id from medicine");
							d1.insertRow(d1.getRowCount(), new Object[] { myid, name, brand, type, 
									dosage, expiry_date});
							hh.gotolastrow(medtable);
							if (medtable.getRowCount() > 0) {
								int row = medtable.getRowCount() - 1;
								medtable.setRowSelectionInterval(row, row);
							}
						} else {
							table_rowrefresh( name, brand, type, dosage, expiry_date);
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
	 private void table_rowrefresh(String name, String brand, String type, String dosage, 
			 String expiry_date) {
			DefaultTableModel d1 = (DefaultTableModel) medtable.getModel();
			d1.setValueAt(name, myrow, 1);
			d1.setValueAt(brand, myrow, 2);
			d1.setValueAt(type, myrow, 3);
			d1.setValueAt(dosage, myrow, 4);
			d1.setValueAt(expiry_date, myrow, 5);			
		}
	
	private void medtable_update() {
		DefaultTableModel m1 = (DefaultTableModel) medtable.getModel();
		m1.setRowCount(0);
		String Sql = "select  *  from  medicine ";
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String medid = rs.getString("medid");			
				String name = rs.getString("name");
				String brand= rs.getString("brand");
				String type = rs.getString("type");
				String dosage = rs.getString("dosage");
				String expiry_date = rs.getString("expiry_date");			
				m1.addRow(new Object[] { medid, name, brand, type, dosage, expiry_date });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "medid", "Name", "Brand", "Type", "Dosage", "Expiry date" };
		((DefaultTableModel) medtable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(medtable, 690,  0, 30, 30, 15, 12, 13);
		int[] cols = { 4};
		hh.cellsright(medtable, cols);		

		medtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				medtable.scrollRectToVisible(medtable.getCellRect(medtable.getRowCount() - 1, 0, true));
			}
		});
		if (medtable.getRowCount() > 0) {
			int row = medtable.getRowCount() - 1;
			medtable.setRowSelectionInterval(row, row);
		}		
	}
	
	private void data_delete() {
		String sql = " delete from medicine where medid= ";
		int flag =dd.data_delete(medtable, sql);
		if (flag > 0) {
		clearFields();
	}}
	
	private void clearFields(){
		 txname.setText("");
		  txbrand.setText(""); 
		  txdosage.setText("");
		  txexpirydate.setText("");
		  cmbtypes.setSelectedIndex(0);
			myrow = 0;
			rowid = "";
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Medicines();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	JTable medtable;
	JLabel lbheader, lbname, lbbrand, lbtype, lbdosage, lbexpirydate;
	JTextField txname, txbrand, txdosage, txexpirydate;
	JComboBox cmbtypes;
   JPanel ePanel, tPanel, bPanel;
   JButton btnsave, btncancel, btndelete;
   JScrollPane jScrollPane1;


}
