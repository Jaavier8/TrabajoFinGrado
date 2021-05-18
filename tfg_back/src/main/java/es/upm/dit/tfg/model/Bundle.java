package es.upm.dit.tfg.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "bundles")
public class Bundle implements Serializable{
	@Id
	@GeneratedValue
	private Long identifier;
	private String id;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<STIXObject> objects;
	private static final long serialVersionUID = 1L;
	
	public Bundle() {
		
	}
	
	public Bundle(String id, List<STIXObject> objects) {
		super();
		this.id = id;
		this.objects = objects;
	}
	
	public Long getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<STIXObject> getObjects() {
		return objects;
	}
	public void setObjects(List<STIXObject> objects) {
		this.objects = objects;
	}

}
