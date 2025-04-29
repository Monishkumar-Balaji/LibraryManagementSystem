package booksDisplay;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/BookDisplayS")
public class BookDisplayS extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BookDisplayS() {super(); }
    
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/userdatabase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345678";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String choiceWhileBookSearch= request.getParameter("choiceWhileBookSearch");
		String customChoice = request.getParameter("customChoice");
		
		if(choiceWhileBookSearch != null)
			showBooksMenu(request,response,Integer.parseInt(choiceWhileBookSearch));
		if(customChoice != null)
			try {
				String currentPage = request.getRequestURI();
				HttpSession session = request.getSession();
				session.setAttribute("previousPage", currentPage);
				
				searchBooks(request,response);
			} catch (ServletException | IOException | BookNotFoundException e) {
				
				e.printStackTrace();
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	 public static void showBooksMenu(HttpServletRequest request, HttpServletResponse response,int choiceWhileBookSearch) throws ServletException, IOException {

	        if (choiceWhileBookSearch <= 5) {
	            int categoryId = choiceWhileBookSearch;
	            displayBooksByCategory(request,response,categoryId);
	        } else if (choiceWhileBookSearch == 6) {
	        	request.getRequestDispatcher("/CustomSearchBook.jsp").forward(request,response);
	        }
	    }
	 
	 public static void displayBooksByCategory(HttpServletRequest request, HttpServletResponse response,int categoryId) {
		 
		 List<Map<String, Object>> books;
		try {
			books = getBooksByCategory(categoryId);

         if (books.isEmpty()) {
             request.setAttribute("message", "No books found in the selected category.");
         } else {
             request.setAttribute("books", books);
         }

         RequestDispatcher dispatcher = request.getRequestDispatcher("/ViewBooksByCategory.jsp");
         dispatcher.forward(request, response);

		}  catch (ServletException e) {
		e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}	
	 }

 private static List<Map<String, Object>> getBooksByCategory(int categoryId) {
	 
     List<Map<String, Object>> books = new ArrayList<>();
     String sql = "SELECT bookTitle, authorName, publicationYear, stocks FROM Books WHERE categoryId = ?";

     try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
          PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

         preparedStatement.setInt(1, categoryId);
         ResultSet resultSet = preparedStatement.executeQuery();

         while (resultSet.next()) {
             Map<String, Object> book = new HashMap<>();
             book.put("bookTitle", resultSet.getString("bookTitle"));
             book.put("authorName", resultSet.getString("authorName"));
             book.put("publicationYear", resultSet.getInt("publicationYear"));
             book.put("availableStock", resultSet.getInt("stocks"));
             books.add(book);
         }

     } catch (SQLException e) {
         e.printStackTrace();
     }
     return books;
 }

 public static void searchBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, BookNotFoundException {

	 int customSearchChoice = Integer.parseInt(request.getParameter("customSearchChoice"));
	 String inputValue = request.getParameter("inputValue");
	 System.out.println(inputValue);
     List<Map<String, Object>> results = null;

     try {
    	 if (customSearchChoice == 1) {
	            results=searchByAuthor(inputValue);
	        }else if(customSearchChoice == 2) {
	        	results=searchByBook(inputValue);
	        }
	        else if(customSearchChoice==3) {
	        	results=searchById(inputValue);
	        }
     }catch(AuthorNotFoundException|BookNotFoundException|IdNotFoundException e) {
     	System.out.println(e);
     }
     System.out.println(results);
     request.setAttribute("results", results);
     request.getRequestDispatcher("/CustomSearchResult.jsp").forward(request, response);
 }
 
 
public static List<Map<String, Object>> searchByAuthor(String authorNameToSearch) throws AuthorNotFoundException {
    String sql = "SELECT bookId,bookTitle, publicationYear, categoryId,stocks FROM books WHERE authorName =?";
    List<Map<String, Object>> results = new ArrayList<>();
    
    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

        preparedStatement.setString(1, authorNameToSearch);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.isBeforeFirst()) {
            throw new AuthorNotFoundException();
        }

        while (resultSet.next()) {
            Map<String, Object> row = new HashMap<>();
            row.put("bookId",resultSet.getInt("bookId"));
            row.put("bookTitle", resultSet.getString("bookTitle"));
            row.put("authorName",authorNameToSearch );
            row.put("publicationYear", resultSet.getInt("publicationYear"));
            row.put("category", categoryFromId(resultSet.getInt("categoryId")));
            results.add(row);
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return results;
}

 
 static List<Map<String, Object>> searchByBook(String BookNameToSearch) throws BookNotFoundException {

     String sql = "SELECT bookId,authorName, publicationYear, categoryId FROM Books WHERE bookTitle = ?";
     List<Map<String, Object>> results = new ArrayList<>();
     
     try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
    	 
    	 	 preparedStatement.setString(1, BookNameToSearch);
	         ResultSet resultSet = preparedStatement.executeQuery();
	
	         if (!resultSet.isBeforeFirst()) {
	             throw new BookNotFoundException();
	         }
	
	         while (resultSet.next()) {
	             Map<String, Object> row = new HashMap<>();
	             row.put("bookId", resultSet.getInt("bookId"));
	             row.put("authorName", resultSet.getString("authorName"));
	             row.put("bookTitle", BookNameToSearch);
	             row.put("publicationYear", resultSet.getInt("publicationYear"));
	             row.put("category", categoryFromId(resultSet.getInt("categoryId")));
	             results.add(row);
	         }

     } catch (SQLException e) {
         e.printStackTrace();
     }
     return results;
 }
 
 static String categoryFromId (int categoryId) {
 	
 	String SqlCategoryFromId = "SELECT categoryName FROM categories WHERE categoryId = ?";

     try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
     	PreparedStatement preparedStatement1 = connection.prepareStatement(SqlCategoryFromId)) {
         preparedStatement1.setInt(1, categoryId);
         try (ResultSet resultSet1 = preparedStatement1.executeQuery()) {
             if (resultSet1.next()) {
                 String categoryFromId = resultSet1.getString("categoryName");
                 return categoryFromId;
             } else {
                 return "No category found for the given categoryId.";
             }
         }
     } catch (SQLException e) {
         e.printStackTrace();
         return "An error occurred while retrieving the category.";
     }
 }

 static List<Map<String, Object>> searchById(String bookIdToSearchStr) throws IdNotFoundException {

	 int bookIdToSearch=Integer.parseInt(bookIdToSearchStr);
     String sql = "SELECT bookId,bookTitle, authorName, publicationYear, categoryId FROM Books WHERE bookId = ?";

     List<Map<String, Object>> results = new ArrayList<>();
     
     try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
          PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

         preparedStatement.setInt(1, bookIdToSearch);
         ResultSet resultSet = preparedStatement.executeQuery();

         if (!resultSet.isBeforeFirst()) {
             throw new IdNotFoundException();
         }

         while (resultSet.next()) {
        	 Map<String, Object> row = new HashMap<>();
             row.put("bookId", bookIdToSearch);
             row.put("authorName", resultSet.getString("authorName"));
             row.put("bookTitle", resultSet.getString("bookTitle"));
             row.put("publicationYear", resultSet.getInt("publicationYear"));
             row.put("category", categoryFromId(resultSet.getInt("categoryId")));
             results.add(row);
         }

     } catch (SQLException e) {
         e.printStackTrace();
     }
     return results;
 }
}


