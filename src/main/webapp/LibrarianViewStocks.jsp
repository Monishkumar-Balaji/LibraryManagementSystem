<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Stock</title>
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
    <link rel="stylesheet" type="text/css" href="CSS/LibrarianViewStocks.css">
</head>
<body>
    <h1 style="text-align: center;">Book Stock</h1>

    <%
        String error = (String) request.getAttribute("error");
        String message = (String) request.getAttribute("message");
        List<Map<String, Object>> books = (List<Map<String, Object>>) request.getAttribute("books");

        if (error != null) {
    %>
        <p><%= error %></p>
    <%
        } else if (message != null) {
    %>
        <p><%= message %></p>
    <%
        } else if (books != null && !books.isEmpty()) {
    %>
        <table>
            <thead>
                <tr>
                    <th>Book ID</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Year</th>
                    <th>Category</th>
                    <th>Stock</th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (Map<String, Object> book : books) {
                %>
                    <tr>
                        <td><%= book.get("bookId") %></td>
                        <td><%= book.get("bookTitle") %></td>
                        <td><%= book.get("authorName") %></td>
                        <td><%= book.get("publicationYear") %></td>
                        <td><%= book.get("categoryName") %></td>
                        <td><%= book.get("stock") %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        } else {
    %>
        <p>No books found in the library.</p>
    <%
        }
    %>
    <a class="container" href="LibrarianMenu.jsp">Go back</a>
</body>
</html>
