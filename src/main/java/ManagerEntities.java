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

    void addEntity(Entity entity) throws EntityException;

    void removeEntity(Entity entity) throws EntityException;

    void editEntity(Entity oldEntity, Entity newEntity) throws  EntityException;

    Entity findEntity(long id);

    Entity findEntity(String name, String author) throws EntityException;

    List<Entity> getEntitiesList() throws EntityException;

    List<Entity> getEntitiesList(Date releaseYear);

    List<Entity> getEntitiesList(GenreEnum genre);

    List<Entity> getEntitiesList(String author);
}
