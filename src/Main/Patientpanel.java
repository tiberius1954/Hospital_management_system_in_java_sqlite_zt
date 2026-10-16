package Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import Classes.Calhelper;
import Classes.Grlib;
import Classes.Hhelper;

public class Patientpanel extends JFrame{

	Grlib gr = new Grlib();	
	Hhelper hh = new Hhelper();	
	Calhelper cc = new Calhelper();
	JFrame myframe = this;
	
	Patientpanel(){
		init();	
		hh.iconhere(this);		
	}
	
	private void init() {
	getContentPane().setBackground(new Color(0, 128, 128));
	setSize(1000, 600);
	setLayout(null);
	setLocationRelativeTo(null);
	setTitle("Patient panel");
	addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent windowEvent) {
			dispose();
		}
	});
	
	lbheader = new JLabel("Patient panel");
	lbheader.setForeground(new Color(255, 255, 255));
	lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
	lbheader.setBounds(20, 10, 300, 35);
	add(lbheader);
	
	mPanel = new JPanel(null);
	mPanel.setBounds(15, 60, 955, 490);
	mPanel.setBackground(new Color(178, 216, 216));
	add(mPanel);
	
	btnpatients = gr.sbcs("Patients");
	btnpatients.setBounds(50, 50, 240, 60);
	btnpatients.setBackground(cc.psarga);
	mPanel.add(btnpatients);
	btnpatients.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
		 Patients pa= new Patients();		
	     pa.setVisible(true);
	   //  dispose();
		}
});
	
	btnrooms = gr.sbcs("Rooms");
	btnrooms.setBounds(50, 120, 240, 60);
	btnrooms.setBackground(cc.psarga);
	mPanel.add(btnrooms);
	btnrooms.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
		Rooms pa= new Rooms();		
	     pa.setVisible(true);
	   //  dispose();
		}
});	
	
	btnadmissions = gr.sbcs("Admissions");
	btnadmissions.setBounds(50, 190, 240, 60);
	btnadmissions.setBackground(cc.psarga);
	mPanel.add(btnadmissions);
	btnadmissions.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
			Choosedepartment cd = new Choosedepartment("admissions");
	         cd.setVisible(true);
	   //  dispose();
		}
});
	
	btnpatientroom = gr.sbcs("Patient in room");
	btnpatientroom.setBounds(50, 260, 240, 60);
	btnpatientroom.setBackground(cc.psarga);
	mPanel.add(btnpatientroom);
	btnpatientroom.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
			Where_is_the_patient cd = new Where_is_the_patient();
	         cd.setVisible(true);
	   //  dispose();
		}
});
	
	btnfreeroom = gr.sbcs("Free rooms");
	btnfreeroom.setBounds(50, 330, 240, 60);
	btnfreeroom.setBackground(cc.psarga);
	mPanel.add(btnfreeroom);
	btnfreeroom.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
			Choosedepartment cd = new Choosedepartment("freerooms");
	         cd.setVisible(true);	      
	   //  dispose();
		}
});
	
	setVisible(true);
}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Patientpanel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


JPanel mPanel;
JButton btnpatients, btnpatientview, btnadmissions, btnrooms, btnpatientroom, btnfreeroom ;
JLabel lbheader;
}
