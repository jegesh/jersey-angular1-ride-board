package net.gesher.rides.server;

import net.gesher.rides.server.dal.DbSessionManager;
import net.gesher.rides.server.dal.UserDal;
import net.gesher.rides.server.entity.User;
import org.junit.Before;
import org.junit.Test;

public class UserDalTest {
    DbSessionManager sessionManager;

    @Before
    public void setup(){
        sessionManager = new DbSessionManager("localhost", "rideboard_test", "root", null);
    }

    @Test
    public void testVerifyUser(){
        UserDal dal = new UserDal(sessionManager);
        User u = dal.verify("aa11", ";lkladl;flasdf");

        assert u.getName().equals("Shuly Rand");
    }
}
