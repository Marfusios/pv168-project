import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Romhulus
 * Date: 13.3.13
 * Time: 16:08
 * To change this template use File | Settings | File Templates.
 */
public interface ManagerEntities {


    public ManagerEntitiesImpl();

    public void addEntita(Disk disk);

    public void addEntita(Book book);

    public void removeEntita(Entita entita);

    public void editEntita(Entita entita, int args);

    private Entita findEntita(int args);

    public List<Entita> getEntitiesList();

    public List<Entita> getEntitiesList(Date releaseYear);

    public List<Entita> getEntitiesList(GenreEnum genre);
}
