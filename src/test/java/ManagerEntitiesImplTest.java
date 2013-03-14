import org.junit.Before;
import org.junit.Test;

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
        manager = new ManagerEntitiesImpl();
    }

    @Test
     public void testAddEntityDisk() {

        Disk disk = new Disk("","");
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


        disk = new Disk("top 10","Pedro");
        manager.addEntity(disk);
        assertNotNull("Added entity don´t create ID",disk.getId());
        Entity result = manager.findEntity(disk.getId());
        assertNotNull("Added entitys not found",result);


    }
    @Test
    public void testFindEntityWithID(){
        Disk disk = new Disk("the best","Lolita");
        manager.addEntity(disk);
        int id=disk.getId();
        assertEquals("Finded entity is not same with added entity",disk,manager.findEntity(id));
        manager.removeEntity(disk);
        assertNull("found an entity after remove it",manager.findEntity(id));

    }
    @Test
    public void testFindEntityWithNameAndAuthor(){
        Disk disk = new Disk("the best","Lolita");
        manager.addEntity(disk);
        assertEquals("Finded entity is not same with added entity",disk,manager.findEntity("the best","Lolita"));
        assertNotSame("Finded entity with wrong name is same with added entity",disk,manager.findEntity("best the","Lolita"));
        assertNotSame("Finded entity with wrong author is same with added entity",disk,manager.findEntity("the best","Orange"));

    }


    @Test
    public void testRemoveEntity() throws Exception {
        Book tmpBook = new Book("Visual C#", "John Sharp");
        manager.addEntity(tmpBook);

        Entity finded = manager.findEntity("Visual C#", "John Sharp");

        assertNotNull("Finded book is null",finded);
        assertEquals("Finded book is not equal tmpBook", tmpBook, finded);

        manager.removeEntity(finded);
        finded = manager.findEntity("Visual C#", "John Sharp");

        assertNull("Finded book should be null after remove", finded);
    }

    @Test
    public void testGetEntitiesList() {

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

        Book tmpBook = new Book("Zarivka","Lampa");
        manager.addEntity(tmpBook);
        tmpList = manager.getEntitiesList();

        assertFalse("Entity list is empty. Method addEntity does not work correctly.", tmpList.isEmpty());
        assertTrue("In the list is more than one element.",tmpList.size() == 1);
        assertEquals("The book from list is not equal tmpBook",tmpBook, tmpList.get(0));

    }
}
