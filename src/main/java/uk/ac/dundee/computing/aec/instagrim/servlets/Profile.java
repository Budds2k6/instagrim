/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Set;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.PicModel;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.models.ProfileDetails;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

/**
 *
 * @author Tristan
 */
@WebServlet(name = "Profile", urlPatterns = {"/Profile","/Profile/*"})
public class Profile extends HttpServlet
{
    // Class variables
    String username;
    Cluster thisCluster;

    public void init(ServletConfig config) throws ServletException
    {
        // TODO Auto-generated method stub
        thisCluster = CassandraHosts.getCluster();
    }
    
    // Acquires the details of the logged in user
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {       
        // Obtains the user login
        HttpSession session = request.getSession();
        LoggedIn userLog = (LoggedIn)session.getAttribute("LoggedIn");
        username = userLog.getUsername();
        
        // Gets the information for the user
        User thisUser = new User();
        thisUser.setCluster(thisCluster);
        ProfileDetails userInfo = thisUser.getUserProfile(username);
        
        // Extracts the user information
        String firstname = userInfo.getFirstname();
        String surname = userInfo.getSurname();
        String email = userInfo.getEmail().toString();
        UUID profilePic = userInfo.getProfilePic();
        
        System.out.println("WHOLOLO Firstname: " + firstname + " Surname: " + surname + " Email: " + email + " FuckYou: " + profilePic);
        
        // Sets the session attributes
        request.setAttribute("firstname", firstname);
        request.setAttribute("surname", surname);
        request.setAttribute("email", email);
        request.setAttribute("profilePic", profilePic);
        
        // Acquires the picture list for the user
        PicModel picture = new PicModel();
        picture.setCluster(thisCluster);
        java.util.LinkedList<Pic> pictureList = picture.getPicsForUser(username);
        request.setAttribute("pictures", pictureList);
        
        // Forwards the details to the profile page
        RequestDispatcher dispatcher = request.getRequestDispatcher("Profile.jsp");
        dispatcher.forward(request, response);
    } 
    
    // Does post
     protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // Relays
        processRequest(request, response);
    }
}




