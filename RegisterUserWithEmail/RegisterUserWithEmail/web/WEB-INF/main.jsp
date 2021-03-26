<%-- 
    Document   : main
    Created on : 2-Sep-2018, 5:52:33 PM
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
        <h1>Register User via Email - Main Page</h1>
        
        Welcome, ${sessionScope.username}<br/>
        <a href="RegisterController?logout=true">Logout</a>
    </body>
</html>
