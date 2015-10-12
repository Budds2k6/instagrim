<%-- 
    Document   : Index (Home)
    Created on : 10/10/2015
    Author     : Tristan Haley
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Instagrim - Home</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <header>
                <h1  style="position: absolute; left: 0; top:-15px " >InstaGrim</h1>
            
            <h2>Home</h2>
            <p>Please choose an option:</p>
        </header>
        <nav>        
            <ul>               
                <li><a href="upload.jsp">Upload</a></li>
                    <%     
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null)
                        {
                            String UserName = lg.getUsername();
                            if (lg.getlogedin())
                            {
                                %>
                                <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
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
