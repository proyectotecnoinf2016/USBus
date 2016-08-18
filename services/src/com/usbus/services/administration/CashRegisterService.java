package com.usbus.services.administration;

import com.usbus.bll.administration.beans.CashRegisterBean;
import com.usbus.commons.enums.CashOrigin;
import com.usbus.commons.enums.CashPayment;
import com.usbus.commons.enums.CashType;
import com.usbus.commons.enums.Rol;
import com.usbus.commons.exceptions.CashRegisterException;
import com.usbus.dal.model.CashRegister;
import com.usbus.services.auth.Secured;
import jxl.write.WriteException;
import org.jose4j.json.internal.json_simple.JSONArray;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

/**
 * Created by jpmartinez on 02/07/16.
 */
@Path("{tenantId}/cashregister")
public class CashRegisterService {
    //    @Secured(Rol.ADMINISTRATOR)
    CashRegisterBean ejb = new CashRegisterBean();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response createService(CashRegister cashRegister) {
        try {
            switch (cashRegister.getType()) {
                case CASH_CLOSURE:
                    ejb.closeCashRegister(cashRegister.getTenantId(), cashRegister.getBranchId(), cashRegister.getWindowsId(), cashRegister.getSellerName(), cashRegister.getAmount());
                    return Response.ok().build();
                case CASH_INIT:
                    ejb.openCashRegister(cashRegister.getTenantId(), cashRegister.getBranchId(), cashRegister.getWindowsId(), cashRegister.getSellerName(), cashRegister.getAmount());
                    return Response.ok().build();
                default:
                    ejb.persist(cashRegister);
                    return Response.ok().build();
            }
        } catch (CashRegisterException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }

    }

    @GET
    @Path("{cashRegisterId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response getService(@PathParam("tenantId") long tenantId, @PathParam("cashRegisterId") Long cashRegisterId) {

        CashRegister cashRegister = ejb.getByLocalId(tenantId, cashRegisterId);
        if (cashRegister == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(cashRegister).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response queryService(@PathParam("tenantId") long tenantId,
                                 @QueryParam("query") String query,
                                 @QueryParam("user") String user,
                                 @QueryParam("branchId") Long branchId,
                                 @QueryParam("windowsId") Long windowsId,
                                 @QueryParam("type") CashType type,
                                 @QueryParam("origin") CashOrigin origin,
                                 @QueryParam("payment") CashPayment payment,
                                 @QueryParam("dateFrom") Date from,
                                 @QueryParam("dateTo") Date to,
                                 @QueryParam("offset") int offset,
                                 @QueryParam("limit") int limit) throws IOException, WriteException {
        List<CashRegister> cashRegisterList = null;
        switch (query.toUpperCase()) {
            case "ALL":
                cashRegisterList = ejb.getByTenantBranchWindow(tenantId, branchId, windowsId, limit, offset);
                if (cashRegisterList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(cashRegisterList).build();

            case "CASH_COUNT":
                try {
                    Double cashCount = ejb.cashCount(tenantId, branchId, windowsId);
                    JSONObject jo = new JSONObject();
                    jo.put("cashCount",cashCount);
                    JSONArray ja = new JSONArray();
                    ja.add(jo);
                    return Response.ok(ja).build();
                } catch (CashRegisterException e) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
                }
            case "PAYMENT":
                cashRegisterList = ejb.getByTypeOriginPaymentDate(tenantId,branchId,windowsId,null,null,null,null,payment,null,limit,offset);
                if (cashRegisterList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(cashRegisterList).build();
            case "TYPE":
                cashRegisterList = ejb.getByTypeOriginPaymentDate(tenantId,branchId,windowsId,null,null,type,null,null,null,limit,offset);
                if (cashRegisterList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(cashRegisterList).build();
            case "ORIGIN":
                cashRegisterList = ejb.getByTypeOriginPaymentDate(tenantId,branchId,windowsId,null,null,null,origin,null,null,limit,offset);
                if (cashRegisterList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(cashRegisterList).build();
            case "DATE":
                cashRegisterList = ejb.getByTypeOriginPaymentDate(tenantId,branchId,windowsId,from,to,null,null,null,null,limit,offset);
                if (cashRegisterList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(cashRegisterList).build();
            case "SELLER":
                cashRegisterList = ejb.getByTypeOriginPaymentDate(tenantId,branchId,windowsId,null,null,null,null,null,user,limit,offset);
                if (cashRegisterList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(cashRegisterList).build();
            case "DATE_X":
                cashRegisterList = ejb.getByTypeOriginPaymentDate(tenantId,branchId,windowsId,from,to,type,origin,payment,user,limit,offset);
                if (cashRegisterList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(cashRegisterList).build();
            case "CURRENT":
                cashRegisterList = ejb.currentCashRegister(tenantId,branchId,windowsId,limit,offset);
                if (cashRegisterList == null) {
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(cashRegisterList).build();
            case "ISOPEN":
                boolean isOpen = ejb.isCashRegisterOpen(tenantId,branchId,windowsId);
                JSONObject jo = new JSONObject();
                jo.put("isOpen",isOpen);
                JSONArray ja = new JSONArray();
                ja.add(jo);
                return Response.ok(ja).build();
            case "REPORT":
                String base64 = ejb.createExcel(tenantId,user,branchId,windowsId,from,to);
                if(base64.equals(null) || base64.isEmpty()){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(base64).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();

    }

}
