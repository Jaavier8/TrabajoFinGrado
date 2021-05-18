package es.upm.dit.tfg.dao;

import java.util.List;

import es.upm.dit.tfg.model.Relationship;

public interface RelationshipDAO {
	public Relationship create(Relationship relationship);
	public Relationship read(String pattern);
	public Relationship update(Relationship relationship);
	public Relationship delete(Relationship relationship);
	public List<Relationship> readAll();
}
