package Databaseop;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import Classes.Employee;
import Classes.Hhelper;
import Classes.Unity;
import Databaseop.Databaseop.Position;
import Classes.Admission;
import Classes.Patient;

public class Databaseop {
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;
	DatabaseHelper dh = new DatabaseHelper();
	Hhelper hh = new Hhelper();
	Patient pat;
	Admission adm;
	Employee emp;
	Position pos;
	Medicine medicine;

	private String[] bloodgnames = new String[] { "O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-" };
	public String[] bloodnames() {
		Arrays.sort(bloodgnames);
		return bloodgnames;
	}

	private String[] paymodes = { "By cash", "Credit", "By cheque" };
	public String[] paymodes() {
		return paymodes;
	}
	
	private String[] emptypes = { "Doctors", "Nurses", "Staff" };
	public String[] emptypes() {
		return emptypes;
	}
	
	

	public String myidfv(JTable dtable) {
		String retid = "";
		try {
			DefaultTableModel model = (DefaultTableModel) dtable.getModel();
			int row = dtable.getSelectedRow();
			if (row > -1) {
				retid = model.getValueAt(row, 0).toString();
			}
		} catch (Exception e) {
			System.out.println("sql error!!!");
		}
		return retid;
	}

	public int data_delete(JTable dtable, String sql) {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		int sIndex = dtable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String iid = d1.getValueAt(sIndex, 0).toString();
		if (iid.equals("")) {
			return flag;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			int selections[] = dtable.getSelectedRows();
			int lastIndex = selections[0];
			String vsql = sql + iid;
			flag = dh.Insupdel(vsql);
			if (flag > 0) {
				d1.removeRow(sIndex);
			}
			if (dtable.getRowCount() > 0) {
				dtable.setRowSelectionInterval(lastIndex - 1, lastIndex - 1);
			}
		}
		return flag;
	}

	public void rtable_delete(JTable dtable, String pid) {
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		String sql;
		int flag = 0;
		int sIndex = dtable.getSelectedRow();
		int selections[] = dtable.getSelectedRows();
		int lastIndex = selections[0];
		sql = "delete from properties where pid='" + pid + "'";
		try {
			flag = dh.Insupdel(sql);
			d1.removeRow(sIndex);
			if (dtable.getRowCount() > 0) {
				dtable.setRowSelectionInterval(lastIndex - 1, lastIndex - 1);
			}
		} catch (Exception e1) {
			if (dtable.getRowCount() > 0)
				dtable.setRowSelectionInterval(0, 0);
		}
	}

	public int tdata_delete(JTable dtable, String sql, int row) {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		flag = dh.Insupdel(sql);
		if (flag == 1) {
			d1.removeRow(row);
		}
		return flag;
	}

	public int table_maxid(String sql) {
		int myid = 0;
		try {
			rs = dh.GetData(sql);
			if (!rs.next()) {
				System.out.println("Error.");
			} else {
				myid = rs.getInt("max_id");
			}
			dh.CloseConnection();
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
		return myid;
	}

	public Boolean cannotdelete(String sql) {
		Boolean found = false;
		rs = dh.GetData(sql);
		try {
			if (rs.next()) {
				found = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
		return found;
	}

	public boolean recordfound(String Query) {
		boolean found = false;
		int count = 0;
		try {
			rs = dh.GetData(Query);
			while (rs.next()) {
				count = rs.getInt("count(*)");
			}
			dh.CloseConnection();
		} catch (SQLException SQLe) {
			System.out.println("Count(*) error " + SQLe.toString());
		}
		if (count > 0) {
			found = true;
		}
		return found;
	}

	public int manyrecord(String Query) {
		int count = 0;
		try {
			rs = dh.GetData(Query);
			while (rs.next()) {
				count = rs.getInt("count(*)");
			}
			dh.CloseConnection();
		} catch (SQLException SQLe) {
			System.out.println("Count(*) error " + SQLe.toString());
		}
		return count;
	}

	public void cmbpositioncombofill(JComboBox mycombo, String type) {
		String sql;
		mycombo.removeAllItems();
		Position A = new Position(0, "");
		mycombo.addItem(A);
		sql = "select posid, name from positions where type='" + type + "' order by upper(name)";
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Position(rs.getInt("posid"), rs.getString("name"));
				mycombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cmbdepartmentcombofill(JComboBox mycombo) {
		String sql;
		mycombo.removeAllItems();
		Department A = new Department(0, "");
		mycombo.addItem(A);
		sql = "select depid, depname from departments  order by upper(depname)";

		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Department(rs.getInt("depid"), rs.getString("depname"));
				mycombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public class Department {
		String depname;
		int depid;

		public Department(int depid, String depname) {
			this.depid = depid;
			this.depname = depname;
		}

		public Department() {

		}

		@Override
		public String toString() {
			return depname;
		}

		public String getDepname() {
			return depname;
		}

		public void setDepname(String depname) {
			this.depname = depname;
		}

		public int getDepid() {
			return depid;
		}

		public void setDepid(int depid) {
			this.depid = depid;
		}
	}

	public void cmbemployeecombofill(JComboBox mycombo) {
		String sql;
		mycombo.removeAllItems();
		Employee A = new Employee("", "","");
		mycombo.addItem(A);		
		sql = "select empid, name, emptype from employees  order by upper(name)";
	   ResultSet rs = dh.GetData(sql);
		try {
		 	while (rs.next()) {
				A = new Employee(rs.getString("empid"), rs.getString("name"), rs.getString("emptype"));
				mycombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cmbmeployee_departmentcombofill(JComboBox mycombo, String type, int pdepid) {
		String sql = "";
		mycombo.removeAllItems();
		Employee A = new Employee("", "","","");
		mycombo.addItem(A);
		sql = "select de.empid, e.name, de.posid, p.name as position from department_employee de"
				+ " left join employees e  on de.empid = e.empid "
				+ " left join positions p  on de.posid = p.posid "
				+ " where e.emptype='" + type + "'  and de.depid = " + pdepid
				+ " order by upper(p.name)";

		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Employee(rs.getString("empid"), rs.getString("name"),
						rs.getString("position"),rs.getString("posid"));
				mycombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void cmbemployee_typecombofill(JComboBox mycombo, String type) {
		String sql = "";
		mycombo.removeAllItems();
		Employee A = new Employee("", "","");
		mycombo.addItem(A);
		sql = "select empid, name, emptype from employees "				
				+ " where emptype='" + type + "' order by upper(name)";
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Employee(rs.getString("empid"), rs.getString("name"), rs.getString("emptype"));
				mycombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void cmbdoctor1combofill(JComboBox mycombo,  int pdepid) {
		String sql="";
		mycombo.removeAllItems();
		Employee A = new Employee("", "","","");
		mycombo.addItem(A);	
			   sql = "select distinct d.empid, e.name, d.posid, p.name as pname from doctorapptimes d "
			   		+ " left join employees e  on e.empid = d.empid "
					+ " left join positions p on p.posid = d.posid "
			   		+  " where d.depid = "+ pdepid +" order by upper(e.name)";		
		
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Employee(rs.getString("empid"), rs.getString("name"), rs.getString("pname"),
						rs.getString("posid"));			
				mycombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cmbposcombofill(JComboBox mycombo, String empid) {
		String sql;
		mycombo.removeAllItems();
		Position A = (new Position(0, ""));
		mycombo.addItem(A);
		sql = "select e.posid, p.name from employee_positions e "
				+ " left join positions p  on e.posid = p.posid where empid='" + empid + "' "
				+ "order by upper(p.name)";
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = (new Position(rs.getInt("posid"), rs.getString("name")));
				mycombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void cmbpos1combofill(JComboBox mycombo, String stype) {
		String sql;
		mycombo.removeAllItems();
		Position A = (new Position(0, ""));
		mycombo.addItem(A);
		sql = "select posid, name from positions  where type='" + stype + "' "
				+ "order by upper(name)";
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = (new Position(rs.getInt("posid"), rs.getString("name")));
				mycombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void countrycombofill(JComboBox ccombo) {
		String Sql = " select countryname from countries order by  upper(countryname)";
		ccombo.removeAllItems();
		ccombo.addItem("");
		try {
			ResultSet res = dh.GetData(Sql);
			while (res.next()) {
				ccombo.addItem(res.getString("countryname"));
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cmbspecicombofill(JComboBox ccombo, String type) {
		ccombo.removeAllItems();
		Specialization A = new Specialization(0, " ");
		ccombo.addItem(A);
		String sql = "select sid, name  from specializations	where type ='" + type + "' order by upper(name)";
		try {
			ResultSet rs = dh.GetData(sql);
			while (rs.next()) {
				A = new Specialization(rs.getInt("sid"), rs.getString("name"));
				ccombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void cmbmedicinesfill(JComboBox ccombo){
		ccombo.removeAllItems();
		Medicine A = new Medicine(0, " ");
		ccombo.addItem(A);
		String sql = "select medid, name  from medicine order by upper(name)";
		try {
			ResultSet rs = dh.GetData(sql);
			while (rs.next()) {
				A = new Medicine(rs.getInt("medid"), rs.getString("name"));
				ccombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	public class Medicine {
		int medid;
		String name;

		public Medicine() {
		}

		public Medicine(int medid, String name) {
			this.medid = medid;
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

		public int getMedid() {
			return medid;
		}

		public String getName() {
			return name;
		}
	}

	
	public class Position {
		String name;
		int posid;

		public Position() {
		}

		public Position(int posid, String name) {
			this.name = name;
			this.posid = posid;
		}

		@Override
		public String toString() {
			return name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getPosid() {
			return posid;
		}

		public void setPosid(int posid) {
			this.posid = posid;
		}
	}

	public class Specialization {
		int sid;
		String name;

		public Specialization() {
		}

		public Specialization(int sid, String name) {
			this.sid = sid;
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

		public int getSid() {
			return sid;
		}

		public void setSid(int sid) {
			this.sid = sid;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public class Cities {
		int cid;
		String name;

		public Cities() {
		}

		public Cities(int cid, String name) {
			this.cid = cid;
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

		public int getCid() {
			return cid;
		}

		public String getName() {
			return name;
		}
	}

	public void cmbcitiescombofill(JComboBox mycombo) {
		mycombo.removeAllItems();
		Cities A = new Cities(0, "");
		mycombo.addItem(A);
		String sql = "select cid, name  from cities order by name";
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Cities(rs.getInt("cid"), rs.getString("name"));
				mycombo.addItem(A);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
	}
	
	public void cmbtestnamescombofill(JComboBox mycombo) {
		mycombo.removeAllItems();
		Tests A = new Tests(0, "");
		mycombo.addItem(A);
		String sql = "select tid, tname  from test order by tname";
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Tests(rs.getInt("tid"), rs.getString("tname"));
				mycombo.addItem(A);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();		
	}
	
	public class Tests {
		int tid;
		String tname;

		public Tests() {
		}

		public Tests(int tid, String tname) {
			this.tid = tid;
			this.tname = tname;
		}

		@Override
		public String toString() {
			return tname;
		}

		public int getTid() {
			return tid;
		}

		public String getTname() {
			return tname;
		}
	}

	public void reltabletview(JTable dtable, String mypid) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String sql = " select rid, rname, rtype, rphone, rpid  from relative where rpid='" + mypid + "'";
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String rid = rs.getString("rid");
				String rname = rs.getString("rname");
				String rtype = rs.getString("rtype");
				String rphone = rs.getString("rphone");
				String rpid = rs.getString("rpid");
				m1.addRow(new Object[] { rid, rname, rtype, rphone, rpid });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
	}

	public void posetabletview(JTable dtable, String myemp) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String sql = " select e.eposid, p.name, e.posid  from employee_positions e "
				+ "left join positions p  on e.posid = p.posid where empid='" + myemp + "'";
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String eposid = rs.getString("eposid");
				String name = rs.getString("name");
				String posid = rs.getString("posid");
				m1.addRow(new Object[] { eposid, name, posid, myemp });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
	}

	public void spectabletview(JTable dtable, String myemp) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String sql = " select e.emsid, s.name, e.sid  from employee_specialization e "
				+ "left join specializations s  on e.sid = s.sid where empid='" + myemp + "'";
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String emsid = rs.getString("emsid");
				String name = rs.getString("name");
				String sid = rs.getString("sid");
				m1.addRow(new Object[] { emsid, name, sid, myemp });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
	}

	public void patienttable_update(JTable dtable, String dheader[], String what) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);
		String sql = "select p.pid, p.pname,p.gender,p. dob, p.phone, p.email, p.blood, "
				+ "p.country, p.cid, c.name as city, p.street, p.createat, p.status, p.remark from patient p "
				+ "left join cities c  on p.cid = c.cid " + what;

		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String pid = rs.getString("pid");
				String pname = rs.getString("pname");
				String gender = rs.getString("gender");
				String dob = rs.getString("dob");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String blood = rs.getString("blood");
				String country = rs.getString("country");
				String cid = rs.getString("cid");
				String city = rs.getString("city");
				String street = rs.getString("street");
				String remark = rs.getString("remark").toString();
				String createat = rs.getString("createat");
				String status = rs.getString("status");

				m1.addRow(new Object[] { pid, pname, gender, dob, phone, email, blood, country, cid, city, street,
						createat, status });
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(dheader);
		
		int[] wcols0 = { 8};
		hh.cellswidth_0(dtable, wcols0);

		int[][] wcols = { { 0, 90 }, { 1, 200 }, { 2, 70 }, { 3, 100 }, { 4, 200 }, { 5, 300 }, { 6, 60 }, { 7, 150 },
				{ 9, 200 }, { 10, 300 }, { 11, 100 }, { 12, 80 } };
		hh.cellswidth(dtable, wcols);

		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		if (dtable.getRowCount() > 0) {
			int row = dtable.getRowCount() - 1;
			dtable.setRowSelectionInterval(row, row);
		}
	}

	public Patient getpatientrecord(String ppid) {
		pat = new Patient();
		String sql = "select p.pid, p.pname,p.gender,p. dob, p.phone, p.email, p.blood, "
				+ "p.country, p.cid, c.name as city, p.street, p.createat, p.status, p.remark from patient p "
				+ "left join cities c  on p.cid = c.cid where pid='" + ppid + "'";
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String pid = rs.getString("pid");
				String pname = rs.getString("pname");
				String gender = rs.getString("gender");
				String dob = rs.getString("dob");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String blood = rs.getString("blood");
				String country = rs.getString("country");
				int cid = rs.getInt("cid");
				String city = rs.getString("city");
				String street = rs.getString("street");
				String createat = rs.getString("createat");
				String status = rs.getString("status");
				String remark = rs.getString("remark");
				pat = new Patient(pid, pname, phone, email, gender, country, cid, city, street, dob, createat, remark,
						status, blood);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		return pat;
	}
	
	public Admission  getadmissionrecord(String padid) {	
		adm = new Admission();
	String sql =	"select  a.adid, a.add_date, a.discharge_date, a.reason, a.pid, p.pname,"
							+ " a.docid, e.name as docname, a.posid, a.nurseid, e.name as nname,"
							+ " a.nposid, a.status, a.depid from admission a " 
							+ "  left join patient p  on a.pid = p.pid "
							+ "  left join employees e  on a.docid = e.empid "
						    + "  left join employees e1  on a.nurseid= e1.empid where adid = '" + padid +"'";			
	
		try {		
			rs = dh.GetData(sql);
			while (rs.next()) {
				int adid = rs.getInt("adid");
				String add_date = rs.getString("add_date");
				String discharge_date = rs.getString("discharge_date");
				String reason = rs.getString("reason");
				String pid = rs.getString("pid");
				String patientname = rs.getString("pname");
				String docid= rs.getString("docid");
				String docname = rs.getString("docname");
				String posid = rs.getString("posid");
				String nurseid = rs.getString("nurseid");
				String nursename = rs.getString("nname");
				String nposid= rs.getString("nposid");
				String status= rs.getString("status");	
				int   depid = rs.getInt("depid");
			
	    		 adm = new Admission ( adid, add_date,  discharge_date,  reason,
	    				 pid, patientname, docid, posid, depid, status, nurseid, nposid);
			}				
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		return adm;
	}		
	
	public void employeetable_update(JTable dtable, String dheader[], String what) {
		DefaultTableModel m1 = (DefaultTableModel) dtable.getModel();
		m1.setRowCount(0);

		String sql = "select e.empid, e.name, e.phone, e.email, e.gender,"
				+ " e.country, e.cid, c.name as city, e.street, e.dateofjoin, e.dob, e.status from employees e "
				+ " left join cities c  on e.cid = c.cid " + what;
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String empid = rs.getString("empid");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String gender = rs.getString("gender");
				String country = rs.getString("country");
				String cid = rs.getString("cid");
				String city = rs.getString("city");
				String street = rs.getString("street");
				String dateofjoin = rs.getString("dateofjoin");
				String dob = rs.getString("dob");
				String status = rs.getString("status");
				m1.addRow(new Object[] { empid, name, phone, email, gender, country, cid, city, street, dateofjoin, dob,
						status });
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(dheader);
		int[] wcols0 = { 6};
		hh.cellswidth_0(dtable, wcols0);

		int[][] wcols = { { 0, 100 }, { 1, 250 }, { 2, 200 }, { 3, 300 }, { 4, 100 }, { 5, 200 }, { 7, 200 },
				{ 8, 300 }, { 9, 100 }, { 10, 100 }, { 11, 100 } };
		hh.cellswidth(dtable, wcols);

		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		if (dtable.getRowCount() > 0) {
			int row = dtable.getRowCount() - 1;
			dtable.setRowSelectionInterval(row, row);
		}
	}

	public Employee getemployee_record(String pempid) {
		emp = new Employee();
		String sql = "select e.empid,  e.name, e.gender, e.dob, e.phone, e.email, "
				+ "e.country, e.cid, c.name as city, e.street, e.dateofjoin, e.status, e.remark, e.emptype from employees e "
				+ "left join cities c  on e.cid = c.cid where empid='" + pempid + "'";
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				String empid = rs.getString("empid");
				String name = rs.getString("name");
				String phone = rs.getString("phone");
				String email = rs.getString("email");
				String gender = rs.getString("gender");
				String country = rs.getString("country");
				int cid = rs.getInt("cid");
				String city = rs.getString("city");
				String street = rs.getString("street");
				String dob = rs.getString("dob");
				String dateofjoin = rs.getString("dateofjoin");
				String status = rs.getString("status");
				String remark = rs.getString("remark");
				String emptype = rs.getString("emptype");
				emp = new Employee(empid, name, phone, email, gender, country, cid, city, street, dateofjoin, dob,
						remark, status, emptype);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		return emp;
	}

	public String[] getroomdata(String proomid) throws SQLException {		
		String[] ret = new String[3];
			String sql = "select *  from rooms  where roomid ='"+proomid +"'";		
		rs = dh.GetData(sql);
		if (rs.next()) {
			ret[0] = rs.getString("roomname");
			 ret[1] = rs.getString("capacity");
			 ret[2] = rs.getString("occupied");		
		}
		dh.CloseConnection();			
		return ret;
	}

	
	public String[] getappointment(String pappid) {
		String [] ret = new String [2];
		String sql = " select app_date, app_time from appointments where appid ='" + pappid +"'";		
		try {
			rs = dh.GetData(sql);
			while (rs.next()) {
				ret[0] = rs.getString("app_date");
				ret[1]= rs.getString("app_time");			
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		 
		return ret;
	}

}
