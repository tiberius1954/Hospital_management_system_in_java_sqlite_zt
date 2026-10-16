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

public class Staffpanel extends JFrame{
	
	Grlib gr = new Grlib();	
	Hhelper hh = new Hhelper();	
	Calhelper cc = new Calhelper();
	JFrame myframe = this;
	
	Staffpanel(){
		init();	
		hh.iconhere(this);		
	}
	
	private void init() {
		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(865, 350);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Staff panel");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		
		lbheader = new JLabel("Staff panel");
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		lbheader.setBounds(20, 10, 300, 35);
		add(lbheader);
	
	mPanel = new JPanel(null);
	mPanel.setBounds(15, 50, 820, 250);
	mPanel.setBackground(new Color(178, 216, 216));
	add(mPanel);
	
	btnstaff = gr.sbcs("Staff");
	btnstaff.setBounds(40, 50, 240, 60);
	btnstaff.setBackground(cc.psarga);
	mPanel.add(btnstaff);
	btnstaff.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
			Employees emp= new Employees(myframe, "s");		
			emp.setVisible(true);
		}
});
	
	btnposition= gr.sbcs("Positions");
	btnposition.setBounds(540, 50, 240, 60);
	btnposition.setBackground(cc.psarga);		
	mPanel.add(btnposition);
	btnposition.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
			Positions ch = new Positions("s");					
			ch.setVisible(true);			
		}
});		
	
	btnspecialization= gr.sbcs("specializations");
	btnspecialization.setBounds(290, 50, 240, 60);
	btnspecialization.setBackground(cc.psarga);		
	mPanel.add(btnspecialization);
	btnspecialization.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
			Specializations ch = new Specializations("s");					
			ch.setVisible(true);			
		}
});		


btnstaffview = gr.sbcs("Staff view");
btnstaffview.setBounds(50, 260, 240, 60);
btnstaffview.setBackground(cc.psarga);
mPanel.add(btnstaffview);
setVisible(true);
	
}
	
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Staffpanel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


JPanel mPanel;
JButton btnstaff, btnposition, btnstaffview, btnspecialization;
JLabel lbheader;
}
