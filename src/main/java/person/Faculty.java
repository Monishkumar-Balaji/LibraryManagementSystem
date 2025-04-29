package person;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Faculty extends Person {
	
	public Faculty(String userId,String password,String name,String email,String phoneNo,String userType,float outstanding){
		super(userId,password,name,email,phoneNo,userType,outstanding);
	}

	public void requestBooks(Person person,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		String requestBookName=request.getParameter("requestBookName");
		String requestBookAuthor=request.getParameter("requestBookAuthor");
		int publicationYear=Integer.parseInt(request.getParameter("publicationYear"));
		
		
		String sql = "INSERT INTO requestBooks (facultyId,bookId,authorName,publicationYear) values(?,?,?,?) ";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement PreparedStatement = connection.prepareStatement(sql)) {

            PreparedStatement.setString(1, person.userId);
            PreparedStatement.setString(2, requestBookName);
            PreparedStatement.setString(3, requestBookAuthor);
            PreparedStatement.setInt(4, publicationYear);
            
            int rowsInserted = PreparedStatement.executeUpdate();
            if (rowsInserted > 0) 
                response.getWriter().print("\nRequest Sent Successfully!");
            	response.setContentType("text/html");
            	RequestDispatcher rd = request.getRequestDispatcher("/FacultyMenu.jsp");
            	rd.include(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


	
