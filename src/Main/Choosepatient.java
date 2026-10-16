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

public class Choosepatient extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	JFrame myframe = this;
	String mywhich="medicalreg";
	
	Choosepatient(){	
		init();	
		hh.iconhere(this);
	}
	
	Choosepatient(String pwhich){
		mywhich =pwhich;
		init();	
		hh.iconhere(this);
	}
	
	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Choose patient");
		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(520, 260);
		setLayout(null);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		
		lbheader = new JLabel("Choose patient");
		lbheader.setBounds(20, 10, 250, 30);
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		add(lbheader);

		ePanel = new JPanel(null);
		ePanel.setBounds(7, 50, 490, 160);
		ePanel.setBackground(hh.vvvkek);
        add(ePanel);
        
    	lbpatient = hh.clabel("Patient name");
		lbpatient.setBounds(10, 35, 110, 25);
		ePanel.add(lbpatient);

		txpatient = hh.dfTextField(150);
		txpatient.setBounds(130, 30, 240, 30);
		ePanel.add(txpatient);

		btnpatient = gr.sbcs("Patients");
		btnpatient.setForeground(Color.black);
		btnpatient.setBackground(Color.ORANGE);
		btnpatient.setBounds(378, 30, 100, 32);
		btnpatient.setFocusable(false);
		ePanel.add(btnpatient);
		btnpatient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Patients pa = new Patients(myframe, "choosepatient");
				pa.setLayout(null);
				pa.setLocationRelativeTo(null);
				pa.setVisible(true);			
			}
		});
		
		txpatientid = hh.dfTextField(10);
		
		btnnext =gr.sbcs("Next");
		btnnext.setBounds(195, 80, 120, 30);
		btnnext.setForeground(Color.black);
		btnnext.setBackground(Color.ORANGE);
		ePanel.add(btnnext);	
		
		btnnext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {	
				String patientid = txpatientid.getText();
				if (!hh.zempty(patientid)) {
					String patientname = txpatient.getText();		
					if (mywhich=="medicalreg") {
					 //     Medical_record_reg dt = new Medical_record_reg(patientid, patientname, mydepid, String depname);
					}
					dispose();
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
					new Choosepatient();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	JLabel lbheader, lbpatient;
	JTextField txpatient, txpatientid;
	JPanel ePanel;
	JButton btnpatient, btnnext;

}
