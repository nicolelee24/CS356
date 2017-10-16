import java.util.ArrayList;

/*
 * 	Abstract question class
 * 	simply contains the possible choices to a question
 */
public abstract class Question {
	
	protected ArrayList<String> choices;
	
	public Question(ArrayList<String> choices) {
		this.choices = choices;
	}

	public ArrayList<String> getChoices() {
		return choices;
	}
	
	// method to check if a response is one of the choices
	// implemented by subclasses
	public abstract boolean checkResponse(ArrayList<String> response); 
}
