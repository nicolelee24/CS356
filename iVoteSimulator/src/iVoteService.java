import java.util.ArrayList;

/*
 * 	This class handles student submissions and
 * 	generates the results.
 */
public class iVoteService {
	private ArrayList<Student> students;
	private int[] answers;
	private Question question;
	
	public iVoteService(Question question) {
		students = new ArrayList<Student>();
		answers = new int[question.getChoices().size()];
		this.question = question;
		
	}
	
	/*
	 * 	Accepts a student's response
	 * 	Since multiple submissions are allowed, have to check if
	 * 	student has submitted a response before and update it
	 */
	public void acceptResponse(Student student) {
		// check to see if the student is new
		if (!students.contains(student)) {
			students.add(student);
		} else {
		// student must already have submitted a response, update it
			int index = students.indexOf(student);
			students.set(index, student);
		}
	}
	
	/*
	 * 	Method to gather all the student's response and
	 * 	count how many chose which choices.
	 */
	public void computeStatistics() {
		int index = 0;
		
		// iterate through all the students
		for (int i = 0; i < students.size(); i++) {
			// iterate through a student's response
			for (int j = 0; j < students.get(i).submitResponse().size(); j++) {
				// increment the choice the student made
				index = question.getChoices().indexOf(students.get(i).submitResponse().get(j));
				answers[index]++;
			}
		}
		
		printStatistics();
	}

	// Method to print the results to console.
	private void printStatistics() {
		System.out.println("The number of students that voted were: " + students.size());
		System.out.println("The results are . . . ");
		
		for (int i = 0; i < answers.length; i++) {
			System.out.println("   " + question.getChoices().get(i) + ": " + answers[i]);
		}
	}
}
