package mvc.controllers;

import mvc.models.ModelState;

public interface Controller<T> {

    void view();

    void remove(long id);

    void add(T obj);

    void edit(T obj);

    void setState(ModelState state);
}
