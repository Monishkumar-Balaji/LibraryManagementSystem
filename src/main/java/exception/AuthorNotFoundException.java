package exception;

@SuppressWarnings("serial")
public class AuthorNotFoundException extends Exception {
	
	public String toString() {
		
		return "Author name not found in database.";
	}
}
