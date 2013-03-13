import java.util.Date;

public abstract class Entity {

    private int id;
	private String name;
	private Date releaseYear;
	private String author;
	private String position;
	private GenreEnum genre;

    public Entity(String name, String author) {
        this.name=name;
        this.author=author;
    }

    public Entity(String name, Date releaseYear, String author, String position, GenreEnum genre) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public Entity() {
        //To change body of created methods use File | Settings | File Templates.
    }


    public void get() {
		throw new UnsupportedOperationException();
	}

	public void set() {
		throw new UnsupportedOperationException();
	}


    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name=name;
    }

    public void setAuthor(String author) {
        this.author=author;
    }
}