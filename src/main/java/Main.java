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

        //ManagerDB.startServer();
        //ManagerDB.createDatabase();

        ClientDataSource ds = new ClientDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(1527);
        ds.setDatabaseName("EvidencyDB");
        ds.setUser("admin");
        ds.setPassword("password");



        ManagerEntities entitiesManager = new ManagerEntitiesImpl(ds);

        entitiesManager.addEntity(new Book("Heeej", "hou"));

        List<Entity> tmp = entitiesManager.getEntitiesList();
        System.out.println(tmp.get(1).getName() + "  " + tmp.get(1).getAuthor());

        //ManagerDB.closeServer();
    }
}