/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Package import
package uk.ac.dundee.computing.aec.instagrim.stores;

/**
 *
 * @author: Tristan Haley
 * @template: Andy Cobley
 */

// Base class for login handling
public class LoggedIn {
    boolean loggedIn = false;
    String Username = null;
    public void LoggedIn()
    { 
        // Constructor
    }
    
    // Sets the username
    public void setUsername(String name)
    {
        this.Username=name;
    }
    
    // Returns the Username
    public String getUsername()
    {
        return Username;
    }
    
    // Sets the status to logged in
    public void setLoggedIn()
    {
        loggedIn = true;
    }
    
    // Sets the status to logged out
    public void setLoggedOut()
    {
        loggedIn = false;
    }
    
    // Sets the status to the parameter
    public void setLoginState(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
    }
    
    // Returns the logged in state
    public boolean getLoggedIn()
    {
        return loggedIn;
    }
}
