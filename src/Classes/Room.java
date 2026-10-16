package Classes;

public class Room {		    
	    private int roomid;
		private String roomname;
		private int floor;	
		private String roomtype;
		private String status;	
		
		public Room( int roomid, String roomname,  String roomtype ) 
		  { 
		  this.roomid = roomid; 
		  this.roomname = roomname; 
		  this.roomtype = roomtype; 
		  } 	
		
		public Room( int roomid, String roomname ) 
		  { 
		  this.roomid = roomid; 
		  this.roomname = roomname; 		  
		  } 	
		
		 public Room(String roomName)
		    {
		        this.roomname = roomname;
		    }
		
	  @Override
		  public String toString() 
		  { 		
		     	return roomname; 		
		  }    
	   
       public int getRoomid() {
	        return roomid;
	    }

	    public void setRoomid(int roomid) {
	        this.roomid = roomid;
	    }

	    public String getRoomname() {
	        return roomname;
	    }

	    public void setRoomname(String roomname) {
	        this.roomname = roomname;
	    }
	  
	    public void setRoomtype(String roomtype) {
	        this.roomtype = roomtype;
	    }

	    public String getRoomtype() {
	        return roomtype;
	    }

	    public void setStatus(String status) {
	        this.status = status;
	    }

	    public String getStatus() {
	        return status;
	    }
}
