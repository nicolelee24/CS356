import java.util.ArrayList;

/*
 * 	Multiple Choice question inherits properties of a Question
 * 	checkResponse is implemented here
 */
public class MultipleChoice extends Question {

	public MultipleChoice(ArrayList<String> choices) {
		super(choices);
	}

	// checks to make sure a response is among the choices
	public boolean checkResponse(ArrayList<String> response) {
		for (int i = 0; i < response.size(); i++) {
			if (!choices.contains(response.get(i))) {
				return false;
			}
		} 
		
		// response is valid
		return true;
	}

}
