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

public class Nursepanel extends JFrame {

	Grlib gr = new Grlib();
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	JFrame myframe = this;

	Nursepanel() {
		init();
		hh.iconhere(this);
	}

	private void init() {
		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(655, 350);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Nurse panel");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});

		lbheader = new JLabel("Nurse panel");
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		lbheader.setBounds(20, 10, 300, 35);
		add(lbheader);

		mPanel = new JPanel(null);
		mPanel.setBounds(15, 50, 610, 250);
		mPanel.setBackground(new Color(178, 216, 216));
		add(mPanel);

		btnnurses = gr.sbcs("Nurses");
		btnnurses.setBounds(50, 50, 240, 60);
		btnnurses.setBackground(cc.psarga);
		mPanel.add(btnnurses);
		btnnurses.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Employees emp = new Employees(myframe, "n");
				emp.setVisible(true);
			}
		});

		btnposition = gr.sbcs("Positions");
		btnposition.setBounds(50, 120, 240, 60);
		btnposition.setBackground(cc.psarga);
		mPanel.add(btnposition);
		
		btnposition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Positions ch = new Positions("n");					
				ch.setVisible(true);			
			}
	});		

		btnspecialization = gr.sbcs("specializations");
		btnspecialization.setBounds(310, 50, 240, 60);
		btnspecialization.setBackground(cc.psarga);
		mPanel.add(btnspecialization);
		
		btnspecialization.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Specializations ch = new Specializations("n");					
				ch.setVisible(true);			
			}
	});		

		btnnurseview = gr.sbcs("Nurses view");
		btnnurseview.setBounds(310, 120, 240, 60);
		btnnurseview.setBackground(cc.psarga);
		mPanel.add(btnnurseview);
		setVisible(true);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Nursepanel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JPanel mPanel;
	JButton btnnurses, btnnurseview, btnposition, btnspecialization;
	JLabel lbheader;
}
