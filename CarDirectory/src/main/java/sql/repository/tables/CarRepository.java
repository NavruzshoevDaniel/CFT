package sql.repository.tables;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metamodel.spi.MetamodelImplementor;
import sql.models.car.Car;
import sql.repository.Repository;
import sql.util.factory.HibernateSessionFactory;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CarRepository implements Repository<Car> {
    private static final Logger LOGGER = LogManager.getLogger(CarRepository.class.getName());


    @Override
    public Car getById(int id) {
        return HibernateSessionFactory.getSessionFactory().openSession().get(Car.class, id);
    }

    @Override
    public void add(Car obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(obj);
        transaction.commit();
        session.close();

    }

    @Override
    public void remove(Car obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Car carFromBase = session.find(Car.class, obj.getId());
        Transaction transaction = session.beginTransaction();
        session.delete(carFromBase);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Car obj) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(obj);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Car> getAll() {
        List<Car> cars = (List<Car>) HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("From Car")
                .list();
        return cars;
    }

    @Override
    public List<String> getAllColumnNames() {
        MetamodelImplementor metamodel = (MetamodelImplementor) HibernateSessionFactory
                .getSessionFactory()
                .getMetamodel();
        ClassMetadata classMetadata = (ClassMetadata) metamodel.entityPersister(Car.class);
        List<String> columnsNames = new ArrayList<>();
        String id = classMetadata.getIdentifierPropertyName();
        if (id != null) {
            columnsNames.add(id);
        }
        columnsNames.addAll(Arrays.asList(classMetadata.getPropertyNames()));
        return columnsNames;
    }

}

