package com.usbus.dal.dao;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.usbus.commons.enums.TicketStatus;
import com.usbus.dal.GenericPersistence;
import com.usbus.dal.MongoDB;
import com.usbus.dal.model.*;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public List<Ticket> getByJourneyId(long tenantId, Long id, int offset, int limit) {
        if (!(tenantId > 0) || (id == null)) {
            return null;
        }


        Query<Journey> queryJ = ds.createQuery(Journey.class);

        queryJ.and(queryJ.criteria("id").equal(id),
                queryJ.criteria("tenantId").equal(tenantId));

        Journey journey = queryJ.get();

        Query<Ticket> query = ds.createQuery(Ticket.class);

        query.and(query.criteria("journey").equal(journey),
                query.criteria("tenantId").equal(tenantId));

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

    public void createPDF(String tenantName, Ticket ticket)	throws DocumentException, IOException {
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

            if (OS.startsWith("Windows")) {
                stringAux = "C:" + File.separator + "USBus" + File.separator + "Tickets" + File.separator +
                        tenantOriginal.getName() + File.separator + year.toString()+"-"+Smon + File.separator + Sday + File.separator +
                ticket.getId() + ".pdf";
                path = new File("C:" + File.separator + "USBus" + File.separator + "Tickets" + File.separator +
                        tenantOriginal.getName() + File.separator + year.toString()+"-"+Smon + File.separator + Sday + File.separator);
                if (!(path.exists() && path.isDirectory())) {
                    path.mkdirs();
                }
            } else {
                stringAux = File.separator + "USBus" + File.separator + "Tickets" + File.separator +
                        tenantOriginal.getName() + File.separator + year.toString()+"-"+Smon + File.separator + Sday + File.separator
                        + ticket.getId() + ".pdf";
                path = new File(File.separator + "USBus" + File.separator + "Tickets" + File.separator +
                        tenantOriginal.getName() + File.separator + year.toString()+"-"+Smon + File.separator + Sday + File.separator);
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

            //"{"tenantId":X, "id":Y}"

            document.add(new Paragraph(""));
            document.add(new Paragraph("    " + tenantOriginal.getName()));
            document.add(new Paragraph("Suc/Puesto "+ ticket.getBranchId() + " / " + ticket.getWindowId()));
            document.add(new Paragraph("Funcionario: " + ticket.getSellerName()));
            document.add(new Paragraph("Fecha: " + Sday + "/" + Smon + "/" + Sday + " " + Shour + ":" + Smin));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Número: " + ticket.getId()));


            // step 5
            document.close();
        }
    }
}
