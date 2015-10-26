<%-- 
    Document   : Profile.jsp
    Created on : 10/10/2015
    Author     : Tristan Haley
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.aec.instagrim.stores.*" %>
<%@page import="uk.ac.dundee.computing.aec.instagrim.servlets.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>InstaGrim - Home</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
            <h1> InstaGrim </h1>
        </header>
            <%          
            // User Login
            LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                
            // Obtain user details
            String username = lg.getUsername();
            String firstname = request.getAttribute("firstname").toString();
            String surname = request.getAttribute("surname").toString();
            String email = request.getAttribute("email").toString();
            %>
            <h2>Welcome <%= username %>!</h2>
            <nav>                
                <ul>
                    <form method="POST" action="/Instagrim/UploadImage" id="uploadForm" name="uploadForm">
                        <input type="hidden" value="<%=username%>" name="username">
                        <li> <a href="#" onclick="document.getElementById('uploadForm').submit()">Upload</a></li>
                    </form>
                    <li><a href="/Instagrim/Images/<%=username%>">Your Images</a></li>
                    <form action="Logout" method="POST">
                        <input style ="background-color: transparent;" type="Image" src="logout-button-blue-hi.png">
                    </form>
                </ul>
            </nav>
            <div style="position: absolute; right: 0px;">
                <form method="POST" enctype="multipart/form-data" action="Image">
                    File to upload: <input type="file" name="upfile">
                    <input type="submit" value="Press"> to upload the file!
                    <input type="hidden" value="true" name="isProfilePic">
                </form>
                <image src="/Instagrim/Image/<%=request.getAttribute("profilePic")%>" width="82px" height="82px" style="display: inline-block" >
            </div>
        <article>
            <h3>Your details:</h3>
            <form method="POST"  action="Register">
                    <li>Username  <input type="text" name="username" maxlength="16" value="<%=username%>"></li>
                    <li>Firstname <input type ="firstname" name="firstname" value="<%=firstname%>"></li>
                    <li>Surname   <input type="surname" name="surname" value="<%=surname%>"></li>
                    <li>Email     <input type="email" name ="email" value="<%=email%>"></li>
                <br/>
                <%-- <input type="submit" value="Commit">  --%>
            </form>
        </article>
        <footer>
            <ul>
                <li>&COPY; T Haley</li>
            </ul>
        </footer>
    </body>
</html>
