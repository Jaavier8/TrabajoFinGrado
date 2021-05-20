package es.upm.dit.tfg.aux;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class Jena {
	private static OntClass indicatorClass;
	
	public static void main(String[] args) throws IOException{
		OntModel model = (OntModel) ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM).read("file:///home/kali/Documents/TFG/cyberthreat_STIX.owl", "RDF/XML");
		DatatypeProperty pattern_type = model.createDatatypeProperty("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#pattern_type");
		for (Iterator<OntClass> i = model.listClasses();i.hasNext();){
			OntClass cls = i.next();
			//cls.getProperty("type");
			System.out.print(cls.getLocalName()+": ");
			if(cls.getLocalName() != null && cls.getLocalName().equals("Indicator"))
				indicatorClass = cls;
				//model.createIndividual("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#Indicator", cls);
			for(Iterator it = cls.listInstances(true);it.hasNext();){
				Individual ind = (Individual)it.next();
				if(ind.isIndividual()){
					System.out.print(ind.getLocalName()+" ");
				}
			}
			System.out.println();
		}  
		Individual indicator = model.createIndividual("http://www.semanticweb.org/upm/ontologies/2019/11/cyberthreat_STIX#Indicator", indicatorClass);
		indicator.setPropertyValue(pattern_type, model.createTypedLiteral("stix"));
		File file = new File("/home/kali/Documents/TFG/prueba.owl");
		if (!file.exists()){
		     file.createNewFile();
		}
		model.write(new PrintWriter(file));
	}
	
}
