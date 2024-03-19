import React, { useState } from 'react'

import './login.css'

const Login = (props) => {

  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  
  const login = () => {
    console.log(email);
    console.log(password);
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
