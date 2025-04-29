<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="CSS/LibrarianViewBorrowedBooks.css">
    <title>Borrowed Books</title>
    <style>
        table {
            width: 95%;
            border-collapse: collapse;
            margin: 20px auto;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        p {
            text-align: center;
            color: red;
        }
    </style>
</head>
<body>
    <h1 style="text-align: center;">Borrowed Books</h1>

    <%
        String error = (String) request.getAttribute("error");
        String message = (String) request.getAttribute("message");
        List<Map<String, Object>> borrowedBooks = (List<Map<String, Object>>) request.getAttribute("borrowedBooks");

        if (error != null) {
    %>
        <p><%= error %></p>
    <%
        } else if (message != null) {
    %>
        <p><%= message %></p>
    <%
        } else if (borrowedBooks != null && !borrowedBooks.isEmpty()) {
    %>
        <table>
            <thead>
                <tr>
                    <th>Serial No</th>
                    <th>User ID</th>
                    <th>User Type</th>
                    <th>Email</th>
                    <th>Phone No</th>
                    <th>Borrowed Time</th>
                    <th>Book ID</th>
                    <th>Book Title</th>
                    <th>Author Name</th>
                    <th>Publication Year</th>
                    <th>Category ID</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Map<String, Object> book : borrowedBooks) {
                %>
                    <tr>
                        <td><%= book.get("serialNo") %></td>
                        <td><%= book.get("userId") %></td>
                        <td><%= book.get("userType") %></td>
                        <td><%= book.get("email") %></td>
                        <td><%= book.get("phoneNo") %></td>
                        <td><%= book.get("borrowedTime") %></td>
                        <td><%= book.get("bookId") %></td>
                        <td><%= book.get("bookTitle") %></td>
                        <td><%= book.get("authorName") %></td>
                        <td><%= book.get("publicationYear") %></td>
                        <td><%= book.get("categoryId") %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        } else {
    %>
        <p>No borrowed books found.</p>
    <%
        }
    %>
    <a href="LibrarianMenu.jsp">Go back</a>
</body>
</html>
