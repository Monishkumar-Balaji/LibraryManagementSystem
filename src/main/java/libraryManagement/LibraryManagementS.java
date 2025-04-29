package libraryManagement;
import login.LoginS;
import exception.UserNotFoundException;
import person.*;

import java.util.Scanner;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

class LibraryManagement {
	static Scanner scan = new Scanner(System.in);
	
	private String userId;
	
	@SuppressWarnings("unused")
	public boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		//sample : aarushireddyozt ogfaculty
		//krishnanairbf7 admin123
		//mayankms needforspeed
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");
		String forgetPass =request.getParameter("forgetPass");
		
		if(forgetPass!=null) {
			request.setAttribute("userId", userId);
			request.getRequestDispatcher("/ChangePassword.jsp").forward(request,response);
		}
				
		try {	    
			if(LoginS.authenticateUser(userId,password)) {
				response.getWriter().append("Login successfull.");
			 	this.userId=userId;
				return true;
			}
		}catch(UserNotFoundException e) {
			try {
				RequestDispatcher rd;
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print("<h4 style='text-align: top;padding:15 px; color: red; font-family: Arial, sans-serif;margin:20 px;align-items:top;'>Login Unsuccessful</h4>");
				rd=request.getRequestDispatcher("/Login.jsp");
				rd.include(request,response);
				response.getWriter().print("<form action='LibraryManagementS' method='post' style='text-align: center; margin-top: 10px;'>");
				response.getWriter().print("<button name='forgetPass' value ='1' style='padding: 10px 20px; font-size: 14px; color: white; background-color: #ff5722;");
				response.getWriter().print("border: none; border-radius: 5px; cursor: pointer; transition: background-color 0.3s;display:block; margin:10px;'>Forget Password</button>");
				response.getWriter().print("</form>");
				return false;
			} catch (ServletException | IOException e1) {
				e1.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	   	return false;	
	   	
	}
	
	@SuppressWarnings("unused")
	public int loginType(Person person) {
	
		Object commonObj = null;
		
		if(person instanceof Student) {
			commonObj = (Student)person;
			return 0;
		}
		else if(person instanceof Faculty) {
			commonObj = (Faculty)person;
			return 2;
		}
		else if(person instanceof Librarian) {
			commonObj = (Librarian)person;
			return 1;
		}
		else {
			System.out.println("Error while passing object to menu method.");
			return -1;
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

@WebServlet("/LibraryManagementS")
public class LibraryManagementS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LibraryManagementS() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		LibraryManagement library = new LibraryManagement();
		
		if(library.checkLogin(request, response)) {
			
			Person person = LoginS.storeDetailsAfterLogin(library.getUserId());
			
			if(person==null)
				System.out.println("person is null.");
			
			HttpSession session = request.getSession();
			session.setAttribute("person", person);
			
			session.setAttribute("name", person.getName());
			session.setAttribute("email", person.getEmail());
			session.setAttribute("phoneNo", person.getPhoneNo());
			session.setAttribute("userType", person.getUserType());
			session.setAttribute("person", person);
			session.setAttribute("outstanding", person.getOutstanding());
			
			if(library.loginType(person)==1) {  //Librarian Login
				System.out.println(person);
				request.getRequestDispatcher("/LibrarianMenu.jsp").forward(request,response);
			}
			else if(library.loginType(person)==0) { //Student Login
				request.getRequestDispatcher("/StudentMenu.jsp").forward(request, response);
			}
			else if(library.loginType(person)==2) {//Faculty Login
				request.getRequestDispatcher("/FacultyMenu.jsp").forward(request, response);
			}
			}
	}

}
