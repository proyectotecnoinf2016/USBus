package administration;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by jpmartinez on 08/05/16.
 */
@Stateless
@Path("/test")
public class TestREST {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test(){
        return "Estas en el servicio del war nuevo Services_war";
    }
}
