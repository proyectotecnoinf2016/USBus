package com.usbus.dal.test;

import com.usbus.dal.dao.BusDAO;
import com.usbus.dal.dao.MaintenanceDAO;
import com.usbus.dal.model.Bus;
import com.usbus.dal.model.Maintenance;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by Lufasoch on 03/07/2016.
 */
public class MaintenanceTest {
    protected MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
    protected BusDAO busDAO = new BusDAO();

    @Test //Correr test de bus antes que este
    public void maintenanceGen() throws ParseException {
        maintenanceDAO.clean();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Montevideo"));

        long tenantId = 1;

//        Maintenance man01 = new Maintenance(tenantId,1, busDAO.getByLocalId(1,"ABC01"),"Intercambiar las ruedas de lugar",999,
//                dateFormat.parse("12/06/2016 12:34");
//        Maintenance man02 = new Maintenance()
    }
}
