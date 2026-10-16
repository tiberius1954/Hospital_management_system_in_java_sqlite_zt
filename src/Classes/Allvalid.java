package Classes;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.sql.DriverManager;
import Classes.Hhelper;
import Databaseop.Databaseop;

public class Allvalid {
	Hhelper hh = new Hhelper();
	Databaseop dd = new Databaseop();
	public String mess = "";
	String ss;
	Boolean yes = false;

	public boolean namevalid(String content) {
		if (hh.zempty(content)) {
			mess= "Name is empty !";
			return false;
		}
		return true;
	}
	public Boolean phonevalid(String content) {
		if (hh.zempty(content)) {
			mess = "Phone is empty !";
			return false;
		} else if (content.length() < 7) {
			mess = "Phone is short !";
			return false;
		}
		return true;
	}

	public boolean emailvalid(String content) {
		if (hh.zempty(content)) {
		     mess = "Email is empty !";
			return false;
		} else if (content.indexOf(".") == -1 || content.indexOf("@") == -1) {
			mess = "Incorrect email  !";
			return false;
		}
		return true;
	}
	public boolean streetvalid(String content) {
		if (hh.zempty(content)) {
			mess = "Street is empty !";
			return false;
		}
		return true;
	}
	public boolean cityvalid(String content) {
		if (hh.zempty(content)) {
			mess = "City is empty !";
			return false;
		}
		return true;
	}
	public Boolean countryvalid(String content) {
		if (hh.zempty(content)) {
			mess = "Country is empty !";
			return false;	
		}
	return true;
	}	
	
	public boolean datevalid(String content) {
		if (hh.zempty(content)) {
			mess = "Date is empty !";
			return false;
		} else {
			if (!hh.validatedate(content, "N")) {
				mess= "Incorrect date !";
				return false;
			}
		}
		return true;
	}
	public boolean dobvalid(String content) {
		if (hh.zempty(content) || content.equals("____-__-__" ) ) {
			mess = "Dob is empty !";
			return false;
		} else {
			if (!hh.validatedate(content, "N")) {
				mess= "Incorrect dob date !";
				return false;
			}
		}
		return true;
	}
	public boolean createatvalid(String content) {
		if (hh.zempty(content)|| content.equals("____-__-__" ) ) {
			mess = "Create at is empty !";
			return false;
		} else {
			if (!hh.validatedate(content, "N")) {
				mess= "Incorrect Date !";
				return false;
			}
		}
		return true;
	}
	public boolean dateofjoinvalid(String content) {
		if (hh.zempty(content)|| content.equals("____-__-__" ) ) {
			mess = "Date of join is empty !";
			return false;
		} else {
			if (!hh.validatedate(content, "N")) {
				mess= "Incorrect date of join !";
				return false;
			}
		}
		return true;
	}
	
	public Boolean patientidvalid(String content, Boolean newrec) {
		if (hh.zempty(content)) {
			mess = "Patientid  is empty !";
			return false;	
		} else if (patidval(content, newrec)== true) {	
			mess = "Patientid  is already exist !";
			return false;
		}
		return true;		
      }
	public Boolean empidvalid(String content, Boolean newrec) {
		if (hh.zempty(content)) {
			mess = "employeeid  is empty !";
			return false;	
		} else if (empidval(content, newrec)== true) {
			mess = "Employeeid  is already exist !";
			return false;
		}			
		return true;		
      }
	
	private Boolean empidval(String empid, Boolean newrec) {
		Boolean ret = false;
		if (newrec == true) {
			String sql =  "select count(*) from employees where empid ='" + empid+"'" ;
		  if(dd.recordfound(sql)==true) {
			  ret = true;
		  }
		}		
		return ret;		
	}
	private Boolean patidval(String pid, Boolean newrec) {	
		Boolean ret = false;
		if (newrec == true) {
			String sql =  "select count(*) from patient where pid ='" + pid +"'";
		  if(dd.recordfound(sql)==true) {
			  ret = true;
		  }
		}				
		return ret;		
	}
}
