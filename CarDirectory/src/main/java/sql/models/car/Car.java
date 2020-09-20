package sql.models.car;

import sql.models.car.exceptions.YearException;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int year;
    private String model;
    private String producer;
    private String body;

    @Transient
    private Map<String, Consumer<Object>> columnsSetter = new HashMap<>();
    @Transient
    private Map<String, Supplier<Object>> columnsGetter = new HashMap<>();


    public Car() {
        initSetters();
        initGetters();
    }

    private void initGetters() {
        columnsGetter.put("id", this::getId);
        columnsGetter.put("year", this::getYear);
        columnsGetter.put("model", this::getModel);
        columnsGetter.put("producer", this::getProducer);
        columnsGetter.put("body", this::getBody);
    }

    private void initSetters() {
        columnsSetter.put("id", o -> setId((Long) o));
        columnsSetter.put("year", o -> {
            if (o instanceof String) {
                setYear(Integer.parseInt((String) o));
            } else {
                setYear((Integer) o);
            }
        });
        columnsSetter.put("model", o -> setModel((String) o));
        columnsSetter.put("producer", o -> setProducer((String) o));
        columnsSetter.put("body", o -> setBody((String) o));
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

    public void setYear(int year) throws YearException {
        if (year >= 1900 && year <= 2025) {
            this.year = year;
        } else {
            throw new YearException("The year must be from 1900 to 2025");
        }
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
                "} ";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Consumer<Object> getSetter(String columnName) {
        return columnsSetter.get(columnName);
    }

    public Supplier<Object> getGetter(String columnName) {
        return columnsGetter.get(columnName);
    }
}
