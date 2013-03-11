import java.util.Date;

public abstract class Entity {

	private String name;
	private Date releaseYear;
	private String author;
	private String position;
	private GenreEnum genre;

	public void get() {
		throw new UnsupportedOperationException();
	}

	public void set() {
		throw new UnsupportedOperationException();
	}

}