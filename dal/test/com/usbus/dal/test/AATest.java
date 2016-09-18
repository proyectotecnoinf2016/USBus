package com.usbus.dal.test;

import com.usbus.commons.auxiliaryClasses.*;
import com.usbus.commons.enums.BusStatus;
import com.usbus.commons.enums.Gender;
import com.usbus.commons.enums.JourneyStatus;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.dao.*;
import com.usbus.dal.model.*;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


/**
 * Created by AAraujo on 28/08/2016.
 */
public class AATest {

    protected TenantDAO tenantDAO = new TenantDAO();
    protected BusDAO busDAO = new BusDAO();
    protected UserDAO userDAO = new UserDAO();
    protected HumanResourceDAO hrDAO= new HumanResourceDAO();
    protected BranchDAO branchDAO = new BranchDAO();
    protected BusStopDAO busStopDAO = new BusStopDAO();
    protected RouteDAO routeDAO = new RouteDAO();
    protected ServiceDAO serviceDAO = new ServiceDAO();
    protected JourneyDAO journeyDAO = new JourneyDAO();
    protected ParcelDAO parcelDAO = new ParcelDAO();

    @Test
    public void tenantTest() {
        //tenantDAO.clean();

        Tenant t = new Tenant(1,"Turil");
        tenantDAO.persist(t);

        t = new Tenant(2,"Agencia Central");
        tenantDAO.persist(t);
    }

    @Test
    public void userTest() {
        //userDAO.cleanUsers(1);
        //userDAO.cleanUsers(2);


        List<Rol> roles = new ArrayList<>();
        roles.add(Rol.ADMINISTRATOR);
        HumanResource hr = new HumanResource(2, "agadmin", "admin@ac.com.uy", "Juan", "Agencia", new Date(), "agadmin", Gender.MALE, new Date(), new Date(), true, true, roles);
        byte[] salt = Password.getNextSalt();
        byte[] hash = Password.hashPassword("agadmin".toCharArray(), salt, 10000, 256);
        hr.setSalt(salt);
        hr.setPasswordHash(hash);
        hrDAO.persist(hr);

        roles = new ArrayList<>();
        roles.add(Rol.ASSISTANT);
        hr = new HumanResource(2, "agguarda", "guarda@ac.com.uy", "Pepe", "Guardiola", new Date(), "agguarda", Gender.MALE, new Date(), new Date(), true, true, roles);
        salt = Password.getNextSalt();
        hash = Password.hashPassword("agguarda".toCharArray(), salt, 10000, 256);
        hr.setSalt(salt);
        hr.setPasswordHash(hash);
        hrDAO.persist(hr);

        roles = new ArrayList<>();
        roles.add(Rol.DRIVER);
        hr = new HumanResource(2, "agchofer", "chofer@ac.com.uy", "Juan", "Choffre", new Date(), "agchofer", Gender.MALE, new Date(), new Date(), true, true, roles);
        salt = Password.getNextSalt();
        hash = Password.hashPassword("agchofer".toCharArray(), salt, 10000, 256);
        hr.setSalt(salt);
        hr.setPasswordHash(hash);
        hrDAO.persist(hr);
    }

    @Test
    public void branchTest() {
        //branchDAO.clean();

        List<Window> windows = new ArrayList<>();
        windows.add(new Window(1L, true, true, true));
        Branch b = new Branch(2, 1L, "Montevideo", windows, true, -34.893664, -56.166309);
        branchDAO.persist(b);

        windows = new ArrayList<>();
        windows.add(new Window(2L, true, true, true));
        b = new Branch(2, 2L, "Colonia", windows, true, -34.472533, -57.842681);
        branchDAO.persist(b);
    }

    @Test
    public void busTest() {
        //busDAO.clean();

        Bus bus = new Bus(2, "SAB1234", "Volvo", "V123", 10.0, 5000.0, BusStatus.ACTIVE, true, 40, 500, 10);
        busDAO.persist(bus);
    }

    @Test
    public void busStopTest() {
        //busStopDAO.clean();

        BusStop bs = new BusStop(2, 1L, "Montevideo", true, 10.0, -34.893664, -56.166309, "Bv. Artigas 1234");
        busStopDAO.persist(bs);

        bs = new BusStop(2, 2L, "Plaza Cuba", true, 10.0, -34.872286, -56.202858, "Bv. Artigas 4321");
        busStopDAO.persist(bs);

        bs = new BusStop(2, 3L, "Colonia", true, 10.0, -34.472533, -57.842681, "Sacramento 123");
        busStopDAO.persist(bs);
    }

    @Test
    public void routeTest() {
        //routeDAO.clean();

        BusStop origin = busStopDAO.getByName(2, "Montevideo");
        BusStop destination = busStopDAO.getByName(2, "Colonia");
        List<RouteStop> stops = new ArrayList<>();
        stops.add(new RouteStop("Montevideo", 0.0, true, -34.893664, -56.166309));
        stops.add(new RouteStop("Plaza Cuba", 10.0, true, -34.872286, -56.202858));
        stops.add(new RouteStop("Colonia", 181.0, true, -34.472533, -57.842681));

        Route r = new Route(2, 1L, "Mvdo - PzaC - Col", origin, destination, stops, true, false, 1.95);
        routeDAO.persist(r);
    }

    @Test
    public void serviceTest() throws ParseException {
        //serviceDAO.clean();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("America/Montevideo"));

        Route r = routeDAO.getByLocalId(2, 1L);
        Service s = new Service(2, 1L, "Montevideo - Colonia (Semi Directo)", DayOfWeek.SUNDAY, format.parse("22:15"), r, 1, true);
        serviceDAO.persist(s);

        s = new Service(2, 2L, "Montevideo - Colonia (Semi Directo)", DayOfWeek.MONDAY, format.parse("22:15"), r, 1, true);
        serviceDAO.persist(s);

        s = new Service(2, 3L, "Montevideo - Colonia (Semi Directo)", DayOfWeek.TUESDAY, format.parse("22:15"), r, 1, true);
        serviceDAO.persist(s);
    }

    @Test
    public void journeyTest() throws ParseException {
        //journeyDAO.clean();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("America/Montevideo"));

        Service s = serviceDAO.getByLocalId(2, 1L);
        Bus bus = busDAO.getByLocalId(2, "SAB1234");
        HumanResource driver = hrDAO.getByUsername(2, "agchofer");
        HumanResource guarda = hrDAO.getByUsername(2, "agguarda");
        Seat[] seats = {};
        Journey j = new Journey(2, 1L, s, format.parse("29/08/2016 21:15"), bus, "", driver, guarda, 222, 40, seats, 11, 500, JourneyStatus.ACTIVE);
        journeyDAO.persist(j);
    }

    @Test
    public void parcelTest() {
        Dimension dim = new Dimension(10.0, 10.0, 10.0);
        Parcel p = new Parcel(2, 1L, dim, 200, 1L, 1L, 2L, "Jean Luc", "Andres Araujo", false, false, true);
        parcelDAO.persist(p);

        p = new Parcel(2, 2L, dim, 220, 1L, 1L, 2L, "Pepe", "Kavesa", false, false, true);
        parcelDAO.persist(p);

        p = new Parcel(2, 3L, dim, 230, 1L, 1L, 2L, "Pepa", "Asevak", false, false, true);
        parcelDAO.persist(p);

        p = new Parcel(2, 4L, dim, 240, 1L, 1L, 2L, "Luli", "Ernest", false, false, true);
        parcelDAO.persist(p);

        p = new Parcel(2, 5L, dim, 250, 1L, 1L, 2L, "Fede", "Rico", false, false, true);
        parcelDAO.persist(p);

        p = new Parcel(2, 6L, dim, 260, 1L, 1L, 3L, "Darth", "Vader", false, false, true);
        parcelDAO.persist(p);
    }
}