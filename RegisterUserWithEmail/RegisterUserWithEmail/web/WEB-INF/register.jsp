<%-- 
    Document   : register
    Created on : 2-Sep-2018, 4:28:37 PM
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
        <h1>Register User via Email - Registration Page</h1>
        
        <form action="RegisterController" method="POST">
            Username: <input type="text" name="username"><br/>
            Password: <input type="text" name="password"><br/>
            E-mail: <input type="text" name="email"><br/>
            <input type="submit" value="Register">
        </form>
        
        <a href="RegisterController">Login</a>

    </body>
</html>
