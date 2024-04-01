import React from 'react'

import { Helmet } from 'react-helmet'

import Table from '../components/table'
import Asset from '../components/Asset'

import './Home.css'
import { Route, Router, Routes } from 'react-router'

const Main = (props) => {
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
          <span className="logoText">Asset management database</span>
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
          >
            Asset
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
          <Routes>
            <Route path='' element={<Table/>}/>
            <Route path='/:id' element={<Asset/>}/>
          </Routes>
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
