package com.usbus.services.administration;

import com.usbus.bll.administration.beans.BranchBean;
import com.usbus.commons.enums.Rol;
import com.usbus.dal.model.Branch;
import com.usbus.services.auth.Secured;
import org.bson.types.ObjectId;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Lufasoch on 22/06/2016.
 */
@Path("{tenantId}/branch")
public class BranchService {
    BranchBean ejb = new BranchBean();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBranch(Branch branch) {
        String oid = ejb.persist(branch);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @PUT
    @Path("{branchId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response updateBranch(@PathParam("tenantId")long tenantId, @PathParam("branchId")Long branchId, Branch branch){
        Branch serviceAux = ejb.getByLocalId(tenantId, branchId);
        branch.set_id(serviceAux.get_id());
        String oid = ejb.persist(branch);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }

    @GET
    @Path("{branchId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT, Rol.CASHIER, Rol.ASSISTANT,Rol.DRIVER,Rol.MECHANIC,Rol.OTHER})
    public Response getBranch(@PathParam("tenantId")long tenantId, @PathParam("branchId") Long branchId){

        Branch branchAux = ejb.getByLocalId(tenantId,branchId);
        if (branchAux == null){
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(branchAux).build();
    }

    //-
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT, Rol.CASHIER, Rol.ASSISTANT,Rol.DRIVER,Rol.MECHANIC,Rol.OTHER})
    public Response queryBranch(@PathParam("tenantId")long tenantId,
                                @QueryParam("query") String query,
                                @QueryParam("status") boolean status,
                                @QueryParam("branchName") String branchName,
                                @QueryParam("offset") int offset,
                                @QueryParam("limit") int limit){
        switch (query.toUpperCase()){
            case "NAME":
                List<Branch> branchAux = ejb.getByBranchName(tenantId,branchName,status,  offset, limit);
                if (branchAux == null){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(branchAux).build();
            case "ALL":
                List<Branch> serviceList = ejb.getBranchesByTenant(tenantId,status, offset, limit);
                if (serviceList == null){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }
                return Response.ok(serviceList).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();

    }

//    @GET
//    @Path("name/{branchName}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
//    public Response getBranch(@PathParam("tenantId")long tenantId, @PathParam("branchName") String branchName){
//
//        Branch branchAux = ejb.getByBranchName(tenantId,branchName);
//        if (branchAux == null){
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//        return Response.ok(branchAux).build();
//    }
//
//    @GET
//    @Path("get/all")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Secured({Rol.ADMINISTRATOR, Rol.CLIENT})
//    public Response getServiceListByTenant(@PathParam("tenantId")long tenantId, @QueryParam("offset") int offset, @QueryParam("limit") int limit){
//
//        List<Branch> serviceList = ejb.getBranchesByTenant(tenantId, offset, limit);
//        if (serviceList == null){
//            return Response.status(Response.Status.NO_CONTENT).build();
//        }
//        return Response.ok(serviceList).build();
//    }

    @DELETE
    @Path("{branchId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Secured(Rol.ADMINISTRATOR)
    public Response removeService(@PathParam("tenantId")Long tenantId, @PathParam("branchId") Long branchId){
        try {
            ejb.setInactive(tenantId,branchId);
            return Response.ok().build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
}
