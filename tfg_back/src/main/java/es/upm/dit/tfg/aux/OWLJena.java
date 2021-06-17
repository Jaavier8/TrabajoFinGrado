package es.upm.dit.tfg.aux;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import es.upm.dit.tfg.model.Bundle;
import es.upm.dit.tfg.model.Campaign;
import es.upm.dit.tfg.model.Indicator;
import es.upm.dit.tfg.model.Malware;
import es.upm.dit.tfg.model.Relationship;

public class OWLJena {
	private static OntClass indicatorClass;
	private static OntClass bundleClass;
	private static OntClass relationshipClass;
	private static OntClass campaignClass;
	private static OntClass malwareClass;
	private static OntModel model;
	
	private static DatatypeProperty type;
	private static DatatypeProperty spec_version;
	private static DatatypeProperty id;
	private static DatatypeProperty created;
	private static DatatypeProperty modified;
	private static DatatypeProperty name;
	private static DatatypeProperty description;
	private static DatatypeProperty pattern;
	private static DatatypeProperty pattern_type;
	private static DatatypeProperty pattern_version;
	private static DatatypeProperty valid_from;
	private static DatatypeProperty relationship_type;
	private static DatatypeProperty source_ref;
	private static DatatypeProperty target_ref;
	private static DatatypeProperty labels;
	private static DatatypeProperty indicates;
	private static DatatypeProperty is_family;
	private static DatatypeProperty isUsedBy;
	private static DatatypeProperty uses;
	private static DatatypeProperty isIndicatedBy;

	
	public OWLJena() {
		this.model = (OntModel) ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM).read("file:///home/kali/Documents/TFG/ontology.owl", "RDF/XML");
		for (Iterator<OntClass> i = model.listClasses();i.hasNext();){
			OntClass cls = i.next();
			if(cls.getLocalName() != null && cls.getLocalName().equals("Indicator"))
				indicatorClass = cls;
			if(cls.getLocalName() != null && cls.getLocalName().equals("Bundle"))
				bundleClass = cls;
			if(cls.getLocalName() != null && cls.getLocalName().equals("Relationship"))
				relationshipClass = cls;
			if(cls.getLocalName() != null && cls.getLocalName().equals("Campaign"))
				campaignClass = cls;
			if(cls.getLocalName() != null && cls.getLocalName().equals("Malware"))
				malwareClass = cls;
		}
		type = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#type");
		spec_version = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#spec_version");
		id = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#id");
		created = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#created");
		modified = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#modified");
		name = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#name");
		description = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#description");
		pattern = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#pattern");
		pattern_type = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#pattern_type");
		pattern_version = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#pattern_version");		
		valid_from = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#valid-from");
		relationship_type = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#relationship_type");
		source_ref = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#source_ref");
		target_ref = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#target_ref");
		labels = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#labels");
		indicates = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#indicates");
		is_family = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2020/2/cibersituational-ontology#is_family");
		isUsedBy = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#isUsedBy");
		uses = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#uses");
		isIndicatedBy = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#isIndicatedBy");
	}
	
	public File createOWL(List<Bundle> bundles) throws IOException {
		for(Bundle bundle: bundles) {
			List<Individual> malwares = new ArrayList<Individual>();
			List<Individual> indicators = new ArrayList<Individual>();
			List<Individual> relationships = new ArrayList<Individual>();
			Campaign c = bundle.getCampaign();
			System.out.println(c.getType());
			//Introducimos Campaign en la ontología
			Individual campaign = model.createIndividual("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#Campaign:" + c.getIdentifier(), campaignClass);
			campaign.setPropertyValue(type, model.createTypedLiteral(c.getType()));
			campaign.setPropertyValue(spec_version, model.createTypedLiteral(c.getSpec_version()));
			campaign.setPropertyValue(id, model.createTypedLiteral(c.getId()));
			campaign.setPropertyValue(created, model.createTypedLiteral(c.getCreated()));
			campaign.setPropertyValue(modified, model.createTypedLiteral(c.getModified()));
			campaign.setPropertyValue(name, model.createTypedLiteral(c.getName()));
			campaign.setPropertyValue(description, model.createTypedLiteral(c.getDescription()));
			campaign.setPropertyValue(labels, model.createTypedLiteral(c.getLabels()));
			//Introducimos Malware
			int pos=0;
			for(Malware mal: bundle.getMalware()) {
				malwares.add(model.createIndividual("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#Malware:" + mal.getIdentifier(), malwareClass));
				malwares.get(pos).setPropertyValue(type, model.createTypedLiteral(mal.getType()));
				malwares.get(pos).setPropertyValue(spec_version, model.createTypedLiteral(mal.getSpec_version()));
				malwares.get(pos).setPropertyValue(id, model.createTypedLiteral(mal.getId()));
				malwares.get(pos).setPropertyValue(created, model.createTypedLiteral(mal.getCreated()));
				malwares.get(pos).setPropertyValue(modified, model.createTypedLiteral(mal.getModified()));
				malwares.get(pos).setPropertyValue(name, model.createTypedLiteral(mal.getName()));
				malwares.get(pos).setPropertyValue(is_family, model.createTypedLiteral(mal.getIs_family()));
				pos++;
			}
			//Introducimos Indicator
			pos=0;
			for(Indicator ind: bundle.getIndicators()) {
				indicators.add(model.createIndividual("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#Indicator:" + ind.getIdentifier(), indicatorClass));
				indicators.get(pos).setPropertyValue(type, model.createTypedLiteral(ind.getType()));
				indicators.get(pos).setPropertyValue(spec_version, model.createTypedLiteral(ind.getSpec_version()));
				indicators.get(pos).setPropertyValue(id, model.createTypedLiteral(ind.getId()));
				indicators.get(pos).setPropertyValue(created, model.createTypedLiteral(ind.getCreated()));
				indicators.get(pos).setPropertyValue(modified, model.createTypedLiteral(ind.getModified()));
				indicators.get(pos).setPropertyValue(name, model.createTypedLiteral(ind.getName()));
				indicators.get(pos).setPropertyValue(description, model.createTypedLiteral(ind.getDescription()));
				indicators.get(pos).setPropertyValue(pattern, model.createTypedLiteral(ind.getPattern()));
				indicators.get(pos).setPropertyValue(pattern_type, model.createTypedLiteral(ind.getPattern_type()));
				indicators.get(pos).setPropertyValue(pattern_version, model.createTypedLiteral(ind.getPattern_version()));
				indicators.get(pos).setPropertyValue(valid_from, model.createTypedLiteral(ind.getValid_from()));
				//Indicator indicates Campaign
				indicators.get(pos).setPropertyValue(indicates, campaign);
				//Indicator indicates Malware
				for (Individual mal: malwares) {
					indicators.get(pos).addProperty(indicates, mal);
				}
				pos++;
			}
			//Introducimos Relationship
			pos=0;
			for(Relationship rel: bundle.getRelationships()) {
				relationships.add(model.createIndividual("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#Relationship:" + rel.getIdentifier(), relationshipClass));
				relationships.get(pos).setPropertyValue(type, model.createTypedLiteral(rel.getType()));
				relationships.get(pos).setPropertyValue(spec_version, model.createTypedLiteral(rel.getSpec_version()));
				relationships.get(pos).setPropertyValue(id, model.createTypedLiteral(rel.getId()));
				relationships.get(pos).setPropertyValue(created, model.createTypedLiteral(rel.getCreated()));
				relationships.get(pos).setPropertyValue(modified, model.createTypedLiteral(rel.getModified()));
				relationships.get(pos).setPropertyValue(relationship_type, model.createTypedLiteral(rel.getRelationship_type()));
				relationships.get(pos).setPropertyValue(source_ref, model.createTypedLiteral(rel.getSource_ref()));
				relationships.get(pos).setPropertyValue(target_ref, model.createTypedLiteral(rel.getTarget_ref()));
				pos++;
			}
			//Propiedades de relación
			for(Individual mal: malwares) {
				//Malware isUsedBy Campaign
				mal.setPropertyValue(isUsedBy, campaign);
				//Campaign uses Malware
				campaign.addProperty(uses, mal);
				//Malware isIndicatedBy Indicator
				for(Individual ind: indicators) {
					mal.addProperty(isIndicatedBy, ind);
				}
			}
			//Campaign isIndicatedBy Indicator
			for(Individual ind: indicators) {
				campaign.addProperty(isIndicatedBy, ind);
			}
		}
		File file = new File("/home/kali/Documents/TFG/ontology_test.owl");
		if (!file.exists()){
		     file.createNewFile();
		}
		model.write(new PrintWriter(file));
		return file;
	}
}
