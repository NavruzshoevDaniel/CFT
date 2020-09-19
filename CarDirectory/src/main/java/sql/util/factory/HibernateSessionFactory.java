package sql.util.factory;


import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import sql.models.car.Car;

public class HibernateSessionFactory {
    private volatile static SessionFactory sessionFactory = null;

    private HibernateSessionFactory() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateSessionFactory.class) {
                if (sessionFactory == null) {
                    Configuration configuration = new Configuration().configure();
                    addAnnotations(configuration);
                    StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties());
                    sessionFactory = configuration.buildSessionFactory(builder.build());
                }
            }
        }
        return sessionFactory;
    }

    private static void addAnnotations(Configuration configuration) {
        configuration.addAnnotatedClass(Car.class);
    }
}
