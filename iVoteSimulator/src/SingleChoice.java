import java.util.ArrayList;

/*
 * 	Single Choice question inherits properties of a Question
 * 	checkResponse is implemented here
 */
public class SingleChoice extends Question {

	public SingleChoice(ArrayList<String> choices) {
		super(choices);
	}

	// checks to make sure a response is valid
	public boolean checkResponse(ArrayList<String> response) {
		// check if only one response was submitted
		if (response.size() > 1) {
			return false;
		}
		
		// check if response is among the choices
		if (!choices.contains(response.get(0))) {
			return false;
		}
		
		// response is valid
		return true;
	}

}
