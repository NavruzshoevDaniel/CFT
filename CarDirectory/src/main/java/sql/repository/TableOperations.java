package sql.repository;

import sql.model.BaseModel;

import java.sql.SQLException;
import java.util.List;

public interface TableOperations<T extends BaseModel> {

    void createTable() throws SQLException;

    void createConstraints() throws SQLException;

    void add(T obj) throws SQLException;

    void remove(int id) throws SQLException;

    List<T> getAllInList() throws SQLException;

    void modify(T obj) throws SQLException;

}
