<%-- 
    Document   : register.jsp
    Created on : 10/10/2015
    Author     : Tristan Haley
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <style>
            article
            {
                margin: auto;
                position:absolute;
                font-family: Courier;
                background: linear-gradient(to right, lightgrey, grey, lightgrey); 
                width: 20%;
                left:40%;
                top: 40%;
                padding:10px;
                border-color: darkgreen;
                border-width: 5px;
                border-style: double;
                border-radius: 10px;
                color: black;
                text-align: left;
            }
        </style>
    </head>
    <body>
        <header>
        <h1>InstaGrim ! </h1>
        <h2>Registration</h2>
        </header>
        <nav>
            <ul>
                
                <%-- <li><a href="/Instagrim/Images/majed">Sample Images</a></li> --%>
                <li><a href="/Instagrim/"> Home </a></li>
            </ul>
        </nav>
       
        <article>
            <h3>Enter your details</h3>
            <form method="POST"  action="Register">
                
                    <li>Username (8-16 chars)  <input type="text" name="username" maxlength="16"></li>
                    <li>Password (8-16 chars)  <input type="password" name="password" maxlength="16"></li>
                    <li>Firstname              <input type ="firstname" name="firstname"></li>
                    <li>Surname                <input type="surname" name="surname"></li>
                    <li>Email                  <input type="email" name ="email"></li>
                
                <br/>
                <input type="submit" value="Register"> 
            </form>
        </article>
    </body>
</html>
