package com.usbus.dal.test;

import com.usbus.commons.enums.BusStatus;
import com.usbus.dal.dao.BusDAO;
import com.usbus.dal.model.Bus;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Lufasoch on 09/06/2016.
 */
public class BusTest {
    protected BusDAO busDAO = new BusDAO();

    @Test
    public void busesByTenantIdAndStatus() throws Exception {
        busDAO.clean();

        Bus bus01 = new Bus(1, "ABC01", "Inu", "0001", 0.1, 5000.0, BusStatus.ACTIVE, true, 44, 200, 12);
        Bus bus02 = new Bus(1, "ABC02", "Inu", "0001", 0.1, 5000.0, BusStatus.ACTIVE, true, 44, 200, 12);
        busDAO.persist(bus01);
        busDAO.persist(bus02);
        List<Bus> busList = busDAO.BusesByTenantIdAndStatus(1, BusStatus.ACTIVE, 0, 5);
        assertEquals(busList.get(0).getBrand(), busList.get(1).getBrand());
    }

}