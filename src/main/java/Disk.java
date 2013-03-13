import java.util.Date;

public class Disk extends Entity {

	private KindEnum kind;
	private TypeEnum type;

    public Disk(String name, String author) {
        super(name, author);
    }

    public Disk(String name, Date releaseYear, String author, String position, GenreEnum genre, KindEnum kind, TypeEnum type) {
        super(name, releaseYear, author, position, genre);
        this.kind = kind;
        this.type = type;
    }

    public Disk() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }

    /**
	 * 
	 * @param kind
	 */
	public void setKind(KindEnum kind) {
		this.kind = kind;
	}

    public KindEnum getKind() {
        return kind;
    }

	public TypeEnum getType() {
		return this.type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(TypeEnum type) {
		this.type = type;
	}


}