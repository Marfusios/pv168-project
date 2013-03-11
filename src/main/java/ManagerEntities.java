import java.util.Date;
import java.util.List;

public class ManagerEntities {

	private List<Entity> entitiesList;

	/**
	 * 
	 * @param disk
	 */
	public void addEntity(Disk disk) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param book
	 */
	public void addEntity(Book book) {
		throw new UnsupportedOperationException();
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
	 * @param entity
	 * @param args
	 */
	public void editEntity(Entity entity, int args) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param args
	 */
	private Entity findEntity(int args) {
		throw new UnsupportedOperationException();
	}

	public List<Entity> getEntitiesList() {
		return this.entitiesList;
	}

	/**
	 * 
	 * @param releaseYear
	 */
	public List<Entity> getEntitiesList(Date releaseYear) {
		return this.entitiesList;
	}

	/**
	 * 
	 * @param genre
	 */
	public List<Entity> getEntitiesList(GenreEnum genre) {
		return this.entitiesList;
	}

}