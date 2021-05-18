package es.upm.dit.tfg.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.tfg.model.Indicator;

public class IndicatorDAOImpl implements IndicatorDAO{
	
	private static IndicatorDAOImpl instance = null;

	private IndicatorDAOImpl() {
	}

	public static IndicatorDAOImpl getInstance() {
		if (null == instance)
			instance = new IndicatorDAOImpl();
		return instance;
	}
	
	@Override
	public Indicator create(Indicator indicator) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		try {
			session.save(indicator);
		} catch (Exception e) {
			indicator = null;
		}
		session.getTransaction().commit();
		session.close();
		return indicator;
	}

	@Override
	public Indicator read(String pattern) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		Indicator indicator = session.get(Indicator.class, pattern);
		session.getTransaction().commit();
		session.close();
		return indicator;
	}

	@Override
	public Indicator update(Indicator indicator) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.saveOrUpdate(indicator);
		session.getTransaction().commit();
		session.close();
		return indicator;
	}

	@Override
	public Indicator delete(Indicator indicator) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.delete(indicator);
		session.getTransaction().commit();
		session.close();
		return indicator;
	}

	@Override
	public List<Indicator> readAll() {
		List<Indicator> allIndicators = new ArrayList<Indicator>();
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		allIndicators.addAll(session.createQuery("from Indicator").list());
		session.getTransaction().commit();
		session.close();
		return allIndicators;
	}

}
