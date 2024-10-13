package dk.marquardt.api;

import dk.marquardt.model.ingoing.DateRequest;
import dk.marquardt.model.outgoing.DateResponse;
import dk.marquardt.service.DateService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;

@ApplicationScoped
@Path("/date")
@Produces(MediaType.APPLICATION_JSON)
@Schema(name = "Date API")
public class DateApi {

    @Inject
    DateService dateService;

    @GET
    @Path("/all")
    @Operation(summary = "Get all dates", description = "Returns all answered dates")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved"),
            @APIResponse(responseCode = "400", description = "Not found"),
            @APIResponse(responseCode = "500", description = "Server error")
    })
    public List<DateResponse> getDates() {
        return dateService.getAllDates();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get date", description = "Returns date with the id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved"),
            @APIResponse(responseCode = "400", description = "Not found"),
            @APIResponse(responseCode = "500", description = "Server error")
    })
    public DateResponse getDate(@PathParam("id") String id) {
        return dateService.getDate(id);
    }

    @PUT
    @Path("/")
    @Operation(summary = "Upsert date", description = "Create or update date and return it")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully updated"),
            @APIResponse(responseCode = "400", description = "Not found"),
            @APIResponse(responseCode = "500", description = "Server error")
    })
    public DateResponse createOrUpdateDate(@RequestBody DateRequest request) {
        return dateService.upsertDate(request);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete date", description = " Remove date with the id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Successfully retrieved"),
            @APIResponse(responseCode = "400", description = "Not found"),
            @APIResponse(responseCode = "500", description = "Server error")
    })
    public void deleteDate(@PathParam("id") String id) {
        dateService.deleteDate(id);
    }
}
