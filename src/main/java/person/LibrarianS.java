package person;
import java.sql.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@SuppressWarnings("unused")
@WebServlet("/LibrarianS")
public class LibrarianS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LibrarianS() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int choice = Integer.parseInt(request.getParameter("menuChoice"));
		
		HttpSession session = request.getSession(false);
		Librarian librarian = (Librarian) session.getAttribute("person");
		
		/*Cookie[] cookies = request.getCookies();
		if (cookies != null) {
		    for (Cookie cookie : cookies) {
		        System.out.println("Cookie: " + cookie.getName() + " = " + cookie.getValue());
		    }
		} else {
		    System.out.println("No cookies found.");
		}*/

		
		switch(choice) {
		case 1:
			request.getRequestDispatcher("/LibrarianAddBooks.jsp").forward(request,response);
			return;
		case 2:
			request.getRequestDispatcher("/LibrarianRemoveBooks.jsp").forward(request, response);
			return;
		case 3:
			System.out.println(librarian);
			librarian.viewStock(request,response);
			break;
		case 4:
			librarian.viewBookRequest(request,response);
			break;
		case 5:
			librarian.viewBorrowedBooks(request,response);
			response.getWriter().print("choice 5");
			break;
		case 6:
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return;
		case 7:
			librarian.addBooks(request, response);
			return;
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String removeChoice = request.getParameter("removeChoice");
        String inputValue = request.getParameter("inputValue");
        
        HttpSession session = request.getSession();
		Librarian librarian = (Librarian) session.getAttribute("person");

        String message;
        switch (removeChoice) {
            case "1": // Remove by Book ID
                message = librarian.removeBookById(inputValue);
                break;
            case "2": // Remove by Book Title
                message = librarian.removeBookByTitle(inputValue);
                break;
            case "3": // Remove by Author Name
                message = librarian.removeBooksByAuthor(inputValue);
                break;
            default:
                message = "Invalid choice.";
        }
        RequestDispatcher rd;
        response.setContentType("text/html");
        response.getWriter().print("<p style='color:red; text-align:top; margin:15 px; '>Book removed successfully.</p>");
        rd =request.getRequestDispatcher("/LibrarianRemoveBooks.jsp");
        rd.include(request, response);
    }

}


       


