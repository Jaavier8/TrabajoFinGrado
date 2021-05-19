package es.upm.dit.tfg.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import es.upm.dit.tfg.dao.IndicatorDAOImpl;
import es.upm.dit.tfg.model.Indicator;

@Path("indicator")
public class IndicatorResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Indicator> readAll () {
		return IndicatorDAOImpl.getInstance().readAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createIndicator(Indicator inew) throws URISyntaxException {
		Indicator i = IndicatorDAOImpl.getInstance().create(inew);
	    if (i != null) {
	            URI uri = new URI("/TFG/rest/indicator" + i.getIdentifier());
	            return Response.created(uri).build();
	    }
	    return Response.status(Response.Status.CONFLICT).build();
	}
}
