package com.usbus.dal.dao;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfWriter;
import com.usbus.commons.auxiliaryClasses.RouteStop;
import com.usbus.commons.auxiliaryClasses.Seat;
import com.usbus.commons.enums.TicketStatus;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.*;
import org.bson.types.ObjectId;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by Lufasoch on 30/05/2016.
 */
public class TicketDAO {
    private final Datastore ds;
    private final GenericPersistence dao;

    public TicketDAO() {
        ds = MongoDB.instance().getDatabase();
        dao = new GenericPersistence();
    }

    public String persist(Ticket ticket) {
        return dao.persist(ticket);
    }

    public long countAll() {
        return dao.count(Ticket.class);
    }

    public long countTenant(long tenantId) {
        Query<Ticket> query = ds.createQuery(Ticket.class);
        query.criteria("tenantId").equal(tenantId);
        return query.countAll();
    }

    public Ticket getById(String id) {
        return dao.get(Ticket.class, id);
    }

    public Ticket getByLocalId(long tenantId, Long id) {
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }

        Query<Ticket> query = ds.createQuery(Ticket.class);

        query.and(query.criteria("id").equal(id),
                query.criteria("tenantId").equal(tenantId));

        return query.get();
    }

    public List<Ticket> TicketsByBuyerAndStatus(Long tenantId,String username, TicketStatus status, int offset, int limit) {
        if ((!(tenantId>0))|| (username == null) || (status == null)) {
            return null;
        }
        Query<User> queryUser = ds.createQuery(User.class);

        queryUser.and(queryUser.criteria("username").equal(username),
                queryUser.criteria("tenantId").equal(tenantId));
        User user =  queryUser.get();

        Query<Ticket> query = ds.createQuery(Ticket.class);
        query.and(query.criteria("passenger").equal(user), query.criteria("status").equal(status));
        return query.offset(offset).limit(limit).asList();
    }

    public List<Ticket> getByJourneyId(long tenantId, Long id, int offset, int limit, TicketStatus ticketStatus) {
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }


        Query<Journey> queryJ = ds.createQuery(Journey.class);

        queryJ.and(queryJ.criteria("id").equal(id),
                queryJ.criteria("tenantId").equal(tenantId));

        Journey journey = queryJ.get();

        Query<Ticket> query = ds.createQuery(Ticket.class);

        query.and(query.criteria("journey").equal(journey),
                query.criteria("tenantId").equal(tenantId),
                query.criteria("status").equal(ticketStatus));

        return query.offset(offset).limit(limit).asList();
    }

    public Ticket setPassenger(long tenantId, Long ticketId, User passenger) {
        if (!(tenantId > 0) || (passenger == null) || !(ticketId > 0)) {
            return null;
        } else {
            Query<Ticket> query = ds.createQuery(Ticket.class);

            query.and(query.criteria("id").equal(ticketId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Ticket> updateOp = ds.createUpdateOperations(Ticket.class).set("passenger", passenger);
            ds.update(query, updateOp);

            return getByLocalId(tenantId, ticketId);
        }
    }

    public void remove(String id) {
        dao.remove(Ticket.class, id);
    }

    public void clean() {
        ds.delete(ds.createQuery(Ticket.class));
    }

    public Long getNextId(long tenantId) {
        if (tenantId < 0) {
            return null;
        } else {
            Query<Ticket> query = ds.createQuery(Ticket.class);
            query.criteria("tenantId").equal(tenantId);
            query.order("-id").retrievedFields(true, "id");
            Ticket ticket = query.get();
            if (ticket == null) {
                return new Long(1);
            }
            return ticket.getId() + 1;
        }
    }

    public void setInactive(Long tenantId, Long ticketId) {
        if (!(tenantId > 0) || (ticketId == null)) {
        } else {
            Query<Ticket> query = ds.createQuery(Ticket.class);

            query.and(query.criteria("id").equal(ticketId),
                    query.criteria("tenantId").equal(tenantId));
            UpdateOperations<Ticket> updateOp = ds.createUpdateOperations(Ticket.class).set("status", TicketStatus.CANCELED);
            ds.update(query, updateOp);
        }
    }

    public List<Ticket> /*getByGetsOff*/getByJourney(long tenantId/*, String getOffStopName*/, Journey journey){
        if (tenantId > 0 /*&& !getOffStopName.equals(null)*/ && !journey.equals(null)) {
            Query<Ticket> query = ds.createQuery(Ticket.class);
            query.and(query.criteria("tenantId").equal(tenantId), query.criteria("journey").equal(journey),
                    query.criteria("closed").equal(true), query.criteria("status").equal(TicketStatus.CONFIRMED));
            return  query.asList();
        } else {
            return null;
        }
    }

    public List<Integer> getFreeSeatsForRouteStop(long tenantId, Double routeStopKmA, Double routeStopKmB, Long journeyId){
        if (tenantId > 0 && routeStopKmA != null && routeStopKmB != null && journeyId != null) {
//GET TICKETS OF A JOURNEY
            Query<Journey> query = ds.createQuery(Journey.class);
            query.and(query.criteria("tenantId").equal(tenantId), query.criteria("id").equal(journeyId));
            Journey journey = query.get();

            List<Ticket> ticketList = getByJourney(tenantId, journey);
            List<Ticket> auxTicketList = new ArrayList<>(ticketList);
//GET TICKETS OF A JOURNEY

//GET RESERVATIONS BY JOURNEY
            Query<Reservation> rquery = ds.createQuery(Reservation.class);
            rquery.and(rquery.criteria("tenantId").equal(tenantId), rquery.criteria("journeyId").equal(journeyId),
                    rquery.criteria("active").equal(true));
            List<Reservation> reservations = rquery.asList();
            List<Reservation> auxReservationList = new ArrayList<>(reservations);
//GET RESERVATIONS BY JOURNEY

//GET RESERVATIONS KM...
            List<RouteStop> routeList = journey.getService().getRoute().getBusStops();
            List<RouteStop> auxRouteStopList = new ArrayList<>(routeList);
            Map<String,Double> map = new HashMap<>();

            for(RouteStop auxRouteStop : auxRouteStopList){
                map.put(auxRouteStop.getBusStop(),auxRouteStop.getKm());
            }
//GET RESERVATIONS KM...

            List<Integer> returnSeatNumberList = new ArrayList<>();
            int numberOfSeats = journey.getBus().getSeats();
            for (Integer i = 1; i <= numberOfSeats; i++) { returnSeatNumberList.add(i); }

            if (!auxTicketList.isEmpty()) {
//REMOVE OCCUPIED SEATS
                for (Ticket auxTicket : auxTicketList) {
                    if( returnSeatNumberList.contains(auxTicket.getSeat())
                        && ( (routeStopKmA >= auxTicket.getKmGetsOn() && routeStopKmA < auxTicket.getKmGetsOff())
                            || (routeStopKmB > auxTicket.getKmGetsOn() && routeStopKmB <= auxTicket.getKmGetsOff())
                            || (routeStopKmA <= auxTicket.getKmGetsOn() && routeStopKmB >= auxTicket.getKmGetsOff()) ) ){
                        returnSeatNumberList.remove(auxTicket.getSeat());
                    }
                }
                for (Reservation auxReservation : auxReservationList) {
                    if( returnSeatNumberList.contains(auxReservation.getSeat())
                            && ( (routeStopKmA >= map.get(auxReservation.getGetsOn()) && routeStopKmA < map.get(auxReservation.getGetsOff()))
                            || (routeStopKmB > map.get(auxReservation.getGetsOn()) && routeStopKmB <= map.get(auxReservation.getGetsOff()))
                            || (routeStopKmA <= map.get(auxReservation.getGetsOn()) && routeStopKmB >= map.get(auxReservation.getGetsOff())) ) ){
                        returnSeatNumberList.remove(auxReservation.getSeat());
                    }
                }
//REMOVE OCCUPIED SEATS
                return returnSeatNumberList;
            }
            else {
                return returnSeatNumberList;
            }
        } else {
            return null;
        }
    }

    public JSONObject getOccupiedSeatsForSubRoute(long tenantId, Double routeStopKmA, Double routeStopKmB, Long journeyId){
        if (tenantId > 0 && routeStopKmA != null && routeStopKmB != null && journeyId != null) {
//GET TICKETS OF A JOURNEY
            Query<Journey> query = ds.createQuery(Journey.class);
            query.and(query.criteria("tenantId").equal(tenantId), query.criteria("id").equal(journeyId));
            Journey journey = query.get();

            //List<Ticket> ticketList = getByJourney(tenantId, journey);

            Query<Ticket> ticketsQuery = ds.createQuery(Ticket.class);
            ticketsQuery.and(ticketsQuery.criteria("tenantId").equal(tenantId), ticketsQuery.criteria("journey").equal(journey),
                    ticketsQuery.criteria("closed").equal(true));
            List<Ticket> ticketList = ticketsQuery.asList();

            List<Ticket> auxTicketList = new ArrayList<>(ticketList);
//GET TICKETS OF A JOURNEY

//GET RESERVATIONS BY JOURNEY
            Query<Reservation> rquery = ds.createQuery(Reservation.class);
            rquery.and(rquery.criteria("tenantId").equal(tenantId), rquery.criteria("journeyId").equal(journeyId),
                    rquery.criteria("active").equal(true));
            List<Reservation> reservations = rquery.asList();
            List<Reservation> auxReservationList = new ArrayList<>(reservations);
//GET RESERVATIONS BY JOURNEY

//GET ROUTESTOPS KM...
            List<RouteStop> routeList = journey.getService().getRoute().getBusStops();
            List<RouteStop> auxRouteStopList = new ArrayList<>(routeList);
            Map<String,Double> map = new HashMap<>();

            for(RouteStop auxRouteStop : auxRouteStopList){
                map.put(auxRouteStop.getBusStop(),auxRouteStop.getKm());
            }
//GET ROUTESTOPS KM...

            JSONObject result = new JSONObject();
            List<Seat> soldSeats = new ArrayList<>();
            List<Seat> bookedSeats = new ArrayList<>();
            List<Integer> processedSeats = new ArrayList<>();
            //int numberOfSeats = journey.getBus().getSeats();
            //for (Integer i = 1; i <= numberOfSeats; i++) { returnSeatNumberList.add(i); }

            if (!auxTicketList.isEmpty()) {
//ADD SOLD SEATS
                for (Ticket auxTicket : auxTicketList) {
                    if(auxTicket.getStatus() != TicketStatus.USED && auxTicket.getStatus() != TicketStatus.CANCELED) {
                        if (!processedSeats.contains(auxTicket.getSeat())
                                && ((routeStopKmA >= auxTicket.getKmGetsOn() && routeStopKmA < auxTicket.getKmGetsOff())
                                || (routeStopKmB > auxTicket.getKmGetsOn() && routeStopKmB <= auxTicket.getKmGetsOff())
                                || (routeStopKmA <= auxTicket.getKmGetsOn() && routeStopKmB >= auxTicket.getKmGetsOff()))) {
                            soldSeats.add(new Seat(auxTicket.getSeat(), null, false));
                            processedSeats.add(auxTicket.getSeat());
                        }
                    }
                }
            }
//ADD SOLD SEATS

//ADD BOOKED SEATS
            if (!auxReservationList.isEmpty()) {
                for (Reservation auxReservation : auxReservationList) {
                    if( !processedSeats.contains(auxReservation.getSeat())
                            && ( (routeStopKmA >= map.get(auxReservation.getGetsOn()) && routeStopKmA < map.get(auxReservation.getGetsOff()))
                            || (routeStopKmB > map.get(auxReservation.getGetsOn()) && routeStopKmB <= map.get(auxReservation.getGetsOff()))
                            || (routeStopKmA <= map.get(auxReservation.getGetsOn()) && routeStopKmB >= map.get(auxReservation.getGetsOff())) ) ){
                        bookedSeats.add(new Seat(auxReservation.getSeat(), null, false));
                        processedSeats.add(auxReservation.getSeat());
                    }
                }
            }
            result.put("sold", new JSONArray(soldSeats));
            result.put("booked", new JSONArray(bookedSeats));

            return result;
        } else {
            return null;
        }
    }

    public List<Ticket> updateTicketsStatus(long tenantId, Long journeyId, String routeStop){
        if (tenantId > 0 && journeyId != null && routeStop != null) {
//GET TICKETS FOR THE JOURNEY
            Query<Journey> query = ds.createQuery(Journey.class);
            query.and(query.criteria("tenantId").equal(tenantId), query.criteria("id").equal(journeyId));
            Journey journey = query.get();

            Query<Ticket> ticketsQuery = ds.createQuery(Ticket.class);
            ticketsQuery.and(ticketsQuery.criteria("tenantId").equal(tenantId), ticketsQuery.criteria("journey").equal(journey),
                    ticketsQuery.criteria("closed").equal(true));
            List<Ticket> ticketList = ticketsQuery.asList();

//UPDATE SEATS
            List<Ticket> updatedTicketList = new ArrayList<>(ticketList);
            if (!ticketList.isEmpty()) {
                for (Ticket auxTicket : ticketList) {
                    if(auxTicket.getGetsOff().getName().equalsIgnoreCase(routeStop)) {
                        auxTicket.setStatus(TicketStatus.USED);
                        String oid = persist(auxTicket);
                        if (oid != null) {
                            updatedTicketList.set(updatedTicketList.indexOf(auxTicket), auxTicket);
                        }
                    }
                }
            }
//UPDATE SEATS

            return updatedTicketList;
        } else {
            return null;
        }
    }

    public static byte[] fileToByteArray(String fileName) {
        try {
            File f = new File(fileName);
            FileInputStream in = new FileInputStream(f);
            byte[] bytes = new byte[(int)f.length()];
            int c = -1;
            int ix = 0;
            while ((c = in.read()) > -1) {
                System.out.println(c);
                bytes[ix] = (byte)c;
                ix++;
            }
            in.close();
            return bytes;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public String createPDF(String tenantName, Ticket ticket) throws DocumentException, IOException, FileNotFoundException {
        // OBTENER TENANT
        Tenant tenantOriginal = null;
        if (tenantName != null && !tenantName.isEmpty()) {

            Query<Tenant> query = ds.createQuery(Tenant.class);
            query.limit(1).criteria("name").equal(tenantName);
            tenantOriginal = query.get();
        }
        // SO CHECK! FOLDER CHECK! PATH CHECK!...
        if (tenantOriginal != null) {
            String OS = System.getProperty("os.name");
            String stringAux;
            File path = null;

            // MUST NOT USE DATE AGAIN
            Date emissionDate = ticket.getEmissionDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(emissionDate);
            Integer year = cal.get(Calendar.YEAR);
            Integer month = cal.get(Calendar.MONTH)+1;
            Integer day = cal.get(Calendar.DAY_OF_MONTH);
            Integer hour = cal.get(Calendar.HOUR_OF_DAY);
            Integer minute = cal.get(Calendar.MINUTE);
            String Syear = year.toString();
            String Smon;
            String Sday;
            String Shour;
            String Smin;

            if(month < 10){
                Smon = "0"+month.toString();
            }else {
                Smon = month.toString();
            }
            if(day < 10) {
                Sday = "0"+day.toString();
            } else {
                Sday = day.toString();
            }
            if(hour < 10) {
                Shour = "0"+hour.toString();
            } else {
                Shour = hour.toString();
            }
            if(minute < 10) {
                Smin = "0"+minute.toString();
            } else {
                Smin = minute.toString();
            }

//            if (OS.startsWith("Windows")) {
//                stringAux = "C:" + File.separator + "USBus" + File.separator + "Tickets" + File.separator +
//                        tenantOriginal.getName() + File.separator + year.toString()+"-"+Smon + File.separator + Sday + File.separator +
//                        ticket.getId() + ".pdf";
//                path = new File("C:" + File.separator + "USBus" + File.separator + "Tickets" + File.separator +
//                        tenantOriginal.getName() + File.separator + year.toString()+"-"+Smon + File.separator + Sday + File.separator);
//                if (!(path.exists() && path.isDirectory())) {
//                    path.mkdirs();
//                }
//            } else {
//                stringAux = File.separator + "USBus" + File.separator + "Tickets" + File.separator +
//                        tenantOriginal.getName() + File.separator + year.toString()+"-"+Smon + File.separator + Sday + File.separator
//                        + ticket.getId() + ".pdf";
//                path = new File(File.separator + "USBus" + File.separator + "Tickets" + File.separator +
//                        tenantOriginal.getName() + File.separator + year.toString()+"-"+Smon + File.separator + Sday + File.separator);
//                if (!(path.exists() && path.isDirectory())) {
//                    path.mkdirs();
//                }
//            }

            if (OS.startsWith("Windows")) {
                stringAux = "C:" + File.separator + "USBus" + File.separator + "Tickets" + File.separator +
                        tenantOriginal.getName() + File.separator + ticket.getBranchId() + ticket.getWindowId()
                + ticket.getId() + ".pdf";
                path = new File("C:" + File.separator + "USBus" + File.separator + "Tickets" + File.separator +
                        tenantOriginal.getName() + File.separator);
                if (!(path.exists() && path.isDirectory())) {
                    path.mkdirs();
                }
            } else {
                stringAux = File.separator + "USBus" + File.separator + "Tickets" + File.separator +
                        tenantOriginal.getName() + File.separator + ticket.getBranchId() + ticket.getWindowId()
                        + ticket.getId() + ".pdf";
                path = new File(File.separator + "USBus" + File.separator + "Tickets" + File.separator +
                        tenantOriginal.getName() + File.separator);
                if (!(path.exists() && path.isDirectory())) {
                    path.mkdirs();
                }
            }

//            // OBTENER USUARIO
//            Query<User> query = ds.createQuery(User.class);
//            query.and(query.criteria("username").equal(passengerNickname),
//                    query.criteria("tenantId").equal(tenantOriginal.getTenantId()));
//            query.retrievedFields(false,"salt","passwordHash");
//            User user = query.get();

            // CREAR DOCUMENTO
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(stringAux));
            document.open();

            String qr ="{\"tenantId\":"+tenantOriginal.getTenantId()+", \"id\":"+ticket.getId()+"}";
            BarcodeQRCode qrcode = new BarcodeQRCode(qr.trim(), 1, 1, null);
            Image qrcodeImage = qrcode.getImage();
            qrcodeImage.setPaddingTop(0);
            qrcodeImage.scalePercent(400);

            document.addAuthor(tenantName);
            document.addCreationDate();

            document.add(new Paragraph(""));
            document.add(new Paragraph("    " + tenantOriginal.getName()));
            document.add(new Paragraph("Suc/Puesto "+ ticket.getBranchId() + " / " + ticket.getWindowId()));
            document.add(new Paragraph("Funcionario: " + ticket.getSellerName()));
            document.add(new Paragraph("Fecha: " + Sday + "/" + Smon + "/" + Sday + " " + Shour + ":" + Smin));
            document.add(new Paragraph("Ticket: " + ticket.getId()));
            document.add(new Paragraph("Servicio: " + ticket.getJourney().getService().getName()));
            document.add(new Paragraph("Sube en: " + ticket.getGetsOn().getName()));
            document.add(new Paragraph("Baja en: " + ticket.getGetsOff().getName()));
            document.add(qrcodeImage);

            // step 5
            document.close();

            byte[] buf = fileToByteArray("your-pdf-file.pdf");
            String s = new sun.misc.BASE64Encoder().encode(buf);
            return s;
        }
        return null;
    }
}
