package pv168.project;

import java.util.Date;

public class Disk extends Entity {

    //region PROPERTIES & FIELDS
	private KindEnum kind;
	private TypeEnum type;

    //endregion


    //region CONSTRUCTORS
    public Disk(String name, String author) {
        super(name, author);
    }

    public Disk(String name, Date releaseYear, String author, String position, GenreEnum genre, KindEnum kind, TypeEnum type) {
        super(name, releaseYear, author, position, genre);
        this.kind = kind;
        this.type = type;
    }

    //endregion


    //region GETTERS & SETTERS
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

     //endregion

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Disk))
            return false;
        if (this == o) return true;
        Disk disk=(Disk) o;
        return(super.equals(o) && kind==disk.getKind() && type==disk.getType());
    }

    @Override
    public int hashCode() {
       return super.hashCode();
    }
}