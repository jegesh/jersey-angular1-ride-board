package net.gesher.rides.server.dal;

import net.gesher.rides.server.entity.User;

public class UserDal {
    private DbSessionManager sessionManager;

    public UserDal(DbSessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    public User getById(String userId){
        return sessionManager.getSessionFactoryInstance().openSession().get(User.class, userId);
    }
}
