package es.upm.dit.tfg.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.tfg.model.Campaign;

public class CampaignDAOImpl implements CampaignDAO{
	private static CampaignDAOImpl instance = null;

	private CampaignDAOImpl() {
	}

	public static CampaignDAOImpl getInstance() {
		if (null == instance)
			instance = new CampaignDAOImpl();
		return instance;
	}
	
	public Campaign create(Campaign campaign) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		try {
			session.save(campaign);
		} catch (Exception e) {
			campaign = null;
		}
		session.getTransaction().commit();
		session.close();
		return campaign;
	}

	public Campaign read(String pattern) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		Campaign campaign = session.get(Campaign.class, pattern);
		session.getTransaction().commit();
		session.close();
		return campaign;
	}
	
	public Campaign update(Campaign campaign) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.saveOrUpdate(campaign);
		session.getTransaction().commit();
		session.close();
		return campaign;
	}
	
	public Campaign delete(Campaign campaign) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.delete(campaign);
		session.getTransaction().commit();
		session.close();
		return campaign;
	}
	
	public List<Campaign> readAll() {
		List<Campaign> allCampaign = new ArrayList<Campaign>();
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		allCampaign.addAll(session.createQuery("from Campaign").list());
		session.getTransaction().commit();
		session.close();
		return allCampaign;
	}
}
