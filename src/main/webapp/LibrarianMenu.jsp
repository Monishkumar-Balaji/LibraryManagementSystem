<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>
    <link rel="stylesheet" type="text/css" href="CSS/LibrarianMenu.css">
</head>
<body>
<div class="librarianMenu">
    <h2>Welcome Librarian</h2>
    <h3>Menu</h3>
    <form action="LibrarianS" method="get">
    	<input type="hidden" name="who" value="librarian">
        <button name ="menuChoice" value="1">Add Books</button><br>
        <button name ="menuChoice" value="2">Remove Books</button><br>
        <button name ="menuChoice" value="3">View Stock</button><br>
        <button name ="menuChoice" value="4">View Book Request</button><br>
        <button name ="menuChoice" value="5">View Borrowed Books</button><br>
        <button name ="menuChoice" value="6">Logout</button><br>
    </form>
</div>
</body>
</html>