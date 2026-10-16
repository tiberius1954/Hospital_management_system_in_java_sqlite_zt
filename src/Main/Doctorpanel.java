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

public class Doctorpanel extends JFrame {	
	Grlib gr = new Grlib();	
	Hhelper hh = new Hhelper();	
	Calhelper cc = new Calhelper();
	JFrame myframe = this;
	
	Doctorpanel(){
		init();	
		hh.iconhere(this);		
	}
	
		private void init() {
			getContentPane().setBackground(new Color(0, 128, 128));
			setSize(960, 470);
			setLayout(null);
			setLocationRelativeTo(null);
			setTitle("Doctor panel");
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent windowEvent) {
					dispose();
				}
			});
			lbheader = new JLabel("Doctor panel");
			lbheader.setForeground(new Color(255, 255, 255));
			lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
			lbheader.setBounds(20, 10, 300, 35);
			add(lbheader);
			
			mPanel = new JPanel(null);
			mPanel.setBounds(15, 50, 915, 365);
			mPanel.setBackground(new Color(178, 216, 216));
			add(mPanel);
			
			btndoctors = gr.sbcs("Doctors");
			btndoctors.setBounds(50, 50, 240, 60);
			btndoctors.setBackground(cc.psarga);
			mPanel.add(btndoctors);
			btndoctors.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					Employees emp= new Employees(myframe, "d");		
					emp.setVisible(true);
				}
		});
		
		
		btndoctorapptimes = gr.sbcs("Doctors appointment times");
		btndoctorapptimes.setBounds(50, 120, 240, 60);
		btndoctorapptimes.setBackground(cc.psarga);
		mPanel.add(btndoctorapptimes);
		btndoctorapptimes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Choosedepartment ch = new Choosedepartment("docapptimes");					
				ch.setVisible(true);			
			}
	});		
		
	
		btndocapptimescancel = gr.sbcs("");
		btndocapptimescancel.setBounds(50, 190, 240, 60);
		btndocapptimescancel.setBackground(cc.psarga);
		 btndocapptimescancel.setText("<html>Doctors appointment<br /><center> times cancel"
		 		+ "</center></html>");
		mPanel.add(btndocapptimescancel);
		 btndocapptimescancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Choosedepartment ch = new Choosedepartment("docapptimecancel");					
				ch.setVisible(true);			
			}
	});		
		
		
		btnappointments= gr.sbcs("Appointments");
		btnappointments.setBounds(50, 260, 240, 60);
		btnappointments.setBackground(cc.psarga);		
		mPanel.add(btnappointments);		
		btnappointments.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Choosedepartment ch = new Choosedepartment("week2");					
				ch.setVisible(true);			
			}
	});		
		
		btnapointmenttable= gr.sbcs("Appointment table");
		btnapointmenttable.setBounds(310, 50, 240, 60);
		btnapointmenttable.setBackground(cc.psarga);		
		mPanel.add(btnapointmenttable);
		btnapointmenttable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Choosedepartment ch = new Choosedepartment("appointmenttable");					
				ch.setVisible(true);
			}
	});
		
		btnposition= gr.sbcs("Positions");
		btnposition.setBounds(310, 120, 240, 60);
		btnposition.setBackground(cc.psarga);		
		mPanel.add(btnposition);
		
		btnposition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Positions ch = new Positions("d");					
				ch.setVisible(true);			
			}
	});		
		
		btnspecialization= gr.sbcs("specializations");
		btnspecialization.setBounds(310, 190, 240, 60);
		btnspecialization.setBackground(cc.psarga);		
		mPanel.add(btnspecialization);		
		btnspecialization.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Specializations ch = new Specializations("d");					
				ch.setVisible(true);			
			}
	});				
		setVisible(true);
	}
	
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Doctorpanel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


JPanel mPanel;
JButton btndoctors, btndoctorapptimes, btndocapptimescancel, btnappointments, 
btnapointmenttable, btnposition, btnspecialization;
JLabel lbheader;
}
