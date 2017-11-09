package net.gesher.rides.server.services;

import net.gesher.rides.server.dal.DbSessionManager;

import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Properties;

@Provider
public class ApplicationProvider extends Application {
    private static final String DB_ENVIRONMENT_TEST = "test";
    private static final String DB_ENVIRONMENT_DEV = "dev";
    private static final String DB_ENVIRONMENT_PRODUCTION = "prod";
    private static final String ENV_RUN_ENVIRONMENT = "HIBERNATE_RUN_ENVIRONMENT";
    private static DbSessionManager sessionManager;

    public ApplicationProvider(){
        super();

        // read config.properties file
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

        String dbEnvironment = System.getenv(ENV_RUN_ENVIRONMENT);
        // initialize session manager
        sessionManager = new DbSessionManager(
                properties.getProperty("db." + dbEnvironment + ".host"),
                properties.getProperty("db." + dbEnvironment + ".name"),
                properties.getProperty("db." + dbEnvironment + ".user"),
                properties.getProperty("db." + dbEnvironment + ".password")
        );

    }

    public static DbSessionManager getSessionManager(){ return sessionManager; }
}
