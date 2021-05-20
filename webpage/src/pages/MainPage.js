import React from "react";

// Bootstrap imports
import Button from "react-bootstrap/Button";

function MainPage(props) {
  return (
    <div className="main-page-container">
      <div className="centered-div">
        <img
          className="logo"
          src={process.env.PUBLIC_URL + "logo.png"}
          alt="Shield from COVID logotype"
        />
        <h1>Trabajo Fin de Grado Javier Rodríguez Gallardo</h1>

        <p className="description">
          Página web creada para la descarga de indicadores de compromiso (IOC) y archivo OWL con datos introducidos.
        </p>

        <div className="buttons-container">
          <Button href="/download" type="submit" variant="primary" className="nord-button">
            Acceder a la aplicación
          </Button>
        </div>
      </div>
    </div>
  );
}

export default MainPage;
