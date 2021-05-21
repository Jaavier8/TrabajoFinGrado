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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.upm.dit.tfg.aux.OWLJena;
import es.upm.dit.tfg.dao.BundleDAOImpl;
import es.upm.dit.tfg.dao.CampaignDAOImpl;
import es.upm.dit.tfg.dao.IndicatorDAOImpl;
import es.upm.dit.tfg.dao.RelationshipDAOImpl;
import es.upm.dit.tfg.model.Bundle;
import es.upm.dit.tfg.model.Campaign;
import es.upm.dit.tfg.model.Indicator;
import es.upm.dit.tfg.model.Relationship;

@Path("indicator")
public class IndicatorResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Indicator> readAll () {
		return getIndicators();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("download")
	public List<Indicator> read(String JSONBodyString) throws URISyntaxException {
		JSONObject jsonBody = new JSONObject(JSONBodyString);
		Boolean domain = jsonBody.getBoolean("domain");
		Boolean ipv4 = jsonBody.getBoolean("ipv4");
		Boolean email = jsonBody.getBoolean("email");
		Boolean hash = jsonBody.getBoolean("hash");
		
		List<String> accepted = new ArrayList<String>();
		if(domain) accepted.add("domain");
		if(ipv4) accepted.add("ipv4");
		if(email) accepted.add("email");
		if(hash) accepted.add("hash");
		
		List<Indicator> allIndicators = getIndicators();
		
		if(accepted.size() == 4) return allIndicators;
		
		List<Indicator> filtered = getIndicatorsFiltered(allIndicators, accepted);
		
		return filtered;
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
	
	private List<Indicator> getIndicators(){
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
	
	private List<Indicator> getIndicatorsFiltered(List<Indicator> all, List<String> accepted){
		List<Indicator> result = new ArrayList<Indicator>();
		for(Indicator ind: all) {
			for(String s: accepted) {
				if(ind.getPattern().contains(s)) result.add(ind);
			}
		}
		return result;
	}
}
