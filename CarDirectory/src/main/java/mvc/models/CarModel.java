package mvc.models;

import mvc.Observable;
import mvc.EventListener;
import sql.models.car.Car;
import sql.service.CarService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarModel implements Observable {
    private Map<String, List<EventListener>> carObservers = new HashMap<>();
    private List<Car> cars;
    private CarService carService = new CarService();
    private ModelState state = ModelState.WAIT;

    public CarModel() {
    }

    public void init() {
        state = ModelState.LOADING_FROM_DATABASE;
        cars = carService.getAll();
        notifyObservers("list");
        state = ModelState.WAIT;
    }

    public void addCar() {
        Car car = new Car();
        carService.add(car);
    }

    @Override
    public void registerObserver(String eventType, EventListener eventListener) {
        if (eventListener == null) {
            throw new NullPointerException();
        }
        if (carObservers.get(eventType).contains(eventListener)) {
            throw new IllegalArgumentException("Repeated observer:" + eventListener);
        }
        carObservers.get(eventType).add(eventListener);
    }

    @Override
    public void notifyObservers(String eventType) {
        for (EventListener eventListener : carObservers.get(eventType)) {
            eventListener.update();
        }
    }

    public ModelState getState() {
        return state;
    }

    public List<Car> getCars() {
        return cars;
    }
}
