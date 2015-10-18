<%-- 
    Document   : Upload
    Created on : Sep 22, 2014, 6:31:50 PM
    Author     : Tristan Haley
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim - Upload</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
    </head>
    <body>
        <h1>InstaGrim! </h1>
        <h2>Upload an image</h2>
        <nav>
            <ul>
                <li><a href="/Instagrim/"> Home </a></li>
                <%-- <li><a href="/Instagrim/Images/majed">Sample Images</a></li> --%>
            </ul>
        </nav>
 
        <article>
            <h3>File Upload</h3>
            <form method="POST" enctype="multipart/form-data" action="Image">
                File to upload: <input type="file" name="upfile"><br/>

                <br/>
                <input type="submit" value="Press"> to upload the file!
            </form>

        </article>
    </body>
</html>
