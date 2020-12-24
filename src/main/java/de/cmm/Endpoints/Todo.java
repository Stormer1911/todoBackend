package de.cmm.Endpoints;

import de.cmm.Main;
import de.cmm.model.TodoModel;
import io.quarkus.panache.common.Sort;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("api/todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Todo {
    private static final Logger LOG = Logger.getLogger(Todo.class);

    @GET
    public @Valid List<TodoModel> getALl() {
        return TodoModel.findAll(Sort.by("id")).list();
    }

    @GET
    @Path("{id}")
    public @Valid TodoModel getSingle(@PathParam long id){
        LOG.info("requested todo Item "+ id);
        Optional<TodoModel> toDoModel = TodoModel.findByIdOptional(id);
        return  toDoModel.orElseThrow(() ->  new WebApplicationException(Response.Status.NOT_FOUND));

    }


    @POST
    @Transactional
    public Response addTodo( TodoModel toDoModel) {
        LOG.info("recieved new todo: "+toDoModel.toString());
        toDoModel.persist();
        return Response.ok(toDoModel).build();
    }

    @PUT
    @Transactional
    public Response chengeTodo(TodoModel toDoModel){

        Optional<TodoModel> optional = TodoModel.findByIdOptional(toDoModel.id);
        TodoModel old = optional.orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));

        old.subtitle = toDoModel.subtitle;
        old.title = toDoModel.title;
        old.done = toDoModel.done;
        LOG.info("edited: "+old.toString());
        return Response.ok(old).build();

    }
}
