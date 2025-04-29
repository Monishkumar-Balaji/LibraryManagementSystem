package exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception{
	
    public String toString() {
    		return "Incorrect UserId or Password";
    	}
}
