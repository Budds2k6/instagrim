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
import com.datastax.driver.core.Session;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
        String EncodedPassword = null;
        
        // Tries to decode password
        try
        {
            EncodedPassword = sha1handler.SHA1(password);
        }
        catch (UnsupportedEncodingException | NoSuchAlgorithmException et)
        {
            System.out.println("Can't check your password");
            return false;
        }
        
        // Creates a link to cassandra, and extracts data
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("insert into userprofiles (login, email, first_name, last_name, password) Values(?,?,?,?,?)");
       
        BoundStatement boundStatement = new BoundStatement(ps);
        session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        username,EncodedPassword));
        //We are assuming this always works.  Also a transaction would be good here !
        
        return true;
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
        PreparedStatement ps = session.prepare("select password from userprofiles where login =?");
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
