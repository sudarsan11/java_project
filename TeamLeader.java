import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class TeamLeader extends Members {

	
	public int id=1;
	String name,task;
	private String password;
	
	
	TeamLeader(String name,String password,Admin project,Team team) {
		
		
		this.name = name;
		this.password = password;
		toDB(project, team);
		
	}
	
	public String toString(){   //Since superclass objects access subclass data we are directly printing objects directly at team 
		
		return name+" "+id;
		
	}
	
	
	public void toDB(Admin project,Team team){
		 
		try{
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","sud1rs1n"); 
			
			
			Statement statement = con.createStatement();
		 	ResultSet resultSet = statement.executeQuery("select * from teamleader");
		 	
		 	while(resultSet.next())
		 	{
		 		id++;
		 		
		 	}
			
			
			String sql2 = "INSERT INTO teamleader " +"(Project_ID,ID,Name,Team_ID,password)"+
	                   "VALUES (?,?,?,?,?)";
	    
		    PreparedStatement myStmt2 = con.prepareStatement(sql2);
		    myStmt2.setInt(1,project.id);
		    myStmt2.setInt(2,id);
		    myStmt2.setString(3,team.memberList.get(0).toString());
		 	myStmt2.setInt(4,team.id);
		 	myStmt2.setString(5,password);
		    myStmt2.executeUpdate(); 
		    
	 	
		}
		
		catch(Exception e){e.printStackTrace();} 
	 	
		 
	} 
	

	public int login(){
		
		int id = 0;
		Scanner in = new Scanner(System.in);
		try{
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","sud1rs1n"); 
			Statement stmt = con.createStatement();
			String sql = "Select Name,password,Team_ID from teamleader";
			
			stmt.executeQuery(sql);
	        ResultSet rs = stmt.getResultSet();
	
	        String user ,pass,u=in.nextLine(),p=in.nextLine();
	        while(rs.next()){
	            user = rs.getString("Name");
	            pass = rs.getString("password");
	
	            if(user.equals(u) && pass.equals(p)){
	                
	            	id = rs.getInt("Team_ID");
	            	System.out.println("T_ID : "+id);
	            	return id;
	            }
	        }
	        
	        
	        
	     }
	        
	     catch(Exception e){e.printStackTrace();}
		
		return id;
		
	}
	
	public void assignTask(Admin project,Team team,int id){
		
		Scanner in = new Scanner(System.in);
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","sud1rs1n"); 		
			int mid=0,lid=0;

    
             
        	project.tasks(team);
        	
        	PreparedStatement stmnt = con.prepareStatement("Select ID from teamleader where Team_ID = ?");
        	stmnt.setInt(1,id);
        	ResultSet rs1 = stmnt.executeQuery();
        	

        	String sql1 = "INSERT INTO tasks " +"(Member_ID,Task,Team_ID)"+
	                   "VALUES (?,?,?)";
        
        	
        	while (rs1.next()) {
                lid = rs1.getInt("ID");
                PreparedStatement myStmt1 = con.prepareStatement(sql1);
                myStmt1.setInt(1,project.teams.get(id-1).teamMembers.get(lid).id);
                myStmt1.setString(2,team.tasks.get(lid).task);
			    myStmt1.setInt(3,id);
			    myStmt1.executeUpdate();
              
            } 
        	
        	PreparedStatement statement = con.prepareStatement("Select ID from members where Team_ID = ?");
        	statement.setInt(1,id);
        	ResultSet resultSet = statement.executeQuery();
        	
	        
        	
        	while (resultSet.next()) {
        		
                mid = resultSet.getInt("ID");
                PreparedStatement myStmt1 = con.prepareStatement(sql1);
			    myStmt1.setInt(1,project.teams.get(id-1).teamMembers.get(mid).id);
			    myStmt1.setString(2,team.tasks.get(mid).task);
			    myStmt1.setInt(3,id);
			    myStmt1.executeUpdate(); 
        	}
        
		}
		
		catch(Exception e){}
		

	
	}

	
		
	public static void main(String[] args) {
		

	}

}
