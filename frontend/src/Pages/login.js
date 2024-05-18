import React, { useState } from 'react';
import axios from 'axios';
import { useCookies } from 'react-cookie';
import { jwtDecode } from 'jwt-decode';
import './login.css';
import URL from '../tools/URL';


const Login = (props) => {

  const [cookies, setCookie, removeCookie] = useCookies();

  const [email, setEmail] = useState("Jons@cflow.no")
  const [password, setPassword] = useState("IDATA2024isbased")
  
  const login = () => {
    console.log(email);
    console.log(password);
    axios.post(`${URL.BACKEND}/api/authenticate`, {
      email: email,
      password: password
    })
    .then(response => {
      if(response.status == 200) {
        console.log("login successful")
        setCookie('JWT', response.data.response, {maxAge: 12*3600, path: '/'});
        window.location.href = '/asset/';
      }
      console.log(response);
    })
    .catch(error => {
      // Handle error
      console.log(error);
    });

  };

  const loginUser = () => {
    axios.post(`${URL.BACKEND}/api/authenticate`, {
      email: "Jend@ntnu.no",
      password: "12RulesForLife"
    })
    .then(response => {
      if(response.status == 200) {
        console.log("login successful")
        setCookie('JWT', response.data.response, {maxAge: 12*3600, path: '/'});
        window.location.href = '/asset/';
      }
      console.log(response);
    })
    .catch(error => {
      // Handle error
      console.log(error);
    });
  }
  return (
    <div className="login-container">
      <div className="login-box">
        <div className="logo-section">
          <img
            alt="Asset Management System Logo"
            src={require("./resources/assetmanagementsystem.png")}
            className="logo-image"
          />
          <h1 className="logo-text">Asset Management Database</h1>
        </div>
        <form onSubmit={login} className="login-form">
          <h2>Log in</h2>
          <input
            type="email"
            id="emailInput"
            placeholder="Email"
            className="input-field"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          <input
            type="password"
            id="passwordInput"
            placeholder="Password"
            className="input-field"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <a href="http://localhost:8080/recover" target="_blank" rel="noopener noreferrer" className="forgot-password-link">
            Forgot your password?
          </a>
          <button onClick={login} type="button" className="login-button">Login</button>
          <button onClick={loginUser} type="button" className="secondary-button">Login User</button>
        </form>
      </div>
    </div>
  );
};


  /*return (
    <div className="login-container">
      <div className="mainLogin">
        <div className="navbar">
          <img
            alt="image"
            src={require("./resources/assetmanagementsystem.png")}
            className="image"
          />
          <span className="logoText">asset Management Database</span>
        </div>
        <form onSubmit={login} className="loginForm">
          <span>Log in</span>
          <input type="text" id='emailInput' placeholder="Email" className="input" value={ email } onChange={ (e) => setEmail(e.target.value) } required/>
          <input type="password" id='passwordInput' placeholder="Password" className="input" value={ password } onChange={(e) => setPassword(e.target.value)} required/>
          <a
            href="localhost:8080/recover"
            target="_blank"
            rel="noreferrer noopener"
          >
            Forgot your password?
          </a>
          <button onClick={login} type='button' className="button">
            <span>
              <span>Login</span>
              <br></br>
            </span>
          </button>
          <button onClick={loginUser} type='button' className="button">
            <span>
              <span>Login user</span>
              <br></br>
            </span>
          </button>
        </form>
      </div>
    </div>
  )*/


export default Login

