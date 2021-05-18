package es.upm.dit.tfg.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.tfg.model.Relationship;

public class RelationshipDAOImpl implements RelationshipDAO{
	private static RelationshipDAOImpl instance = null;

	private RelationshipDAOImpl() {
	}

	public static RelationshipDAOImpl getInstance() {
		if (null == instance)
			instance = new RelationshipDAOImpl();
		return instance;
	}
	
	public Relationship create(Relationship relationship) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		try {
			session.save(relationship);
		} catch (Exception e) {
			relationship = null;
		}
		session.getTransaction().commit();
		session.close();
		return relationship;
	}

	public Relationship read(String pattern) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		Relationship relationship = session.get(Relationship.class, pattern);
		session.getTransaction().commit();
		session.close();
		return relationship;
	}
	
	public Relationship update(Relationship relationship) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.saveOrUpdate(relationship);
		session.getTransaction().commit();
		session.close();
		return relationship;
	}
	
	public Relationship delete(Relationship relationship) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.delete(relationship);
		session.getTransaction().commit();
		session.close();
		return relationship;
	}
	
	public List<Relationship> readAll() {
		List<Relationship> allRelationship = new ArrayList<Relationship>();
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		allRelationship.addAll(session.createQuery("from Relationship").list());
		session.getTransaction().commit();
		session.close();
		return allRelationship;
	}
}
