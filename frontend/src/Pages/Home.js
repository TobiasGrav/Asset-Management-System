import React from 'react'

import { Helmet } from 'react-helmet'

import Table from '../components/AssetTable'
import Asset from '../components/Asset'
import { jwtDecode } from 'jwt-decode'

import './Home.css'
import { Route, Router, Routes, useNavigate } from 'react-router'
import { useCookies } from 'react-cookie'

const Main = ({children}) => {

  const [cookies, setCookie, deleteCookie] = useCookies();

  const navigate = useNavigate();

  console.log(jwtDecode(cookies.JWT));

  const asset = () => {
    navigate("/asset/");
  }

  const company = () => {
    navigate("/company/");
  }

  const site = () => {
    navigate("/site/");
  }

  return (
    <div className="body">
      <Helmet>
        <title>MNGSYS</title>
        <meta property="og:title" content="MNGSYS" />
      </Helmet>
      <div className="navbarContainer">
        <div className="logoContainer">
          <img
            alt="image"
            src={require('./resources/assetmanagementsystem.png')}
            className="logoImage"
          />
          <span className="logoText">Asset Management Database</span>
        </div>
        <div className="bookmarkContainer">
          
        </div>
        <div className="utilityContainer">
          <img
            alt="image"
            src={require('./resources/bell.png')}
            className="utilityIcon"
          />
          <img
            alt="image"
            src={require('./resources/frame.png')}
            className="utilityIcon"
          />
        </div>
      </div>
      <div className="mainContainer">
        <div className="leftContainer">
          <span className="navText">
            <span>Overview</span>
            <br></br>
          </span>
          <button
            type="button"
            className="navButton"
            onClick={asset}
          >
            Asset
          </button>
          <button
            type="button"
            className="navButton"
            onClick={company}
          >
            Company
          </button>
          <button
            type="button"
            className="navButton"
            onClick={site}
          >
            Site
          </button>
          <button
            type="button"
            className="navButton"
          >
            Customer
          </button>
          <button
            type="button"
            className="navButton"
          >
            Customer support
          </button>
        </div>
        <div className="rightContainer">
          {children}
        </div>
      </div>
      <div className="bottomContainer">
        <span className="asset-overview-text103">
          This is a product created for a bachelors at NTNU Ålesund
        </span>
      </div>
    </div>
  )
}

export default Main