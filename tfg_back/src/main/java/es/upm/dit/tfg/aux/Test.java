package es.upm.dit.tfg.aux;

import java.io.IOException;

import es.upm.dit.tfg.dao.BundleDAOImpl;

public class Test {

	public static void main(String[] args) throws IOException {
		OWLJena test = new OWLJena();
		test.createOWL(BundleDAOImpl.getInstance().readAll());
	}

}

