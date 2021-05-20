import React from "react";

// Bootstrap imports
import Button from "react-bootstrap/Button";

function OntologyPage(props) {
  return (
    <div className="main-page-container">
      <div className="centered-div">
        <h1>Trabajo Fin de Grado Javier Rodríguez Gallardo</h1>

        <div className="buttons-container">
          <Button href="/" type="submit" variant="primary" className="nord-button">
            Acceder a la aplicación
          </Button>
        </div>
      </div>
    </div>
  );
}

export default OntologyPage;
