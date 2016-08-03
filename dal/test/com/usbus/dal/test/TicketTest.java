package com.usbus.dal.test;

import com.usbus.commons.auxiliaryClasses.RouteStop;
import com.usbus.commons.auxiliaryClasses.Seat;
import com.usbus.commons.enums.*;
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
 * Created by Kavesa on 09/06/16.
 */
public class TicketTest {
    protected TicketDAO ticketDAO = new TicketDAO();
    protected UserDAO userDAO = new UserDAO();
    protected JourneyDAO journeyDAO = new JourneyDAO();
    protected ServiceDAO serviceDAO = new ServiceDAO();
    protected BusDAO busDAO = new BusDAO();
    protected HumanResourceDAO humanResourceDAO = new HumanResourceDAO();
    protected TenantDAO tenantDAO = new TenantDAO();
    protected BusStopDAO busStopDAO = new BusStopDAO();
    protected RouteDAO routeDAO = new RouteDAO();

    @Test
    public void persistDotDotDot() throws ParseException {
        ticketDAO.clean();
        userDAO.cleanUsers(1);
        journeyDAO.clean();
        serviceDAO.clean();
        busDAO.clean();
        humanResourceDAO.clean();
        tenantDAO.clean();
        busStopDAO.clean();
        routeDAO.clean();

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("America/Montevideo"));

        //TENANT
        Tenant tenant01 = new Tenant(1,"SSS");
        tenantDAO.persist(tenant01);
        //TENANT

        //BUSSTOP
        BusStop busStop01 = new BusStop(1,1L,"B01",true,1D,0D,0D);
        BusStop busStop02 = new BusStop(1,2L,"B02",true,1D,0D,15D);
        BusStop busStop03 = new BusStop(1,3L,"B03",true,1D,15D,15D);
        BusStop busStop04 = new BusStop(1,4L,"B04",true,1D,30D,15D);
        busStopDAO.persist(busStop01);
        busStopDAO.persist(busStop02);
        busStopDAO.persist(busStop03);
        busStopDAO.persist(busStop04);
        //BUSSTOP

        //ROUTE
        RouteStop routeStop01 = new RouteStop("B01",0D,false,0D,0D);
        RouteStop routeStop02 = new RouteStop("B02",15D,false,0D,15D);
        RouteStop routeStop03 = new RouteStop("B03",30D,false,15D,15D);
        RouteStop routeStop04 = new RouteStop("B04",45D,false,30D,15D);
        List<RouteStop> routeStopList01 = new ArrayList<>();
        routeStopList01.add(routeStop01);
        routeStopList01.add(routeStop02);
        routeStopList01.add(routeStop03);
        routeStopList01.add(routeStop04);
        Route route01 = new Route(1,1L,"EREFOUR",busStop01,busStop04,routeStopList01,true,false,3D);
        routeDAO.persist(route01);
        //ROUTE

        //BUS
        Bus bus01 = new Bus(1,"Bus01","Marsadas Bans","ReChino",0D,10000D, BusStatus.ACTIVE,true,46,400,16);
        busDAO.persist(bus01);
        //BUS

        //HHRR
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("America/Montevideo"));

        Date date = new Date();
        List<Rol> roles = new ArrayList<>();
        roles.add(Rol.DRIVER);
        roles.add(Rol.ASSISTANT);

        HumanResource HR01 = new HumanResource(2,"Pepe","pepe@bc.com","Pe","Pe",date,"123456", Gender.MALE, date, date, true, true, roles);
        HumanResource HR02 = new HumanResource(2,"Pepa","pepa@bc.com","Pe","Pa",date,"123456", Gender.FEMALE, date, date, true, true, roles);
        humanResourceDAO.persist(HR01);
        humanResourceDAO.persist(HR02);
        //HHRR

        //SERVICE
        Service service01 = new Service(1,1L,"EREFOURMON", DayOfWeek.MONDAY,new Date(),route01,1,true);
        serviceDAO.persist(service01);
        //SERVICE

        //JOURNEY
        Seat v3 = new Seat(3, Position.CORRIDOR, false);
        Seat v8 = new Seat(8, Position.CORRIDOR, false);
        Seat v13 = new Seat(13, Position.CORRIDOR, false);
        Seat v23 = new Seat(23, Position.WINDOWS, false);
        Seat v40 = new Seat(40, Position.CORRIDOR, false);

        Journey journey01 = new Journey(1,1L,service01,new Date(),bus01,"No",HR01,HR02,1,46,new Seat[]{v3},0,50, JourneyStatus.ACTIVE);
        journeyDAO.persist(journey01);
        //JOURNEY

        //TICKET
        Ticket ticket01 = new Ticket(1,1L,new Date(),false,null,null,456D,null/*user*/,"Joaquin",null/*seller*/,"Rigoberta",true,
                TicketStatus.CONFIRMED,"Hola soy un tocken de Paypal",journey01,1L,3,busStop01,"B01",busStop04,"B04",route01,
                1L,new Date(),null/*BreanchId*/,null/*WindowId*/);
        ticketDAO.persist(ticket01);
        //TICKET

        //ACTUAL TEST
        List<Integer> seatsNumbers = ticketDAO.getFreeSeatsForRouteStop(1,15D,1L);
        //ACTUAL TEST

        System.out.println(seatsNumbers);
////
////        ticket = new Ticket(2, 2L, format.parse("02/06/2016 12:35"), false, null, 406.1, udao.getByUsername(2, "tecnoinf2016"), null);
////        dao.persist(ticket);
////
////        ticket = new Ticket(2, 3L, format.parse("08/06/2016 12:36"), false, null, 123.0, udao.getByUsername(2, "tecnoinf2016"), null);
////        dao.persist(ticket);
////
////        ticket = new Ticket(2, 4L, format.parse("11/06/2016 12:37"), false, null, 456.4, udao.getByUsername(2, "tecnoinf2016"), null);
////        dao.persist(ticket);
////
////        ticket = new Ticket(2, 5L, format.parse("15/06/2016 12:38"), false, null, 656.5, udao.getByUsername(2, "tecnoinf2016"), null);
////        dao.persist(ticket);
////
////        ticket = new Ticket(2, 6L, format.parse("15/06/2016 12:38"), false, null, 656.5, null, null);
////        dao.persist(ticket);
//
//
//        System.out.println(dao.countAll());
    }
}
