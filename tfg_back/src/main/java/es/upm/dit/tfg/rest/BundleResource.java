package es.upm.dit.tfg.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import javax.json.JsonArray;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import es.upm.dit.tfg.dao.BundleDAOImpl;
import es.upm.dit.tfg.dao.CampaignDAOImpl;
import es.upm.dit.tfg.dao.ExternalReferenceDAOImpl;
import es.upm.dit.tfg.dao.IndicatorDAOImpl;
import es.upm.dit.tfg.dao.RelationshipDAOImpl;
import es.upm.dit.tfg.model.Bundle;
import es.upm.dit.tfg.model.Campaign;
import es.upm.dit.tfg.model.ExternalReference;
import es.upm.dit.tfg.model.Indicator;
import es.upm.dit.tfg.model.Relationship;


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
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createBundle(String JSONBodyString) throws URISyntaxException {
		JSONObject jsonBody = new JSONObject(JSONBodyString);
		String type = jsonBody.getString("type");
		String id = jsonBody.getString("id");
		
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
	            URI uri = new URI("/TFG-SERVICE/rest/bundle/" + bundle.getIdentifier());
	            return Response.created(uri).build();
	    }
	    return Response.status(Response.Status.NOT_FOUND).build();
	}

	private Campaign parser2Campaign(JSONObject campaign) {
		Gson gson = new Gson();
		Campaign c = gson.fromJson(campaign.toString(), Campaign.class);
		return c;
	}
	
	private Indicator parser2Indicator(JSONObject indicator) {
		Gson gson = new Gson();
		Indicator i = gson.fromJson(indicator.toString(), Indicator.class);
		return i;
	}
	
	private Relationship parser2Relationship(JSONObject relationship) {
		Gson gson = new Gson();
		Relationship r = gson.fromJson(relationship.toString(), Relationship.class);
		return r;
	}
}
