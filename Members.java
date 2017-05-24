import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Members {
	
	
	public int id = 1;
	static int i=0 ;
	String name,task,status;
	
	public String toString(){
		
		return name+" "+id;
		
	}
	
	Members(String name,Admin project,Team team){

		this(); 				
		this.name = name;
		i++;
		toDB(project, team);
	}
	
	Members(){
		
		
	}

	public void toDB(Admin project,Team team){
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","sud1rs1n"); 
			
			Statement statement = con.createStatement();
		 	ResultSet resultSet = statement.executeQuery("select * from members");
		 	
		 	while(resultSet.next())
		 	{
		 		id++;	
		 		
		 	}
		 	
			String sql3 = "INSERT INTO members " +"(ID,Name,Team_ID,Project_ID)"+
	                   "VALUES (?,?,?,?)";
	    
		    PreparedStatement myStmt3 = con.prepareStatement(sql3);
		    myStmt3.setInt(1,id);
		    if(i>=5)
		    	i=0;
		    myStmt3.setString(2,team.memberList.get(i));
		    myStmt3.setInt(3,team.id);
		 	myStmt3.setInt(4,project.id);
		    myStmt3.executeUpdate(); 
		    
		    
		}
		
		catch(Exception e){e.printStackTrace();} 
	 	
		
		
	}
	
	
	
	public static void main(String[] args) {
	
	
	}
	

	 
	
	

}
