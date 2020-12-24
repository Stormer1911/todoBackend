package de.cmm.Endpoints;

import de.cmm.model.HelloWorldModel;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

@Path("api/hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldEndpoint {

    @GET
    public Response getHelloWorld(){

        System.out.println("test");
        return Response.accepted().entity(new HelloWorldModel("Hello: "+new Date().toString())).build();
    }


}
