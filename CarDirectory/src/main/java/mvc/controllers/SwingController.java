package mvc.controllers;

import mvc.models.CarModel;
import mvc.views.SwingView;

public class SwingController implements Controller {
    private CarModel carModel;
    private SwingView view;


    public SwingController(CarModel carModel) {
        this.carModel = carModel;
        this.view = new SwingView(carModel, this);
        view.createView();
        this.carModel.init();
    }


    @Override
    public void remove() {

    }

    @Override
    public void add() {

    }

    @Override
    public void edit() {

    }
}
