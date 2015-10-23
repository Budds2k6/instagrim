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
    String username = null;
    String firstname = null;
    String surname = null;
    String email = null;
    public void LoggedIn()
    { 
        // Constructor
    }
    
    // Sets the username
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    // Sets the Firstname
    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }
    
    // Sets the Surname
    public void setSurname (String surname)
    {
        this.surname = surname;
    }
    
    // Sets the Email
    public void setEmail (String email)
    {
        this.email = email;
    }
    
    // Returns the Username
    public String getUsername()
    { return username; }
    
    // Returns Firstname
    public String getFirstname()
    { return firstname; }
    
    // Returns Surname
    public String getSurname()
    { return surname; }
    
    // Returns Email
    public String getEmail()
    { return email; }
       
    // Returns login status
    public boolean getLoggedIn()
    { return loggedIn; }
        
    // Sets the status to the parameter
    public void setLoginState(boolean loggedIn)
    {
        this.loggedIn = loggedIn;
    }
    
    // Returns the logged in state

}
