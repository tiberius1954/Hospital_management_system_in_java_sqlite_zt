package Main;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import Classes.Allvalid;
import Classes.Calhelper;
import Classes.Employee;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Weekpanel2 extends JFrame {
	Hhelper hh = new Hhelper();
	Calhelper cc = new Calhelper();
	DatabaseHelper dh = new DatabaseHelper();
	Allvalid allv = new Allvalid();
	Grlib gr = new Grlib();
	Databaseop dd = new Databaseop();	
	LocalDate actdate;
	String docdid="";
	JFrame myframe = this;
	String doctorname="";
	int mydepid = 0;
	String mydepname = "";
	
	Weekpanel2(){
		setTitle("Doctor appointment choose");
		actdate = LocalDate.now();
		initcomponents();	
		hh.iconhere(this);
	}
	Weekpanel2( int pdepid, String pdepname){
		setTitle("Doctor appointment choose");
		mydepid = pdepid;
		mydepname = pdepname;		
		actdate = LocalDate.now();
		initcomponents();	
		dd.cmbmeployee_departmentcombofill(cmbdoctors, "d", mydepid);
		hh.iconhere(this);	
	}
	
	private void initcomponents(){	
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setTitle("Appointments");

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setLayout(null);	
		getContentPane().setBackground(new Color(0, 128, 128));
		lbheader = hh.fflabel("Appointments - " +mydepname);
		lbheader.setBounds(30, 5, 450, 35);	
		add(lbheader);
		
		mainPanel = new JPanel(null);
		mainPanel.setBounds(25,50, 1240, 605);
	    mainPanel.setBackground(new Color(178, 216, 216));
		add(mainPanel);
		
		lPanel = new JPanel(null);
		lPanel.setBounds(10, 20, 490, 565);
		lPanel.setBorder(hh.bb);
		lPanel.setBackground(new Color(178, 216, 216));		
		
		lbdoctor = hh.clabel("Doctor name");
		lbdoctor.setBounds(5, 70, 110, 25);
		lPanel.add(lbdoctor);
		
		cmbdoctors = hh.cbcombo();
		cmbdoctors.setBounds(125, 70, 320, 30);
		cmbdoctors.setFont(hh.textf);
		cmbdoctors.setName("doctors");
		lPanel.add(cmbdoctors);

		cmbdoctors.addItemListener(new ItemListener() {
		     @Override
		     public void itemStateChanged(ItemEvent e) {		  
		         if (e.getStateChange() == ItemEvent.SELECTED) {
                   //  String seg = cmbdoctors.getSelectedItem().toString();                   
                         if (cmbdoctors.getSelectedItem() != null) {
                 			Employee emp = (Employee) cmbdoctors.getSelectedItem();
                 			String empid = emp.getEmpid();                 		
                 			String posid = emp.getPosid();
                 			 refreshCalendar( empid, posid, mydepid) ;	                 	
                            }
		             }
		     }
		     });		
		
		lbpatient = hh.clabel("Patient name");
		lbpatient.setBounds(5, 120, 110, 25);
		lPanel.add(lbpatient);
		
		txpatient = hh.dfTextField(150);
		txpatient.setBounds(125, 120, 245, 30);
		lPanel.add(txpatient);

		btnpatient = gr.sbcs("Patients");
		btnpatient.setForeground(Color.black);
		btnpatient.setBackground(Color.ORANGE);
		btnpatient.setBounds(380, 120, 100, 32);
		btnpatient.setFocusable(false);
		lPanel.add(btnpatient);
		btnpatient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				Patients pa = new Patients(myframe, "weekpanel");
				pa.setLayout(null);
				pa.setLocationRelativeTo(null);
				pa.setVisible(true);
			}
		});
		
		 txpatientid =hh.dfTextField(10);
		
		lbpurpose = hh.clabel("Purpose");
		lbpurpose.setBounds(160, 170, 120, 25);
		lPanel.add(lbpurpose);
		
		txapurpose = hh.cTextarea();
		pPane = new JScrollPane(txapurpose, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pPane.setBounds(20, 205, 450, 200);
		pPane.setBorder(hh.borderf);
		pPane.setViewportView(txapurpose);
		lPanel.add(pPane);
		
		lbappdate = hh.clabel("Appointment date");
		lbappdate.setBounds(40, 435, 150, 25);
		lPanel.add(lbappdate);
		
		txappdate = hh.DatetextField();
		txappdate.setBounds(195, 435, 110, 30);
		txappdate.setEditable(false);
		lPanel.add(txappdate);
		
		lbapptime = hh.clabel("Time");
		lbapptime.setBounds(315, 435, 50, 25);
		lPanel.add(lbapptime);
		
		txapptime = hh.TimetextField();
		txapptime.setBounds(370, 435, 60, 30);
		txapptime.setEditable(false);
		lPanel.add(txapptime);		
		mainPanel.add(lPanel);		
		
		btnsave = gr.sbcs("Save");
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		btnsave.setBounds(120, 500, 120, 32);
		btnsave.setFocusable(false);
		lPanel.add(btnsave);
	    btnsave.addActionListener(e -> data_save());

		btncancel = gr.sbcs("Cancel");
		btncancel.setForeground(Color.black);
		btncancel.setBackground(hh.vvzold);
		btncancel.setBounds(250, 500, 120, 32);
		btncancel.setFocusable(false);
		lPanel.add(btncancel);
//		btncancel.addActionListener(e -> cancelall());			
		
		String sdepid = hh.itos(mydepid);
		mPanel = new Calendarpanel2("","", sdepid, myframe);
		mPanel.setLayout(null);
		mPanel.setBounds(510, 20, 715, 500);
		mPanel.setBackground(new Color(178, 216, 216));
		mPanel.setBorder(hh.borderf);	   
	    mainPanel.add(mPanel);		
		setSize(1305, 720);
		setVisible(true);		
	}
	
	private void refreshCalendar(String pdocid, String pposid, int pdepid) {
		mainPanel.remove(mPanel);
		mainPanel.repaint();
		mainPanel.revalidate();
		mPanel.removeAll();	
		String sdepid = hh.itos(pdepid);
		mPanel = new Calendarpanel2(pdocid, pposid, sdepid, myframe);
		mPanel.setLayout(null);
		mPanel.setBounds(510, 20, 715, 500);
		mPanel.setBorder(hh.borderf);	
		mPanel.repaint();
		mPanel.revalidate();
		mainPanel.add(mPanel);
	}
	
	private void data_save() {
		String sql = "";
		String empid="";
		String posid="";
		String pid= txpatientid.getText();
		String seg = cmbdoctors.getSelectedItem().toString();
           if (!hh.zempty(seg)) {             
           	 empid = (String) ((Employee) cmbdoctors.getSelectedItem()).getEmpid();     
             posid = (String) ((Employee) cmbdoctors.getSelectedItem()).getPosid();                   
           }
          String app_date = txappdate.getText();
          String app_time = txapptime.getText();
          String purpose = txapurpose.getText();
          String status = "Pending";         
           String created_at = hh.currentDate();
//           \\ Done, paid, scheduled
           if (pvalidation(pid, empid, app_date, app_time) == false) {        	   
   			    return;
           }      
     
   	     sql = "insert into appointments (pid, docid, posid, depid, app_date, app_time, purpose, "
   					+ " status, created_at) values (" + " '" + pid + "','" + empid + "','"
   					+ posid + "'," + mydepid + ",'" + app_date + "','" + app_time + "','" + purpose
   					+ "','" + status + "','" +  created_at+ "')";
   		
   		try {
   			int flag = dh.Insupdel(sql);
   			if (flag == 1) {
				hh.ztmessage("Success", "Message");
   				} else {
				JOptionPane.showMessageDialog(null, "sql error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}
   			
   		}
           
           
	private Boolean pvalidation(String pid, String sempid, String appdate, String apptime ) {
		Boolean ret = true;
		ArrayList<String> err = new ArrayList<String>();

		if ( hh.zempty(pid)) {
			err.add("Patient name is empty ! ");
			ret = false;
		}
		if ( hh.zempty(sempid)) {
			err.add("Doctor name is empty ! ");
			ret = false;
		}
		if (appdate.equals("____-__-__" ) ) {
			err.add("Appointment date is empty !");
			ret = false;
		}
		if ( apptime.equals("__:__" ) ) {
			err.add("Appointment time is empty !");
			ret = false;
		}

		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message",1);
		}
		return ret;
	}
	
	
	
	public void writeout(String sdate, String stime) {
		 txappdate.setText(sdate);
		 txapptime.setText(stime);
	}
	
	
	public void passtopatient(String patientid, String patientname) {
		 txpatient.setText(patientname);
		 txpatientid.setText(patientid);
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Weekpanel2 wp = new Weekpanel2();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	JPanel mainPanel, mPanel, lPanel;
	JLabel lbheader, lbheader1, lbtimer, lbdepartment, lbdoctor, lbpatient, lbpurpose,
	lbappdate, lbapptime;
	JButton btnpatient, btnsave, btncancel;
	JComboBox  cmbdoctors;
	JTextField txpatient, txappdate, txapptime, txpatientid;
     JTextArea  txapurpose;
     JScrollPane pPane;
     
}
