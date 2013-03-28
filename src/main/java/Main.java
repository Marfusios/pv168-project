/**
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
        ClientDataSource ds = new ClientDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(1527);
        ds.setDatabaseName("EvidencyDBEmbedded");
        ds.setUser("root");
        ds.setPassword("password");
        //endregion

        ManagerEntities entitiesManager = new ManagerEntitiesImpl(ds);

        //entitiesManager.addEntity(new Book("Heeej", "hou"));

        List<Entity> tmp = entitiesManager.getEntitiesList();
        System.out.print("ID | NAME | AUTHOR | RELEASE YEAR \n");
        for(Entity one : tmp)
        {
            System.out.print(one.toString() + '\n');
        }
        }

        finally {
            ManagerDB.closeServer();
        }

    }
}