package net.gesher.rides.server.services;

import com.sun.jersey.api.core.PackagesResourceConfig;
import net.gesher.rides.server.dal.DbSessionManager;

import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationProvider extends PackagesResourceConfig {
    private static DbSessionManager sessionManager;
    public ApplicationProvider(){
        super("net.gesher.rides.server");

        sessionManager = new DbSessionManager();
    }

    public static DbSessionManager getSessionManager(){ return sessionManager; }
}
