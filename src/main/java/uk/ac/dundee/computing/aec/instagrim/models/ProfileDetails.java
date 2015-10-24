/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.aec.instagrim.models;

import java.util.Set;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tristan
 */
// Stores details of the user profile
public class ProfileDetails
{
    String username;
    String firstname;
    String surname;
    UUID profilePic;
    Set<String> email;
    
  public ProfileDetails()
  {
      // Constructor - Shell
  }
  
  // Constructor Two
  public ProfileDetails(String username, String firstname, String surname, UUID profilePic, Set<String> email)
  {
      this.username = username;
      this.firstname = firstname;
      this.surname = surname;
      this.email = email;
      this.profilePic = profilePic;
  }
  
  // Accessor Username
  public String getUsername()
  { return username; }
  
  // Accessor Firstname
  public String getFirstname()
  { return firstname; }
  
  // Accessor Surname
  public String getSurname()
  { return surname; }
  
  // Accessor Email
  public Set<String> getEmail()
  { return email; }
  
  // Accessor profilePic
  public UUID getProfilePic()
  { return profilePic; }
}
