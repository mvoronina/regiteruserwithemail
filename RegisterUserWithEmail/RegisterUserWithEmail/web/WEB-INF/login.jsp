<%-- 
    Document   : login
    Created on : 2-Sep-2018, 4:28:09 PM
    Author     : john
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Register User via Email - Login Page</h1>
        
        <form action="RegisterController" method="POST">
            Username: <input type="text" name="username"><br/>
            Password: <input type="password" name="password"><br/>
            <input type="submit" value="Login">
        </form>
        ${requestScope.message}<br/>
        <a href="RegisterController?register=true">Register</a>
        
    </body>
</html>
