package es.upm.dit.tfg.dao;

import java.util.List;

import es.upm.dit.tfg.model.STIXObject;

public interface STIXObjectDAO {
	public STIXObject create(STIXObject stixobject);
	public STIXObject read(String pattern);
	public STIXObject update(STIXObject stixobject);
	public STIXObject delete(STIXObject stixobject);
	public List<STIXObject> readAll();
}
