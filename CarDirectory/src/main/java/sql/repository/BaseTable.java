package sql.repository;


import java.sql.*;


public class BaseTable implements AutoCloseable {
    protected String urlDataBase;
    protected Connection connection;
    protected String nameTable;
    protected Statement statement;

    public BaseTable(String nameTable, String urlDatabase) throws SQLException {
        this.urlDataBase = urlDatabase;
        this.nameTable = nameTable;
        connection = DriverManager.getConnection(urlDatabase);
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed())
            connection.close();
    }

    public void executeSqlStatement(String sql) throws SQLException {
        reopenConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
    }


    public void executeUpdateSqlStatement(String sql) throws SQLException {
        reopenConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);
        statement.close();
    }

    public void reopenConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(urlDataBase);
        }
    }

}
