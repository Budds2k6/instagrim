<%-- 
    Document   : login.jsp
    Created on : 13/10/2015
    Author     : Tristan Haley
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim - Login</title>
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
                float: center;
            }
        </style>
    </head>
    <body>
        <header>
        <h1>InstaGrim ! </h1>
        <h2>Home</h2>
        </header>
        <nav>
            <ul>
                
                <%-- <li><a href="/Instagrim/Images/majed">Sample Images</a></li> --%>
                <li><a href="/Instagrim/"> Home </a></li>
            </ul>
        </nav>
        <article>
            <h3>Login</h3>
            <form method="POST"  action="Login">
                <ul>
                    <li>Username <input type="text" name="username"></li>
                    <li>Password <input type="password" name="password"></li>
                </ul>
                <br/>
                <input style="text-align: left" type="submit" value="Login"> 
            </form>

        </article>
        <footer>
            <ul>
               
            </ul>
        </footer>
    </body>
</html>
