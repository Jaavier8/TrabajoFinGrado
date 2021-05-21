import React, { useState, useEffect } from "react";

// Bootstrap imports
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";


import ActionBar from "../components/ActionBar";

function OntologyPage(props) {

  const [campaignNames, setCampaignNames] = useState([]);
  const [campaignAccepted, setCampaignAccepted] = useState([]);

  useEffect(async () => {
    try {
      const namesRes = await fetch(`http://localhost:8080/TFG/rest/bundle/names`);
      let names = await namesRes.json();
      console.log(names)
      setCampaignNames(names);
    } catch (e) {
      // Nothing to do
    }
  }, []);

  const updateFormState = event => {
    const { name } = event.target;
    let names = campaignAccepted.slice();
    if(names.includes(name)){
      let i = names.indexOf(name);
      names.splice(i,1);
    } else {
      names.push(name);
    }
    console.log(names)
    setCampaignAccepted(names);
  };

  return (
    <div>
      <ActionBar />
      <div className="ontology-page-container">
        <div className="centered-div">
          <h1>Página para obtener datos a introducir en la ontología</h1>
          <h4>Seleccione las campañas en las que esté interesado/a y elija descargarlas en formato JSON o el archivo OWL con las campañas introducidas en la ontología</h4>
          <div className="checkbox">
            <Form>
              {campaignNames.map((name, index) =>
                <Form.Group controlId="formCampaignCheckbox">
                    <Form.Check
                      name={name}
                      type="checkbox"
                      label={name}
                      onChange={updateFormState}
                    />
                </Form.Group>
              )}
            </Form>
          </div>
          <div className="buttons-container">

          </div>
        </div>
      </div>
    </div>
  );
}

export default OntologyPage;
