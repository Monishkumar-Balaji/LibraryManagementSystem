<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" type="text/css" href="CSS/BorrowBooks.css">
	
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Borrow Books</title>
</head>
<body>
	<%
        // Determine the previous page based on user type
        String action = "";
        if ("student".equals(session.getAttribute("userType"))) {
            action = "StudentS";
        } else if ("faculty".equals(session.getAttribute("userType"))) {
            action = "FacultyS";
        }
    %>

    
    <form action="<%= action %>" method="post">
        <input type="hidden" name="choice" value="2">
        <label>Enter Book id to Issue : </label>
        <input name="bookId" >
        <br>
        <button type="submit">Issue Book</button>
    </form>
    <%
        // Determine the previous page based on user type
        String previousPage = "";
        if ("student".equals(session.getAttribute("userType"))) {
            previousPage = "StudentMenu.jsp";
        } else if ("faculty".equals(session.getAttribute("userType"))) {
            previousPage = "FacultyMenu.jsp";
        }
    %>

    <!-- Use the resolved previousPage variable -->
    <br>
    <a href="<%= previousPage %>">Go Back</a>
</body>
</html>