/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.aec.instagrim.models;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import com.datastax.driver.core.Session;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;
import uk.ac.dundee.computing.aec.instagrim.lib.AeSimpleSHA1;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;

/**
 *
 * @author Administrator
 */
public class User
{
    Cluster cluster;
    public User()
    {
        // Constructor
    }
    
    // Registers a new user
    public boolean RegisterUser(String firstname, String surname, String email, String username, String password)
    {
        AeSimpleSHA1 sha1handler = new AeSimpleSHA1();
        String encodedPassword;
        
        // Adds the email to a set for the database
        Set<String> thisEmail = new HashSet<>();
        thisEmail.add(email);        
        
        // Tries to decode password
        try
        {
            encodedPassword = sha1handler.SHA1(password);
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException et)
        {
            System.out.println("Can't check your password");
            return false;
        }
        
        // Creates a link to cassandra, and extracts data
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("INSERT INTO userprofiles (login, email, first_name, last_name, password) VALUES(?,?,?,?,?)");
       
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username, thisEmail, firstname, surname, encodedPassword));
        //We are assuming this always works.  Also a transaction would be good here !
        
        return true;
    }
    
    // Updates the details of the user
    public boolean updateUserDetails(String firstname, String surname, String email, String username)
    {
        // Establishes connection to Cassandra
        Session thisCluster = cluster.connect("instagrim");
        
        // Adds the email to a set for the database
        Set<String> thisEmail = new HashSet<>();
        thisEmail.add(email); 
        
        // Creates a link to cassandra, and extracts data
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("UPDATE userprofiles SET (email = ?, first_name = ?, last_name = ?) WHERE login = ?");
       
        // Executes statement
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        thisEmail, firstname, surname, username));
        
        return true;
    }
    
    // Returns the user profile information
    public ProfileDetails getUserProfile(String username)
    {
        // To hold the returned information
        ProfileDetails userDetails = null;
        
        System.out.println(username);
        
        // Establishes connection to database
        Session thisSession = cluster.connect("instagrim");
        PreparedStatement query = thisSession.prepare("SELECT * FROM userprofiles WHERE login = ?");
        ResultSet queryResult;
        BoundStatement boundQuery = new BoundStatement(query);
        
        // Execute query
        queryResult = thisSession.execute(boundQuery.bind(username));
        
        // Extracts all information from query
        if (!queryResult.isExhausted())
        {
            for (Row dataLine : queryResult)
            {
                String firstname = dataLine.getString("first_name");
                String surname = dataLine.getString("last_name");
                UUID profilePic = dataLine.getUUID("profile_pic");
                Set<String> email = dataLine.getSet("email", String.class);  
                
                System.out.println("GET Firstname: " + firstname + " Surname: " + surname + " Email: " + email + " FuckYou: " + profilePic);
                
                // Adds the user details to a new class
                userDetails = new ProfileDetails (username, firstname, surname, profilePic, email);
            }
        }
        
        // Returns the user class
        return userDetails;
    }
    
    
    // Checks if the user is valid
    public boolean IsValidUser(String username, String password)
    {
        // Secure hash
        AeSimpleSHA1 sha1handler =  new AeSimpleSHA1();
        String EncodedPassword = null;
        
        // Attempt to password check
        try 
        {
            EncodedPassword= sha1handler.SHA1(password);
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException et)
        {
            System.out.println("Can't check your password");
            return false;
        }
        
        // Cassandra query
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select password from userprofiles where login = ?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username));
        
        // Checks for images
        if (rs.isExhausted())
        {
            System.out.println("No Images returned");
            return false;
        }
        else
        {
            for (Row row : rs)
            {
                String StoredPass = row.getString("password");
                if (StoredPass.compareTo(EncodedPassword) == 0)
                { return true; }
            }
        }
        
        return false;  
    }
    
    // Updates the cluster
    public void setCluster(Cluster cluster)
    {
     this.cluster = cluster;
    } 
}
