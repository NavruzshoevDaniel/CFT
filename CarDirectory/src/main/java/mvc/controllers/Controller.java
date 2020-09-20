package mvc.controllers;

import mvc.models.ModelState;

public interface Controller<T> {

    void view();

    void remove(T obj);

    void add(T obj);

    void edit(T obj);

    void setState(ModelState state);
}
