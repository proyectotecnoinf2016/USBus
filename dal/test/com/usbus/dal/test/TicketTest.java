package com.usbus.dal.test;

import com.itextpdf.text.DocumentException;
import com.usbus.dal.dao.TenantDAO;
import com.usbus.dal.dao.TicketDAO;
import com.usbus.dal.dao.UserDAO;
import com.usbus.dal.model.Tenant;
import com.usbus.dal.model.Ticket;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Kavesa on 09/06/16.
 */
public class TicketTest {
    protected TicketDAO dao = new TicketDAO();
    protected UserDAO udao = new UserDAO();
    protected TenantDAO tdao = new TenantDAO();

    @Test
    public void persist() throws ParseException {
        dao.clean();
//
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
//        format.setTimeZone(TimeZone.getTimeZone("America/Montevideo"));
//
////        Ticket ticket = new Ticket(2, 1L, format.parse("08/05/2016 12:34"), false, null, 456.5, udao.getByUsername(2, "tecnoinf2016"), null);
////        dao.persist(ticket);
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

    @Test
    public void pdfTest() throws IOException, DocumentException {
        tdao.clean();
        String tenantName = "SSS";
        Tenant t = new Tenant(1,tenantName);
        tdao.persist(t);
        Ticket ticket = new Ticket();
        Date date = new Date();
        ticket.setEmissionDate(date);
        ticket.setId(666L);
        dao.createPDF(tenantName, ticket);
    }
}
