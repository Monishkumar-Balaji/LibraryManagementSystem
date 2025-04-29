package login;
import person.*;
import exception.*;


import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginS")
public class LoginS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public LoginS() { super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/userdatabase"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = "12345678"; 
    

    public static boolean authenticateUser(String userId, String password) throws UserNotFoundException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
    
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "SELECT * FROM Users WHERE userId = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            stmt.setString(2, password);
            
            rs = stmt.executeQuery();

            if(rs.next()) {
            	return true;
            }
            else {
            	throw new UserNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static Person storeDetailsAfterLogin(String userId){
    	
    	Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
           
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "SELECT password,name,email,phoneNo,userType,outstanding FROM Users WHERE userId = ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            
            rs = stmt.executeQuery();
            
            if(!rs.isBeforeFirst()) {
            	System.out.println("No Details found for the given user.");
                return null;
            }
            
            while(rs.next()) {
            	String password=rs.getString("password");
	    		String name=rs.getString("name");
	    		String email=rs.getString("email");
	    		String phoneNo=rs.getString("phoneNo");
	    		String userType=rs.getString("userType");
	    		float outstanding = rs.getFloat("outstanding");
	            System.out.println(name+userType);
	            
	            if(userType.equals("student")) {
	            	return new Student(userId,password,name,email,phoneNo,userType,outstanding);
	            }
	            else if (userType.equals("faculty")){
	            	return new Faculty(userId,password,name,email,phoneNo,userType,outstanding);
            	}
	            else if(userType.equals("librarian")) {
	            	return new Librarian(userId,password,name,email,phoneNo,userType,outstanding);
	            }
            	System.out.println("No Details found for the given user.");
            	return null;
            }
        } catch (SQLException e) {
        	System.out.println("No Details found for the given user.");
            e.printStackTrace();
            return null;
            
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
		return null;
    }
    
   
    
    }




