/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.User;

/**
 *
 * @author Tristan Haley
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet
{
    Cluster cluster = null;
    public void init(ServletConfig config) throws ServletException
    {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }




    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // Acquires the user details from form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String surname = request.getParameter("surname");
     
        // Runs validation checks on registration details
        boolean isValid = validationCheck(username, password, email, firstname, surname);
        
        // Validates user
        if (isValid)
        {
            // Creates a new user with the details
            User us = new User();
            us.setCluster(cluster);
            us.RegisterUser(firstname, surname, email, username, password);
        }
        // Directs the user to the index page
	response.sendRedirect("/Instagrim");
        
    }
    
    // Checks the validation of the registration inputs
    protected boolean validationCheck(String firstname, String surname, String email, String username, String password)
    {
        // Sets up the session access
        Session thisSession = cluster.connect("Instagrim");
        PreparedStatement query = thisSession.prepare("SELECT * FROM userprofiles WHERE login =?");
        BoundStatement boundStatement  = new BoundStatement(query);
        
        ResultSet thisSet = thisSession.execute(boundStatement.bind(username));
        
        // Checks if the username already exists in the database, or not
        if (thisSet.isExhausted())
        {
            // Username validation
             if (username.length() < 8)
             {
                 return false;
             }
        }
        
        // Email validation
        if (!email.contains("@"))
        {
            return false;
        }
        return true;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
