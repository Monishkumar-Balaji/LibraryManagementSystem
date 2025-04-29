<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="CSS/CustomSearchResult.css">
    <title>Search Results</title>
</head>
<body>
    <h1>Search Results</h1>
    
    <!-- Display results table if data exists -->
    <%
        List<Map<String, Object>> results = (List<Map<String, Object>>) request.getAttribute("results");
        String error = (String) request.getAttribute("error");

        if (results != null && !results.isEmpty()) {
    %>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Year</th>
                    <th>Category</th>
                </tr>
            </thead>
            <tbody>
                <%
                   
                    for (Map<String, Object> row : results) {
                    	int bookId = (int) row.get("bookId");
                        String bookTitle = (String) row.get("bookTitle");
                        String authorName = (String) row.get("authorName");
                        int publicationYear = (int) row.get("publicationYear");
                        String category = (String) row.get("category");
                %>
                    <tr>
                        <td><%= bookId %></td>
                        <td><%= bookTitle %></td>
                        <td><%= authorName %></td>
                        <td><%= publicationYear %></td>
                        <td><%= category %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        } else if (error != null) {
    %>
        <p>Error: <%= error %></p>
    <%
        } else {
    %>
        <p>No results found.</p>
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
</body>
</html>
