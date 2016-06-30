package com.usbus.services.administration;

import com.usbus.bll.administration.beans.TenantBean;
import com.usbus.commons.auxiliaryClasses.TenantStyleAux;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.model.Tenant;
import com.usbus.services.auth.Secured;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

/**
 * Created by Lufasoch on 26/06/2016.
 */
@Path("{tenantId}/Tenant")
public class TenantService {
    TenantBean ejb = new TenantBean();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getTenantStyle(@PathParam("tenantId")long tenantId) throws IOException {

        TenantStyleAux tenantStyleAux = ejb.getTenantStyle(tenantId);
        if (tenantStyleAux == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(tenantStyleAux).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR})
    public Response updateTenantStyle(@PathParam("tenantId") Long tenantId, TenantStyleAux style ) throws IOException {
        String String = ejb.saveTenantStyle(tenantId,style);
        if (String == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(ejb.getById(String)).build();
    }
}
