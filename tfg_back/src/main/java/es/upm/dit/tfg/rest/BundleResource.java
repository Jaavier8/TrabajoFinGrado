package es.upm.dit.tfg.rest;

import java.io.File;
import java.io.IOException;
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

import es.upm.dit.tfg.aux.OWLJena;
import es.upm.dit.tfg.dao.BundleDAOImpl;
import es.upm.dit.tfg.dao.CampaignDAOImpl;
import es.upm.dit.tfg.dao.ExternalReferenceDAOImpl;
import es.upm.dit.tfg.dao.IndicatorDAOImpl;
import es.upm.dit.tfg.dao.MalwareDAOImpl;
import es.upm.dit.tfg.dao.RelationshipDAOImpl;
import es.upm.dit.tfg.model.Bundle;
import es.upm.dit.tfg.model.Campaign;
import es.upm.dit.tfg.model.ExternalReference;
import es.upm.dit.tfg.model.Indicator;
import es.upm.dit.tfg.model.Malware;
import es.upm.dit.tfg.model.Relationship;


@Path("bundle")
public class BundleResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Bundle> readAll() throws IOException{
		return BundleDAOImpl.getInstance().readAll();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("names")
	public List<String> readNames() throws IOException{
		List<String> result = new ArrayList<String>();
		List<Campaign> campaigns = CampaignDAOImpl.getInstance().readAll();
		for(Campaign c: campaigns) {
			result.add(c.getName());
		}
		return result;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("campaigns")
	public List<Campaign> readCampaigns() throws IOException{
		List<String> result = new ArrayList<String>();
		List<Campaign> campaigns = CampaignDAOImpl.getInstance().readAll();
		for(Campaign c: campaigns) {
			result.add(c.getName());
		}
		return CampaignDAOImpl.getInstance().readAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createBundle(String JSONBodyString) throws URISyntaxException {
		JSONObject jsonBody = new JSONObject(JSONBodyString);
		String type = jsonBody.getString("type");
		String id = jsonBody.getString("id");
		
		List<Malware> malwares = new ArrayList<Malware>();
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
		        } else if (jsonObject.getString("type").equals("malware")) {
		        	System.out.println(jsonObject);
		        	Malware mal = parser2Malware(jsonObject);
		        	MalwareDAOImpl.getInstance().create(mal);
		        	malwares.add(mal);
		        }
		    } catch (JSONException e) {
		        e.printStackTrace();
		    }
		}
		
		Campaign campaign = parser2Campaign(objects.getJSONObject(0));
		CampaignDAOImpl.getInstance().create(campaign);
		
		Bundle bundle = BundleDAOImpl.getInstance().create(new Bundle(id, type, indicators, relationships, malwares, campaign));
	    if (bundle != null) {
	            URI uri = new URI("/TFG/rest/bundle" + bundle.getIdentifier());
	            return Response.created(uri).build();
	    }
	    return Response.status(Response.Status.CONFLICT).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("jsondownload")
	public List<Bundle> jsonDownload(String JSONBodyString) throws URISyntaxException {
		JSONObject jsonBody = new JSONObject(JSONBodyString);
		JSONArray names = jsonBody.getJSONArray("names");
		
		List<String> namesList = new ArrayList<String>();
		for (int i = 0; i < names.length(); i++) {
			namesList.add(names.getString(i));
		}
		
		List<Bundle> result = new ArrayList<Bundle>();
		
		for(Bundle b: BundleDAOImpl.getInstance().readAll()) {
			if(namesList.contains(b.getCampaign().getName())) result.add(b);
		}
		
		return result;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("owldownload")
	public Response owlDownload(String JSONBodyString) throws URISyntaxException, IOException {
		JSONObject jsonBody = new JSONObject(JSONBodyString);
		JSONArray names = jsonBody.getJSONArray("names");
		
		List<String> namesList = new ArrayList<String>();
		for (int i = 0; i < names.length(); i++) {
			namesList.add(names.getString(i));
		}
		
		List<Bundle> filteredBundle = new ArrayList<Bundle>();
		
		for(Bundle b: BundleDAOImpl.getInstance().readAll()) {
			if(namesList.contains(b.getCampaign().getName())) filteredBundle.add(b);
		}
		
		OWLJena jenaAPI = new OWLJena();
		File result = jenaAPI.createOWL(filteredBundle);
		
		return Response.ok(result, MediaType.APPLICATION_OCTET_STREAM).build();
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
	
	private Malware parser2Malware(JSONObject malware) {
		Gson gson = new Gson();
		Malware m = gson.fromJson(malware.toString(), Malware.class);
		return m;
	}
}
