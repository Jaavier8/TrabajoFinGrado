package es.upm.dit.tfg.dao;

import es.upm.dit.tfg.model.ExternalReference;

public interface ExternalReferenceDAO {
	public ExternalReference create(ExternalReference reference);
	public ExternalReference read(Long indicator);
	public ExternalReference update(ExternalReference reference);
	public ExternalReference delete(ExternalReference reference);
}
