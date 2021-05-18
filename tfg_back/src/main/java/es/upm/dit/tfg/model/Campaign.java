package es.upm.dit.tfg.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "campaigns")
public class Campaign extends STIXObject implements Serializable{
	private String name;
	private String description;
	@Column
	@ElementCollection(targetClass = String.class)
	private List<String> labels;
	@Column
	@ElementCollection(targetClass = String.class)
	private List<String> external_references;
	private static final long serialVersionUID = 1L;
	
	public Campaign(String name, String description, List<String> labels,
			List<String> external_references) {
		super();
		this.name = name;
		this.description = description;
		this.labels = labels;
		this.external_references = external_references;
	}

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

	public List<String> getExternal_references() {
		return external_references;
	}

	public void setExternal_references(List<String> external_references) {
		this.external_references = external_references;
	}

}
