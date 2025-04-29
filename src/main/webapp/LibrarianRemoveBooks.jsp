<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Remove Books</title>
    <link rel="stylesheet" type="text/css" href="CSS/LibrarianRemoveBooks.css">
    
    
</head>
<body>
    <h1>Remove Books</h1>
    <form action="LibrarianS" method="post">
    	<input type="hidden" name ="menuChoice" value="2">
        <label for="removeChoice">Choose an option to remove books:</label><br>
        <input type="radio" id="bookId" name="removeChoice" value="1" required>
        <label for="bookId">Remove by Book ID</label><br>
        <input type="radio" id="bookTitle" name="removeChoice" value="2">
        <label for="bookTitle">Remove by Book Title</label><br>
        <input type="radio" id="authorName" name="removeChoice" value="3">
        <label for="authorName">Remove by Author Name</label><br><br>

        <label for="inputValue">Enter the value:</label>
        <input type="text" id="inputValue" name="inputValue" required><br><br>

        <button type="submit">Remove</button>
        <a href="LibrarianMenu.jsp">Go Back</a>
    </form>

</body>
</html>
