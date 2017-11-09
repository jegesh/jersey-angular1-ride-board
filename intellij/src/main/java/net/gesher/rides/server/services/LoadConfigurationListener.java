package net.gesher.rides.server.services;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.Properties;

public class LoadConfigurationListener implements ServletContextListener {
    private static final String DB_ENVIRONMENT_TEST = "test";
    private static final String DB_ENVIRONMENT_DEV = "dev";
    private static final String DB_ENVIRONMENT_PRODUCTION = "prod";
    public static final String DB_HOST = "host";
    public static final String DB_NAME = "name";
    public static final String DB_USERNAME = "username";
    public static final String DB_PASSWORD = "password";

    public void contextInitialized(ServletContextEvent sce) {
        // read config.properties file
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }

        // set appropriate db connection params on context
        ServletContext context = sce.getServletContext();
        String dbEnvironment = context.getInitParameter("hibernate.environment");
        setDbParams(properties, context,dbEnvironment);

    }

    private void setDbParams(Properties properties, ServletContext context, String env){
        context.setAttribute(DB_HOST, properties.getProperty("db." + env + ".host"));
        context.setAttribute(DB_NAME, properties.getProperty("db." + env + ".name"));
        context.setAttribute(DB_USERNAME, properties.getProperty("db." + env + ".user"));
        context.setAttribute(DB_PASSWORD, properties.getProperty("db." + env + ".password"));
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // nothing to do here
    }


}
