<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" type="text/css" href="CSS/RequestBooks.css">
	
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Request Books</title>
</head>
<body>
    <h3>Request Books</h3>
    <br>
    <p>Faculties are allowed to request certain books that are not available in the library, this notification will be sent to the librarian.</p>

    <form action="FacultyS" method="post">
        <input type="hidden" name="choice" value="6">
        <label>Enter the book name to Request : </label>
        <input name="requestBookName">
        <br>
        <label>Enter Author Name of the book : </label>
        <input name="requestBookAuthor">
        <br>
        <label>Enter Publication Year of the book : </label>
        <input name="publicationYear">
        <br>
        <button type="submit">Request Book</button>
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
    <center>
    <a href="<%= previousPage %>">Go Back</a>
    </center>
</body>
</html>