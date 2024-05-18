import React, {useEffect} from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {setAdminStatus, setManagerStatus, setRolesFromJWT} from "./tools/globals";
import {useCookies} from "react-cookie";

const Root = () => {
    const [cookies] = useCookies(['JWT']);
    const jwtToken = cookies.JWT;

    if (jwtToken) {
        setRolesFromJWT(jwtToken);
    }
}

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <Root />
      <App />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
