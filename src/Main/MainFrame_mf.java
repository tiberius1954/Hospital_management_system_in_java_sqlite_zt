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

public class MainFrame_mf extends JFrame{
	Grlib gr = new Grlib();	
	Hhelper hh = new Hhelper();	
	Calhelper cc = new Calhelper();
	
	MainFrame_mf(){
		init();	
		hh.iconhere(this);		
	}
	
	private void init() {
		getContentPane().setBackground(new Color(0, 128, 128));
	//	setSize(760, 350);
		 setSize(1000, 650);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Hospital dashboard");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
			//	Mainframe mf = new Mainframe();
			//	mf.setVisible(true);	// redirect to Mainframe 
				dispose();
			}
		});
		
		lbheader = new JLabel("Hospital dashboard");
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		lbheader.setBounds(20, 10, 700, 35);
		add(lbheader);
		
		mPanel = new JPanel(null);
		mPanel.setBounds(15, 50, 240, 550);
		mPanel.setBackground(new Color(178, 216, 216));
		add(mPanel);	
		
		lbpicture = new JLabel();
		lbpicture.setForeground(new Color(255, 255, 255));
		lbpicture.setBounds(265, 50, 710, 550);
		lbpicture.setBorder(hh.borderz);
		add(lbpicture);
		
		hosimage = new ImageIcon(ClassLoader.getSystemResource("images/mhospital.jpg"));	
		lbpicture.setIcon(hosimage);	
		
		btndoctors = gr.sbcs("Doctors");
		btndoctors.setBounds(30, 50, 170, 60);
		btndoctors.setBackground(cc.psarga);
		mPanel.add(btndoctors);
		btndoctors.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {				
				Doctorpanel    dp =new Doctorpanel();
			    dp.setVisible(true);			  
			}
		});		
		
		btnnurses = gr.sbcs("Nurses");
		btnnurses.setBounds(30, 120, 170, 60);
		btnnurses.setBackground(cc.psarga);
		mPanel.add(btnnurses);		
		btnnurses.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {				
				Nursepanel    np =new Nursepanel();
			    np.setVisible(true);			  
			}
		});		
		
		btnstaff = gr.sbcs("Staff");
		btnstaff.setBounds(30, 190, 170, 60);
		btnstaff.setBackground(cc.psarga);
		mPanel.add(btnstaff);		
		btnstaff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {				
				Staffpanel    np =new Staffpanel();
			    np.setVisible(true);			  
			}
		});		
		
		btnpatients = gr.sbcs("Patients");
		btnpatients.setBounds(30, 260, 170, 60);
		btnpatients.setBackground(cc.psarga);
		mPanel.add(btnpatients);
		btnpatients.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {				
				Patientpanel   np =new Patientpanel();
			    np.setVisible(true);			  
			}
		});	
		
		btnmedical_data= gr.sbcs("Medical data");
		btnmedical_data.setBounds(30, 330, 170, 60);
		btnmedical_data.setBackground(cc.psarga);
		mPanel.add(btnmedical_data);
		btnmedical_data.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {				
				Medicalpanel    np =new Medicalpanel();
			    np.setVisible(true);			  
			}
		});	
		
		btndepartments = gr.sbcs("Departments");
		btndepartments.setBounds(30, 400, 170, 60);
		btndepartments.setBackground(cc.psarga);
		mPanel.add(btndepartments);	
		btndepartments.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {				
				Departmentpanel    np =new Departmentpanel();
			    np.setVisible(true);			  
			}
		});			
		
		setVisible(true);
	}
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainFrame_mf();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
JPanel mPanel;
JButton btndoctors, btnpatients, btndepartments, btnappointments, btnmedical_data,
btnstaff, btnnurses;
JLabel lbheader, lbpicture;
ImageIcon hosimage;
}
