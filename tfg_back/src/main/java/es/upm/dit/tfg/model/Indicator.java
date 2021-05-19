package es.upm.dit.tfg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "indicators")
public class Indicator extends STIXObject implements Serializable{
	@Column(columnDefinition = "varchar(max)")
	private String name;
	@Column(columnDefinition = "varchar(max)")
	private String description;
	@Column(nullable = false)
	private String pattern;
	private String pattern_type;
	private String pattern_version;
	private String valid_from;
	private static final long serialVersionUID = 1L;
	
	public Indicator() {
		
	}
	
	public Indicator(String name, String description, String pattern, String pattern_type, String pattern_version,
			String valid_from) {
		super();
		this.name = name;
		this.description = description;
		this.pattern = pattern;
		this.pattern_type = pattern_type;
		this.pattern_version = pattern_version;
		this.valid_from = valid_from;
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

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern_type() {
		return pattern_type;
	}

	public void setPattern_type(String pattern_type) {
		this.pattern_type = pattern_type;
	}

	public String getPattern_version() {
		return pattern_version;
	}

	public void setPattern_version(String pattern_version) {
		this.pattern_version = pattern_version;
	}

	public String getValid_from() {
		return valid_from;
	}

	public void setValid_from(String valid_from) {
		this.valid_from = valid_from;
	}

	
}
