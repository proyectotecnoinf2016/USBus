package com.usbus.services.administration;

import com.usbus.bll.administration.beans.MaintenanceBean;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.model.Maintenance;
import com.usbus.services.auth.Secured;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Lufasoch on 27/06/2016.
 */
@Path("{tenantId}/maintenance")
public class MaintenanceService {
    MaintenanceBean ejb = new MaintenanceBean();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response createMaintenance(Maintenance maintenance) {
        String oid = ejb.persist(maintenance);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @PUT
    @Path("{maintenanceId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response updateMaintenance(@PathParam("tenantId")Long tenantId, @PathParam("maintenanceId")Long maintenanceId, Maintenance maintenance){
        Maintenance maintenanceAux = ejb.getByLocalId(tenantId, maintenanceId);
        maintenance.set_id(maintenanceAux.get_id());
        String oid = ejb.persist(maintenance);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @GET
    @Path("{maintenanceId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response getService(@PathParam("tenantId")long tenantId, @PathParam("maintenanceId") Long maintenanceId){

        Maintenance maintenanceAux = ejb.getByLocalId(tenantId,maintenanceId);
        if (maintenanceAux == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(maintenanceAux).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
    public Response getAll(@PathParam("tenantId")long tenantId,
                           @QueryParam("offset") int offset,
                           @QueryParam("limit") int limit){
        List<Maintenance> maintenanceList = ejb.getByTenant(tenantId, offset, limit);
        if (maintenanceList == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(maintenanceList).build();
    }
}
