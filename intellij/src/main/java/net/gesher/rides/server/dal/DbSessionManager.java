package net.gesher.rides.server.dal;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

/**
 * Created by yaakov on 8/22/17.
 */

public class DbSessionManager {
    private SessionFactory sessionFactory;
    private static DbSessionManager instance;

    public DbSessionManager(){
// TODO get connection properties from web.xml
        setupSession();
    }

    public DbSessionManager(String host, String dbName, String user, String password){
        Properties dbConnectionProperties = new Properties();
        dbConnectionProperties.put( "hibernate.connection.url", "jdbc:mysql://" + host + ":3306/" + dbName);
        dbConnectionProperties.put( "hibernate.connection.username", user);
        if(password != null)
            dbConnectionProperties.put( "hibernate.connection.password", password);
        setupSession(dbConnectionProperties);
    }

    private void setupSession(Properties configProperties){
        // A SessionFactory is set up once for an application!
        try {

            sessionFactory = new Configuration().mergeProperties(configProperties).configure().buildSessionFactory();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void setupSession(){
//         A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml or other xml config
                .build();
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void tearDownSession() throws Exception {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public static DbSessionManager getInstance(){
        if(instance == null)
            instance = new DbSessionManager();
        return instance;
    }

    public SessionFactory getSessionFactoryInstance(){
        return sessionFactory;
    }
}