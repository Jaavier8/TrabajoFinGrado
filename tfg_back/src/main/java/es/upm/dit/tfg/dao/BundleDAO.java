package es.upm.dit.tfg.dao;

import java.util.List;

import es.upm.dit.tfg.model.Bundle;

public interface BundleDAO {
	public Bundle create(Bundle bundle);
	public Bundle read(String pattern);
	public Bundle update(Bundle bundle);
	public Bundle delete(Bundle bundle);
	public List<Bundle> readAll();
}
