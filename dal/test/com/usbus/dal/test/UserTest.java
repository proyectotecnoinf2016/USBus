package com.usbus.dal.test;

import com.usbus.commons.enums.Gender;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.dao.UserDAO;
import com.usbus.dal.model.User;
import org.junit.Test;

import java.util.Date;

/**
 * Created by JuanPablo on 4/28/2016.
 */
public class UserTest {
    protected MongoDB persistence;
    protected GenericPersistence genericPersistence = new GenericPersistence();
    protected UserDAO dao = new UserDAO();
    @Test
    public void persist() {
        if(dao.countTenant(999)<=0)
            dao.cleanUsers(999);
        Date date = new Date();
        User usu = new User(999,"usu1","usu1@my.com","usuario 1","prueba 1",date, "123456", Gender.MALE, date, date, true);
        dao.persist(usu);
        usu = new User(999,"usu2","usu2@my.com","usuario 2","prueba 2",date, "123456", Gender.MALE, date, date, true);
        dao.persist(usu);

        System.out.println(dao.countTenant(999));

    }
}