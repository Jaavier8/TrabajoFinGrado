package es.upm.dit.tfg.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.tfg.model.STIXObject;

public class STIXObjectDAOImpl implements STIXObjectDAO{
	private static STIXObjectDAOImpl instance = null;

	private STIXObjectDAOImpl() {
	}

	public static STIXObjectDAOImpl getInstance() {
		if (null == instance)
			instance = new STIXObjectDAOImpl();
		return instance;
	}
	
	public STIXObject create(STIXObject stix) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		try {
			session.save(stix);
		} catch (Exception e) {
			stix = null;
		}
		session.getTransaction().commit();
		session.close();
		return stix;
	}

	public STIXObject read(String pattern) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		STIXObject stix = session.get(STIXObject.class, pattern);
		session.getTransaction().commit();
		session.close();
		return stix;
	}
	
	public STIXObject update(STIXObject stix) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.saveOrUpdate(stix);
		session.getTransaction().commit();
		session.close();
		return stix;
	}
	
	public STIXObject delete(STIXObject stix) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.delete(stix);
		session.getTransaction().commit();
		session.close();
		return stix;
	}
	
	public List<STIXObject> readAll() {
		List<STIXObject> allStix = new ArrayList<STIXObject>();
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		allStix.addAll(session.createQuery("from STIXObject").list());
		session.getTransaction().commit();
		session.close();
		return allStix;
	}
	
}
