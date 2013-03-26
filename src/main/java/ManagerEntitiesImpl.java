import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ManagerEntitiesImpl implements ManagerEntities {


    private final DataSource dataSource;

    public ManagerEntitiesImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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
        /*Connection con = null;
        PreparedStatement st = null;
        try {
            List<Book> books = new ArrayList<>();
            con = dataSource.getConnection();
            //začátek SQL operace
            st = con.prepareStatement("select * from books");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                books.add(new Book(id, name, author));
            }
            //konec SQL operace
            return books;
        } catch (SQLException e) {
            log.error("cannot select Entity", e);
            throw new EntityException("database select failed", e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    log.error("cannot close statement", e);
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    log.error("cannot close connection", e);
                }
            }
        }
    }   */
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