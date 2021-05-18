package es.upm.dit.tfg.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.tfg.model.Bundle;

public class BundleDAOImpl {
	private static BundleDAOImpl instance = null;

	private BundleDAOImpl() {
	}

	public static BundleDAOImpl getInstance() {
		if (null == instance)
			instance = new BundleDAOImpl();
		return instance;
	}
	
	public Bundle create(Bundle bundle) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		try {
			session.save(bundle);
		} catch (Exception e) {
			bundle = null;
		}
		session.getTransaction().commit();
		session.close();
		return bundle;
	}

	public Bundle read(String pattern) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		Bundle bundle = session.get(Bundle.class, pattern);
		session.getTransaction().commit();
		session.close();
		return bundle;
	}
	
	public Bundle update(Bundle bundle) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.saveOrUpdate(bundle);
		session.getTransaction().commit();
		session.close();
		return bundle;
	}
	
	public Bundle delete(Bundle bundle) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.delete(bundle);
		session.getTransaction().commit();
		session.close();
		return bundle;
	}
	
	public List<Bundle> readAll() {
		List<Bundle> allBundle = new ArrayList<Bundle>();
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		allBundle.addAll(session.createQuery("from Bundle").list());
		session.getTransaction().commit();
		session.close();
		return allBundle;
	}
}
