package mvc.controllers;

import mvc.models.CarModel;
import mvc.models.ModelState;
import mvc.views.SwingView;
import sql.models.car.Car;

public class SwingController implements Controller<Car> {
    private CarModel carModel;
    private SwingView view;


    public SwingController(CarModel carModel) {
        this.carModel = carModel;
        this.view = new SwingView(carModel, this);
        view.createView();
        this.carModel.init();
        view();
    }


    @Override
    public void view() {
        carModel.view();
    }

    @Override
    public void remove(long id) {

    }

    @Override
    public void add(Car obj) {
        carModel.addCar(obj);
    }

    @Override
    public void edit(Car obj) {
        carModel.updateCar(obj);
    }


    @Override
    public void setState(ModelState state) {
        carModel.setState(state);
    }
}
