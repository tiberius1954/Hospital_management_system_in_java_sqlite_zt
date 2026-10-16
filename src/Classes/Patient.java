package Classes;

public class Patient {
	
     private String pid;	
	private String pname;
	private String phone;
	private String email;
	private String gender;
	private  String country;
	private int cid;
	private String city;
	private String street;
	private String dob;
	private String createat;
     private String remark;	
     private String status;
     private String blood;
	
	public Patient(String pid,  String pname,  String phone, String email,
			String gender,  String country, int cid, String city, String street, String dob, String createat,
			String remark, String status, String blood) {
	     this.pid = pid;
	     this.cid = cid;
	     this.pname =pname;	
	     this.phone = phone;
	     this.email = email;
	     this.gender = gender;
	     this.country = country;
	     this.cid = cid;
	     this.city = city;
	     this.street = street;
	     this.dob = dob;
	     this.createat = createat;
	     this.remark = remark;
	     this.status = status;
	     this.blood = blood;
	}		
	
	public Patient(){
		
	}
	@Override
	public String toString() {
		return pname;
	}
	
	public void setPid(String pid) {
	    this.pid = pid;
	}
	public String getPid() {
	    return pid;
	}
	public void setCid(int cid) {
	    this.cid = cid;
	}
	public int getCid() {
	    return cid;
	}
	
	public void setGender(String gender) {
	    this.gender = gender;
	}
	public  String getGender() {
	    return gender;
	}
	public void setPname(String pname) {
	    this.pname = pname;
	}
	public  String getPname() {
	    return pname;
	}

	public void setPhone(String Phone) {
	    this.phone = phone;
	}
	public  String getPhone() {
	    return phone;
	}

	public void setEmail(String email) {
	    this.email = email;
	}
	public  String getEmail() {
	    return email;
	}
	
	public void setCountry(String country) {
	    this.country = country;
	}
	public  String getCountry() {
	    return country;
	}
	
	public void setCity(String city) {
	    this.city = city;
	}
	public  String getCity() {
	    return city;
	}

	public void setStreet(String street) {
	    this.street = street;
	}
	public  String getStreet() {
	    return street;
	}
	
	public void setDob(String dob) {
	    this.dob = dob;
	}
	public  String getDob() {
	    return dob;
	}
	
	public void setCreateat(String createat) {
	    this.createat = createat;
	}
	public  String getCreateat() {
	    return createat;
	}
	
	public void setRemark(String remark) {
	    this.remark= remark;
	}
	public  String getRemark() {
	    return remark;
	}
	public void setStatus(String status) {
	    this.status = status;
	}
	public  String getStatus() {
	    return status;
	}
	public void setBlood(String blood) {
	    this.blood = blood;
	}
	public  String getBlood() {
	    return blood;
	}
}
