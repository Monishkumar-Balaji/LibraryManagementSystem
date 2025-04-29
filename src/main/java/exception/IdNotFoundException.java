package exception;

@SuppressWarnings("serial")
public class IdNotFoundException extends Exception{

	public String toString() {
		
		return "Book Id not found in the database";
	}
}
