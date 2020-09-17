import mvc.controllers.Controller;
import mvc.controllers.SwingController;
import mvc.models.CarModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sql.models.car.Car;
import sql.util.Utils;


public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class.getName());


    public static void main(String[] args) {

        /*CarModel carModel = new CarModel();
        Controller controller = new SwingController(carModel);*/

        for (String string:
        Utils.getAttributes(Car.class)) {
            System.out.println(string);
        }


    }
}
