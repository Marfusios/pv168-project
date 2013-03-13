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

    void addEntity(Entity entity);

    void removeEntity(Entity entity);

    void editEntity(Entity oldEntity, Entity newEntity);

    Entity findEntity(int id);

    Entity findEntity(String name, String author);

    List<Entity> getEntitiesList();

    List<Entity> getEntitiesList(Date releaseYear);

    List<Entity> getEntitiesList(GenreEnum genre);

    List<Entity> getEntitiesList(String author);
}
