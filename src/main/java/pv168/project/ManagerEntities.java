package pv168.project;

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

    void addEntity(Entity entity) throws EntityException,NullPointerException,IllegalArgumentException;

    void removeEntity(Entity entity) throws EntityException;

    void removeAll() throws EntityException;

    void editEntity(Entity oldEntity, Entity newEntity) throws  EntityException;

    Entity findEntity(long id) throws EntityException;

    Entity findEntity(String name, String author) throws EntityException;

    List<Entity> getEntitiesList() throws EntityException;

    List<Entity> getEntitiesList(Date releaseYear) throws EntityException;

    List<Entity> getEntitiesList(GenreEnum genre) throws EntityException;

    List<Entity> getEntitiesList(String author) throws EntityException;

    List<Entity> getBooksList() throws EntityException;

    List<Entity> getDisksList() throws EntityException;
}
