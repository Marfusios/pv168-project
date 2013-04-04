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

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Book))
            return false;
        if (this == o) return true;
        Book book= (Book) o;
        return(super.equals(o) && pageCount==book.getPageCount());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}