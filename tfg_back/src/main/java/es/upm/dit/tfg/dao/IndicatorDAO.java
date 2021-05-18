package es.upm.dit.tfg.dao;

import java.util.List;

import es.upm.dit.tfg.model.Indicator;

public interface IndicatorDAO {
	public Indicator create(Indicator indicator);
	public Indicator read(String pattern);
	public Indicator update(Indicator indicator);
	public Indicator delete(Indicator indicator);
	public List<Indicator> readAll();
}
