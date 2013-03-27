import java.util.Date;

public abstract class Entity {

    //region PROPERTIES & FIELDS
    private long id;
	private String name;
	private Date releaseYear;
	private String author;
	private String position;
	private GenreEnum genre;

    //endregion


    //region CONSTRUCTORS
    public Entity(String name, String author) {
        this.name=name;
        this.author=author;
    }

    protected Entity(String name, Date releaseYear, String author, String position, GenreEnum genre) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.author = author;
        this.position = position;
        this.genre = genre;
    }

    //endregion


    //region GETTERS & SETTERS
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Date releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public GenreEnum getGenre() {
        return genre;
    }

    public void setGenre(GenreEnum genre) {
        this.genre = genre;
    }

    //endregion
}