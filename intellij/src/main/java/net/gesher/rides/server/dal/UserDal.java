package net.gesher.rides.server.dal;

import net.gesher.rides.server.entity.Ride;
import net.gesher.rides.server.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.WebApplicationException;
import java.util.List;

public class UserDal {
    private DbSessionManager sessionManager;

    public UserDal(DbSessionManager sessionManager){
        this.sessionManager = sessionManager;
    }

    public User getById(String userId){
        return sessionManager.getSessionFactoryInstance().openSession().get(User.class, userId);
    }

    public User registerUser(User u){
        Session session =  sessionManager.getSessionFactoryInstance().openSession();
        session.save(u);
        session.close();
        return u;
    }

    public User verify(String id, String password){
        Session session =  sessionManager.getSessionFactoryInstance().openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.where(builder.and(builder.equal(root.get("id"), id), builder.equal(root.get("password"), password)));
        criteria.select(root);

        Query query = session.createQuery(criteria);
        List<User> matchingUsers = query.getResultList();
        if(matchingUsers.size() == 0) return null;
        if(matchingUsers.size() == 1) return matchingUsers.get(0);
        throw new IllegalStateException(String.format("Multiple users in database with id %s and same password", id));
    }
}
