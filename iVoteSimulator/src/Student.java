import java.util.ArrayList;

/*
 * 	Student class, contains an ID and their response
 */
public class Student {
	
	private static int num_of_students = 0;
	private String id;
	private ArrayList<String> response;
	
	public Student (ArrayList<String> response) {
		// use count of how many students as Unique ID
		// in reality the id could be set to the student's school ID
		num_of_students++;
		this.id = "" + num_of_students;
		this.response = response;
	}
	
	public Student() {
		num_of_students++;
		this.id = "" + num_of_students;
	}

	public String getId() {
		return id;
	}
	
	public void setResponse(ArrayList<String> response) {
		this.response = response;
	}
	
	public ArrayList<String> submitResponse() {
		return response;
	}	
	
}
