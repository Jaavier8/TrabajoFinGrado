package es.upm.dit.tfg.dao;

import java.util.List;

import es.upm.dit.tfg.model.Campaign;

public interface CampaignDAO {
	public Campaign create(Campaign campaign);
	public Campaign read(String pattern);
	public Campaign update(Campaign campaign);
	public Campaign delete(Campaign campaign);
	public List<Campaign> readAll();
}
