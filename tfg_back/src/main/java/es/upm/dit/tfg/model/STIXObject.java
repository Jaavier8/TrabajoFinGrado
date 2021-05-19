package es.upm.dit.tfg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "stixobjects")
@Inheritance(strategy = InheritanceType.JOINED)
public class STIXObject implements Serializable {
	@Id
	@GeneratedValue
	private Long identifier;
	@Column(nullable = false)
	private String type;
	private String spec_version;
	private String id;
	private String created;
	private String modified;
	private static final long serialVersionUID = 1L;

	public STIXObject() {
		
	}

	public STIXObject(String type, String spec_version, String id, String created, String modified) {
		super();
		this.type = type;
		this.spec_version = spec_version;
		this.id = id;
		this.created = created;
		this.modified = modified;
	}

	public Long getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(Long identifier) {
		this.identifier = identifier;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSpec_version() {
		return spec_version;
	}

	public void setSpec_version(String spec_version) {
		this.spec_version = spec_version;
	}

	public String getId() {
		return id;
	}

	public void setStixId(String id) {
		this.id = id;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

}
