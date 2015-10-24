package uk.ac.dundee.computing.aec.instagrim.models;

/*
 * Expects a cassandra columnfamily defined as
 * use keyspace2;
 CREATE TABLE Tweets (
 user varchar,
 interaction_time timeuuid,
 tweet varchar,
 PRIMARY KEY (user,interaction_time)
 ) WITH CLUSTERING ORDER BY (interaction_time DESC);
 * To manually generate a UUID use:
 * http://www.famkruithof.net/uuid/uuidgen
 */
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.Bytes;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import static org.imgscalr.Scalr.*;
import org.imgscalr.Scalr.Method;

import uk.ac.dundee.computing.aec.instagrim.lib.*;
import uk.ac.dundee.computing.aec.instagrim.stores.Pic;
//import uk.ac.dundee.computing.aec.stores.TweetStore;

// Defines the picture attributes
public class PicModel
{
    Cluster cluster;
    

    public void PicModel()
    {
        // Constructor
    }

    // Sets the cluster
    public void setCluster(Cluster cluster)
    {
        this.cluster = cluster;
    }
    
    // Obtains, and returns the profile picture for the user
    public UUID getProfilePic(String username)
    {
        // Sets up session access
        Session thisSession = cluster.connect("instagrim");
        PreparedStatement thisRequest = thisSession.prepare("SELECT profile_pic from userprofiles WHERE login = ?");
        ResultSet result = null;
        BoundStatement boundStatement = new BoundStatement(thisRequest);
        
        // Obtains the search results
        result = thisSession.execute(boundStatement.bind(username));
        
        // If end of search
        if (result.isExhausted())
        {
            System.out.println("No profile picture found!");
            return null;
        }
        else // If picture found
        {
            // Iterates through found rows (only one)
            for (Row picRow : result)
            {
                // Return UUID of picture
                UUID picID = picRow.getUUID("profile_pic");
                return picID;
            }
        }    
        return null;
    }

    // *** I've no idea what's going on here ***
    public void insertPic(byte[] b, String type, String name, String user)
    {
        try
        {
            Convertors convertor = new Convertors();

            String types[] = Convertors.SplitFiletype(type);
            ByteBuffer buffer = ByteBuffer.wrap(b);
            int length = b.length;
            java.util.UUID picID = convertor.getTimeUUID();
            
            //The following is a quick and dirty way of doing this, will fill the disk quickly !
            Boolean success = (new File("/var/tmp/instagrim/")).mkdirs();
            FileOutputStream output = new FileOutputStream(new File("/var/tmp/instagrim/" + picID));

            //*** Thumbnail handling ? ***
            output.write(b);
            byte []  thumb = picReSize(picID.toString(),types[1]);
            int thumbLength = thumb.length;
            ByteBuffer thumbBuff = ByteBuffer.wrap(thumb);
            byte[] processeDB = picDecolour(picID.toString(),types[1]);
            ByteBuffer processedBuff = ByteBuffer.wrap(processeDB);
            int processedLength = processeDB.length;
            Session session = cluster.connect("instagrim");

            // Adds picture to the database (setup)
            PreparedStatement psInsertPic = session.prepare("insert into pics ( picid, image, thumb, processed, user, interaction_time, imagelength, thumblength, processedlength, type, name) values(?,?,?,?,?,?,?,?,?,?,?)");
            PreparedStatement psInsertPicToUser = session.prepare("insert into userpiclist ( picid, user, pic_added) values(?,?,?)");
            BoundStatement bsInsertPic = new BoundStatement(psInsertPic);
            BoundStatement bsInsertPicToUser = new BoundStatement(psInsertPicToUser);

            Date DateAdded = new Date();
            
            // Adds picture to the database (execution)
            session.execute(bsInsertPic.bind(picID, buffer, thumbBuff,processedBuff, user, DateAdded, length,thumbLength,processedLength, type, name));
            session.execute(bsInsertPicToUser.bind(picID, user, DateAdded));
            session.close();

        }
        catch (IOException ex)
        {
            System.out.println("Error --> " + ex);
        }
    }

    // Resizes the thumbnail
    public byte[] picReSize(String picid,String type)
    {
        try
        {
            BufferedImage BI = ImageIO.read(new File("/var/tmp/instagrim/" + picid));
            BufferedImage thumbnail = createThumbnail(BI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(thumbnail, type, baos);
            baos.flush();
            
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            
            return imageInByte;
        }
        catch (IOException et)
        {}
        
        return null;
    }
    
    // Re-colours the image
    public byte[] picDecolour(String picid,String type)
    {
        try
        {
            BufferedImage BI = ImageIO.read(new File("/var/tmp/instagrim/" + picid));
            BufferedImage processed = createProcessed(BI);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(processed, type, baos);
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        }
        catch (IOException et)
        {}
        
        return null;
    }

    // Creates a thumbnail of the image
    public static BufferedImage createThumbnail(BufferedImage img)
    {
        img = resize(img, Method.SPEED, 250, OP_ANTIALIAS, OP_GRAYSCALE);
         //Let's add a little border before we return result.
        return pad(img, 2);
    }
    
    // 
    public static BufferedImage createProcessed(BufferedImage img)
    {
        int width = img.getWidth()-1;
        img = resize(img, Method.SPEED, width, OP_ANTIALIAS, OP_GRAYSCALE);
        return pad(img, 4);
    }
   
    // Returns a list of pictures for the user
    public java.util.LinkedList<Pic> getPicsForUser(String user)
    {
        java.util.LinkedList<Pic> Pics = new java.util.LinkedList<>();
        Session session = cluster.connect("instagrim");
        PreparedStatement ps = session.prepare("select picid from userpiclist where user = ?");
        ResultSet rs = null;
        BoundStatement boundStatement = new BoundStatement(ps);
        rs = session.execute( // this is where the query is executed
                boundStatement.bind( // here you are binding the 'boundStatement'
                        user));
        
        if (rs.isExhausted())
        {
            System.out.println("No Images returned");
            return null;
        }
        else
        {
            for (Row row : rs)
            {
                Pic pic = new Pic();
                java.util.UUID UUID = row.getUUID("picid");
                System.out.println("UUID" + UUID.toString());
                pic.setUUID(UUID);
                Pics.add(pic);

            }
        }
        return Pics;
    }

    public Pic getPic(int image_type, java.util.UUID picid)
    {
        Session session = cluster.connect("instagrim");
        ByteBuffer bImage = null;
        String type = null;
        int length = 0;
        try
        {
            Convertors convertor = new Convertors();
            ResultSet rs = null;
            PreparedStatement ps = null;
         
            if (image_type == Convertors.DISPLAY_IMAGE)
            {
                ps = session.prepare("select image,imagelength,type from pics where picid =?");
            }
            else if (image_type == Convertors.DISPLAY_THUMB)
            {
                ps = session.prepare("select thumb,imagelength,thumblength,type from pics where picid =?");
            }
            else if (image_type == Convertors.DISPLAY_PROCESSED)
            {
                ps = session.prepare("select processed,processedlength,type from pics where picid =?");
            }
            
            BoundStatement boundStatement = new BoundStatement(ps);
            rs = session.execute( // this is where the query is executed
                    boundStatement.bind( // here you are binding the 'boundStatement'
                            picid));

            if (rs.isExhausted())
            {
                System.out.println("No Images returned");
                return null;
            }
            else
            {
                for (Row row : rs)
                {
                    if (image_type == Convertors.DISPLAY_IMAGE)
                    {
                        bImage = row.getBytes("image");
                        length = row.getInt("imagelength");
                    }
                    else if (image_type == Convertors.DISPLAY_THUMB)
                    {
                        bImage = row.getBytes("thumb");
                        length = row.getInt("thumblength");
                
                    }
                    else if (image_type == Convertors.DISPLAY_PROCESSED)
                    {
                        bImage = row.getBytes("processed");
                        length = row.getInt("processedlength");
                    }
                    
                    type = row.getString("type");

                }
            }
        }
        catch (Exception et)
        {
            System.out.println("Can't get Pic" + et);
            return null;
        }
        
        session.close();
        Pic p = new Pic();
        p.setPic(bImage, length, type);

        return p;
    }

}
