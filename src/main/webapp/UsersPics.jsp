<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim - Your Gallery</title>
        <link rel="stylesheet" type="text/css" href="/Instagrim/Styles.css" />
    </head>
    <body>
        <header>
        
        <h1>InstaGrim! </h1>
        <h2>Your Gallery</h2>
        </header>
        
        <nav>
            <ul>
                <li><a href="/Instagrim/"> Home </a></li>
                <form method="POST" action="/Instagrim/UploadImage" id="uploadForm" name="uploadForm">
                    <input type="hidden" value="<%=username%>" name="username">
                    <li> <a href="#" onclick="document.getElementById('uploadForm').submit()">Upload</a></li>
                </form>
                <li><a href="/Instagrim/Profile"> Profile </a></li>
            </ul>
        </nav>
 
        <article>
            <h2>Your Pics</h2>
        <%
            // Loads in picture list
            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
            
            // If picture list is empty
            if (lsPics == null) {
        %>
                <%-- Displays empty gallery message --%>
                <p>Gallery Empty!</p>
        <%
            } 
            else // If pictures in gallery
            {
                // Iterator for list
                Iterator<Pic> iterator;
                iterator = lsPics.iterator();
                int count = 0;
                
                // Iterates through list
                while (iterator.hasNext())
                {
                    Pic p = (Pic) iterator.next();
        %>
                    <a style="display: inline-block; margin-left: 25%;" href="/Instagrim/Image/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a>
        <%
                    count++;
                    
                    if (count == 3)
                    {
                        count = 0;
        %>
                        <br/>
        <%
                    }
                }  
            }
        %>
        </article>
    </body>
</html>
