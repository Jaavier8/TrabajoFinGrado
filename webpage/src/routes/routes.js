import React, { useEffect } from "react";

import {
  BrowserRouter,
  Route,
  Switch,
} from "react-router-dom";

// Local imports
import MainPage from "../pages/MainPage";
import DownloadPage from "../pages/DownloadPage";
import ThreatPage from "../pages/ThreatPage";
import OntologyPage from "../pages/OntologyPage";

function Routes(props) {

  return (
    <BrowserRouter>
      <Switch>
        <Route path="/" exact>
          <MainPage />
        </Route>
        <Route path="/download" >
          <DownloadPage />
        </Route>
        <Route path="/threat" >
          <ThreatPage />
        </Route>
        <Route path="/ontology" >
          <OntologyPage />
        </Route>
      </Switch>
    </BrowserRouter>
  );
}

export default Routes;
