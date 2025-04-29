package person;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/FacultyS")
public class FacultyS extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public FacultyS() {super();}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
int menuChoice = Integer.parseInt(request.getParameter("menuChoice"));
		
		HttpSession session = request.getSession();
		Faculty faculty = (Faculty) session.getAttribute("person");
		
		switch(menuChoice) {
		case 1:
			request.getRequestDispatcher("/BookDisplayMenu.jsp").forward(request, response);
			return;
		case 2:
			request.getRequestDispatcher("/BorrowBooks.jsp").forward(request,response); 
			return;
		case 3:
			request.getRequestDispatcher("/ReturnBooks.jsp").forward(request, response);
			return;
		case 4:
			request.getRequestDispatcher("/EditProfile.jsp").forward(request, response);
			return;
		case 5:
			faculty.viewBorrowedBooks(faculty, request, response);
			return;
		case 6:
			request.getRequestDispatcher("/RequestBooks.jsp").forward(request, response);
			return;
		case 7:
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int choice = Integer.parseInt(request.getParameter("choice"));
		
		HttpSession session = request.getSession();
		Faculty faculty = (Faculty) session.getAttribute("person");
		
		switch(choice) {
		case 2:
			faculty.borrowBook(faculty,request,response);
			return;
		case 3:
			faculty.returnBook(faculty,request,response);
			return;
		case 4:
			faculty.editProfile(faculty,request, response);
			return;
		case 6:
			faculty.requestBooks(faculty,request,response);
		}
	}

}
