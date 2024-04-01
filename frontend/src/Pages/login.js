import React, { useState } from 'react';
import axios from 'axios';

import './login.css';

const Login = (props) => {

  const [email, setEmail] = useState("Jons@ntnu.no")
  const [password, setPassword] = useState("IDATA2024isbased")
  
  const login = () => {
    console.log(email);
    console.log(password);
    axios.post('http://localhost:8080/api/authenticate', {
      email: email,
      password: password
    })
    .then(response => {
      if(response.status == 200) {
        console.log("login successful")
        axios.get("http://localhost:8080/", {
          headers: {
            Authorization: 'Bearer ' + response.data.response,
            Accept: "application/json",
            'Content-Type': "application/json"
          }
        })
        .then(response => {
          console.log(response.data);
        })
        .catch(error => {
          console.error('Error fetching data:', error);
        });
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
      <div className="mainLogin">
        <div className="navbar">
          <img
            alt="image"
            src={require("./resources/assetmanagementsystem.png")}
            className="image"
          />
          <span className="logoText">Asset Management Database</span>
        </div>
        <form onSubmit={login} className="loginForm">
          <span>Log in</span>
          <input type="text" id='emailInput' placeholder="Email" className="input" value={ email } onChange={ (e) => setEmail(e.target.value) } required/>
          <input type="text" id='passwordInput' placeholder="Password" className="input" value={ password } onChange={(e) => setPassword(e.target.value)} required/>
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
        </form>
      </div>
    </div>
  )
}

export default Login
