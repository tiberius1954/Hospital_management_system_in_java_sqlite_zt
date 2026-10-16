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

public class Choose_patient_department extends JFrame {

	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Department depa;
	JFrame myframe = this;	
	String mywhich ="";
	
	Choose_patient_department(String pwhich){	
		mywhich = pwhich;
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
		setTitle("ChoosePatient and  Department");
		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(630, 330);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel("Choose patient and department");
		lbheader.setBounds(20, 10, 400, 30);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		ePanel = new JPanel(null);
		ePanel.setBounds(20, 50, 575, 220);
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
		
		lbpatient = hh.clabel("Patient name");
		lbpatient.setBounds(10, 90, 150, 25);
		ePanel.add(lbpatient);

		txpatient = hh.dfTextField(150);
		txpatient.setBounds(170, 90, 280, 30);
		ePanel.add(txpatient);

		btnpatient = gr.sbcs("Patients");
		btnpatient.setForeground(Color.black);
		btnpatient.setBackground(Color.ORANGE);
		btnpatient.setBounds(460, 90, 100, 32);
		btnpatient.setFocusable(false);
		ePanel.add(btnpatient);
		btnpatient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Patients pa = new Patients(myframe, "choose_patient_department");
				pa.setLayout(null);
				pa.setLocationRelativeTo(null);
				pa.setVisible(true);
			}
		});
		
		txpatientid = hh.dfTextField(10);		
		
		btnnext =gr.sbcs("Next");
		btnnext.setBounds(255, 155, 120, 30);
		btnnext.setForeground(Color.black);
		btnnext.setBackground(Color.ORANGE);
		ePanel.add(btnnext);	
		
		btnnext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				int depid = (int) ((Department) cmbdepartments.getSelectedItem()).getDepid();	
				String patientname = txpatient.getText();
				String pid = txpatientid.getText();
				if (depid > 0 && !hh.zempty(pid)) {
				String depname = (String) ((Department) cmbdepartments.getSelectedItem()).toString();				
				if (mywhich=="patienttest") {
				     Patienttests dt = new Patienttests(depid, depname, pid, patientname );
				     dispose();
				} else if (mywhich=="prescription") {
			          Patient_prescription dt = new Patient_prescription(depid, depname, pid, patientname );
			          dispose(); 
				}	 else if (mywhich=="medicalreg") {
			          Medical_record_reg dt = new Medical_record_reg (pid, patientname, depid, depname );
			          dispose(); 
				  }	else if (mywhich=="visitreg") {
    			          Visit_record_reg dt = new Visit_record_reg (pid, patientname, depid, depname );
			          dispose(); 			
				}
			}
		}	
		});
	
		setVisible(true);	
	}
	
	public void passtopatient(String patientid, String patientname) {
		 txpatient.setText(patientname);
		 txpatientid.setText(patientid);		 
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Choose_patient_department("");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	JPanel ePanel;
	JComboBox cmbdepartments;
JButton btnnext, btnpatient;
JLabel lbdepname, lbheader, lbpatient;
JTextField txpatientid, txpatient;
}
