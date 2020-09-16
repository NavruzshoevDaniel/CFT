import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sql.model.car.Car;
import sql.repository.tables.Cars;

import java.sql.SQLException;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());
    public static final String DB_URL = "jdbc:h2:file:db/car";
    public static final String DB_Driver = "org.h2.Driver";

    public static void main(String[] args) {


        try {
            Class.forName(DB_Driver);
            Cars cars = new Cars("cars", DB_URL);
            cars.createTable();
            cars.add(new Car(2000, "X6", "BMW", "SUV"));
            cars.remove(1);
            System.out.println( cars.getAllInList());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
