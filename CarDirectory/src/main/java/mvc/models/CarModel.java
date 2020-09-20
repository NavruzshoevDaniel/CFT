package mvc.models;

import mvc.Observable;
import mvc.EventListener;
import mvc.views.SwingView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sql.models.car.Car;
import sql.service.CarService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarModel implements Observable {
    private static final Logger LOGGER = LogManager.getLogger(SwingView.class.getName());

    private Map<String, List<EventListener>> carObservers = new HashMap<>();

    private List<String> tableColumnNames;
    private List<Row> rows= new ArrayList<>();
    private CarService carService = new CarService();
    private ModelState state = ModelState.WAITING;

    public CarModel() {
    }

    public void init() {
        setState(ModelState.INITING);
        LOGGER.info("Started init");
        tableColumnNames = carService.getAllColumnNames();

        loadRows();
        LOGGER.info("Finished init");
        setState(ModelState.WAITING);
    }

    public void view() {
        setState(ModelState.VIEWING);
        notifyObservers("list");
    }

    public void addCar(Car car) {
        setState(ModelState.ADDING_TO_DATABASE);
        carService.add(car);
    }

    public Car getById(int id){
        return carService.getById(id);
    }

    public void updateCar(Car car) {
        setState(ModelState.EDITING_CAR_FROM_DATABASE);
        carService.update(car);
    }

    public void removeCar(Car car){
        setState(ModelState.REMOVING_FROM_DATABASE);
        carService.remove(car);
    }

    @Override
    public void registerObserver(String eventType, EventListener eventListener) {
        if (eventType == null || eventListener == null) {
            throw new NullPointerException();
        }

        List<EventListener> eventValue = carObservers.get(eventType);

        if (eventValue != null) {
            if (eventValue.contains(eventListener)) {
                throw new IllegalArgumentException("Repeated observer:" + eventListener);
            } else {
                carObservers.get(eventType).add(eventListener);
            }

        } else {
            List<EventListener> listListeners = new ArrayList<>();
            listListeners.add(eventListener);
            carObservers.put(eventType, listListeners);
        }

    }

    @Override
    public void notifyObservers(String eventType) {
        for (EventListener eventListener : carObservers.get(eventType)) {
            eventListener.update();
        }
    }

    public synchronized ModelState getState() {
        return state;
    }

    public Car[] getCars() {
        return carService.getAll().toArray(new Car[0]);
    }

    public List<String> getTableColumnNames() {
        return tableColumnNames;
    }

    public synchronized void setState(ModelState state) {
        this.state = state;
    }

    private void loadRows() {
        for (Car car : getCars()) {
            Row row = new Row();
            for (String columnName : getTableColumnNames()) {
                row.add(car.getGetter(columnName).get());
            }
            rows.add(row);
        }
    }

    public List<Row> getRows() {
        return rows;
    }
}
