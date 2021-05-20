import React from "react";
import { Navbar, Nav, Button } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";

function ActionBar(props) {
  return (
    <Navbar bg="dark" variant="dark" className="navbar-container">
      <LinkContainer to="/download">
        <Navbar.Brand>
          <img
            alt=""
            src="/logo.png"
            width="30"
            height="30"
            className="d-inline-block align-top"
          />{" "}
          TFG
        </Navbar.Brand>
      </LinkContainer>
      <Nav className="mr-auto">
        <LinkContainer to="/threat">
          <Nav.Link>Análisis de amenzas</Nav.Link>
        </LinkContainer>
        <LinkContainer to="/ontology">
          <Nav.Link>Datos ontología</Nav.Link>
        </LinkContainer>
      </Nav>
    </Navbar>
  );
}

export default ActionBar;
