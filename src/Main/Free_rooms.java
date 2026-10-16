package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Calhelper;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Free_rooms extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();	
	String[] types = new String[] { "", "General", "Deluxe", "Private" };
	String[] status = new String[] { "", "Empty", "Empty slots", "Full", "Reserved", "Unavailable" };
	int mydepid = 0;
	String mydepname ="";
	
	Free_rooms(){
		init();
		hh.iconhere(this);		
	}
	
	Free_rooms(int pdepid, String pdepname){
		mydepid = pdepid;
		mydepname = pdepname;
		init();
		hh.iconhere(this);	
	    frtable_update(" where depid = '" +mydepid +"'  and capacity > occupied " );
		
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
		
		setTitle("Free rooms");
		setSize(740, 580);
		setLayout(null);
		setLocationRelativeTo(null);
		getContentPane().setBackground(new Color(0, 128, 128));
		lbheader = new JLabel("Free rooms " + mydepname);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 20));
		lbheader.setBounds(20, 10, 800, 35);
		add(lbheader);
		mPanel = new JPanel(null);
		mPanel.setBounds(10, 50, 700, 480);
		mPanel.setBackground(hh.vvvkek);
		add(mPanel);
		
		tPanel = new JPanel(null);
		tPanel.setBounds(10, 10, 680, 460);
		tPanel.setBackground(hh.vvvkek);
		tPanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
		mPanel.add(tPanel);
		
		searchPanel = new JPanel(null);
		searchPanel.setBounds(120, 30, 400, 45);
		searchPanel.setBorder(hh.linesz);
		searchPanel.setBackground(hh.blue2);
		tPanel.add(searchPanel);

		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(10, 10, 70, 25);
		searchPanel.add(lbsearch);

		btnGroup = new ButtonGroup();
		rfree = new JRadioButton("Free   ");
		rfree.setHorizontalTextPosition(SwingConstants.LEFT);
		rfree.setActionCommand("free");
		rfree.setBounds(90, 10, 80, 25);
		rfree.setFont(new Font("Tahoma", Font.BOLD, 16));
		rfree.setBackground(Color.ORANGE);
		btnGroup.add(rfree);
		rnfree = new JRadioButton("Non free");
		rnfree.setHorizontalTextPosition(SwingConstants.LEFT);
		rnfree.setActionCommand("nonfree");
		rnfree.setBounds(175, 10, 100, 25);
		rnfree.setFont(new Font("Tahoma", Font.BOLD, 16));
		rnfree.setBackground(Color.ORANGE);
		btnGroup.add(rnfree);
	
		searchPanel.add(rfree);
		searchPanel.add(rnfree);	

	
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setBounds(285, 10, 90, 27);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		searchPanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    sqlgyart();
			}
		});
		rfree.setSelected(true);
		
		
		frtable = hh.ztable();
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) frtable.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		frtable.setTableHeader(new JTableHeader(frtable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});
		hh.madeheader(frtable);
		frtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				frtable.scrollRectToVisible(frtable.getCellRect(frtable.getRowCount() - 1, 0, true));
			}
		});
		
		jScrollPane1 = new JScrollPane(frtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		frtable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][]{ { null, null, null, null, null, null}}, 
				new String[] { "roomid","Room name", "Capacity", "Occupied", "Roomtype","Status" }));		
		jScrollPane1.setViewportView(frtable);
		jScrollPane1.setBounds(45, 95, 600, 300);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);
		
		int[] wcols0 = { 0};
		hh.cellswidth_0(frtable, wcols0);

		int[][] wcols = { { 1, 130 }, { 2, 70 }, { 3, 70 }, { 4, 140 }, { 5, 130 } };
		hh.cellswidth(frtable, wcols);
		setVisible(true);
	}
	
	
	private void frtable_update(String swhere) {
		DefaultTableModel m1 = (DefaultTableModel) frtable.getModel();
		m1.setRowCount(0);
		String Sql = "select  roomid, roomname, capacity , occupied, roomtype,"
				+ " status  from rooms "+ swhere;
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String roomid = rs.getString("roomid");
				String roomname = rs.getString("roomname");
				String capacity = rs.getString("capacity");
				String occupied = rs.getString("occupied");
				String roomtype = rs.getString("roomtype");				
				String status = rs.getString("status");
				m1.addRow(new Object[] { roomid, roomname, capacity, occupied, roomtype,status });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "roomid", "Room name", "Capacity", "Occupied", "Roomtype", "Status" };
		((DefaultTableModel) frtable.getModel()).setColumnIdentifiers(fej);		

		int[][] wcols = { { 1, 130 }, { 2, 70 }, { 3, 70 }, { 4, 140 } , { 5, 130 }};
		hh.cellswidth(frtable, wcols);
	
		int[] wcols0 = {0};
		hh.cellswidth_0(frtable, wcols0);

		frtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				frtable.scrollRectToVisible(frtable.getCellRect(frtable.getRowCount() - 1, 0, true));
			}
		});
		if (frtable.getRowCount() > 0) {
			int row = frtable.getRowCount() - 1;
			frtable.setRowSelectionInterval(row, row);
		}
	}
	private void sqlgyart() {
		int jelem = 0;
		if (rfree.isSelected()) {
			jelem = 1;
		} else if (rnfree.isSelected()) {
			jelem = 2;		
		} else {
			String qual = "Did not choose !";
			JOptionPane.showMessageDialog(null, qual);	
			return;
		}
		   String sql = "";
		if (jelem == 1) {
			sql = " where depid = '" +mydepid +"'  and capacity > occupied ";
		} else if (jelem == 2) {
			sql = "  where depid = '" +mydepid +"'  and capacity == occupied ";	
		}
		 frtable_update(sql);
	}	
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Free_rooms();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	JComboBox cmbdepartments;
	JTable frtable;
	JLabel lbheader,  lbsearch;
	JPanel mPanel, tPanel,  searchPanel;
	JButton btnsearch, btnclear; 
	JRadioButton rfree, rnfree;
	ButtonGroup btnGroup = new ButtonGroup();
	JTextField txsearch;
	JScrollPane  jScrollPane1;

}
