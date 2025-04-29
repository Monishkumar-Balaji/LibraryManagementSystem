package person;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.ChangePasswordS;
import login.LoginS;

import java.time.LocalDate;


@SuppressWarnings("unused")
public  class Person  {
		protected String userId;
		protected String password;
		protected String name;
		protected String email;
		protected String phoneNo;
		protected String userType;
		protected float outstanding = 0;
		
		
		public static Scanner scan = new Scanner(System.in);
		
		protected static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/userdatabase";
	    protected static final String DB_USER = "root";
	    protected static final String DB_PASSWORD = "12345678";
		
		public Person(String userId,String password,String name,String email,String phoneNo,String userType,float outstanding){
			this.userId=userId;
			this.password=password;
			this.name=name;
			this.email=email;
			this.phoneNo=phoneNo;
			this.userType=userType;
			this.outstanding=outstanding;
		}
		
		public String getName() {
			return this.name;
		}
		public String getEmail() {
			return this.email;
		}
		public String getPhoneNo() {
			return this.phoneNo;
		}
		public String getUserType() {
			return this.userType;
		}
		public float getOutstanding() {
			return this.outstanding;
		}
		@Override
		public String toString() {
			return "User Id: "+this.userId+"\nName: "+this.name+"\nEmail Id: "+this.email+"\nPhone Number: "+this.phoneNo+"\nUser Type: "+this.userType+"\nOutstanding :₹ "+this.outstanding+"\n";
		}
		
		
		
		public void borrowBook(Person person,HttpServletRequest request, HttpServletResponse response) throws IOException {
		    int maxBooks = 0;

		    if (person.userType.equals("student")) {
		        maxBooks = 3;
		    } else if (person.userType.equals("faculty")) {
		        maxBooks = 7;
		    } 
		    else {
		    	System.out.println("Neither student nor faculty is trying to borrow book.");
		    	return;
		    }
		    String goBack ="";
            if(person.userType.equals("student")) goBack="StudentMenu.jsp";
            else if(person.userType.equals("faculty")) goBack="FacultyMenu.jsp";
		    

		    int borrowedCount = 0;
		    response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        
		    String countSql = "SELECT COUNT(*) AS borrowedCount FROM borrowedBooks WHERE userId = ?";
		    
		    try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		         PreparedStatement countPreparedStatement = connection.prepareStatement(countSql)) {
		        countPreparedStatement.setString(1, person.userId);
		        ResultSet countResultSet = countPreparedStatement.executeQuery();

		        if (countResultSet.next()) {
		            borrowedCount = countResultSet.getInt("borrowedCount");
		        }

		        if (borrowedCount >= maxBooks) {
		        	out.println("<style background: linear-gradient(to right, #89f7fe, #66a6ff);</style>");
		            out.println("<center>You have reached the maximum limit of borrowed books (" + maxBooks + ").");
		            out.println("<br><a href="+goBack+">Go Back</a></center>");
		            return;
		        }

		        int bookId = Integer.parseInt(request.getParameter("bookId"));
		      
		        String sql = "SELECT bookTitle, authorName, publicationYear, categoryId FROM Books WHERE bookId = ?";
		        String borrowSql = "INSERT INTO borrowedBooks (userId, userType, email, phoneNo, borrowedTime, bookId, bookTitle, authorName, publicationYear, categoryId) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)";
		        String updateStock ="UPDATE Books SET stocks=stocks-1 WHERE bookId =?";
		        
		        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
		             PreparedStatement borrowPreparedStatement = connection.prepareStatement(borrowSql);
		        	 PreparedStatement updateStocks = connection.prepareStatement(updateStock)) {

		            preparedStatement.setInt(1, bookId);
		            ResultSet resultSet = preparedStatement.executeQuery();

		            if (!resultSet.isBeforeFirst()) {
		                out.println("No books found for the given ID.<br><a href="+goBack+">Go Back</a>");
		                return;
		            }
		            updateStocks.setInt(1,bookId);
		            updateStocks.executeUpdate();
		            
		            String bookTitle = "";
		            String authorName = "";
		            int categoryId = 0;
		            int publicationYear = 0;

		            while (resultSet.next()) {
		                bookTitle = resultSet.getString("bookTitle");
		                authorName = resultSet.getString("authorName");
		                categoryId = resultSet.getInt("categoryId");
		                publicationYear = resultSet.getInt("publicationYear");
		            }

		            borrowPreparedStatement.setString(1, person.userId);
		            borrowPreparedStatement.setString(2, person.userType);
		            borrowPreparedStatement.setString(3, person.email); 
		            borrowPreparedStatement.setString(4, person.phoneNo); 
		            borrowPreparedStatement.setInt(5, bookId);
		            borrowPreparedStatement.setString(6, bookTitle);
		            borrowPreparedStatement.setString(7, authorName);
		            borrowPreparedStatement.setInt(8, publicationYear);
		            borrowPreparedStatement.setInt(9, categoryId);

		            int rowsInserted = borrowPreparedStatement.executeUpdate();
		            if (rowsInserted > 0) {
		                LocalDate today = LocalDate.now();
		                int remainingBooks = maxBooks - (borrowedCount + 1);
		                out.println("<body style='background: linear-gradient(to right, #89f7fe, #66a6ff);'><center><p style ='text-align:center; margin-top:60px; padding 10 px;'>You have successfully issued the book: " + bookTitle+"</p>");
		           		out.println("<br><p style ='padding: 10 px;margin:20px;'>Due date to return the book: "+today.plusDays(14)+"</p>");
		          		out.println("<br><p style ='padding: 10 px;margin:20px;'>You can issue " + remainingBooks + " more book(s).</p>");
		                out.println("<br><a href="+goBack+">Go Back</a></center></body>");
		            }
		            else {
		            	out.println("<center>borrow unsuccessful.Contact Admin.");
		            	out.println("<br><a href="+goBack+">Go Back</a></center>");
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}


		public void returnBook(Person person,HttpServletRequest request, HttpServletResponse response) throws IOException {

			int bookId=Integer.parseInt(request.getParameter("bookId"));
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			
	        Date currentDate = new java.sql.Date(System.currentTimeMillis()); // Use today's date

	        String updateStock ="UPDATE Books SET stocks=stocks+1 WHERE bookId =?";
	        String fetchSql = "SELECT borrowedTime FROM borrowedBooks WHERE bookId = ? AND userId = ?";
	        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	             PreparedStatement fetchPreparedStatement = connection.prepareStatement(fetchSql);
	             PreparedStatement updateStocks = connection.prepareStatement(updateStock)) {

	            fetchPreparedStatement.setInt(1, bookId);
	            fetchPreparedStatement.setString(2, person.userId);
	            updateStocks.setInt(1,bookId);
	            
	            ResultSet resultSet = fetchPreparedStatement.executeQuery();
	            updateStocks.executeUpdate();
	            
	            String goBack ="";
                if(person.userType.equals("student")) goBack="StudentMenu.jsp";
                else if(person.userType.equals("faculty")) goBack="FacultyMenu.jsp";

	            if (!resultSet.isBeforeFirst()) {
	                out.println("<body style='background: linear-gradient(to right, #89f7fe, #66a6ff);'><center><br>No borrowing record found for this book and user.");
	                out.println("<br><a href="+goBack+">Go Back</a></center></body>");
	                return;
	            }

	            java.sql.Date borrowedDate = null;

	            while (resultSet.next()) {
	                borrowedDate = resultSet.getDate("borrowedTime");
	            }

	            float fineAmount = calculateFine(borrowedDate, currentDate);
	            if (fineAmount > 0) {
	                outstanding += fineAmount; // Store in class variable
	                out.println("<body style='background: linear-gradient(to right, #89f7fe, #66a6ff);'><center><br>You are returning the book late. Fine amount: ₹" + fineAmount+"</center></body>");
	                storeFineInDatabase(person.userId, outstanding); // Store fine in database
	            } else {
	                out.println("<body style='background: linear-gradient(to right, #89f7fe, #66a6ff);'><center><br><p style ='padding:10 px; margin:10 px;'>You are returning the book on time. No fine.");
	                
	            }

	            String deleteSql = "DELETE FROM borrowedBooks WHERE bookId = ? AND userId = ?";
	            try (PreparedStatement deletePreparedStatement = connection.prepareStatement(deleteSql)) {
	                deletePreparedStatement.setInt(1, bookId);
	                deletePreparedStatement.setString(2, person.userId);
	                int rowsDeleted = deletePreparedStatement.executeUpdate();
	                
	                
	                
	                if (rowsDeleted > 0) {
	                    out.println("<br>You have successfully returned the book.</p>");
	                    out.println("<br><a href="+goBack+">Go Back</a></center></body>");
	                } else {
	                    out.println("Failed to return the book.");
	                    out.println("<br><a href="+goBack+">Go Back</a>");
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    private float calculateFine(java.sql.Date borrowedDate, java.sql.Date currentDate) {
	        long diffInMillies = currentDate.getTime() - borrowedDate.getTime();
	        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);

	        if (diffInDays > 14) {
	            int fineDays = (int) (diffInDays - 14);
	            return fineDays * 10; // Fine is 10 rupees per day
	        }

	        return 0; // No fine
	    }

	    private void storeFineInDatabase(String userId, float outstanding) {
	        String sql = "UPDATE users SET outstanding = ? WHERE userId = ?";
	        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	            preparedStatement.setFloat(1, outstanding);
	            preparedStatement.setString(2, userId);
	            int rowsUpdated = preparedStatement.executeUpdate();
	            
	            if (rowsUpdated > 0) {
	                System.out.println("Fine amount updated in the database.");
	            } else {
	            	System.out.println("Failed to update fine amount.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	    public void viewBorrowedBooks(Person person, HttpServletRequest request, HttpServletResponse response) throws IOException {
	        String sql = "SELECT bookId, bookTitle, authorName, borrowedTime FROM borrowedBooks WHERE userId = ?";
	        List<Map<String, Object>> borrowedBooks = new ArrayList<>();
	        String message = null;

	        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

	            preparedStatement.setString(1, this.userId);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (!resultSet.isBeforeFirst()) {
	                message = "No books borrowed.";
	            } else {
	                while (resultSet.next()) {
	                    Map<String, Object> book = new HashMap<>();
	                    book.put("bookId", resultSet.getInt("bookId"));
	                    book.put("bookTitle", resultSet.getString("bookTitle"));
	                    book.put("authorName", resultSet.getString("authorName"));
	                    book.put("borrowedTime", resultSet.getTimestamp("borrowedTime").toString());
	                    borrowedBooks.add(book);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            message = "An error occurred while retrieving borrowed books.";
	        }

	        // Set attributes for the JSP
	        request.setAttribute("books", borrowedBooks);
	        request.setAttribute("message", message);

	        try {
	            request.getRequestDispatcher("ViewBorrowedBooks.jsp").forward(request, response);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }


	    public void editProfile(Person person,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	        String newName = request.getParameter("newName");
	        String newEmail = request.getParameter("newEmail");
	        String newPhoneNo = request.getParameter("newPhoneNo");
	        String newPassword = request.getParameter("newPassword");
	        
	        String goBack ="";
            if(person.userType.equals("student")) goBack="StudentMenu.jsp";
            else if(person.userType.equals("faculty")) goBack="FacultyMenu.jsp";
	        
	        if (newName == null || newName.isEmpty()) {
	            newName = this.name;
	        }
	        if (newEmail == null || newEmail.isEmpty()) {
	            newEmail = this.email;
	        }
	        if (newPhoneNo == null || newPhoneNo.isEmpty()) {
	            newPhoneNo = this.phoneNo;
	        }

	        // SQL to update profile
	        String sql = "UPDATE users SET name = ?, email = ?, phoneNo = ? WHERE userId = ?";

	        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

	            preparedStatement.setString(1, newName);
	            preparedStatement.setString(2, newEmail);
	            preparedStatement.setString(3, newPhoneNo);
	            preparedStatement.setString(4, this.userId);

	            int rowsAffected = preparedStatement.executeUpdate();
	            response.setContentType("html/text");
	            
                
	            if (rowsAffected > 0) {
	                // Update the current object
	                this.name = newName;
	                this.email = newEmail;
	                this.phoneNo = newPhoneNo;

	                // If a password update is requested
	                if (newPassword != null && !newPassword.isEmpty()) {
	                    request.getRequestDispatcher("/ChangePassword.jsp").forward(request, response);
	                }
	            } 

	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("caugth");
	            request.setAttribute("message", "An error occurred while updating the profile.");
	        }

	        // Forward to the JSP
	        try {
	        	RequestDispatcher rd ;
	        	response.setContentType("text/html");
	        	response.getWriter().print("Profile updated successfully.");
	            rd=request.getRequestDispatcher(goBack);
	            rd.include(request, response);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }


	    
}



