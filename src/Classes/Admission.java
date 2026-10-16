package Classes;

public class Admission {
	
	private int adid;
	private String add_date;
	private String discharge_date;
	private String reason;
	private String pid;
	private String patientname;
	private String docid;
	private String posid;
	private int depid;
	private String status;
	private String nurseid;
	private String nposid;

	
	 public Admission() {
		 
	 }	
	
	 public Admission( int adid, String add_date,  String discharge_date,  String reason,
			 String pid, String patientname, String docid, String posid, int depid,
			 String status, String nurseid, String nposid ){
		 this.adid = adid;
		 this.add_date = add_date;
		 this.discharge_date = discharge_date;
		 this.reason = reason;
		 this.pid = pid;
		 this.patientname = patientname;
		 this.docid = docid;
		 this.posid = posid;
		 this.depid = depid;
		 this.status = status;
		 this.nurseid = nurseid;		
		 this.nposid = nposid;
	  	}
	 
	   public int getAdid() {
	        return adid;
	    }
	   
	public String getAdd_date() {
		return add_date;
	}
	
	public  String getDischarge_date() {
		return discharge_date;
	}
	
	public String getReason() {
		return reason;
	}
	
	public String getPid() {
		return pid;
	}
	public String getPatientname() {
		return patientname;
	}
	
	public String getDocid() {
		return docid;
	}
	
	public int getDepid() {
		return depid;
	}
	
	public String getPosid() {
		return posid;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getNurseid() {
		return nurseid;
	}
	public String getNposid() {
		return nposid;
	}
	
	}
