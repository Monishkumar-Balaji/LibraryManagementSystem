package exception;

@SuppressWarnings("serial")
public class BookNotFoundException extends Exception{

	public String toString() {
		
		return "Book not found in database.";
	}
}
