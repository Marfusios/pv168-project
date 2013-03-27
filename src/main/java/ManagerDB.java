import org.apache.derby.drda.NetworkServerControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement prestat = null;
        Statement stmt;

        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/EvidencyDB;user=admin;password=password;create=true");

           /* stmt = conn.createStatement();
            try
            {
                stmt.execute("CREATE TABLE entities(id int primary key, name varchar(20))");
            }
            catch (Exception e) {
                //e.printStackTrace();
            }

            try
            {
                stmt.execute("INSERT INTO newTestTable VALUES(10, 'Hey,'), (20, 'Look I changed'), (30, 'The code!')");
            }
            catch (Exception e) {

            }

            prestat.close();     */
            conn.close();

            return true;

        } catch (SQLException e) {
            log.error("Cannot create database",e);
            return false;
        } catch (Exception e) {
            log.error("Cannot create database",e);
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
