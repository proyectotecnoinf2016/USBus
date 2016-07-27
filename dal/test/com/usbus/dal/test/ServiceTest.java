//package com.usbus.dal.test;
//
//import com.usbus.commons.auxiliaryClasses.RouteStop;
//import com.usbus.dal.dao.BusStopDAO;
//import com.usbus.dal.dao.RouteDAO;
//import com.usbus.dal.dao.ServiceDAO;
//import com.usbus.dal.model.BusStop;
//import com.usbus.dal.model.Route;
//import com.usbus.dal.model.Service;
//import org.junit.Test;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.DayOfWeek;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.TimeZone;
//
///**
// * Created by Kavesa on 09/06/16.
// */
//public class ServiceTest {
//    protected ServiceDAO dao = new ServiceDAO();
//    protected RouteDAO rdao = new RouteDAO();
//    protected BusStopDAO bsdao = new BusStopDAO();
//
//    @Test
//    public void persist() throws ParseException {
//        dao.clean();
//        rdao.clean();
//        bsdao.clean();
//
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//        format.setTimeZone(TimeZone.getTimeZone("America/Montevideo"));
//
//        Service srv = new Service(2, 1L, "Montevideo - Rivera (Directo)", DayOfWeek.SATURDAY, format.parse("23:15"), null, 2, true);
//        dao.persist(srv);
//
//        srv = new Service(2, 2L, "Montevideo - Rivera (Semi-Directo)", DayOfWeek.WEDNESDAY, format.parse("20:45"), null, 2, true);
//        dao.persist(srv);
//
//        srv = new Service(2, 3L, "Montevideo - Colonia (Directo)", DayOfWeek.SUNDAY, format.parse("08:30"), null, 2, true);
//        dao.persist(srv);
//
//        srv = new Service(2, 4L, "Montevideo - Tacuarembó (Común)", DayOfWeek.MONDAY, format.parse("12:45"), null, 2, true);
//        dao.persist(srv);
//
//        BusStop origin = new BusStop(2, 1L, "Montevideo", true, 10.0);
//        bsdao.persist(origin);
//        BusStop destination = new BusStop(3, 2L, "Colonia", true, 10.0);
//        bsdao.persist(destination);
//        RouteStop rsMvd = new RouteStop("Montevideo", 0.0, false);
//        RouteStop rsPzaCuba = new RouteStop("Plaza Cuba", 7.4, false);
//        RouteStop rsColonia = new RouteStop("Colonia", 176.5, false);
//        List<RouteStop> routeStops = new ArrayList<>();
//        routeStops.add(rsMvd);
//        routeStops.add(rsPzaCuba);
//        routeStops.add(rsColonia);
//        Route route = new Route(2, 1L, "Montevideo - Colonia", origin, destination, routeStops, true, false, 2.30);
//        rdao.persist(route);
//        srv = new Service(2, 5L, "Montevideo - Colonia (Común)", DayOfWeek.FRIDAY, format.parse("09:15"), route, 3, true);
//        dao.persist(srv);
//
//        List<Service> serviceList = dao.getServicesByDayOfTheWeek(2,DayOfWeek.MONDAY,0,100);
//        System.out.print("Cantidad de servicios: ");
//        System.out.println(dao.countAll());
//        System.out.print("Cantidad de servicios por día de la semana: ");
//        System.out.println(serviceList.size());
//    }
//}
