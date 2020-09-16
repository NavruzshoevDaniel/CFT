package sql.model.car;

import sql.model.BaseModel;

public class Car extends BaseModel {

    private int year;
    private String model;
    private String producer;
    private String body;

    public Car(int year, String model, String producer, String body) {
        this.year = year;
        this.model = model;
        this.producer = producer;
        this.body = body;
    }

    public Car(long id, int year, String model, String producer, String body) {
        super(id);
        this.year = year;
        this.model = model;
        this.producer = producer;
        this.body = body;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
