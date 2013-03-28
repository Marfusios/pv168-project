import org.apache.derby.drda.NetworkServerControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
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

    final static Logger log = LoggerFactory.getLogger(ManagerEntitiesImpl.class);
    static NetworkServerControl server;

    public static boolean startServer()
    {
        try {
            ManagerDB.server = new NetworkServerControl(InetAddress.getByName("localhost"), 1527);
            ManagerDB.server.start(null);
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
        } catch (InstantiationException e) {
           log.error("EmbeddedDriver not found", e);
        } catch (IllegalAccessException e) {
            log.error("EmbeddedDriver not found 2", e);
        } catch (ClassNotFoundException e) {
            log.error("EmbeddedDriver not found 3", e);
        }

        Connection conn = null;
        PreparedStatement prestat = null;
        Statement stmt;

        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/EvidencyDB;user=admin;password=password;create=true");

            stmt = conn.createStatement();
            try
            {
                stmt.execute("CREATE TABLE entities (id INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT entities_pk PRIMARY KEY, name VARCHAR(64), author VARCHAR(32), releaseYear DATE, position VARCHAR(128), genre VARCHAR(32) CONSTRAINT genre_fk REFERENCES genres)");
                stmt.execute("CREATE TABLE genres (genre VARCHAR(32) NOT NULL CONSTRAINT genres_pk PRIMARY KEY )");
                stmt.execute("CREATE TABLE books (id INT CONSTRAINT entities_id REFERENCES entities, pageCount INT)");
                stmt.execute("CREATE TABLE disks (id INT CONSTRAINT disk_id_fk REFERENCES entities, kind VARCHAR(32) CONSTRAINT kind_ck CHECK (kind IN ('cd', 'dvd', 'blue-ray')), type VARCHAR(32) CONSTRAINT type_ck CHECK (type IN ('film', 'series', 'data', 'music', 'game')))");
            }
            catch (Exception e) {
                log.error("Cannot create table", e);
            }

            try
            {
                stmt.execute("INSERT INTO genres (genre) VALUES ('COMEDY')");
                stmt.execute("INSERT INTO genres (genre) VALUES ('THRILLER')");
                stmt.execute("INSERT INTO genres (genre) VALUES ('DRAMA')");
                stmt.execute("INSERT INTO genres (genre) VALUES ('POETRY')");
            }
            catch (Exception e) {
                log.error("Cannot insert values", e);
            }

            prestat.close();
            conn.close();

            return true;

        } catch (SQLException e) {
            log.error("Cannot create database",e);
            return false;
        } catch (Exception e) {
            log.error("Cannot create database 2",e);
            return false;
        }
        finally{
            if (prestat != null){
                try { prestat.close();} catch (SQLException e){}
            }
            if (conn != null){
                try {conn.close();} catch(SQLException e) {}
            }
        }
    }
}
