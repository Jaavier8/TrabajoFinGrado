package es.upm.dit.tfg.dao;

import java.util.ArrayList;
import java.util.List;

import es.upm.dit.tfg.model.Bundle;
import es.upm.dit.tfg.model.STIXObject;

public class MockDataFiller {

	public static void main(String[] args) {
		List<STIXObject> bundleObjects = new ArrayList<STIXObject>();
		Bundle bundle = new Bundle("bundle--5ee82b5d-0278-4ae1-844e-28fda7af943c",bundleObjects);
		BundleDAOImpl.getInstance().create(bundle);
	}

}
