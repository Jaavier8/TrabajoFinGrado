package es.upm.dit.tfg.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.upm.dit.tfg.aux.OWLJena;
import es.upm.dit.tfg.dao.BundleDAOImpl;
import es.upm.dit.tfg.dao.IndicatorDAOImpl;
import es.upm.dit.tfg.model.Bundle;
import es.upm.dit.tfg.model.Indicator;

@Path("indicator")
public class IndicatorResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Indicator> readAll () {
		List<Indicator> result = new ArrayList<Indicator>();
		List<Indicator> allIndicators = IndicatorDAOImpl.getInstance().readAll();
		List<Indicator> indicatorsFromBundle = new ArrayList<Indicator>();
		for(Bundle b: BundleDAOImpl.getInstance().readAll()) {
			indicatorsFromBundle.addAll(b.getIndicators());
		}
		for(Indicator i: allIndicators) {
			if(!indicatorsFromBundle.contains(i)) {
				result.add(i);
			}
		}
		return result;
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
