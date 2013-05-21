package pv168.project;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.derby.drda.*;
import java.util.Properties;
import java.net.InetAddress;
import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: m4r10
 * Date: 3/27/13
 * Time: 8:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManagerDB {

    final static Logger log = LoggerFactory.getLogger(ManagerDB.class);
    static NetworkServerControl server;


    public static boolean startServer()
    {
        try {
            ManagerDB.server = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
            ManagerDB.server.start(null);
            ManagerDB.log.info("SERVER STARTED");
            return true;
        } catch (Exception e1) {
            ManagerDB.log.error("Cannot start server", e1);
            return false;
        }
    }


    public static boolean closeServer()
    {
        try
        {
            ManagerDB.server.shutdown();
            ManagerDB.log.info("SERVER STOPPED");
            return true;
        }
        catch (Exception ex)
        {
            ManagerDB.log.error("Cannot shutdown server", ex);
            return false;
        }
    }


    public static boolean createDatabase()
    {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            ManagerDB.log.info("Class was found");
        } catch (InstantiationException e) {
            ManagerDB.log.error("EmbeddedDriver not found", e);
        } catch (IllegalAccessException e) {
            ManagerDB.log.error("EmbeddedDriver not found 2", e);
        } catch (ClassNotFoundException e) {
            ManagerDB.log.error("EmbeddedDriver not found 3", e);
        }

        Connection conn = null;
        Statement stmt;

        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/EvidencyDBEmbedded;user=root;password=password;create=true");
            ManagerDB.log.info("Connection established");

            stmt = conn.createStatement();
            try
            {
                stmt.execute("CREATE TABLE genres (genre VARCHAR(32) NOT NULL CONSTRAINT genres_pk PRIMARY KEY )");
                ManagerDB.log.info("Created table: genres");

                stmt.execute("CREATE TABLE entities (id INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT entities_pk PRIMARY KEY, name VARCHAR(64), author VARCHAR(32), releaseYear DATE, position VARCHAR(128), genre VARCHAR(32) CONSTRAINT genre_fk REFERENCES genres)");
                ManagerDB.log.info("Created table: entities");

                stmt.execute("CREATE TABLE books (id INT CONSTRAINT entities_id REFERENCES entities, pageCount INT)");
                ManagerDB.log.info("Created table: books");

                /*stmt.execute("DROP TABLE disks");
                pv168.project.ManagerDB.log.info("Deleted table: disks"); */

                stmt.execute("CREATE TABLE disks (id INT CONSTRAINT disk_id_fk REFERENCES entities, kind VARCHAR(32) CONSTRAINT kind_ck CHECK (kind IN ('CD', 'DVD', 'BLUE-RAY')), type VARCHAR(32) CONSTRAINT type_ck CHECK (type IN ('FILM', 'SERIES', 'DATA', 'MUSIC', 'GAME')))");
                ManagerDB.log.info("Created table: disks");
            }
            catch (Exception e) {
                ManagerDB.log.info("Tables already created");
            }

            try
            {

                stmt.execute("INSERT INTO genres (genre) VALUES ('COMEDY')");
                stmt.execute("INSERT INTO genres (genre) VALUES ('THRILLER')");
                stmt.execute("INSERT INTO genres (genre) VALUES ('DRAMA')");
                stmt.execute("INSERT INTO genres (genre) VALUES ('POETRY')");
                log.info("Values was insert");
            }
            catch (Exception e) {
                ManagerDB.log.info("Values already added");
            }

            conn.close();
            ManagerDB.log.error("Connection was closed");
            return true;

        } catch (SQLException e) {
            ManagerDB.log.error("Cannot create database",e);
            return false;
        } catch (Exception e) {
            ManagerDB.log.error("Cannot create database 2",e);
            return false;
        }
        finally{
            if (conn != null){
                try {conn.close();} catch(SQLException e) {}
            }
        }
    }
}
