package Classes;

public class Employee {	
	
		private String empid;
		private String name;
		private String phone;
		private String email;
		private String gender;
		private  String country;
		private int cid;
		private String city;
		private String street;
		private String dob;
		private String dateofjoin;
	     private String remark;	
	     private String status;
	     private String emptype;
	     private String position;
	     private String posid;
		
		public Employee(String empid, String name,  String phone, String email,
				String gender,  String country, int cid, String city, String street,  String dateofjoin,
				String dob, String remark, String status, String emptype) {
		     this.empid = empid;
		     this.cid = cid;
		     this.name =name;		
		     this.phone = phone;
		     this.email = email;
		     this.gender = gender;
		     this.country = country;
		     this.cid = cid;
		     this.city = city;
		     this.street = street;
		     this.dob = dob;
		     this.dateofjoin = dateofjoin;
		     this.remark = remark;
		     this.status = status;
		     this.emptype = emptype;		    
		}			
		
		public Employee(String empid, String name, String emptype ) {	
			   this.empid = empid;
			   this.name =name;	
			   this.emptype = emptype;	
		}
	
		public Employee(String empid, String name, String position, String posid ) {	
			   this.empid = empid;
			   this.name =name;	
			   this.position = position;
			   this.posid = posid;
		}
		
		public Employee() {
			
		}
		@Override
		public String toString() {
			return getContact(); 	
		}
		
		 public String getContact() {
			 if(position!=null) {			
			    return name + " " + position;
			 } else {			
				 return name;
			 }
		 }		 
		
		public void setEmpid(String empid) {
		    this.empid = empid;
		}
		public String getEmpid() {
		    return empid;
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
		public void setName(String name) {
		    this.name = name;
		}
		public  String getName() {
		    return name;
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
		
		public void setDateofjoin(String dateofjoin) {
		    this.dateofjoin = dateofjoin;
		}
		
		
		public  String getDateofjoin() {
		    return dateofjoin;
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
		public void setEmptype(String emptype) {
		    this.emptype= emptype;
		}
		public  String getEmptype() {
		    return emptype;
		}
		public void setPosition (String position) {
			this.position = position;
		}
		public  String getPosition() {
		    return position;
		}
		public void setPosid (String posid) {
			this.posid = posid;
		}
		
		public  String getPosid() {
		    return posid;
		}
		
}
