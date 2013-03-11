
public class Disk extends Entity {

	private KindEnum kind;
	private TypeEnum type;

	public KindEnum getKind() {
		return this.kind;
	}

	/**
	 * 
	 * @param kind
	 */
	public void setKind(KindEnum kind) {
		this.kind = kind;
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