package util;

import model.Movie;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Objects;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory createSessionFactory() {
        if (Objects.isNull(sessionFactory)) {
            try {
                // Create the SessionFactory from hibernate.cfg.xml
                Configuration configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
                configuration.addAnnotatedClass(Movie.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Throwable ex) {
                System.err.println("Initial SessionFactory creation failed.");
                ex.printStackTrace();
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

}
