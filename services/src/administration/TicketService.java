package administration;

import com.usbus.bll.administration.beans.TicketBean;
import com.usbus.dal.model.Ticket;
import org.bson.types.ObjectId;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Lufasoch on 06/06/2016.
 */
@Path("{tenantId}/ticket")
public class TicketService {
    TicketBean ejb = new TicketBean();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBus(Ticket ticketAux){
        ObjectId oid = ejb.persist(ticketAux);
        if (oid==null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.ok(ejb.getById(oid)).build();
    }


}
