/**
 * Created with IntelliJ IDEA.
 * User: Romhulus
 * Date: 2/27/13
 * Time: 11:48 AM
 * To change this template use File | Settings | File Templates.
 */

import org.apache.derby.jdbc.ClientDataSource;

public class Main {

    public static void main(String[] args) throws EntityException {
        //String url = "jdbc:derby://localhost:1527/MojeDB";

        ClientDataSource ds = new ClientDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(1527);
        ds.setDatabaseName("EvidencyDB");
        ds.setUser("admin");
        ds.setPassword("password");

        ManagerEntities bookManager = new ManagerEntitiesImpl(ds);

        //List<Book> allBooks = bookManager.getAllBooks();
        //System.out.println("allBooks = " + allBooks);
    }
}