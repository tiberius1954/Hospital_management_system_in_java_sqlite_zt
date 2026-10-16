package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Department;

public class Choosedepartment extends JFrame {

	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Department depa;
	String mywhich = "";

	Choosedepartment(String swhich) {
		mywhich= swhich;
		init();
		dd.cmbdepartmentcombofill(cmbdepartments);
		hh.iconhere(this);
	}

	private void init() {

		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Choose Department");
		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(500, 260);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel("Choose department");
		lbheader.setBounds(20, 10, 250, 30);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		ePanel = new JPanel(null);
		ePanel.setBounds(7, 50, 470, 160);
		ePanel.setBackground(hh.vvvkek);
	//	ePanel.setBorder(hh.ztroundborder(Color.DARK_GRAY));
        add(ePanel);
        
		lbdepname = hh.clabel("Department name");
		lbdepname.setBounds(10, 40, 150, 30);
		ePanel.add(lbdepname);

		cmbdepartments = hh.cbcombo();
		cmbdepartments.setBounds(170, 40, 280, 30);
		cmbdepartments.setFont(hh.textf);
		cmbdepartments.setName("department");
		ePanel.add(cmbdepartments);
		
		btnnext =gr.sbcs("Next");
		btnnext.setBounds(255, 100, 120, 30);
		btnnext.setForeground(Color.black);
		btnnext.setBackground(Color.ORANGE);
		ePanel.add(btnnext);	
		setVisible(true);
		
		btnnext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				int depid = (int) ((Department) cmbdepartments.getSelectedItem()).getDepid();			
				if (depid > 0) {
					String depname = (String) ((Department) cmbdepartments.getSelectedItem()).toString();
			   if (mywhich =="week2") {
				   Weekpanel2 dt = new Weekpanel2(depid, depname);
				   dt.setVisible(true);
					dispose();
			   } else if (mywhich == "docapptimes") {
					Docapptimes dt= 	new Docapptimes( depid, depname);
					 dt.setVisible(true);
						dispose();
			   } else if (mywhich== "docapptimecancel"){
					Docapptimecancel dt= 	new Docapptimecancel( depid, depname);
					 dt.setVisible(true);
						dispose();
			   } else if (mywhich == "appointmenttable") {
				   Appointmenttable dt = new Appointmenttable(depid, depname);
				   dt.setVisible(true);
					dispose();
			   } else if (mywhich == "freerooms") {
				   Free_rooms dt = new Free_rooms(depid, depname);
				   dt.setVisible(true);
					dispose();
			   }else if (mywhich == "admissions") {
				   Admissionb dt = new Admissionb(depid, depname);
				   dt.setVisible(true);
					dispose();
			   }			
			}			
			}
		});
	}		

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Choosedepartment("");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JComboBox cmbdepartments;
	JLabel lbheader, lbdepname;
	JPanel ePanel;
	JButton btnnext;
}
