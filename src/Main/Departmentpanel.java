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

public class Departmentpanel extends JFrame{
	Grlib gr = new Grlib();	
	Hhelper hh = new Hhelper();	
	Calhelper cc = new Calhelper();
	JFrame myframe = this;
	Departmentpanel(){
		init();	
		hh.iconhere(this);		
	}
	private void init() {
		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(645, 350);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Department panel");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		
		lbheader = new JLabel("Department panel");
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		lbheader.setBounds(20, 10, 300, 35);
		add(lbheader);
		
		mPanel = new JPanel(null);
		mPanel.setBounds(15, 50, 600, 250);
		mPanel.setBackground(new Color(178, 216, 216));
		add(mPanel);
		
		btndepartment = gr.sbcs("Departments");
		btndepartment.setBounds(50, 60, 240, 60);
		btndepartment.setBackground(cc.psarga);
		mPanel.add(btndepartment);
		btndepartment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			Departments me= new Departments();		
		     me.setVisible(true);
			}
	});
		
		btnddepartment_emp = gr.sbcs("Employees in departments");
		btnddepartment_emp.setBounds(310, 60, 240, 60);
		btnddepartment_emp.setBackground(cc.psarga);
		mPanel.add(btnddepartment_emp);
		btnddepartment_emp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			Choose_department_employeetyp pa= new Choose_department_employeetyp();		
		     pa.setVisible(true);
		     dispose();
			}
	});		
		setVisible(true);		
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Departmentpanel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


JPanel mPanel;
JButton btndepartment, btnddepartment_emp;
JLabel lbheader;
}
