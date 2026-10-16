package Main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Classes.Grlib;
import Classes.Hhelper;

public class Mainframe extends JFrame{
	Grlib gr = new Grlib();
	Hhelper hh = new Hhelper();
	Thread th;
	ImageIcon images;
	JFrame frame;
	JLabel lbl;
	int i = 0;
	int j;
	 private JLabel[] labels;
	 Panel panel;
	 JButton btnenter, btnexit;
	 keeptoo.KGradientPanel kpanel;

	public static void main(String[] args){
		Mainframe sa = new Mainframe();	
	}

	public Mainframe(){	
		getContentPane().setBackground(hh.skek);
	    setLayout(null);	
	    setSize(1000, 600);
	    setLocationRelativeTo(null);
	    setUndecorated(true);	    
		th = new Thread();
		labels = new JLabel[9];		
		panel = new Panel(null);
		panel.setBounds(0,0, 980,300);
    	add(panel);				

		add(panel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
			SwingAnimator();
		btnenter = gr.sbcs("Enter");
		btnenter.setBounds(415, 300, 230, 60);
	 	btnenter.setBackground(new Color(255, 97, 0));
		btnenter.setForeground(new Color(255, 255, 255));
		btnenter.setFont(new java.awt.Font("Lucida Handwriting", 1, 48));
		add(btnenter);
		
		btnenter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {	
				new MainFrame_mf();
			 dispose();
			}
		});
		
		btnexit = gr.sbcs("Exit");
		btnexit.setBounds(415, 380, 230, 60);
	 	btnexit.setBackground(new Color(255, 97, 0));
		btnexit.setForeground(new Color(255, 255, 255));
		btnexit.setFont(new java.awt.Font("Lucida Handwriting", 1, 48));
		add(btnexit);	
		
		btnexit.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent evt) {
		   dispose();
		}
	});		
		validate();
    	repaint();
	}	

	public void SwingAnimator(){
		try{
			for(i = 1; i <= 8; i++){
				labels[i] = new JLabel();
			    int x  = i *101;	
				labels[i].setBounds(x,100,100,100);
				panel.add(labels[i]);
				images = new ImageIcon(ClassLoader.getSystemResource("images/pic" + i + ".png"));	
				labels[i].setIcon(images);				
				th.sleep(50);
			}
		}
		catch(InterruptedException e){}
	}
}