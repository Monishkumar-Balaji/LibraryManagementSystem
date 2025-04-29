<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" type="text/css" href="CSS/StudentMenu.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>
</head>
<body>
    <h2>Welcome <%=session.getAttribute("name") %></h2>
    <h3>Home
    </h3>
    <form action="StudentS" method="get">
    	<input type="hidden" name="who" value="student">
        <button name ="menuChoice" value="1">View Books</button><br>
        <button name ="menuChoice" value="2">Issue Books</button><br>
        <button name ="menuChoice" value="3">Return Books</button><br>
        <button name ="menuChoice" value="4">View/Edit Profile</button><br>
        <button name ="menuChoice" value="5">View Borrowed Books</button><br>
        <button name ="menuChoice" value="6">Logout</button><br>
    </form>
    
</body>
</html>