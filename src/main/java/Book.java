import java.util.Date;

public class Book extends Entity {

    //region PROPERTIES & FIELDS
	private int pageCount;

    //endregion


    //region CONSTRUCTORS
    public Book(String name, String author) {
        super(name, author);
    }

    public Book(String name, Date releaseYear, String author, String position, GenreEnum genre, int pageCount) {
        super(name, releaseYear, author, position, genre);
        this.pageCount = pageCount;
    }

    //endregion


    //region GETTERS & SETTERS
    public int getPageCount() {
		return this.pageCount;
	}

	/**
	 * 
	 * @param pageCount
	 */
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

    //endregion

}