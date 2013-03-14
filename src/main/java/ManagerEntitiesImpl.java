import java.util.Date;
import java.util.List;


public class ManagerEntitiesImpl implements ManagerEntities {

	/**
	 * 
	 * @param entity
	 */
	public void addEntity(Entity entity) {
		//throw new UnsupportedOperationException();
	}


	/**
	 * 
	 * @param entity
	 */
	public void removeEntity(Entity entity) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param oldEntity
	 * @param newEntity
	 */
	public void editEntity(Entity oldEntity, Entity newEntity) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param id
	 */
	public Entity findEntity(int id) {
		Book tmp = new Book("Name", "Test");
        tmp.setId(0);

        return tmp;
	}

    /**
     *
     * @param name
     * @param author
     */
    public Entity findEntity(String name, String author) {
        throw new UnsupportedOperationException();
    }


	public List<Entity> getEntitiesList() {
        throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param releaseYear
	 */
	public List<Entity> getEntitiesList(Date releaseYear) {
        throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param genre
	 */
	public List<Entity> getEntitiesList(GenreEnum genre) {
        throw new UnsupportedOperationException();
	}

    /**
     *
     * @param author
     */
    public List<Entity> getEntitiesList(String author) {
        throw new UnsupportedOperationException();
    }
}