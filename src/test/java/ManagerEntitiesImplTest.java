import org.apache.derby.jdbc.ClientDataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: m4r10
 * Date: 3/13/13
 * Time: 8:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ManagerEntitiesImplTest {

    ManagerEntitiesImpl manager;

    @Before
    public void setUp() {

        ManagerDB.startServer();

        ClientDataSource ds = new ClientDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(1527);
        ds.setDatabaseName("EvidencyDBEmbedded");
        ds.setUser("root");
        ds.setPassword("password");

        manager = new ManagerEntitiesImpl(ds);
    }


    @Test
     public void testAddEntityDisk() {
      try{
         try{
             manager.addEntity(null);
             fail("Manager added null object");
         }catch(NullPointerException ex){}

        /*Disk disk = new Disk("","");
        try{
         manager.addEntity(disk);
         fail("Entity disk with empty arguments was added to manager");
        }catch (IllegalArgumentException ex){}

        disk = new Disk("","Pedro");
        try{
            manager.addEntity(disk);
            fail("Entity disk with empty  first argument was added to manager");
        }catch (IllegalArgumentException ex){}

        disk = new Disk("top 10","");
        try{
            manager.addEntity(disk);
            fail("Entity disk with empty second argument was added to manager");
        }catch (IllegalArgumentException ex){}

        */
        Disk disk = new Disk("top 10","Pedro");
        manager.addEntity(disk);
        assertNotNull("Added entity don´t create ID",disk.getId());
        //Entity result = manager.findEntity(disk.getId());
        //assertNotNull("Added entities not found",result);

      }catch (EntityException ex){fail();}
    }


    @Test
    public void testFindEntityWithID(){
        try{
        Disk disk = new Disk("the best","Lolita");
        manager.addEntity(disk);
        long id=disk.getId();
        assertEquals("Found entity is not same with added entity",disk,manager.findEntity(id));
        manager.removeEntity(disk);
        assertNull("found an entity after remove it",manager.findEntity(id));
        }catch (EntityException ex){}
    }


    @Test
    public void testFindEntityWithNameAndAuthor(){
        try
        {
        Disk disk = new Disk("the best","Lolita");
        try{
            manager.addEntity(disk);
        }catch(EntityException ex ) {}
        assertEquals("Found entity is not same with added entity",disk,manager.findEntity("the best","Lolita"));
        assertNotSame("Found entity with wrong name is same with added entity",disk,manager.findEntity("best the","Lolita"));
        assertNotSame("Found entity with wrong author is same with added entity",disk,manager.findEntity("the best","Orange"));
        }
        catch (EntityException ex) {fail();}

    }



    @Test
    public void testRemoveEntity() throws Exception{
    try{
        try{
            manager.removeEntity(null);
            fail("Manager remove null object");
        }catch(NullPointerException ex){}

        Book tmpBook = new Book("Visual C#", "John Sharp");
        try{
            manager.addEntity(tmpBook);
        }catch(EntityException ex ) {}

        Entity found = manager.findEntity("Visual C#", "John Sharp");

        assertNotNull("Found book is null",found);
        assertEquals("Found book is not equal tmpBook", tmpBook, found);

        manager.removeEntity(found);
        found = manager.findEntity("Visual C#", "John Sharp");

        assertNull("Found book should be null after remove", found);
    }catch(EntityException ex){fail();}
    }


    @Test
    public void testGetEntitiesList()
    {
        try
        {
            List<Entity> tmpList = manager.getEntitiesList();

            assertNotNull(tmpList);
            if(!tmpList.isEmpty())
            {
                for(Entity one : tmpList)
                {
                    manager.removeEntity(one);
                }
            }

            tmpList = manager.getEntitiesList();
            assertTrue("Entity list isn't empty. Method removeEntity does not work correctly.", tmpList.isEmpty());

            Book tmpBook = new Book("The godfather","Mario Puzo");
            try{
                manager.addEntity(tmpBook);
            }catch(EntityException ex ) {}
            tmpList = manager.getEntitiesList();

            assertFalse("Entity list is empty. Method addEntity does not work correctly.", tmpList.isEmpty());
            assertTrue("In the list is more than one element.",tmpList.size() == 1);
            //assertEquals("The book from list is not equal tmpBook",tmpBook, tmpList.get(0));
        }
        catch (EntityException enEx)
        {
            fail("Cannot get entities list " + enEx.toString());
        }
    }


    @Test
    public void testEditEntity(){
        try{
        try{
            manager.editEntity(null, new Book("TestName", "TestAuthor"));
            fail("Manager modify null object");
        }catch(NullPointerException ex){}
        try{
        manager.addEntity(new Book("Visual C#", "John Sharp"));
        }catch (EntityException ex) {}
        Entity oldEntity = manager.findEntity("Visual C#", "John Sharp");
        assertNotNull("Found entity is null", oldEntity);

        manager.editEntity(oldEntity, new Book("C#", oldEntity.getAuthor()));
        Entity found = manager.findEntity("C#", oldEntity.getAuthor());
        assertNotNull("Modified entity is not in the DB", found);

        manager.editEntity(found , new Book(found.getName(), new Date(12), found.getAuthor(), "Position", GenreEnum.COMEDY, 580));
        found = manager.findEntity("C#", oldEntity.getAuthor());
        assertNotNull("Modified entity is not in the DB", found);

        assertEquals("Entity was modified badly", found.getPosition(), "Position");

    }catch (EntityException e) {fail();}
  }
}
