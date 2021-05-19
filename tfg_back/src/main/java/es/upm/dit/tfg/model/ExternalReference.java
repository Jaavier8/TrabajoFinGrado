package es.upm.dit.tfg.model;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "external_references")
public class ExternalReference {
	@Id
	@GeneratedValue
	private Long identifier;
//	private Map<String, String> references;
	private String source_name;
	private String url;
	
	public ExternalReference() {
		
	}
	
//	public ExternalReference(Map<String, String> references) {
//		super();
//		this.references = references;
//	}


	public ExternalReference(String source_name, String url) {
		this.source_name = source_name;
		this.url = url;
	}
//	
//	public String getSource_name() {
//		return source_name;
//	}
//
//	public void setSource_name(String source_name) {
//		this.source_name = source_name;
//	}
//
//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
}
