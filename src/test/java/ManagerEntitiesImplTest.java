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
     public void testAddEntityDisk() throws Exception {

    }

    @Test
    public void testAddEntityBook() throws Exception {

    }

    @Test
    public void testRemoveEntity() throws Exception {

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

        Book tmpBook = new Book();
        manager.addEntity(tmpBook);
        tmpList = manager.getEntitiesList();

        assertFalse("Entity list is empty. Method addEntity does not work correctly.", tmpList.isEmpty());
        assertTrue("In the list is more than one element.",tmpList.size() == 1);
        assertEquals("The book from list is not equal tmpBook",tmpBook, tmpList.get(0));

    }
}
