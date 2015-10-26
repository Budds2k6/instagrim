<%-- 
    Document   : Upload
    Created on : 19/10/2015
    Author     : Tristan Haley
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim - Upload</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        
        <style>
            div
            {
                background-color:lightgrey;
                border-style: grove;
                border-radius: 10px;
                padding-bottom: 5px;
                margin-bottom: 5px;
                font-family: monospace;
                color: black;
                opacity: 0.5;
                width: 10%;
            }
            div:hover
            {
                opacity: 0.8;
            } 
            
            h2
            {
                background-color: lightgoldenrodyellow;
                opacity: 0.7;
                border-radius:3px;
                border-color: darkkhaki;
                border-style: dotted;
                color: black
            }
            
        </style>
        
    </head>
    <body>
        <h1 style="padding: 0px;">InstaGrim! </h1>
        <h2 style="">Upload an image</h2>
        <nav>
            <ul>
                <li><a href="/Instagrim/Profile"> Profile </a></li>
            </ul>
        </nav>
 
        <article>
            <h3>File Upload</h3>
            <form method="POST" enctype="multipart/form-data" action="Image">
                File to upload: <input type="file" name="upfile">
                <br/>
                <br/>
                <input type="submit" value="Press"> to upload the file!
                <input type="hidden" value="false" name="isProfilePic">
                
                <div>
                    <h3 style="background-color: aquamarine; text-align: center;"> Filter </h3>
                    <input type="radio" value="None"      name="Filter" checked="true">None<br>
                    <input type="radio" value="Greyscale" name="Filter">Greyscale<br>
                    <input type="radio" value="Brighter" name="Filter">Brighter<br>
                    <input type="radio" value="Darker" name="Filter">Darker<br>
                </div>
            </form>

        </article>
    </body>
</html>
