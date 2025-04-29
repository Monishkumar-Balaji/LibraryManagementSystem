package login;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet("/ChangePasswordS")
public class ChangePasswordS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/userdatabase"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "12345678"; 
    
    public ChangePasswordS() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userId = request.getParameter("userId");
		
		String newPassword=request.getParameter("newPassword");
		String newPasswordReEnter=request.getParameter("newPasswordReEnter");
		
    	if(!newPassword.equals(newPasswordReEnter)) {
    		RequestDispatcher rd;
    		response.setContentType("text/html");
    		response.getWriter().print("<h3>Wrong Re-Password Entered</h3>");
    		rd=request.getRequestDispatcher("/ChangePassword.jsp");
    		rd.include(request, response);
    		return;
    	}
    	
    	String sql = "UPDATE users SET password=? WHERE userId = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, userId);  

            // Execute update
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                response.getWriter().print("<h3>Password updated Succesfully</h3>");
                response.setContentType("text/html");
//              response.getWriter().print("<br><a href=Login.jsp>Go to Login Page</a>");
                RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
                rd.include(request, response);
            } else {
            	response.getWriter().print("<h3>Failed to update profile. Please try again.</h3>");
            }
          } catch (SQLException e) {
	            e.printStackTrace();
	        }
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
