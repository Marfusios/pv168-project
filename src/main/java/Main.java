/**
 * Created with IntelliJ IDEA.
 * User: Romhulus
 * Date: 2/27/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */


import org.apache.derby.jdbc.ClientDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws EntityException {

        try
        {
            ManagerDB.startServer();
            //ManagerDB.createDatabase();

            //region DATASOURCE
            Properties prop = new Properties();
            prop.load(new FileInputStream("src/config.properties"));

            ClientDataSource ds = new ClientDataSource();
            ds.setServerName(prop.getProperty("serverName"));
            ds.setPortNumber(Integer.parseInt(prop.getProperty("port")));
            ds.setDatabaseName(prop.getProperty("databaseName"));
            ds.setUser(prop.getProperty("user"));
            ds.setPassword(prop.getProperty("password"));
            //endregion

            ManagerEntities entitiesManager = new ManagerEntitiesImpl(ds);

            /*DateFormat format = new SimpleDateFormat("EEE MMM dd yyyy", Locale.US);
            entitiesManager.addEntity(new Disk("Take The Crown", format.parse("Sat Feb 17 2012"), "Robbie Williams", "Doma v obyvaku", GenreEnum.COMEDY, KindEnum.CD, TypeEnum.MUSIC));   */


            System.out.println("\n");
            List<Entity> tmp = entitiesManager.getEntitiesList();
            System.out.print("ID | NAME | AUTHOR | RELEASE YEAR | POSITION | GENRE \n");
            for(Entity one : tmp)
            {
                System.out.print(one.toString() + '\n');
            }
            System.out.println("\n");

        }

        catch(FileNotFoundException fex)
        {
            System.out.println("File not found " + fex.toString());
        }
        catch (IOException ioex)
        {
            System.out.println("IOException " + ioex.toString());
        }
        catch (Exception ex)
        {
            System.out.println("Exception " + ex);
            ex.printStackTrace();
        }

        finally {
            ManagerDB.closeServer();
        }

    }
}