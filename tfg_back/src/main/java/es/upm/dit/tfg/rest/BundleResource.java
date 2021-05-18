package es.upm.dit.tfg.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.upm.dit.tfg.dao.BundleDAOImpl;
import es.upm.dit.tfg.model.Bundle;


@Path("bundle")
public class BundleResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Bundle> readAll () {
		return BundleDAOImpl.getInstance().readAll();
	}
	
//	@GET
//	@Path("{pattern}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public STIX readSTIX (@PathParam("pattern") String pattern) {
//		return STIXDAOImpl.getInstance().read(pattern);
//	}
	
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response create(STIX snew) throws URISyntaxException {
//	    STIX s = STIXDAOImpl.getInstance().create(snew);
//	    if (s != null) {
//	            URI uri = new URI("/TFG-SERVICE/rest/TFGs/" + s.getId());
//	            return Response.created(uri).build();
//	    }
//	    return Response.status(Response.Status.NOT_FOUND).build();
//	}
}
