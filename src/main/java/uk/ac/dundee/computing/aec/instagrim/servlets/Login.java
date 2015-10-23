/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
//import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.aec.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.aec.instagrim.models.User;
import uk.ac.dundee.computing.aec.instagrim.stores.LoggedIn;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet
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
        // Obtains username + password from Cassandra
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        
        // Creates a new user, and requests session information
        User us = new User();
        us.setCluster(cluster);
        boolean isValid = us.IsValidUser(username, password);
        HttpSession session = request.getSession();
        
        System.out.println("Session in servlet " + session);
        
        // If login is valid
        if (isValid)
        {
            // Updates login instance
            LoggedIn lg = new LoggedIn();
            lg.setLoginState(true);
            lg.setUsername(username);
            lg.setFirstname(firstname);
            lg.setSurname(surname);
            lg.setEmail(email);
            //request.setAttribute("LoggedIn", lg);
            
            // Saves login instance to session
            session.setAttribute("LoggedIn", lg);
            System.out.println("Session in servlet " + session);
            
            // Directs to profile page
            RequestDispatcher rd = request.getRequestDispatcher("Profile.jsp");
	    rd.forward(request, response);
        }
        else
        {
            response.sendRedirect("/Instagrim/login.jsp");
        }
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
