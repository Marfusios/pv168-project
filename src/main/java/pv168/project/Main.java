package pv168.project; /**
 * Created with IntelliJ IDEA.
 * User: Romhulus
 * Date: 2/27/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */


import org.apache.derby.jdbc.ClientDataSource;

import java.util.List;

public class Main {

    public static void main(String[] args) throws EntityException {

        try
        {
            ManagerDB.startServer();
            ManagerDB.createDatabase();

            //region DATASOURCE
            //Properties prop = new Properties();
            //prop.load(ClassLoader.getSystemResourceAsStream("pv168.project.config.properties"));

            ClientDataSource ds = new ClientDataSource();

            ds.setServerName("localhost");
            ds.setPortNumber(1527);
            ds.setDatabaseName("EvidencyDBEmbedded");
            ds.setUser("root");
            ds.setPassword("password");
          /*ds.setServerName(prop.getProperty("serverName"));
            ds.setPortNumber(Integer.parseInt(prop.getProperty("port")));
            ds.setDatabaseName(prop.getProperty("databaseName"));
            ds.setUser(prop.getProperty("user"));
            ds.setPassword(prop.getProperty("password"));    */
            //endregion

            ManagerEntities entitiesManager = new ManagerEntitiesImpl(ds);

            //DateFormat format = new SimpleDateFormat("EEE MMM dd yyyy", Locale.US);
            //entitiesManager.addEntity(new pv168.project.Disk("Take The Crown", format.parse("Sat Feb 17 2012"), "Robbie Williams", "Doma v obyvaku", pv168.project.GenreEnum.COMEDY, pv168.project.KindEnum.CD, pv168.project.TypeEnum.MUSIC));

            System.out.println("\n");
            List<Entity> tmp = entitiesManager.getEntitiesList();
            System.out.print("ID | NAME | AUTHOR | RELEASE YEAR | POSITION | GENRE \n");
            for(Entity one : tmp)
            {
                System.out.print(one.toString() + '\n');
            }
            System.out.println("\n");
        }
        /*catch(FileNotFoundException fex)
        {
            System.out.println("File not found " + fex.toString());
        }
        catch (IOException ioex)
        {
            System.out.println("IOException " + ioex.toString());
        }  */
        catch (Exception ex)
        {
            System.out.println("Exception " + ex.toString());
            ex.printStackTrace();
        }

        finally {
            ManagerDB.closeServer();
        }

    }
}