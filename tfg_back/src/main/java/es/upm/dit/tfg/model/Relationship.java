package es.upm.dit.tfg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "relationships", uniqueConstraints = @UniqueConstraint(columnNames = {"source_ref", "target_ref"}))
public class Relationship extends STIXObject implements Serializable{
	private String relationship_type;
	@Column(nullable = false)
	private String source_ref;
	@Column(nullable = false)
	private String target_ref;
	private static final long serialVersionUID = 1L;
	
	public Relationship() {
		
	}
	
	public Relationship(String relationship_type, String source_ref, String target_ref) {
		super();
		this.relationship_type = relationship_type;
		this.source_ref = source_ref;
		this.target_ref = target_ref;
	}
	
	public String getRelationship_type() {
		return relationship_type;
	}
	public void setRelationship_type(String relationship_type) {
		this.relationship_type = relationship_type;
	}
	public String getSource_ref() {
		return source_ref;
	}
	public void setSource_ref(String source_ref) {
		this.source_ref = source_ref;
	}
	public String getTarget_ref() {
		return target_ref;
	}
	public void setTarget_ref(String target_ref) {
		this.target_ref = target_ref;
	}
}
