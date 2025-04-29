<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Books</title>
    <link rel="stylesheet" type="text/css" href="CSS/LibrarianAddBooks.css">
</head>
<body>
<div class="librarianAddBooks">
    <form action="LibrarianS" method="get" >
        <h3>Welcome to Add Books</h3>
        <input type="hidden" name="menuChoice" value="7">
        <label>Enter the Book name to Add : </label>
        <input type="text" name="bookToAdd">
        <br>
        <label>Enter the Book Id : </label>
        <input type="text"  name="idToAdd">
        <br>
        <label>Enter the Author Name : </label>
        <input type="text"  name="authorToAdd">
        <br>
        <label>Enter the Publication Year : </label>
        <input type="text"  name="yearToAdd">
        <br>
        <label>Enter the category of the Book : </label>
        <select name="categoryIdToAdd">
            <option value="1">1.Fiction</option>
            <option value="2">2.Non-Fiction</option>
            <option value="3">3.Computer Science</option>
            <option value="4">4.Information Technology</option>
            <option value="5">5.Electronics and Communication</option>
        </select>
        <br>
        <label>Enter the Number of Stocks to Add : </label>
        <input type="text" name="stockToAdd">
        <br>
        <button type="submit">Submit</button>
    <a href="LibrarianMenu.jsp">Go Back</a>
    </form>
</div>
</body>
</html>