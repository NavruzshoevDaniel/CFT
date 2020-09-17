import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sql.models.car.Car;
import sql.service.CarService;
import sql.util.factory.HibernateSessionFactory;

import java.util.List;
import java.util.function.Consumer;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());
    public static final String DB_URL = "jdbc:h2:file:db/car";
    public static final String DB_Driver = "org.h2.Driver";

    public static void main(String[] args) {

        CarService carService = new CarService();

        List<Car> cars = carService.getAll();
        cars.stream().forEach(System.out::println);
        //carService.add(car);

    }
}
