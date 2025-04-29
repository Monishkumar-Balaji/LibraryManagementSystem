
package person;
import java.io.IOException;
import java.io.Serializable;
import java.sql.*;

import java.util.Scanner;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Librarian extends Person implements Serializable{
	
	public static Scanner scan = new Scanner(System.in);
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/userdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";

	
	public Librarian(String userId,String password,String name,String email,String phoneNo,String userType,float outstanding){
		super(userId,password,name,email,phoneNo,userType,outstanding);
	}
	
	public void addBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		
		String idToAdd = request.getParameter("idToAdd");
		String bookToAdd = request.getParameter("bookToAdd");
		String authorToAdd = request.getParameter("authorToAdd");
		int yearToAdd = Integer.parseInt(request.getParameter("yearToAdd"));
		int categoryIdToAdd = Integer.parseInt(request.getParameter("categoryIdToAdd"));
		int stockToAdd = Integer.parseInt(request.getParameter("stockToAdd"));
		
		String sql = "INSERT INTO books(bookId,bookTitle,authorName,publicationYear,categoryId,stocks) VALUES(?,?,?,?,?,?);";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        	PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
        		
        	preparedStatement.setString(1,idToAdd);
        	preparedStatement.setString(2,bookToAdd);
        	preparedStatement.setString(3,authorToAdd);
        	preparedStatement.setInt(4,yearToAdd);
        	preparedStatement.setInt(5,categoryIdToAdd);
        	preparedStatement.setInt(6,stockToAdd);
        	
        	RequestDispatcher rd;
        	response.setContentType("text/html");
        	int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                response.getWriter().print("<p style='text-align:top;color:red;'>Book Added successfully.</p>");
                rd=request.getRequestDispatcher("/LibrarianAddBooks.jsp");
                rd.include(request, response);
            } else {
                System.out.println("Failed to add the book.");
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
        }
     }
	
	public String removeBookById(String idToRemove) {
        String sql = "DELETE FROM books WHERE bookId = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, idToRemove);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? "Book removed successfully." : "No book found with the given ID.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error removing book: " + e.getMessage();
        }
    }

	public String removeBookByTitle(String titleToRemove) {
        String sql = "DELETE FROM books WHERE bookTitle = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, titleToRemove);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0 ? "Book removed successfully." : "No book found with the given title.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error removing book: " + e.getMessage();
        }
    }

	public String removeBooksByAuthor(String authorToRemove) {
        String sql = "DELETE FROM books WHERE authorName = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, authorToRemove);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0
                    ? rowsAffected + " book(s) by " + authorToRemove + " removed successfully."
                    : "No books found for the given author.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error removing books: " + e.getMessage();
        }
    }


	public void viewStock(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String sql = "SELECT bookId, bookTitle, authorName, publicationYear, categoryId, stocks FROM books;";
	    List<Map<String, Object>> books = new ArrayList<>();

	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	         PreparedStatement preparedStatement = connection.prepareStatement(sql);
	         ResultSet resultSet = preparedStatement.executeQuery()) {

	        if (!resultSet.isBeforeFirst()) { // Check if the result set is empty
	            request.setAttribute("message", "No books found in the library.");
	        } else {
	            while (resultSet.next()) {
	                Map<String, Object> book = new HashMap<>();
	                book.put("bookId", resultSet.getInt("bookId"));
	                book.put("bookTitle", resultSet.getString("bookTitle"));
	                book.put("authorName", resultSet.getString("authorName"));
	                book.put("publicationYear", resultSet.getInt("publicationYear"));
	                book.put("categoryId", resultSet.getInt("categoryId"));
	                book.put("stock", resultSet.getInt("stocks"));

	                // Optionally map categoryId to a category name
	                String categoryName = categoryFromId(resultSet.getInt("categoryId"));
	                book.put("categoryName", categoryName);

	                books.add(book);
	            }
	            request.setAttribute("books", books);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Error fetching book data: " + e.getMessage());
	    }

	    // Forward the request to the JSP
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/LibrarianViewStocks.jsp");
	    dispatcher.forward(request, response);
	}

	// Example method to map categoryId to categoryName
	private String categoryFromId(int categoryId) {
	    switch (categoryId) {
	        case 1: return "Fiction";
	        case 2: return "Non-Fiction";
	        case 3: return "Science";
	        default: return "Unknown";
	    }
	}


	public void viewBookRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String sql = "SELECT facultyId, bookId, authorName, publicationYear FROM requestBooks;";
	    List<Map<String, Object>> bookRequests = new ArrayList<>();

	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	         PreparedStatement preparedStatement = connection.prepareStatement(sql);
	         ResultSet resultSet = preparedStatement.executeQuery()) {

	        if (!resultSet.isBeforeFirst()) { // Check if the result set is empty
	            request.setAttribute("message", "No book requests found.");
	        } else {
	            while (resultSet.next()) {
	                Map<String, Object> bookRequest = new HashMap<>();
	                bookRequest.put("facultyId", resultSet.getString("facultyId"));
	                bookRequest.put("bookId", resultSet.getString("bookId"));
	                bookRequest.put("authorName", resultSet.getString("authorName"));
	                bookRequest.put("publicationYear", resultSet.getInt("publicationYear"));

	                bookRequests.add(bookRequest);
	            }
	            request.setAttribute("bookRequests", bookRequests);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Error fetching book requests: " + e.getMessage());
	    }

	    // Forward to JSP
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/LibrarianViewRequest.jsp");
	    dispatcher.forward(request, response);
	}


	public void viewBorrowedBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String sql = "SELECT * FROM borrowedBooks";
	    List<Map<String, Object>> borrowedBooks = new ArrayList<>();

	    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	         Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(sql)) {

	        if (!resultSet.isBeforeFirst()) { // Check if the result set is empty
	            request.setAttribute("message", "No borrowed books found.");
	        } else {
	            while (resultSet.next()) {
	                Map<String, Object> book = new HashMap<>();
	                book.put("serialNo", resultSet.getInt("serialNo"));
	                book.put("userId", resultSet.getString("userId"));
	                book.put("userType", resultSet.getString("userType"));
	                book.put("email", resultSet.getString("email"));
	                book.put("phoneNo", resultSet.getString("phoneNo"));
	                book.put("borrowedTime", resultSet.getTimestamp("borrowedTime").toString());
	                book.put("bookId", resultSet.getInt("bookId"));
	                book.put("bookTitle", resultSet.getString("bookTitle"));
	                book.put("authorName", resultSet.getString("authorName"));
	                book.put("publicationYear", resultSet.getInt("publicationYear"));
	                book.put("categoryId", resultSet.getInt("categoryId"));

	                borrowedBooks.add(book);
	            }
	            request.setAttribute("borrowedBooks", borrowedBooks);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        request.setAttribute("error", "Error fetching borrowed books: " + e.getMessage());
	    }

	    // Forward to JSP
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/LibrarianViewBorrowedBooks.jsp");
	    dispatcher.forward(request, response);
	}

		
	}

