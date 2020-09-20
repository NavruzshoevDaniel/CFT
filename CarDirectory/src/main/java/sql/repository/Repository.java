package sql.repository;


import java.util.List;

public interface Repository<T> {

    T getById(long id);

    void add(T obj);

    void remove(T obj);

    void update(T obj);

    List<T> getAll();

    List<String> getAllColumnNames();
}
