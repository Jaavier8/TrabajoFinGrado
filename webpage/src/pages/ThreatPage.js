import React, { useState } from "react";

// Bootstrap imports
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";

function ThreatPage(props) {

  //const [typeIndicators, setTypeIction] = useState(["domain", "ipv4-addr", "emaildomain", "hashes"]);
  const [domainCheck, setDomainCheck] = useState(false);
  const [ipv4Check, setIpv4Check] = useState(false);
  const [emaildomainCheck, setEmaildomainCheck] = useState(false);
  const [hashesCheck, setHashesCheck] = useState(false);

  const updateFormState = event => {
    const { name } = event.target;
    switch (name) {
      case "domainCheck":
        setDomainCheck(!domainCheck);
        break;
      case "ipv4Check":
        setIpv4Check(!ipv4Check);
        break;
      case "emaildomainCheck":
        setEmaildomainCheck(!emaildomainCheck);
        break;
      case "hashesCheck":
        setHashesCheck(!hashesCheck);
        break;
      default:
        return;
    }
  };

  const handleDownload = async (
    domain,
    ipv4,
    email,
    hash
  ) => {
    const request = {
      domain: domain,
      ipv4: ipv4,
      email: email,
      hash: hash
    };
    try {
      const res = await fetch(
        `http://localhost:8080/TFG/rest/indicator`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json; charset=UTF-8"
          },
          body: JSON.stringify(request)
        }
      );
      if (!res.ok) {
        alert("Hubo un fallo al descargar");
      }
    } catch (e) {
      // Nothing to do
    }
  };

  return (
    <div className="threat-page-container">
      <div className="centered-div">
        <h1>Página para análisis de amenazas</h1>
        <h4>Seleccione los indicatores en los que esté interesado y elija ver un ejemplo o descargarlos en formato JSON</h4>
        <div className="checkbox">
          <Form>
            <Form.Group controlId="formDomainCheckbox">
                <Form.Check
                  name="domainCheck"
                  type="checkbox"
                  label="Dominios maliciosos."
                  onChange={updateFormState}
                />
            </Form.Group>
            <Form.Group controlId="formIPCheckbox">
                <Form.Check
                  name="ipv4Check"
                  type="checkbox"
                  label="IPs maliciosas (incluidas IPs que corren certificados SSL maliciosos e IPs utilizadas para realizar ciberataques)."
                  onChange={updateFormState}
                />
            </Form.Group>
            <Form.Group controlId="formEmailCheckbox">
                <Form.Check
                  name="emaildomainCheck"
                  type="checkbox"
                  label="Dominios de correo utilizados para actos maliciosos."
                  onChange={updateFormState}
                />
            </Form.Group>
            <Form.Group controlId="formHashesCheckbox">
                <Form.Check
                  name="hashesCheck"
                  type="checkbox"
                  label="Hashes de ficheros (incluidos hashes de certificados SSL maliciosos y hashes de malware)."
                  onChange={updateFormState}
                />
            </Form.Group>
          </Form>
        </div>
        <div className="buttons-container">
          <Button type="submit" variant="primary" className="nord-button">
            Descargar indicadores en formato JSON
          </Button>
        </div>
      </div>
    </div>
  );
}

export default ThreatPage;
