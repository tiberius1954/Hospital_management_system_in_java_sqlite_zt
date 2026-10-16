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
public class Choose_department_employeetyp extends JFrame{	
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Department depa;
	JFrame myframe = this;	
	String mywhich ="";
	protected String[] emptypes = dd.emptypes();
	
	 Choose_department_employeetyp(){	
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
			setTitle("Choose department and  employee type");
			getContentPane().setBackground(new Color(0, 128, 128));
			setSize(630, 330);
			setLayout(null);
			setLocationRelativeTo(null);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent windowEvent) {
					dispose();
				}
			});

			lbheader = new JLabel("Choose department and employee type");
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
			
			lbemployeetype = hh.clabel("Employee types");
			lbemployeetype.setBounds(10, 90, 150, 25);
			ePanel.add(lbemployeetype);
			
			cmbemployeetypes = hh.cbcombo();
			cmbemployeetypes.setModel(new DefaultComboBoxModel(emptypes));
			cmbemployeetypes.setBounds(170, 90, 280, 30);
			cmbemployeetypes.setFont(hh.textf);	
			ePanel.add(cmbemployeetypes);
		
			btnnext =gr.sbcs("Next");
			btnnext.setBounds(255, 155, 120, 30);
			btnnext.setForeground(Color.black);
			btnnext.setBackground(Color.ORANGE);
			ePanel.add(btnnext);	
			
			btnnext.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					int depid = (int) ((Department) cmbdepartments.getSelectedItem()).getDepid();	
					String emptypL="";				
					String emptype =  (String) cmbemployeetypes.getSelectedItem();	
					if (emptype =="Doctors") {
						emptypL = "d";
					} else if (emptype =="Nurses") {						
						emptypL = "n";
					} else if (emptype =="Staff") {	
						emptypL = "s";
					}
					if (depid > 0 && !hh.zempty(emptype)) {
					      String depname = (String) ((Department) cmbdepartments.getSelectedItem()).toString();		
				          Department_employee dt = new Department_employee(depid, depname, emptypL);
					     dispose();						}					
					}
			});
		
			setVisible(true);		

		 
	 }
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Choose_department_employeetyp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	JPanel ePanel;
	JComboBox cmbdepartments, cmbemployeetypes;
	JLabel lbheader, lbdepname, lbemployeetype;
    JButton btnnext; 
}
