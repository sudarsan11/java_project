import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
	
    int id=1;
    String teamName,projectName;
	long endDate,startDate;
	ArrayList<Team> teams = new ArrayList<Team>();
	
    
	public static void main(String args[]){
		
		Scanner in = new Scanner(System.in);	
		Admin project = null;
		Tasks tasks = null;
		Team team = null;
		int choice=0;
		
	do{	
		
		System.out.println("Enter a choice\n1:Create team\n2:Show team\n3:Assign Tasks");
	    choice = in.nextInt();
		in.nextLine();
		
		switch(choice){
		
			case 1:
				
				project = new Admin();
				System.out.println("Enter team name");
				project.teamName = in.nextLine();
				System.out.println("Enter project name");
				project.projectName = in.nextLine();
				System.out.println("Enter start date");
				project.startDate = in.nextLong();
				System.out.println("Enter end date");
				project.endDate = in.nextLong();
				
				team = new Team(project);
				project.teams.add(team);
				project.createTeam(team,project);
			break;
			
			case 2:
				project = new Admin();
				project.showTeam();
				break;
				
			case 3:
				
				System.out.println("Login to assign tasks");
				TeamLeader tl = (TeamLeader) team.teamMembers.get(0);
				int id = tl.login();
				project.teams.add(team);
				tl.assignTask(project,team,id);				
			default:
				
				
		
		}
		
	}while(choice>0);	
		
		
		
	}
	
	
	
	public void createTeam(Team team,Admin project){
	
		Scanner in = new Scanner(System.in);
		
		System.out.println("Enter leader name");
		team.memberList.add(in.nextLine());
		
		System.out.println("Enter password");
		String password = in.nextLine();
		
		System.out.println("Enter members name");
		for(int i=1;i<5;i++){
			team.memberList.add(in.nextLine());
		}
		
		
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","sud1rs1n"); 
			
			//To assign unique ID
			Statement statement = con.createStatement();
		 	ResultSet resultSet = statement.executeQuery("select * from admin");
		 	
		 	while(resultSet.next())
		 	{
		 		project.id++;
		 		team.id++;
		 	}  
		 	
		 	//Project is created   
		    String sql1 = "INSERT INTO project " +"(Project_ID,Project_Name,Start_Date,End_Date)"+
		                   "VALUES (?,?,?,?)";
		    
		    PreparedStatement myStmt1 = con.prepareStatement(sql1);
		    myStmt1.setInt(1,project.id);
		    myStmt1.setString(2,team.projectName);
		    myStmt1.setDate(3,new java.sql.Date(project.startDate));
		 	myStmt1.setDate(4,new java.sql.Date(project.endDate));
		    myStmt1.executeUpdate(); 
			
	 	 	
		 	//Team is stored DB
		    String sql = "INSERT INTO admin " +"(Project_ID,Project_Name,Team_Name,Team_ID)"+
		                   "VALUES (?,?,?,?)";
		    
		    
		    PreparedStatement myStmt = con.prepareStatement(sql);
		    myStmt.setInt(1,project.id);
		    myStmt.setString(2,team.projectName);
		    myStmt.setString(3,team.teamName);
		 	myStmt.setInt(4,team.id);
		    myStmt.executeUpdate();
		    
	 }
	 
	 catch(Exception e){
		e.printStackTrace(); 
	 }  
		
	//Creating leaders and members	
	TeamLeader tl = new TeamLeader(team.memberList.get(0),password,project,team);
	Members m1 = new Members(team.memberList.get(1),project, team);
	Members m2 = new Members(team.memberList.get(2),project, team);
	Members m3 = new Members(team.memberList.get(3),project, team);
	Members m4 = new Members(team.memberList.get(4),project, team);
	
	team.addMembers(tl);
	team.addMembers(m1);
	team.addMembers(m2);
	team.addMembers(m3);
	team.addMembers(m4);
	
	
	}
	
	public void showTeam(){
		 
		try{

			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/company","root","sud1rs1n");  
		    Statement st =  con.createStatement();
		    String sql = ("SELECT * from admin");
		    ResultSet rs = st.executeQuery(sql);
		    
		 	while(rs.next()) { 
			 
			 System.out.println("Project ID   : "+rs.getInt("Project_ID"));
			 System.out.println("Project Name : "+rs.getString("Project_Name"));
			 System.out.println("Team Name    : "+rs.getString("Team_Name"));
			 System.out.println("Team ID      : "+rs.getInt("Team_ID")+"\n");
		 
		 	}


		}
		
		catch(Exception e){e.printStackTrace();}
		
		
	 }
	 
	 public void tasks(Team team){
		
		Scanner in = new Scanner(System.in); 
		
		System.out.println("Enter the tasks");
	 	Tasks t = new Tasks(in.nextLine());
		Tasks t1 = new Tasks(in.nextLine());
		Tasks t2 = new Tasks(in.nextLine());
		Tasks t3 = new Tasks(in.nextLine());
		Tasks t4 = new Tasks(in.nextLine());
		
		team.tasks.add(t);
		team.tasks.add(t1);
		team.tasks.add(t2);
		team.tasks.add(t3);
		team.tasks.add(t4);
		
	 }
	
	 public void status(Team team){
		 
		 Scanner in = new Scanner(System.in);
		 System.out.println("Enter the tasks");
		 		 
		 
	 }
	
}
