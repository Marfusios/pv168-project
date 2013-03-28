import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ManagerEntitiesImpl implements ManagerEntities {


    //region PROPERTIES & FIELDS
    final static Logger log = LoggerFactory.getLogger(ManagerEntitiesImpl.class);
    private final DataSource dataSource;

    //endregion


    //region CONSTRUCTORS
    public ManagerEntitiesImpl(DataSource dataSource) {
        if(dataSource == null) throw new IllegalArgumentException("dataSource");

        this.dataSource = dataSource;
    }

    //endregion


    //region METHODS

    /**
	 * 
	 * @param entity
	 */
	public void addEntity(Entity entity) throws EntityException {
        Connection con = null;
        PreparedStatement st = null;
        Long id;
        try {
            con = dataSource.getConnection();
            //začátek SQL operace
            st = con.prepareStatement("insert into entities (name,author,releaseyear,position,genre) values (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, entity.getName());
            st.setString(2, entity.getAuthor());
            st.setDate(3,(java.sql.Date)entity.getReleaseYear());
            st.setString(4,entity.getPosition());
            st.setString(5,entity.getGenre().toString());
            st.executeUpdate();
            ResultSet keys = st.getGeneratedKeys();
            if (keys.next()) {
                id = keys.getLong(1);
                entity.setId(id);
            }

                if(entity instanceof Book){
                     Book book=(Book)entity;
                     st = con.prepareStatement("insert into books (id,pagecount) values (id,?)",PreparedStatement.NO_GENERATED_KEYS) ;
                     st.setInt(1,book.getPageCount());
                     st.executeUpdate();
                }
                if(entity instanceof Disk){
                   Disk disk = (Disk) entity;
                    st = con.prepareStatement("insert into disks (id,kind,type) values (id,?)",PreparedStatement.NO_GENERATED_KEYS);
                    st.setString(1,disk.getKind().toString());
                    st.setString(2,disk.getType().toString());
                    st.executeUpdate();
                }

        } catch (SQLException e) {
            log.error("cannot insert entity", e);
            throw new EntityException("database insert failed", e);
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
	}


	/**
	 * 
	 * @param entity
	 */
	public void removeEntity(Entity entity) throws EntityException {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = dataSource.getConnection();
            //začátek SQL operace
            if(entity instanceof Book)
                st = con.prepareStatement("DELETE FROM books WHERE id =?");
            if(entity instanceof Disk)
                st=con.prepareStatement("DELETE FROM disks WHERE id=?");
            st.setLong(1, entity.getId());
            st.executeUpdate();

            st = con.prepareStatement("DELETE FROM entities WHERE id=?");
            st.setLong(1, entity.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            log.error("cannot remove entity", e);
            throw new EntityException("database insert failed", e);
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
    }
	/**
	 * 
	 * @param oldEntity
	 * @param newEntity
	 */
	public void editEntity(Entity oldEntity, Entity newEntity) throws EntityException {
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = dataSource.getConnection();
            //začátek SQL operace
            st = con.prepareStatement("update entities set name=?, author=?, releaseyear=?, position=?, genre=? where id=?");
            st.setString(1, newEntity.getName());
            st.setString(2, newEntity.getAuthor());
            st.setDate(3,(java.sql.Date)newEntity.getReleaseYear());
            st.setString(4,newEntity.getPosition());
            st.setString(5,newEntity.getGenre().toString());
            st.setLong(6,oldEntity.getId());
            int n = st.executeUpdate();
            if(n!=1) {
                throw new EntityException("not updated entity with id "+oldEntity.getId(),null);
            }
            if(newEntity instanceof Book){
                Book book=(Book)newEntity;
                if(oldEntity instanceof Book){
                    st = con.prepareStatement("update books set pagecount=? values (?) where id=?") ;
                    st.setInt(1,book.getPageCount());
                    st.setLong(2,oldEntity.getId());
                }else{
                    st = con.prepareStatement("insert into books (id,pagecount) values (?,?)",PreparedStatement.NO_GENERATED_KEYS);
                    st.setLong(1,oldEntity.getId());
                    st.setInt(2,book.getPageCount());
                }
                st.executeUpdate();
                if(n!=1) {
                    throw new EntityException("not updated entity with id "+oldEntity.getId(),null);
                }
            }
            if(newEntity instanceof Disk){
                Disk disk=(Disk)newEntity;
                if(oldEntity instanceof Disk){
                    st = con.prepareStatement("update disks set kind=?, type=? values (?,?) where id=?") ;
                    st.setString(1,disk.getKind().toString());
                    st.setString(2,disk.getType().toString());
                    st.setLong(3,oldEntity.getId());
                }else{
                    st = con.prepareStatement("insert into disks (id,kind,type) values (?,?,?)",PreparedStatement.NO_GENERATED_KEYS);
                    st.setLong(1,oldEntity.getId());
                    st.setString(2,disk.getKind().toString());
                    st.setString(3,disk.getType().toString());
                }
                st.executeUpdate();
                if(n!=1) {
                    throw new EntityException("not updated entity with id "+oldEntity.getId(),null);
                }
            }
        } catch (SQLException e) {
            log.error("cannot update entity", e);
            throw new EntityException("database update failed", e);
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
	}

	/**
	 * 
	 * @param id
	 */
	public Entity findEntity(long id) {
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


	public List<Entity> getEntitiesList() throws EntityException {
        Connection con = null;
        PreparedStatement st = null;
        try {
            List<Entity> entities = new ArrayList<>();
            con = dataSource.getConnection();

            //SQL operation for BOOKS
            st = con.prepareStatement("select * from entities natural join books");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                Date releaseYear = rs.getDate("releaseYear");
                String position = rs.getString("position");
                GenreEnum genre = null;
                if(rs.getString("genre") != null)
                     genre = GenreEnum.valueOf(rs.getString("genre"));
                int pageCount = rs.getInt("pageCount");

                Book tmp = new Book(name, releaseYear, author, position, genre, pageCount);
                tmp.setId(id);
                entities.add(tmp);
            }

            //SQL operation for DISKS
            st = con.prepareStatement("select * from entities natural join disks");
            rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                Date releaseYear = rs.getDate("releaseYear");
                String position = rs.getString("position");

                GenreEnum genre = null;
                if(rs.getString("genre") != null)
                    genre = GenreEnum.valueOf(rs.getString("genre"));

                KindEnum kind = null;
                if(rs.getString("kind") != null)
                    kind = KindEnum.valueOf(rs.getString("kind"));

                TypeEnum type = null;
                if(rs.getString("type") != null)
                    type = TypeEnum.valueOf(rs.getString("type"));

                Disk tmp = new Disk(name, releaseYear, author, position, genre, kind, type);
                tmp.setId(id);
                entities.add(tmp);
            }

            return entities;

        } catch (SQLException e) {
            log.error("cannot select entities", e);
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

    //endregion
}