import React, { useState, useEffect } from "react";

// Bootstrap imports
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";


import ActionBar from "../components/ActionBar";

function OntologyPage(props) {

  const [campaigns, setCampaigns] = useState([]);
  const [campaignAccepted, setCampaignAccepted] = useState([]);

  useEffect(async () => {
    try {
      const campaignsRes = await fetch(`http://localhost:8080/TFG/rest/bundle/campaigns`);
      let campaigns = await campaignsRes.json();
      console.log(campaigns)
      setCampaigns(campaigns);
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

  function download(filename, text) {
    var element = document.createElement("a");
    element.setAttribute(
      "href",
      "data:text/json;charset=utf-8," + encodeURIComponent(text)
    );
    element.setAttribute("download", filename);
    element.style.display = "none";
    document.body.appendChild(element);
    element.click();
    document.body.removeChild(element);
  }

  function downloadBlob(blob, name = 'file.txt') {
    // Convert your blob into a Blob URL (a special url that points to an object in the browser's memory)
    const blobUrl = URL.createObjectURL(blob);

    // Create a link element
    const link = document.createElement("a");

    // Set link's href to point to the Blob URL
    link.href = blobUrl;
    link.download = name;

    // Append link to the body
    document.body.appendChild(link);

    // Dispatch click event on the link
    // This is necessary as link.click() does not work on the latest firefox
    link.dispatchEvent(
      new MouseEvent('click', {
        bubbles: true,
        cancelable: true,
        view: window
      })
    );

    // Remove link from body
    document.body.removeChild(link);
  }

  const handleDownload = async (type) => {
    const request = {
      names: campaignAccepted
    };
    try {
      console.log(JSON.stringify(request))
      const res = await fetch(
        `http://localhost:8080/TFG/rest/bundle/` + type,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json; charset=UTF-8"
          },
          body: JSON.stringify(request)
        }
      );
      if(type == "jsondownload"){
        let resData = await res.json();
        download(
          "prueba.json",
          JSON.stringify(resData)
        );
      } else {
        let blob = await res.blob();
        downloadBlob(
          blob,
          "test.owl"
        )
      }
    } catch (e) {
      // Nothing to do
    }
  };

  return (
    <div>
      <ActionBar />
      <div className="ontology-page-container">
        <div className="centered-div">
          <h1>Página para obtener datos a introducir en la ontología</h1>
          <h4>A continuación se muestran las campañas de ataque que se están llevando a cabo y que se encuentran en la base de datos</h4>
          <h8>Seleccione las campañas en las que esté interesado/a y elija descargarlas en formato JSON o el archivo OWL con las campañas introducidas en la ontología (incluidos los indicadores y objetos malware relacionados con sus propiedades y relaciones)</h8>
          <div className="checkbox">
            <Form>
              {campaigns.map((campaign, index) =>
                <Form.Group controlId="formCampaignCheckbox">
                    <Form.Check
                      name={campaign.name}
                      type="checkbox"
                      label={campaign.name}
                      onChange={updateFormState}
                    />
                    Description: {campaign.description}
                </Form.Group>
              )}
            </Form>
          </div>
          <div className="buttons-container">
            <Button onClick={e => handleDownload("jsondownload")} className="nord-button" variant="primary">
              Descargar en formato JSON
            </Button>
            <Button onClick={e => handleDownload("owldownload")} className="nord-button" variant="primary">
              Descargar archivo OWL
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default OntologyPage;
