package sql.service;

import sql.models.car.Car;
import sql.repository.tables.CarRepository;

import java.util.List;

public class CarService implements Service<Car> {
    private CarRepository carRepository = new CarRepository();

    public CarService() {
    }


    @Override
    public Car getById(int id) {
        return carRepository.getById(id);
    }

    @Override
    public void add(Car obj) {
        carRepository.add(obj);
    }

    @Override
    public void remove(Car obj) {
        carRepository.remove(obj);
    }

    @Override
    public void update(Car obj) {
        carRepository.update(obj);
    }

    @Override
    public List<Car> getAll() {
        return carRepository.getAll();
    }

    @Override
    public List<String> getAllColumnNames() {
        return carRepository.getAllColumnNames();
    }
}
