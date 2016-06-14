package com.usbus.dal.test;

import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.dao.TenantDAO;
import com.usbus.dal.model.Tenant;
import org.junit.Test;

/**
 * Created by jpmartinez on 08/05/16.
 */
public class TenantTest {
    protected MongoDB persistence;
    protected GenericPersistence genericPersistence = new GenericPersistence();
    protected TenantDAO dao = new TenantDAO();
    @Test
    public void persist() {
        dao.clean();

        Tenant t = new Tenant(1,"Prueba1");
        dao.persist(t);

        t = new Tenant(2,"Prueba2");
        dao.persist(t);

        System.out.println(dao.countAll());

        t = new Tenant(1,"Prueba1");
        dao.persist(t);

        System.out.println(dao.countAll());
    }
}
