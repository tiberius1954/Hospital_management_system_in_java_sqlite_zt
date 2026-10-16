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

public class Medicalpanel  extends JFrame{

	Grlib gr = new Grlib();	
	Hhelper hh = new Hhelper();	
	Calhelper cc = new Calhelper();
	JFrame myframe = this;

	Medicalpanel(){
		init();	
		hh.iconhere(this);		
	}
	private void init() {
		getContentPane().setBackground(new Color(0, 128, 128));
		setSize(865, 350);
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Medical panel");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		
		mPanel = new JPanel(null);
		mPanel.setBounds(15, 50, 820, 250);
		mPanel.setBackground(new Color(178, 216, 216));
		add(mPanel);
		
		lbheader = new JLabel("Medical panel");
		lbheader.setForeground(new Color(255, 255, 255));
		lbheader.setFont(new Font("tahoma", Font.BOLD, 24));
		lbheader.setBounds(20, 10, 300, 35);
		add(lbheader);
		
		btnmedicalrec = gr.sbcs("Medical records");
		btnmedicalrec.setBounds(40, 50, 240, 60);
		btnmedicalrec.setBackground(cc.psarga);
		mPanel.add(btnmedicalrec);
		btnmedicalrec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			Choose_patient_department me= new Choose_patient_department("medicalreg");		
		     me.setVisible(true);		 
			}
	});
		
		btntests = gr.sbcs("Patient tests");
		btntests.setBounds(290, 50, 240, 60);
		btntests.setBackground(cc.psarga);
		mPanel.add(btntests);
		btntests.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			Choose_patient_department pa= new Choose_patient_department("patienttest");		
		     pa.setVisible(true);
			}
	});
		
		btnpatientpres = gr.sbcs("Patient prescriptions");
		btnpatientpres .setBounds(540, 50, 240, 60);
		btnpatientpres.setBackground(cc.psarga);
		mPanel.add(btnpatientpres );
		btnpatientpres.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			Choose_patient_department pa= new Choose_patient_department("prescription");		
		     pa.setVisible(true);
			}
	});	
		
		btnvisitrec = gr.sbcs("Visit records");
		btnvisitrec.setBounds(40, 130, 240, 60);
		btnvisitrec.setBackground(cc.psarga);
		mPanel.add(btnvisitrec);
		btnvisitrec.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			Choose_patient_department me= new Choose_patient_department("visitreg");		
		     me.setVisible(true);		 
			}
	});
		
	     btnmedicines = gr.sbcs("Medicines");
		btnmedicines.setBounds(290, 130, 240, 60);
		btnmedicines .setBackground(cc.psarga);
		mPanel.add(btnmedicines );
		btnmedicines .addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			Medicines me= new Medicines();		
		     me.setVisible(true);
			}
	});
		
		setVisible(true);
	}
	
	
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Medicalpanel();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


JPanel mPanel;
JButton btnmedicalrec, btntests, btnpatientpres, btnmedicines, btnprescription, btnvisitrec ;
JLabel lbheader;
}
