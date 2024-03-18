import React from 'react'

import './login.css'

const Login = (props) => {
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
        <form className="loginForm">
          <span>Log in</span>
          <input type="text" placeholder="Email" className="input" />
          <input type="text" placeholder="Password" className="input" />
          <a
            href="https://example.com"
            target="_blank"
            rel="noreferrer noopener"
          >
            Forgot your password?
          </a>
          <button type="button" className="button">
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
