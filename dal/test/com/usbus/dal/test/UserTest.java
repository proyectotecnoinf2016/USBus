package com.usbus.dal.test;

import com.usbus.commons.enums.Gender;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.dao.HumanResourceDAO;
import com.usbus.dal.dao.UserDAO;
import com.usbus.dal.model.HumanResource;
import com.usbus.dal.model.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JuanPablo on 4/28/2016.
 */
public class UserTest {
    protected MongoDB persistence;
    protected GenericPersistence genericPersistence = new GenericPersistence();
    protected UserDAO userDAO = new UserDAO();
    protected HumanResourceDAO hrDAO= new HumanResourceDAO();
    @Test
    public void persist() {
        if(userDAO.countTenant(999)<=0)
            userDAO.cleanUsers(999);
        Date date = new Date();
        User usu = new User(999,"usu1","usu1@my.com","usuario 1","prueba 1",date, "123456", Gender.MALE, date, date, true);
        userDAO.persist(usu);
        usu = new User(999,"usu2","usu2@my.com","usuario 2","prueba 2",date, "123456", Gender.MALE, date, date, true);
        userDAO.persist(usu);

        System.out.println(userDAO.countTenant(999));

        HumanResource hr = new HumanResource();
        hr.setStatus(true);
        hr.setActive(true);
        hr.setBirthDate(date);
        hr.setTenantId(999);
        hr.setEmail("hr1@s.com");
        hr.setUsername("usu1");
        List<Rol> roles = new ArrayList<>();
        roles.add(Rol.MECHANIC);
        roles.add(Rol.OTHER);
        hr.setRoles(roles);
        hrDAO.persist(hr);


        hr = new HumanResource();
        hr.setStatus(true);
        hr.setActive(true);
        hr.setTenantId(999);
        hr.setBirthDate(date);
        hr.setEmail("hr2@s.com");
        hr.setUsername("HR");
        roles = new ArrayList<>();
        roles.add(Rol.ADMINISTRATOR);
        roles.add(Rol.MECHANIC);
        hr.setRoles(roles);
        hrDAO.persist(hr);

        List<HumanResource> lhr = hrDAO.getAllHumanResources(999,0,1000);
        System.out.println(hrDAO.countTenant(999));




    }
}
