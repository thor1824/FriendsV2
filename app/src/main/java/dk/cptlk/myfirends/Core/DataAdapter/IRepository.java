package dk.cptlk.myfirends.Core.DataAdapter;

import java.util.List;

public interface IRepository<T> {

    public T create(T e);

    public T read(long id);

    public List<T> readAll();

    public T update(T e);

    public void delete(long id);

}
