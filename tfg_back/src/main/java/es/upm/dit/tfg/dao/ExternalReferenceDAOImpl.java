package es.upm.dit.tfg.dao;

import org.hibernate.Session;

import es.upm.dit.tfg.model.ExternalReference;

public class ExternalReferenceDAOImpl implements ExternalReferenceDAO{
	private static ExternalReferenceDAOImpl instance = null;

	private ExternalReferenceDAOImpl() {
	}

	public static ExternalReferenceDAOImpl getInstance() {
		if (null == instance)
			instance = new ExternalReferenceDAOImpl();
		return instance;
	}
	
	public ExternalReference create(ExternalReference reference) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		try {
			session.save(reference);
		} catch (Exception e) {
			reference = null;
		}
		session.getTransaction().commit();
		session.close();
		return reference;
	}

	public ExternalReference read(Long indicator) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		ExternalReference reference = session.get(ExternalReference.class, indicator);
		session.getTransaction().commit();
		session.close();
		return reference;
	}
	
	public ExternalReference update(ExternalReference reference) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.saveOrUpdate(reference);
		session.getTransaction().commit();
		session.close();
		return reference;
	}
	
	public ExternalReference delete(ExternalReference reference) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.delete(reference);
		session.getTransaction().commit();
		session.close();
		return reference;
	}
}
