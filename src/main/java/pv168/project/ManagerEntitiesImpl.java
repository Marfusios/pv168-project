package pv168.project;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.derby.jdbc.ClientDataSource;
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
    private final ClientDataSource dataSource;

    //endregion


    //region CONSTRUCTORS
    public ManagerEntitiesImpl(ClientDataSource dataSource) {
        if(dataSource == null) throw new IllegalArgumentException("dataSource");

        this.dataSource = dataSource;
    }

    public ManagerEntitiesImpl() {

        //Properties prop = new Properties();
        //prop.load(ClassLoader.getSystemResourceAsStream("pv168.project.config.properties"));

        this.dataSource = new ClientDataSource();

        /*ds.setServerName(prop.getProperty("serverName"));
        ds.setPortNumber(Integer.parseInt(prop.getProperty("port")));
        ds.setDatabaseName(prop.getProperty("databaseName"));
        ds.setUser(prop.getProperty("user"));
        ds.setPassword(prop.getProperty("password"));    */

        dataSource.setServerName("localhost");
        dataSource.setPortNumber(1527);
        dataSource.setDatabaseName("EvidencyDBEmbedded");
        dataSource.setUser("root");
        dataSource.setPassword("password");
    }

    //endregion


    //region METHODS

    /**
	 *
     * @param entity
     */
	public void addEntity(Entity entity) throws EntityException,NullPointerException,IllegalArgumentException {
        if(entity == null) throw new NullPointerException("entity");
        if(entity.getName().isEmpty() || entity.getAuthor().isEmpty()) throw new IllegalArgumentException("entity");

        Connection con = null;
        PreparedStatement st = null;
        Long id;
        try {
            con = dataSource.getConnection();
            //začátek SQL operace
            st = con.prepareStatement("insert into entities (name,author,releaseYear,position,genre) values (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, entity.getName());
            st.setString(2, entity.getAuthor());
            if(entity.getReleaseYear() != null)
            {
                long time = entity.getReleaseYear().getTime();
                st.setDate(3, new java.sql.Date(time));
            }
            else
                st.setDate(3, null);
            st.setString(4,entity.getPosition());

            if(entity.getGenre() != null)
                st.setString(5,entity.getGenre().toString());
            else
                st.setString(5,null);
            st.executeUpdate();
            ResultSet keys = st.getGeneratedKeys();
            if (keys.next()) {
                id = keys.getLong(1);
                entity.setId(id);
            }

                if(entity instanceof Book){
                     Book book=(Book)entity;
                     st = con.prepareStatement("insert into books (id,pageCount) values (?,?)",PreparedStatement.NO_GENERATED_KEYS) ;
                     st.setLong(1, book.getId());
                     st.setInt(2,book.getPageCount());
                     st.executeUpdate();
                }
                if(entity instanceof Disk){
                   Disk disk = (Disk) entity;
                    st = con.prepareStatement("insert into disks (id,kind,type) values (?,?,?)",PreparedStatement.NO_GENERATED_KEYS);
                    st.setLong(1,disk.getId());
                    if(disk.getKind() != null)
                        st.setString(2,disk.getKind().toString());
                    else
                        st.setString(2, null);

                    if(disk.getType() != null)
                        st.setString(3,disk.getType().toString());
                    else
                        st.setString(3,null);
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
        if(entity == null) throw new NullPointerException("entity");

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
        if(oldEntity == null) throw new NullPointerException("oldEntity");
        if(newEntity == null) throw new NullPointerException("newEntity");

        Connection con = null;
        PreparedStatement st = null;
        try {
            con = dataSource.getConnection();
            //začátek SQL operace
            st = con.prepareStatement("update entities set name=?, author=?, releaseYear=?, position=?, genre=? where id=?");
            st.setString(1, newEntity.getName());
            st.setString(2, newEntity.getAuthor());
            if(newEntity.getReleaseYear() != null)
            {
                long time = newEntity.getReleaseYear().getTime();
                st.setDate(3,new java.sql.Date(time));
            }
            else
            {
                st.setDate(3, null);
            }
            st.setString(4,newEntity.getPosition());
            if(newEntity.getGenre() != null)
                st.setString(5,newEntity.getGenre().toString());
            else
                st.setString(5, null);
            st.setLong(6,oldEntity.getId());
            int n = st.executeUpdate();
            if(n!=1) {
                throw new EntityException("The entity with id "+oldEntity.getId()+ " was not updated ",null);
            }
            if(newEntity instanceof Book){
                Book book=(Book)newEntity;
                if(oldEntity instanceof Book){
                    st = con.prepareStatement("update books set pageCount=? where id=?") ;
                    st.setInt(1,book.getPageCount());
                    st.setLong(2,oldEntity.getId());
                    st.executeUpdate();
                }else{
                    st = con.prepareStatement("insert into books (id,pageCount) values (?,?)",PreparedStatement.NO_GENERATED_KEYS);
                    st.setLong(1,oldEntity.getId());
                    st.setInt(2,book.getPageCount());
                    st.executeUpdate();
                    if(n!=1)
                        throw new EntityException("The book with id "+oldEntity.getId()+ " was not updated ",null);

                    st = con.prepareStatement("delete from disks where id=?");
                    st.setLong(1, oldEntity.getId());
                    st.executeUpdate();

                }
                if(n!=1) {
                    throw new EntityException("The book with id "+oldEntity.getId()+ " was not updated ",null);
                }
            }
            if(newEntity instanceof Disk){
                Disk disk=(Disk)newEntity;
                if(oldEntity instanceof Disk){
                    st = con.prepareStatement("update disks set kind=?, type=?  where id=?") ;
                    if(disk.getKind() != null)
                        st.setString(1,disk.getKind().toString());
                    else
                        st.setString(1, null);
                    if(disk.getType() != null)
                        st.setString(2,disk.getType().toString());
                    else
                        st.setString(2, null);
                    st.setLong(3,oldEntity.getId());
                }else{
                    st = con.prepareStatement("insert into disks (id,kind,type) values (?,?,?)",PreparedStatement.NO_GENERATED_KEYS);
                    st.setLong(1,oldEntity.getId());
                    if(disk.getKind() != null)
                        st.setString(2,disk.getKind().toString());
                    else
                        st.setString(2, null);
                    if(disk.getType() != null)
                        st.setString(3,disk.getType().toString());
                    else
                        st.setString(3, null);
                    n = st.executeUpdate();
                    if(n!=1)
                    throw new EntityException("The disk with id "+oldEntity.getId()+ " was not updated ",null);

                    st = con.prepareStatement("delete from books where id=?");
                    st.setLong(1, oldEntity.getId());

                }
                n = st.executeUpdate();
                if(n!=1) {
                    throw new EntityException("The disk with id "+oldEntity.getId()+ " was not updated ",null);
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
	public Entity findEntity(long id) throws EntityException{
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = dataSource.getConnection();

            //SQL operation for BOOKS
            st = con.prepareStatement("select * from entities, books WHERE entities.id = books.id AND entities.id=?");
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                long idTmp = rs.getLong("id");
                String nameTmp = rs.getString("name");
                String authorTmp = rs.getString("author");
                java.sql.Date releaseYearTmp = rs.getDate("releaseYear");
                java.util.Date releaseYear = null;
                if(releaseYearTmp != null)
                     releaseYear = new Date(releaseYearTmp.getTime());
                String position = rs.getString("position");
                GenreEnum genre = null;
                if(rs.getString("genre") != null)
                    genre = GenreEnum.valueOf(rs.getString("genre"));
                int pageCount = rs.getInt("pageCount");

                Book tmp = new Book(nameTmp, releaseYear, authorTmp, position, genre, pageCount);
                tmp.setId(idTmp);
                return tmp;
            }

            //SQL operation for DISKS
            st = con.prepareStatement("select * from entities, disks WHERE entities.id = disks.id AND entities.id=?");
            st.setLong(1, id);
            rs = st.executeQuery();

            while (rs.next()) {
                long idTmp = rs.getLong("id");
                String nameTmp = rs.getString("name");
                String authorTmp = rs.getString("author");
                java.sql.Date releaseYearTmp = rs.getDate("releaseYear");
                java.util.Date releaseYear = null;
                if(releaseYearTmp != null)
                    releaseYear = new Date(releaseYearTmp.getTime());
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

                Disk tmp = new Disk(nameTmp, releaseYear, authorTmp, position, genre, kind, type);
                tmp.setId(idTmp);
                return  tmp;
            }

            return null;

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
     * @param name
     * @param author
     * @return Found entity
     * @throws EntityException
     */
    public Entity findEntity(String name, String author) throws EntityException{
        Connection con = null;
        PreparedStatement st = null;
        try {
            con = dataSource.getConnection();

            //SQL operation for BOOKS
            st = con.prepareStatement("select * from entities, books WHERE entities.id = books.id AND name = ? AND author = ?");
            st.setString(1, name);
            st.setString(2, author);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String nameTmp = rs.getString("name");
                String authorTmp = rs.getString("author");
                java.sql.Date releaseYearTmp = rs.getDate("releaseYear");
                java.util.Date releaseYear = null;
                if(releaseYearTmp != null)
                    releaseYear = new Date(releaseYearTmp.getTime());
                String position = rs.getString("position");
                GenreEnum genre = null;
                if(rs.getString("genre") != null)
                    genre = GenreEnum.valueOf(rs.getString("genre"));
                int pageCount = rs.getInt("pageCount");

                Book tmp = new Book(nameTmp, releaseYear, authorTmp, position, genre, pageCount);
                tmp.setId(id);
                return tmp;
            }

            //SQL operation for DISKS
            st = con.prepareStatement("select * from entities, disks WHERE entities.id = disks.id AND name = ? AND author = ?");
            st.setString(1, name);
            st.setString(2, author);
            rs = st.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String nameTmp = rs.getString("name");
                String authorTmp = rs.getString("author");
                java.sql.Date releaseYearTmp = rs.getDate("releaseYear");
                java.util.Date releaseYear = null;
                if(releaseYearTmp != null)
                    releaseYear = new Date(releaseYearTmp.getTime());
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

                Disk tmp = new Disk(nameTmp, releaseYear, authorTmp, position, genre, kind, type);
                tmp.setId(id);
                return  tmp;
            }

            return null;

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
     * @return
     * @throws EntityException
     */
	public List<Entity> getEntitiesList() throws EntityException {
        Connection con = null;
        PreparedStatement st = null;
        try {
            List<Entity> entities = new ArrayList<>();
            con = dataSource.getConnection();

            //SQL operation for BOOKS
            st = con.prepareStatement("SELECT * FROM entities, books WHERE entities.id = books.id");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                java.sql.Date releaseYearTmp = rs.getDate("releaseYear");
                java.util.Date releaseYear = null;
                if(releaseYearTmp != null)
                    releaseYear = new Date(releaseYearTmp.getTime());
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
            st = con.prepareStatement("select * from entities, disks where entities.id = disks.id");
            rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                java.sql.Date releaseYearTmp = rs.getDate("releaseYear");
                java.util.Date releaseYear = null;
                if(releaseYearTmp != null)
                    releaseYear = new Date(releaseYearTmp.getTime());
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
	public List<Entity> getEntitiesList(Date releaseYear) throws EntityException{
        if(releaseYear == null) throw new NullPointerException("releaseYear");

        Connection con = null;
        PreparedStatement st = null;
        try {
            List<Entity> entities = new ArrayList<>();
            con = dataSource.getConnection();

            //SQL operation for BOOKS
            st = con.prepareStatement("SELECT * FROM entities, books WHERE entities.id = books.id AND releaseYear=?");
            long time = releaseYear.getTime();
            st.setDate(1, new java.sql.Date(time));
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
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
            st = con.prepareStatement("select * from entities, disks where entities.id = disks.id AND releaseYear=?");
            time = releaseYear.getTime();
            st.setDate(1, new java.sql.Date(time));
            rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
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
	 * @param genre
	 */
	public List<Entity> getEntitiesList(GenreEnum genre) throws EntityException{
        if(genre == null) throw new NullPointerException("genre");

        Connection con = null;
        PreparedStatement st = null;
        try {
            List<Entity> entities = new ArrayList<>();
            con = dataSource.getConnection();

            //SQL operation for BOOKS
            st = con.prepareStatement("SELECT * FROM entities, books WHERE entities.id = books.id AND genre=?");
            st.setString(1, genre.toString());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                java.sql.Date releaseYearTmp = rs.getDate("releaseYear");
                java.util.Date releaseYear = null;
                if(releaseYearTmp != null)
                    releaseYear = new Date(releaseYearTmp.getTime());
                String position = rs.getString("position");
                int pageCount = rs.getInt("pageCount");

                Book tmp = new Book(name, releaseYear, author, position, genre, pageCount);
                tmp.setId(id);
                entities.add(tmp);
            }

            //SQL operation for DISKS
            st = con.prepareStatement("select * from entities, disks where entities.id = disks.id AND genre=?");
            st.setString(1, genre.toString());
            rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String author = rs.getString("author");
                java.sql.Date releaseYearTmp = rs.getDate("releaseYear");
                java.util.Date releaseYear = null;
                if(releaseYearTmp != null)
                    releaseYear = new Date(releaseYearTmp.getTime());
                String position = rs.getString("position");

                GenreEnum genreTmp = null;
                if(rs.getString("genre") != null)
                    genre = GenreEnum.valueOf(rs.getString("genre"));

                KindEnum kind = null;
                if(rs.getString("kind") != null)
                    kind = KindEnum.valueOf(rs.getString("kind"));

                TypeEnum type = null;
                if(rs.getString("type") != null)
                    type = TypeEnum.valueOf(rs.getString("type"));

                Disk tmp = new Disk(name, releaseYear, author, position, genreTmp, kind, type);
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
     * @param author
     */
    public List<Entity> getEntitiesList(String author) throws EntityException{
        if(author == "") throw new IllegalArgumentException("releaseYear");

        Connection con = null;
        PreparedStatement st = null;
        try {
            List<Entity> entities = new ArrayList<>();
            con = dataSource.getConnection();

            //SQL operation for BOOKS
            st = con.prepareStatement("SELECT * FROM entities, books WHERE entities.id = books.id AND author=?");
            st.setString(1, author);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String authorTmp = rs.getString("author");
                java.sql.Date releaseYearTmp = rs.getDate("releaseYear");
                java.util.Date releaseYear = null;
                if(releaseYearTmp != null)
                    releaseYear = new Date(releaseYearTmp.getTime());
                String position = rs.getString("position");
                GenreEnum genre = null;
                if(rs.getString("genre") != null)
                    genre = GenreEnum.valueOf(rs.getString("genre"));
                int pageCount = rs.getInt("pageCount");

                Book tmp = new Book(name, releaseYear, authorTmp, position, genre, pageCount);
                tmp.setId(id);
                entities.add(tmp);
            }

            //SQL operation for DISKS
            st = con.prepareStatement("select * from entities, disks where entities.id = disks.id AND author=?");
            st.setString(1, author);
            rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String authorTmp = rs.getString("author");
                java.sql.Date releaseYearTmp = rs.getDate("releaseYear");
                java.util.Date releaseYear = null;
                if(releaseYearTmp != null)
                    releaseYear = new Date(releaseYearTmp.getTime());
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

                Disk tmp = new Disk(name, releaseYear, authorTmp, position, genre, kind, type);
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

    //endregion
}