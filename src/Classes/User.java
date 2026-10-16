package Classes;

public class User {

    private int uid;
    private String lname; 
    private String fname; 
    private String phone;
    private String email;   
    private String country;
    private int cid;
    private String street;
    private String postcode;
    private String role;
    
    public User() {
		
  	}
    
    public User (int uid, String fname, String lname, String role, String phone, String email,
    		String country,  int cid, String street,  String postcode) {
    	this.uid = uid;
    	this.fname = fname;  
    	this.lname = lname; 
    	this.role = role;
    	this.phone = phone;
    	this.email = email;
    	this.country = country;
    	this.cid = cid;
    	this.street = street;
    	this.postcode = postcode;
    }
    public User (int uid, String fname, String lname, String role) {
    	this.uid = uid;
    	this.fname = fname;  
    	this.lname = lname; 
    	this.role = role;    
    }
    public User (int uid, String fname, String lname) {
    	this.uid = uid;
    	this.fname = fname;   
    	this.lname = lname;
    }
  

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String name) {
        this.fname = fname;
    }
    
    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    @Override
	  public String toString() 
	  { 	
	     	return fname + " "	 + lname + " " + role;
	  } 
}
