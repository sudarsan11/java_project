import java.util.ArrayList;
import java.util.List;


public class Team {

	ArrayList<String> memberList = new ArrayList<String>();
	ArrayList<Tasks> tasks = new ArrayList<Tasks>();
	
	String teamName,projectName;
    public int id=1;
    
    
	Team (Admin project){
		
		this.teamName = project.teamName ;
		this.projectName = project.projectName ;
		
	}
	
	 List<Members> teamMembers = new ArrayList<Members>();
	 
	 public void addMembers(Members member){
		 
		 teamMembers.add(member);
		 
	 }
	 
	 	
	 public void addTasks(Tasks task){
		 
		 tasks.add(task);
	 }
	 
	 public static void main(String[] args) {
			
			
			
	}

}
