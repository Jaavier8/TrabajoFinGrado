import React from "react";

// Bootstrap imports
import Button from "react-bootstrap/Button";
import Card from "react-bootstrap/Card";
import CardDeck from "react-bootstrap/CardDeck";
import { LinkContainer } from "react-router-bootstrap";

import ActionBar from "../components/ActionBar";


function DownloadPage(props) {
  return (
    <div>
      <ActionBar />
      <div className="download-page-container">
        <div className="centered-div">
          <h1>Trabajo Fin de Grado Javier Rodríguez Gallardo</h1>
          <div className="cards-container">
            <CardDeck>
              <Card>
                <Card.Body>
                  <Card.Title>Análisis de amenazas</Card.Title>
                  <Card.Text>
                    En esta sección podrá ver y descargar aquellos indicadores en los que esté interesado para realizar un análisis de amenazas.
                  </Card.Text>
                </Card.Body>
                <LinkContainer
                  className="container-dashboard"
                  to="/threat"
                >
                  <Button type="submit" variant="primary" className="nord-button">
                    Acceder a anásisis de amenzas
                  </Button>
                </LinkContainer>
              </Card>
              <Card>
                <Card.Body>
                  <Card.Title>Datos ontología</Card.Title>
                  <Card.Text>
                    En esta sección podrá ver las campañas de ataques que se están llevando a cabo en el momoento, así como sus indicadores. También podrá descargar un archivo OWL con estos datos introducidos en una ontología STIX.
                  </Card.Text>
                </Card.Body>
                <LinkContainer
                  className="container-dashboard"
                  to="/ontology"
                >
                  <Button type="submit" variant="primary" className="nord-button">
                    Acceder a apartado de ontología
                  </Button>
                </LinkContainer>
              </Card>
            </CardDeck>
          </div>
        </div>
      </div>
    </div>
  );
}

export default DownloadPage;
