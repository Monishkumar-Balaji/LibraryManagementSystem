<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="CSS/CustomSearchBook.css">
    
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Book</title>
</head>
<body>
    <h3>Search Books</h3>
    <form action="BookDisplayS" method="get">
    	<input type="hidden" name="customChoice" value="2">
        <label for="removeChoice">Choose an option to Search books:</label><br>
        <input type="radio" id="bookId" name="customSearchChoice" value="1" required>
        <label for="bookId">Search by Author</label><br>
        <input type="radio" id="bookTitle" name="customSearchChoice" value="2">
        <label for="bookTitle">Search Book by Title</label><br>
        <input type="radio" id="authorName" name="customSearchChoice" value="3">
        <label for="authorName">Search Book by Id</label><br><br>

        <label for="inputValue">Enter the value:</label>
        <input type="text" id="inputValue" name="inputValue" required><br><br>

        <button type="submit">Search</button>

<br>
<a href="<%= request.getHeader("Referer") %>">Back to Home</a>
    </form>
</body>
</html>