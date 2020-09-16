package sql.repository.tables;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sql.model.car.Car;
import sql.repository.BaseTable;
import sql.repository.TableOperations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Cars extends BaseTable implements TableOperations<Car> {
    private static final Logger LOGGER = LogManager.getLogger(Cars.class.getName());


    public Cars(String tableNAme, String urlDatabase) throws SQLException {
        super(tableNAme, urlDatabase);
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + nameTable +
                "(id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                "year INTEGER NOT NULL," +
                "model VARCHAR(255) NOT NULL," +
                "producer VARCHAR(255) NOT NULL," +
                "body VARCHAR(255) NOT NULL)");

        LOGGER.info("Cars table has just been created");
    }

    @Override
    public void createConstraints() throws SQLException {
        super.executeSqlStatement("ALTER TABLE " + nameTable +
                " ADD CONSTRAINT check_year CHECK(year <= 2100 and year > 1900)");
    }

    @Override
    public void add(Car obj) throws SQLException {
        super.executeUpdateSqlStatement("INSERT INTO " + nameTable + " VALUES (" +
                "NULL" + ", " +
                obj.getYear() + ", " +
                "'" + obj.getModel() + "', " +
                "'" + obj.getProducer() + "', " +
                "'" + obj.getBody() + "')");
    }

    @Override
    public void remove(int id) throws SQLException {
        super.executeUpdateSqlStatement("DELETE FROM " + nameTable +
                " WHERE id = " + id);
    }

    @Override
    public List<Car> getAllInList() throws SQLException {
        reopenConnection();
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM " + nameTable;
        ResultSet resultSet = statement.executeQuery(sql);
        List<Car> carList = new ArrayList<>();

        while (resultSet.next()) {
            carList.add(createCar(resultSet));
        }
        statement.close();
        return carList;
    }

    @Override
    public void modify(Car obj) throws SQLException {

    }

    public Car createCar(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("id");
        int year = resultSet.getInt("year");
        String model = resultSet.getString("model");
        String producer = resultSet.getString("producer");
        String body = resultSet.getString("body");
        Car newCar = new Car(id, year, model, producer, body);

        return newCar;
    }

}
