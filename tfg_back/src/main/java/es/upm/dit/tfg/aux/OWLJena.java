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
import es.upm.dit.tfg.model.Relationship;

public class OWLJena {
	private static OntClass indicatorClass;
	private static OntClass bundleClass;
	private static OntClass relationshipClass;
	private static OntClass campaignClass;
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

	
	public OWLJena() {
		this.model = (OntModel) ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM).read("file:///home/kali/Documents/TFG/cyberthreat_STIX.owl", "RDF/XML");
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
	}
	
	public File createOWL(List<Bundle> bundles) throws IOException {
		for(Bundle bundle: bundles) {
			List<Individual> individuals = new ArrayList<Individual>();
			int pos=0;
			for(Indicator ind: bundle.getIndicators()) {
				individuals.add(model.createIndividual("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#Indicator:" + ind.getIdentifier(), indicatorClass));
				individuals.get(pos).setPropertyValue(type, model.createTypedLiteral(ind.getType()));
				individuals.get(pos).setPropertyValue(spec_version, model.createTypedLiteral(ind.getSpec_version()));
				individuals.get(pos).setPropertyValue(id, model.createTypedLiteral(ind.getId()));
				individuals.get(pos).setPropertyValue(created, model.createTypedLiteral(ind.getCreated()));
				individuals.get(pos).setPropertyValue(modified, model.createTypedLiteral(ind.getModified()));
				individuals.get(pos).setPropertyValue(name, model.createTypedLiteral(ind.getName()));
				individuals.get(pos).setPropertyValue(description, model.createTypedLiteral(ind.getDescription()));
				individuals.get(pos).setPropertyValue(pattern, model.createTypedLiteral(ind.getPattern()));
				individuals.get(pos).setPropertyValue(pattern_type, model.createTypedLiteral(ind.getPattern_type()));
				individuals.get(pos).setPropertyValue(pattern_version, model.createTypedLiteral(ind.getPattern_version()));
				individuals.get(pos).setPropertyValue(valid_from, model.createTypedLiteral(ind.getValid_from()));
				pos++;
			}
			for(Relationship rel: bundle.getRelationships()) {
				individuals.add(model.createIndividual("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#Relationship:" + rel.getIdentifier(), relationshipClass));
				individuals.get(pos).setPropertyValue(type, model.createTypedLiteral(rel.getType()));
				individuals.get(pos).setPropertyValue(spec_version, model.createTypedLiteral(rel.getSpec_version()));
				individuals.get(pos).setPropertyValue(id, model.createTypedLiteral(rel.getId()));
				individuals.get(pos).setPropertyValue(created, model.createTypedLiteral(rel.getCreated()));
				individuals.get(pos).setPropertyValue(modified, model.createTypedLiteral(rel.getModified()));
				individuals.get(pos).setPropertyValue(relationship_type, model.createTypedLiteral(rel.getRelationship_type()));
				individuals.get(pos).setPropertyValue(source_ref, model.createTypedLiteral(rel.getSource_ref()));
				individuals.get(pos).setPropertyValue(target_ref, model.createTypedLiteral(rel.getTarget_ref()));
				pos++;
			}
			Campaign c = bundle.getCampaign();
			individuals.add(model.createIndividual("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#Campaign:" + c.getIdentifier(), campaignClass));
			individuals.get(pos).setPropertyValue(type, model.createTypedLiteral(c.getType()));
			individuals.get(pos).setPropertyValue(spec_version, model.createTypedLiteral(c.getSpec_version()));
			individuals.get(pos).setPropertyValue(id, model.createTypedLiteral(c.getId()));
			individuals.get(pos).setPropertyValue(created, model.createTypedLiteral(c.getCreated()));
			individuals.get(pos).setPropertyValue(modified, model.createTypedLiteral(c.getModified()));
			individuals.get(pos).setPropertyValue(name, model.createTypedLiteral(c.getName()));
			individuals.get(pos).setPropertyValue(description, model.createTypedLiteral(c.getDescription()));
			individuals.get(pos).setPropertyValue(labels, model.createTypedLiteral(c.getLabels()));
		}
		File file = new File("/home/kali/Documents/TFG/cyberthreat_STIX_test.owl");
		if (!file.exists()){
		     file.createNewFile();
		}
		model.write(new PrintWriter(file));
		return file;
	}
	
}
