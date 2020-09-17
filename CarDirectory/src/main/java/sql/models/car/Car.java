package sql.models.car;

import sql.models.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cars")
public class Car extends BaseModel {

    private int year;
    private String model;
    private String producer;
    private String body;

    public Car() {

    }

    public Car(int year, String model, String producer, String body) {
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


    @Override
    public String toString() {
        return "Car{" +
                "year=" + year +
                ", model='" + model + '\'' +
                ", producer='" + producer + '\'' +
                ", body='" + body + '\'' +
                ", id=" + id +
                "} " + super.toString();
    }
}
