import mvc.controllers.Controller;
import mvc.controllers.SwingController;
import mvc.models.CarModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sql.models.car.Car;
import sql.service.CarService;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());


    public static void main(String[] args) {

        System.out.println(new CarService().getAll());
        CarModel carModel = new CarModel();
        Controller<Car> controller = new SwingController(carModel);

    }
}
