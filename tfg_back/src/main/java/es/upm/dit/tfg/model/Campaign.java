package es.upm.dit.tfg.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "campaigns")
public class Campaign extends STIXObject implements Serializable{
	private String name;
	@Column(columnDefinition = "varchar(max)")
	private String description;
	@Column
	@ElementCollection(targetClass = String.class)
	private List<String> labels;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_id_campaign")
	private List<ExternalReference> external_references;
	private static final long serialVersionUID = 1L;
	
	public Campaign() {
		
	}
	
	public Campaign(String type, String spec_version, String id, String created, String modified, String name, String description, List<String> labels,
			List<ExternalReference> external_references) {
		super(type, spec_version, id, created, modified);
		this.name = name;
		this.description = description;
		this.labels = labels;
		this.external_references = external_references;
	}
	
//	public Campaign(String name, String description, List<String> labels) {
//		super();
//		this.name = name;
//		this.description = description;
//		this.labels = labels;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

//	public List<ExternalReference> getExternal_references() {
//		return external_references;
//	}
//
//	public void setExternal_references(List<ExternalReference> external_references) {
//		this.external_references = external_references;
//	}

}
