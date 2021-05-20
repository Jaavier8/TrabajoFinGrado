import React, { useEffect } from "react";

import {
  BrowserRouter,
  Route,
  Switch,
} from "react-router-dom";

// Local imports
import MainPage from "../pages/MainPage";
import DownloadPage from "../pages/DownloadPage";

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
      </Switch>
    </BrowserRouter>
  );
}

export default Routes;
