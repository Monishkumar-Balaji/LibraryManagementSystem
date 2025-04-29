<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Requests</title>
    <link rel="stylesheet" type="text/css" href="CSS/LibrarianViewRequest.css">
    <style>
        table {
            width: 80%;
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
    <h1 style="text-align: center;">Book Requests</h1>

    <%
        String error = (String) request.getAttribute("error");
        String message = (String) request.getAttribute("message");
        List<Map<String, Object>> bookRequests = (List<Map<String, Object>>) request.getAttribute("bookRequests");

        if (error != null) {
    %>
        <p><%= error %></p>
    <%
        } else if (message != null) {
    %>
        <p><%= message %></p>
    <%
        } else if (bookRequests != null && !bookRequests.isEmpty()) {
    %>
        <table>
            <thead>
                <tr>
                    <th>Faculty ID</th>
                    <th>Book ID</th>
                    <th>Author</th>
                    <th>Year</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Map<String, Object> bookRequest : bookRequests) {
                %>
                    <tr>
                        <td><%= bookRequest.get("facultyId") %></td>
                        <td><%= bookRequest.get("bookId") %></td>
                        <td><%= bookRequest.get("authorName") %></td>
                        <td><%= bookRequest.get("publicationYear") %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        } else {
    %>
        <p>No book requests found.</p>
    <%
        }
    %>
    <a href="LibrarianMenu.jsp">Go back</a>
</body>
</html>
