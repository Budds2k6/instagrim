<%-- 
    Document   : Profile (Profile)
    Created on : 10/10/2015
    Author     : Tristan Haley
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>InstaGrim - Home</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <header>
            <h1> InstaGrim </h1>
            <h2>Welcome</h2>
        </header>
        <nav>        
            <ul>               
                    <%  
                // Checks if logged in
                LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");

                // If they are logged in
                if (lg != null)
                {
                    // Obtain username data
                    String UserName = lg.getUsername();

                    if (lg.getLoggedIn())
                    {
                        %>
                        <p style="color: black"> Welcome <%= UserName %>! </p>
                        <li><a href="upload.jsp">Upload</a></li>
                        <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>

                        <form action="Logout" method="POST">
                            <input type="Image" src="lkju.png">
                        </form>

                        <%
                    }
                }else
                {
                    %>
                         <li><a href="register.jsp">Register</a></li>
                        <li><a href="login.jsp">Login</a></li>
                    <%        
                }%>
            </ul>
            
        </nav>
        <footer>
            <ul>
                <li>&COPY; T Haley</li>
            </ul>
        </footer>
    </body>
</html>
