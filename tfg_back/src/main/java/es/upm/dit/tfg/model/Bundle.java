package es.upm.dit.tfg.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bundles")
public class Bundle implements Serializable{
	@Id
	@GeneratedValue
	private Long identifier;
	private String id;
	private String type;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_id_bundle")
	private List<Indicator> indicators;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_id_bundle")
	private List<Relationship> relationships;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "fk_id_bundle")
	private List<Malware> malware;
	@OneToOne
	private Campaign campaign;
	private static final long serialVersionUID = 1L;
	
	public Bundle() {
		
	}
	
	public Bundle(String id, String type, List<Indicator> indicators, List<Relationship> relationships, List<Malware> malware,
			Campaign campaign) {
		super();
		this.id = id;
		this.type = type;
		this.indicators = indicators;
		this.relationships = relationships;
		this.malware = malware;
		this.campaign = campaign;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Indicator> getIndicators() {
		return indicators;
	}

	public void setIndicators(List<Indicator> indicators) {
		this.indicators = indicators;
	}

	public List<Relationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships;
	}
	
	public List<Malware> getMalware() {
		return malware;
	}

	public void setMalware(List<Malware> malware) {
		this.malware = malware;
	}

	public Campaign getCampaign() {
		return campaign;
	}
	
	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

}
