import java.util.ArrayList;
import java.util.Random;

/*
 * 	Driver to test the iVoteService
 * 	Both Single Choice and Multiple Choice questions are tested once
 * 	Random number of students are selected and generated
 * 	Random response are submitted to the iVoteService
 */
public class iVoteDriver {

	public static void main(String[] args) {
		
		// create and initialize single choices
		// can be True/False, Yes/No, Right/Wrong, etc.
		// True/False will be used in this demo, can be changed to any string
		ArrayList<String> singleChoice = new ArrayList<String>(2);
		singleChoice.add("True");
		singleChoice.add("False");
		
		// create and initialize multiple choices
		// can be A/B/C/D/etc., 1/2/3/etc., Likert scale, etc.
		// ABCDE will be used in this demo, can be changed to any string
		// Amount of choice can also be changed, sticking with 4 for simplicity
		ArrayList<String> multipleChoice = new ArrayList<String>(4);
		multipleChoice.add("A");
		multipleChoice.add("B");
		multipleChoice.add("C");
		multipleChoice.add("D");
		
		// create the questions
		Question singleChoiceQ = new SingleChoice(singleChoice);
		Question multipleChoiceQ = new MultipleChoice(multipleChoice);
		
		System.out.println("Starting simulation of Single Choice.");
		// simulate students answering the single choice question
		simulation(singleChoiceQ, "Single");
		System.out.println("Simulation of Single Choice completed.\n");
		
		System.out.println("Starting simulation of Multiple Choice");
		// simulate students answering the multiple choice question
		simulation(multipleChoiceQ, "Multiple");
		System.out.println("Simulation of Multiple Choice completed");

	}
	
	/*
	 * 	This method simulates randomly creating a number of students 
	 * 	and having them submit a response to the iVoteService.
	 * 	iVoteService will then print out the statistics of the responses
	 */
	private static void simulation(Question question, String type) {
		Random rand = new Random();
		int rounds = 0;
		
		// initialize the iVoteService
		iVoteService iVote = new iVoteService(question);

		// randomly select a number of students
		// typical class size is between 20 to 40 students
		int num_of_students = rand.nextInt(21) + 20;

		// create student and responses
		for (int i = 0; i < num_of_students; i++) {
			// create a student
			Student student = new Student();
			
			// simulate a student having one or more submissions
			// only last submission is saved/submitted
			// setting number of submissions between 1 and 4
			rounds = rand.nextInt(4) + 1;
			for (int j = 0; j < rounds; j++) {
				// make the student submit an answer
				simulateResponse(student, question, type);
				
				// send the student's response to the iVoteService
				iVote.acceptResponse(student);
			}
			
		}
		
		// submission is over, calculate the statistics
		iVote.computeStatistics();
	}

	/*
	 *  make the student randomly submit a response
	 * 	single choice questions allow only one choice to be selected
	 *  multiple choice questions can allow one or more choices to be selected
	 */
	private static void simulateResponse(Student student, Question question, String type) {
		Random rand = new Random();
		ArrayList<String> response = new ArrayList<String>();
		
		if (type == "Single") {
			// select a random choice of either "True" or "False" from the question choices
			response.add(question.getChoices().get(rand.nextInt(question.getChoices().size())));
			
		} else {
			// type is multiple choice question
			
			// choose how many choices to select
			int num_of_choices = rand.nextInt(question.getChoices().size()) + 1;
			int picked = 0;
			int index = 0;
			
			// randomly select answers until number of choices has been reached
			// duplicate choices are not allowed
			while (picked < num_of_choices) {
				index = rand.nextInt(question.getChoices().size());
				
				// check if selected choice is already in the response
				if (!response.contains(question.getChoices().get(index))) {
					response.add(question.getChoices().get(index));
					picked++;
				}
			}
		}
		
		// make sure response is valid
		if (question.checkResponse(response)) {
			// student submits the response
			student.setResponse(response);
		} else {
			// response is invalid, try again
			simulateResponse(student, question, type);
		}

	}

}
