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
		String domain = jsonBody.getString("domain");
		String ipv4 = jsonBody.getString("ipv4");
		String email = jsonBody.getString("email");
		String hash = jsonBody.getString("hash");
		
		List<String> accepted = new ArrayList<String>();
		if(domain.equals("true")) accepted.add("domain");
		if(ipv4.equals("true")) accepted.add("ipv4");
		if(email.equals("true")) accepted.add("email");
		if(hash.equals("true")) accepted.add("hash");
		
		List<Indicator> allIndicators = getIndicators();
		
		if(accepted.size() == 4) return allIndicators;
		
		List<Indicator> filtered = getIndicatorsFiltered(allIndicators, accepted);
		
		
		List<Indicator> indicators = new ArrayList<Indicator>();
		List<Relationship> relationships = new ArrayList<Relationship>();
		JSONArray objects = jsonBody.getJSONArray("objects");
				
		for (int i = 1; i < objects.length(); i++) {
		    try {
		        JSONObject jsonObject = objects.getJSONObject(i);
		        if (jsonObject.getString("type").equals("indicator")) {
		        	Indicator ind = parser2Indicator(jsonObject);
		        	IndicatorDAOImpl.getInstance().create(ind);
		            indicators.add(ind);
		        } else if (jsonObject.getString("type").equals("relationship")) {
		        	Relationship rel = parser2Relationship(jsonObject);
		        	RelationshipDAOImpl.getInstance().create(rel);
		        	relationships.add(rel);
		        }
		    } catch (JSONException e) {
		        e.printStackTrace();
		    }
		}
		
		Campaign campaign = parser2Campaign(objects.getJSONObject(0));
		CampaignDAOImpl.getInstance().create(campaign);
		
		Bundle bundle = BundleDAOImpl.getInstance().create(new Bundle(id, type, indicators, relationships, campaign));
	    if (bundle != null) {
	            URI uri = new URI("/TFG/rest/bundle" + bundle.getIdentifier());
	            return Response.created(uri).build();
	    }
	    return Response.status(Response.Status.CONFLICT).build();
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
	}
}
