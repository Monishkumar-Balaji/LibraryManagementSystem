<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" type="text/css" href="CSS/ChangePassword.css">
	
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ChangePassword</title>
</head>
<body>
    <h3>Welcome to Change Password</h3>
    <form action ="ChangePasswordS" method = post>
    	<label>Enter userId:</label>
        <input type="text" name="userId">
        <br>
        <label>Enter new Password:</label>
        <input type="password" name="newPassword">
        <br>
        <label>Re-enter new Password:</label>
        <input type="password" name="newPasswordReEnter">
        <button >Submit</button>
    </form>
    
</body>
</html>