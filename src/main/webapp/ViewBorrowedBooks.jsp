<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="CSS/ReturnBooks.css">
    <title>Borrowed Books</title>
</head>
<body>
<center>
    <h1>Borrowed Books</h1>

    <%
        List<Map<String, Object>> books = (List<Map<String, Object>>) request.getAttribute("books");
        String message = (String) request.getAttribute("message");

        if (message != null) {
    %>
        <p><%= message %></p>
    <%
        } else if (books != null && !books.isEmpty()) {
    %>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Borrowed Time</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Map<String, Object> book : books) {
                        int bookId = (int) book.get("bookId");
                        String bookTitle = (String) book.get("bookTitle");
                        String authorName = (String) book.get("authorName");
                        String borrowedTime = (String) book.get("borrowedTime");
                %>
                    <tr>
                        <td><%= bookId %></td>
                        <td><%= bookTitle %></td>
                        <td><%= authorName %></td>
                        <td><%= borrowedTime %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        }
    %>

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
</center>
</body>
</html>
