package sql.service;

import java.util.List;

public interface Service<T> {

    T getById(int id);

    void add(T obj);

    void remove(T obj);

    void update(T obj);

    List<T> getAll();
}
