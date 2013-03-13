import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Romhulus
 * Date: 13.3.13
 * Time: 16:08
 * To change this template use File | Settings | File Templates.
 */
public interface ManagerEntities {

    void addEntity(Disk disk);

    void addEntity(Book book);

    void removeEntity(Entity entity);

    void editEntity(Entity entity, int args);

    Entity findEntity(int args);

    List<Entity> getEntitiesList();

    List<Entity> getEntitiesList(Date releaseYear);

    List<Entity> getEntitiesList(GenreEnum genre);
}
