import java.util.Date;

public class Book extends Entity {

	private int pageCount;

    public Book(String name, String author) {
        super(name, author);
    }

    public Book(int id, String name, Date releaseYear, String author, String position, GenreEnum genre, int pageCount) {
        super(id, name, releaseYear, author, position, genre);
        this.pageCount = pageCount;
    }


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


}