package net.gesher.rides.server.services;

import net.gesher.rides.server.dal.DbSessionManager;

import java.io.IOException;
import java.util.Properties;

public class HibernateInitializer {
    private static final String DB_ENVIRONMENT_TEST = "test";
    private static final String DB_ENVIRONMENT_DEV = "dev";
    private static final String DB_ENVIRONMENT_PRODUCTION = "prod";
    private static final String ENV_RUN_ENVIRONMENT = "HIBERNATE_RUN_ENVIRONMENT";
    private static final String ENV_HIBERNATE_DB_NAME = "HIBERNATE_DB_NAME";
    private static final String ENV_HIBERNATE_DB_HOST = "HIBERNATE_DB_HOST";
    private static final String ENV_HIBERNATE_DB_PASSWORD = "HIBERNATE_DB_PASSWORD";
    private static final String ENV_HIBERNATE_DB_USERNAME = "HIBERNATE_DB_USERNAME";
    private static DbSessionManager sessionManager;

    static {
        if (sessionManager == null) {
            // read config.properties file
            Properties properties = new Properties();
//            try {
//                properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties"));
//            } catch (IOException e) {
//                e.printStackTrace();
//                throw new IllegalStateException(e);
//            }

            String dbEnvironment = System.getenv(ENV_RUN_ENVIRONMENT);
            // initialize session manager
            sessionManager = new DbSessionManager(
//                    properties.getProperty("db." + dbEnvironment + ".host"),
//                    properties.getProperty("db." + dbEnvironment + ".name"),
//                    properties.getProperty("db." + dbEnvironment + ".user"),
//                    properties.getProperty("db." + dbEnvironment + ".password")
                    System.getenv(ENV_HIBERNATE_DB_HOST),
                    System.getenv(ENV_HIBERNATE_DB_NAME),
                    System.getenv(ENV_HIBERNATE_DB_USERNAME),
                    System.getenv(ENV_HIBERNATE_DB_PASSWORD)
            );
        }
    }

    public static DbSessionManager getSessionManager(){ return sessionManager; }
}
